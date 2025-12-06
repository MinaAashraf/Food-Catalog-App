# Food Catalog App

A modern Android application for browsing, searching, and managing food product catalogs. Built with Kotlin, Jetpack Compose, modular architecture, and Koin for dependency injection.

## Features
- Browse food categories and products
- Search products by name
- Add products to cart
- View and manage cart details
- Modularized codebase (app, core, productCatalog modules)
- Modern UI with Jetpack Compose
- Dependency injection with Koin
- Room database and network support

## Project Structure
- **app/**: Main application module, entry point, navigation, and theming
- **core/**: Shared core logic, UI components, data, and network layers
- **productCatalog/**: Catalog domain, presentation, and use cases

## Tech Stack
- Kotlin, Jetpack Compose, material3
- Koin (DI), Room (local DB), Ktor (network), DataStore (preferences)
- Coroutines, Flow, Modular Architecture
- Modular Gradle setup (KTS)
