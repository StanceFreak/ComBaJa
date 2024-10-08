package com.stancefreak.combaja.utils

sealed class NavRoute(val route: String) {
    data object Splash: NavRoute(route = "splash")
    data object Home: NavRoute(route = "home")
}