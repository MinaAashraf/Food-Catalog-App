package com.example.foodcatalogapp.catalog.data.cache

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.foodcatalogapp.catalog.domain.model.CartDetailsModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CartDetailsDataStoreImpl(context: Context) : CartDetailsDataStore {
    private val dataStore: DataStore<Preferences> =
        context.dataStorePreferences

    companion object {
        val PRODUCT_IDS_KEY = stringPreferencesKey("product_ids")
        val TOTAL_PRICE_KEY = stringPreferencesKey("total_price")
        private const val CART_DATA_STORE_NAME = "cart_details_preferences"
        private val Context.dataStorePreferences by preferencesDataStore(
            name = CART_DATA_STORE_NAME
        )
    }

    override suspend fun saveCartDetails(cartDetails: CartDetailsModel) {
        dataStore.edit { preferences ->
            val idsString = cartDetails.productIds?.joinToString(",") ?: ""
            preferences[PRODUCT_IDS_KEY] = idsString
            preferences[TOTAL_PRICE_KEY] = cartDetails.totalPrice
        }
    }

    override fun getCartDetails(): Flow<CartDetailsModel> {
        return dataStore.data.map { preferences ->
            val idsString = preferences[PRODUCT_IDS_KEY] ?: ""
            val productIds =
                if (idsString.isNotEmpty()) {
                    idsString.split(",")
                        .mapNotNull { it.toIntOrNull() }
                } else {
                    emptyList()
                }

            CartDetailsModel(
                productIds = productIds,
                totalPrice = preferences[TOTAL_PRICE_KEY] ?: "0"
            )
        }
    }

    override suspend fun clearCartDetails() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}