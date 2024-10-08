package com.stancefreak.combaja.view.splash

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.window.SplashScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.stancefreak.combaja.R
import com.stancefreak.combaja.utils.NavRoute
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_logo_test),
            contentDescription = "logo app"
        )
        Text(text = "Nama App")
    }
    LaunchedEffect(true) {
        delay(3000L)
        navController.apply {
            popBackStack()
            navigate(NavRoute.Home.route)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashPreview() {
    val navController = rememberNavController()
    SplashScreen(navController)
}