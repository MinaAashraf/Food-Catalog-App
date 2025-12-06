package com.example.foodcatalogapp.catalog.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_EXPANDED_LOWER_BOUND
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_MEDIUM_LOWER_BOUND
import coil3.compose.AsyncImage
import com.example.catalog.core.uicomponents.composables.ErrorScreen
import com.example.catalog.core.uicomponents.composables.LoadingScreen
import com.example.catalog.core.uicomponents.theme.FoodCatalogAppTheme
import com.example.foodcatalogapp.catalog.R
import com.example.foodcatalogapp.catalog.presentation.event.CatalogUiEvent
import com.example.foodcatalogapp.catalog.presentation.model.CartDetailsPresentationModel
import com.example.foodcatalogapp.catalog.presentation.model.CatalogPresentationModel
import com.example.foodcatalogapp.catalog.presentation.model.ProductPresentationModel
import com.example.foodcatalogapp.catalog.presentation.state.CatalogResult
import com.example.foodcatalogapp.catalog.presentation.state.CatalogUiState
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProductCatalogScreen() {
    val viewModel: CatalogViewModel = koinViewModel()
    val catalogUiState by viewModel.catalogUiState.collectAsStateWithLifecycle()
    val cartDetails by viewModel.cartDetailsState.collectAsStateWithLifecycle()

    Column(modifier = Modifier.imePadding()) {
        SearchBar(
            modifier = Modifier.padding(16.dp),
            searchQuery = catalogUiState.searchQuery.orEmpty(),
            onQueryChange = {
                viewModel.onEvent(
                    CatalogUiEvent.OnSearchQuery(it)
                )
            }
        )

        when (val result = catalogUiState.catalogResult) {
            is CatalogResult.CatalogSuccess -> {
                ProductCatalogContent(
                    catalogState = catalogUiState,
                    cartDetails = cartDetails,
                    onEvent = viewModel::onEvent
                )
            }

            is CatalogResult.Error -> {
                ErrorScreen(
                    stringResource(result.message)
                )
            }

            is CatalogResult.Loading -> {
                LoadingScreen()
            }
        }
    }
}

@Composable
private fun ProductCatalogContent(
    catalogState: CatalogUiState,
    cartDetails: CartDetailsPresentationModel,
    onEvent: (CatalogUiEvent) -> Unit
) {
    val catalogs = (catalogState.catalogResult as CatalogResult.CatalogSuccess).catalogs

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        PrimaryScrollableTabRow(
            modifier = Modifier.fillMaxWidth(),
            containerColor = MaterialTheme.colorScheme.onPrimary,
            selectedTabIndex = catalogState.selectedTabIndex,
            edgePadding = 8.dp,
            tabs = {
                CategoryTabs(
                    catalogs = catalogs,
                    selectedIndex = catalogState.selectedTabIndex,
                    onTabClick = { index ->
                        onEvent(CatalogUiEvent.OnCategoryTabClick(index))
                    }
                )
            }
        )
        if (catalogs.isNotEmpty()) {
            ProductsLazyGrid(
                modifier = Modifier
                    .weight(1f)
                    .background(Color.LightGray),
                products = catalogs[catalogState.selectedTabIndex].products,
                selectedTabIndex = catalogState.selectedTabIndex,
                onProductClick = { productId, productPrice ->
                    onEvent(
                        CatalogUiEvent.OnProductClick(
                            productId = productId,
                            productPrice = productPrice
                        )
                    )
                }
            )
        }
        Button(
            modifier = Modifier
                .padding(16.dp)
                .height(48.dp),
            shape = RoundedCornerShape(6.dp),
            onClick = {

            }
        ) {
            CartDetailsRow(cartDetails)
        }
    }
}

@Composable
private fun SearchBar(
    modifier: Modifier = Modifier,
    searchQuery: String,
    onQueryChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.onPrimary),
        colors = TextFieldDefaults.colors().copy(
            focusedContainerColor = MaterialTheme.colorScheme.onPrimary,
            unfocusedContainerColor = MaterialTheme.colorScheme.onPrimary
        ),
        shape = RoundedCornerShape(8.dp),
        placeholder = { Text(stringResource(R.string.search_products_label)) },
        value = searchQuery,
        onValueChange = onQueryChange,
        leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.search_icon),
                contentDescription = null
            )
        },
    )
}

