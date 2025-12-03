package com.example.foodcatalogapp.catalog.data.repository

import com.example.catalog.core.cache.database.dao.CatalogDAO
import com.example.catalog.core.common.ErrorCatalog
import com.example.catalog.core.common.ResultState
import com.example.foodcatalogapp.catalog.data.api.CatalogService
import com.example.foodcatalogapp.catalog.data.mapper.toCategoryDomainModels
import com.example.foodcatalogapp.catalog.data.mapper.toCategoryEntities
import com.example.foodcatalogapp.catalog.data.mapper.toProductDomainModels
import com.example.foodcatalogapp.catalog.data.mapper.toProductEntities
import com.example.foodcatalogapp.catalog.domain.model.ProductCategoryModel
import com.example.foodcatalogapp.catalog.domain.model.ProductModel
import com.example.foodcatalogapp.catalog.domain.repository.CatalogRepository

class CatalogRepositoryImpl(
    private val catalogService: CatalogService,
    private val catalogDao: CatalogDAO
) : CatalogRepository {
    override suspend fun getCategories(): ResultState<List<ProductCategoryModel>> {
        (catalogService.getCategories() as? ResultState.Success)?.body?.let { categories ->
            catalogDao.insertCategories(categories.toCategoryEntities())
        }
        return catalogDao.getCategories().let { categories ->
            if (categories.isNotEmpty()) {
                ResultState.Success(categories.toCategoryDomainModels())
            } else {
                ResultState.Error(ErrorCatalog.NoDataFoundError)
            }
        }
    }

    override suspend fun getProducts(): ResultState<List<ProductModel>> {
        (catalogService.getProducts() as? ResultState.Success)?.body?.let { products ->
            catalogDao.insertProducts(products.toProductEntities())
        }
        return catalogDao.getProducts().let { products ->
            if (products.isNotEmpty()) {
                ResultState.Success(products.toProductDomainModels())
            } else {
                ResultState.Error(ErrorCatalog.NoDataFoundError)
            }
        }
    }

    override suspend fun filterProductsByName(productName: String): ResultState<List<ProductModel>> {
        return catalogDao.getProductByName(productName).let { products ->
            if (products.isNotEmpty()) {
                ResultState.Success(products.toProductDomainModels())
            } else {
                ResultState.Error(ErrorCatalog.NoDataFoundError)
            }
        }
    }
}