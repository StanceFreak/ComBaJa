package com.stancefreak.combaja.view.seeall

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.stancefreak.combaja.R
import com.stancefreak.combaja.utils.BaseCardList
import com.stancefreak.combaja.utils.BaseTopBar
import com.stancefreak.combaja.view.home.HomeViewModel

@Composable
fun SeeAllScreen(
    navController: NavController,
    viewModel: HomeViewModel
) {
    val genreList by viewModel.genreList.observeAsState()
    val movieList by viewModel.movieList.observeAsState()
    val tabItem = listOf("SEDANG TAYANG", "AKAN DATANG")
    val pagerState = rememberPagerState {
        tabItem.size
    }
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val cardModifier = Modifier
        .padding(10.dp)
        .wrapContentSize()
    
    LaunchedEffect(key1 = selectedTabIndex) {
        pagerState.animateScrollToPage(selectedTabIndex)
    }

//    LaunchedEffect(key1 = pagerState.currentPage) {
//        selectedTabIndex = pagerState.currentPage
//    }

    LaunchedEffect(key1 = pagerState.currentPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress) {
            selectedTabIndex = pagerState.currentPage
        }
    }
    
    BaseTopBar(
        title = "Film Bioskop",
        navigateBack = { navController.popBackStack() }
    ) {
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabItem.forEachIndexed { index, item ->
                Tab(
                    selected = index == selectedTabIndex,
                    onClick = { selectedTabIndex = index },
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 15.dp),
                        text = item,
                        color = colorResource(id = R.color.black),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) { index ->
            LazyVerticalGrid(
                columns = GridCells.Fixed(2)
            ) {
                items(movieList!!.results) { movie ->
                    BaseCardList(
                        modifier = cardModifier,
                        posterPath = movie.posterPath) {
                    }
                }
            }
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = tabItem[index])
            }
        }
    }
}