@Composable
private fun CategoryTabs(
    catalogs: List<CatalogPresentationModel>,
    selectedIndex: Int,
    onTabClick: (Int) -> Unit
) {
    catalogs.forEachIndexed { index, catalog ->
        Tab(
            selected = selectedIndex == index,
            text = { Text(catalog.categoryName) },
            onClick = { onTabClick(index) }
        )
    }
}

@Composable
private fun ProductsLazyGrid(
    modifier: Modifier,
    products: List<ProductPresentationModel>,
    selectedTabIndex: Int,
    onProductClick: (Int, String) -> Unit
) {

    val gridState = rememberLazyGridState(
        initialFirstVisibleItemIndex = 0
    )
    LaunchedEffect(selectedTabIndex) {
        gridState.animateScrollToItem(0)
    }

    LazyVerticalGrid(
        modifier = modifier.fillMaxWidth(),
        state = gridState,
        columns = GridCells.Fixed(calculateAdaptiveGridColumnCount()),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            items = products,
            key = { product -> product.id }
        ) { product ->
            ProductElement(
                product = product,
                onProductClick = { onProductClick(product.id, product.price) }
            )
        }
    }
}

@Composable
private fun ProductElement(
    product: ProductPresentationModel,
    onProductClick: () -> Unit
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp

    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors()
            .copy(containerColor = MaterialTheme.colorScheme.onPrimary),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
        onClick = onProductClick
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height((screenHeight * 0.25).dp),
                model = product.image,
                error = painterResource(R.drawable.place_holder),
                contentScale = ContentScale.Crop,
                clipToBounds = true,
                contentDescription = null
            )
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(product.name)
                product.description?.let {
                    Text(
                        text = it,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
                Text("${product.price} ${stringResource(R.string.unit)}")
            }
        }
    }
}

@Composable
private fun CartDetailsRow(cartDetails: CartDetailsPresentationModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.onPrimary)
                .size(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = cartDetails.productIds.size.toString(),
                style = TextStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            )
        }
        Text(
            text = stringResource(R.string.view_order_text),
            style = TextStyle(fontWeight = FontWeight.Bold)
        )
        Spacer(Modifier.weight(1f))
        Text(
            text = "${cartDetails.totalPrice} ${stringResource(R.string.unit)}",
            style = TextStyle(fontWeight = FontWeight.Bold)
        )
        Icon(
            painter = painterResource(R.drawable.right_arrow_icon),
            contentDescription = null
        )
    }
}

@Composable
private fun calculateAdaptiveGridColumnCount(): Int {
    val sizeClass = currentWindowAdaptiveInfo().windowSizeClass
    return remember(sizeClass) {
        when {
            (sizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_EXPANDED_LOWER_BOUND)) -> 6
            (sizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_MEDIUM_LOWER_BOUND)) -> 4
            else -> 2
        }
    }
}

@Preview
@Composable
private fun ProductCatalogContentPreview() {
    FoodCatalogAppTheme {
        ProductCatalogContent(
            catalogState = CatalogUiState(
                CatalogResult.CatalogSuccess(
                    catalogs = listOf(
                        CatalogPresentationModel(
                            categoryId = 1,
                            categoryName = "Breakfast",
                            products = listOf(
                                ProductPresentationModel(
                                    name = "BreakFast1",
                                    description = "Amazing BreakFast",
                                    price = "50.788",
                                    image = ""
                                ),
                                ProductPresentationModel(
                                    name = "BreakFast2",
                                    description = "Amazing BreakFast",
                                    price = "50.788",
                                    image = ""
                                ),
                                ProductPresentationModel(
                                    name = "BreakFast3",
                                    description = "Amazing BreakFast",
                                    price = "50.788",
                                    image = ""
                                )
                            )
                        )
                    )
                )
            ),
            cartDetails = CartDetailsPresentationModel(),
            onEvent = {}
        )
    }
}

@Preview
@Composable
private fun SearchBarPreview() {
    FoodCatalogAppTheme {
        SearchBar(
            searchQuery = "",
            onQueryChange = {}
        )
    }
}