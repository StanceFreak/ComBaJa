package com.stancefreak.combaja.view.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.stancefreak.combaja.R
import com.stancefreak.combaja.utils.NavRoute
import com.stancefreak.combaja.view.home.HomeScreen
import com.stancefreak.combaja.view.home.HomeViewModel
import com.stancefreak.combaja.view.seeall.SeeAllScreen
import com.stancefreak.combaja.view.splash.SplashScreen

class NavigationActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val homeViewModel: HomeViewModel by viewModels()
            NavHost(navController = navController, startDestination = NavRoute.Splash.route) {
                composable(NavRoute.Splash.route) {
                    SplashScreen(navController = navController)
                }
                composable(NavRoute.Home.route) {
                    HomeScreen(navController, homeViewModel)
                }
                composable(NavRoute.SeeAll.route) {
                    SeeAllScreen(navController, homeViewModel)
                }
            }
        }
    }

    fun initViews() {

    }
}