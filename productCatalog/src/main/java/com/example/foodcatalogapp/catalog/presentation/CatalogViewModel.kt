package com.example.foodcatalogapp.catalog.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catalog.core.common.ErrorCatalog
import com.example.catalog.core.common.onError
import com.example.catalog.core.common.onSuccess
import com.example.foodcatalogapp.catalog.R
import com.example.foodcatalogapp.catalog.domain.model.CartDetailsModel
import com.example.foodcatalogapp.catalog.domain.usecase.AddToCartUseCase
import com.example.foodcatalogapp.catalog.domain.usecase.ClearCartUseCase
import com.example.foodcatalogapp.catalog.domain.usecase.FilterProductsByNameUseCase
import com.example.foodcatalogapp.catalog.domain.usecase.GetCatalogsUseCase
import com.example.foodcatalogapp.catalog.domain.usecase.ReadCartDetailsUseCase
import com.example.foodcatalogapp.catalog.presentation.event.CatalogUiEvent
import com.example.foodcatalogapp.catalog.presentation.mapper.toCartPresentationModel
import com.example.foodcatalogapp.catalog.presentation.mapper.toCatalogPresentationModel
import com.example.foodcatalogapp.catalog.presentation.model.CartDetailsPresentationModel
import com.example.foodcatalogapp.catalog.presentation.state.CatalogResult
import com.example.foodcatalogapp.catalog.presentation.state.CatalogUiState
import com.example.foodcatalogapp.catalog.presentation.utils.toTwoDigitDecimals
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class CatalogViewModel(
    private val getCatalogsUseCase: GetCatalogsUseCase,
    private val filterProductsByNameUseCase: FilterProductsByNameUseCase,
    private val readCartDetailsUseCase: ReadCartDetailsUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val clearCartUseCase: ClearCartUseCase
) : ViewModel() {

    private val _catalogUiState = MutableStateFlow(CatalogUiState())
    val catalogUiState = _catalogUiState.asStateFlow()

    var cartDetailsState: StateFlow<CartDetailsPresentationModel> = readCartDetails()

    init {
        getCatalogs()
        observeSearchQuery()
    }

    fun onEvent(event: CatalogUiEvent) {
        when (event) {
            is CatalogUiEvent.OnSearchQuery -> {
                _catalogUiState.update {
                    it.copy(searchQuery = event.query)
                }
            }

            is CatalogUiEvent.OnCategoryTabClick -> {
                _catalogUiState.update {
                    it.copy(selectedTabIndex = event.tabIndex)
                }
            }

            is CatalogUiEvent.OnProductClick -> {
                addToCart(
                    productId = event.productId,
                    productPrice = event.productPrice
                )
            }

            is CatalogUiEvent.OnCartButtonClick -> {
                clearCart()
            }
        }
    }

    private fun getCatalogs() {
        viewModelScope.launch {
            updateCatalogResult(CatalogResult.Loading)
            getCatalogsUseCase()
                .onSuccess { catalogModels ->
                    updateCatalogResult(
                        CatalogResult.CatalogSuccess(
                            catalogModels.toCatalogPresentationModel()
                        )
                    )
                }.onError { error ->
                    updateCatalogResult(
                        CatalogResult.Error(getErrorMessage(error))
                    )
                }
        }
    }

    private fun searchProductByName(productName: String) {
        viewModelScope.launch {
            filterProductsByNameUseCase(productName).onSuccess { catalogs ->
                _catalogUiState.update {
                    it.copy(
                        catalogResult = CatalogResult.CatalogSuccess(
                            catalogs.toCatalogPresentationModel()
                        ),
                        selectedTabIndex = 0
                    )
                }
            }.onError { error ->
                updateCatalogResult(
                    CatalogResult.Error(getErrorMessage(error))
                )
            }
        }
    }

    private fun observeSearchQuery() {
        viewModelScope.launch {
            _catalogUiState
                .mapNotNull { it.searchQuery }
                .debounce(500L)
                .distinctUntilChanged()
                .collectLatest { query ->
                    if (query.isNotEmpty()) {
                        searchProductByName(query)
                    } else {
                        getCatalogs()
                    }
                }
        }
    }

    private fun readCartDetails(): StateFlow<CartDetailsPresentationModel> {
        return readCartDetailsUseCase()
            .map { cartDetailsModel ->
                cartDetailsModel.toCartPresentationModel()
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = CartDetailsPresentationModel()
            )
    }

    private fun addToCart(productId: Int, productPrice: String) {
        cartDetailsState.value.let { currentCart ->
            if (productId !in currentCart.productIds) {
                viewModelScope.launch {
                    addToCartUseCase(
                        CartDetailsModel(
                            productIds = currentCart.productIds + productId,
                            totalPrice = sumPrices(
                                currentCart.totalPrice,
                                productPrice
                            )
                        )
                    )
                }
            }
        }
    }

    private fun clearCart() {
        viewModelScope.launch {
            clearCartUseCase
        }
    }

    private fun updateCatalogResult(newResult: CatalogResult) {
        _catalogUiState.update {
            it.copy(catalogResult = newResult)
        }
    }

    private fun sumPrices(price1: String, price2: String): String {
        val p1 = price1.toDoubleOrNull() ?: 0.0
        val p2 = price2.toDoubleOrNull() ?: 0.0
        return (p1 + p2).toTwoDigitDecimals()
    }

    private fun getErrorMessage(error: ErrorCatalog): Int {
        return if (error is ErrorCatalog.NoDataFoundError) {
            R.string.no_data_found_error
        } else {
            R.string.generic_error
        }
    }
}