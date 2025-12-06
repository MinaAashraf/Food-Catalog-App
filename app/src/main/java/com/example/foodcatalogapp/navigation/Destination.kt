package com.example.foodcatalogapp.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.catalog.R

enum class Destination(
    val route: String,
    @StringRes val label: Int,
    @DrawableRes val icon: Int
) {
    CATALOG(
        route = "catalog",
        label = R.string.catalog_label,
        icon = R.drawable.catalog_icon
    ),
    ORDERS(
        route = "orders",
        label = R.string.orders_label,
        icon = R.drawable.orders_icon
    ),
    MENU(
        route = "menu",
        label = R.string.menu_label,
        icon = R.drawable.menu_icon
    ),
    SETTINGS(
        route = "settings",
        label = R.string.settings_label,
        icon = R.drawable.settings_icon
    )
}
