package com.stancefreak.combaja.utils

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.stancefreak.combaja.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseTopBar(
    title: String,
    navigateBack: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        fontSize = 18.sp,
                        text = title,
                        color = colorResource(id = R.color.black),
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_back),
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { innerPad ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPad)
        ) {
            content()
        }
    }
}