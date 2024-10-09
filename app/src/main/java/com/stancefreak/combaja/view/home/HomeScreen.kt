package com.stancefreak.combaja.view.home

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.stancefreak.combaja.R
import com.stancefreak.combaja.utils.InlineContent
import com.stancefreak.combaja.utils.NavRoute
import com.stancefreak.combaja.view.splash.SplashScreen

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel
) {
    var selectedGenre by remember { mutableIntStateOf(-1) }
    val genreList by viewModel.genreList.observeAsState()
    val movieList by viewModel.movieList.observeAsState()
    val forwardText = buildAnnotatedString {
        append("Lihat Semua")
        appendInlineContent("trendingForward", "[icon]")
    }
    LaunchedEffect(Unit) {
        viewModel.apply {
            fetchPopularGenres()
            fetchPopularMovies()
        }
    }
    Scaffold(
        bottomBar = {
            BottomAppBar(
                containerColor = colorResource(id = R.color.red),
                contentColor =colorResource(id = R.color.purple_200),
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = "Bottom app bar",
                )
            }
        },
    ) { innerPad ->
        LazyColumn (
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPad)
        ) {
            item {
                Row (
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        fontSize = 18.sp,
                        text = "Trending Saat Ini",
                        color = colorResource(id = R.color.black),
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(Color.Red))
                    Text(
                        modifier = Modifier
                            .clickable {
                                navController.navigate(NavRoute.SeeAll.route)
                            },
                        text = forwardText,
                        inlineContent = InlineContent(
                            Icons.AutoMirrored.Filled.KeyboardArrowRight
                        ),
                        color = colorResource(id = R.color.midnight_blue),
                        fontWeight = FontWeight.SemiBold,
                    )
                }

                genreList?.let { item ->
                    LazyRow {
                        items(item.genres) { genre ->
                            HomePopularGenres(genre, selectedGenre, viewModel) { genreId ->
                                selectedGenre = genreId
                            }
                        }
                    }
                }
                movieList?.let { movie ->
                    // repeating list like tix.id app
                    val startIndex = Int.MAX_VALUE / 2
                    val pagerState = rememberPagerState(
                        initialPage = startIndex,
                        pageCount = { Int.MAX_VALUE }
                    )
                    LaunchedEffect(pagerState) {
                        snapshotFlow { pagerState.currentPage }.collect { page ->
                            Log.d("Page change", "Page changed to ${(page - startIndex).floorMod(movie.results.size)}")
                        }
                    }
                    PagerLayout {
                        HorizontalPager(
                            contentPadding = PaddingValues(horizontal = 95.dp),
                            modifier = Modifier
                                .weight(1f, true)
                                .fillMaxWidth(),
                            state = pagerState,
                            pageSpacing = (-20).dp
                        ) { index ->
                            val pageIndex = (index - startIndex).floorMod(movie.results.size)
                            val pageOffset = pagerState.currentPage - index + pagerState.currentPageOffsetFraction
                            HomePopularMovies(movie.results[pageIndex], pageOffset, pageIndex)
                        }
                    }
                }
            }
        }
    }
}

private fun Int.floorMod(other: Int): Int = when (other) {
    0 -> this
    else -> this - floorDiv(other) * other
}

@Composable
fun PagerLayout(content: @Composable RowScope.() -> Unit) {
    Row {
        content()
    }
}

@Preview(showBackground = true,)
@Composable
fun HomePreview() {
    val navController = rememberNavController()
    val viewModel = HomeViewModel()
    HomeScreen(navController, viewModel)
}