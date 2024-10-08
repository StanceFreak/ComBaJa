package com.stancefreak.combaja.view.home

import android.graphics.Paint.Align
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.fontscaling.MathUtils.lerp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.stancefreak.combaja.R
import com.stancefreak.combaja.data.response.Result
import kotlin.math.absoluteValue

@Composable
fun HomePopularMovies(
    movie: Result,
    pageOffset: Float,
    pageIndex: Int,
) {
    Card(
        modifier = Modifier
            .graphicsLayer {
                alpha = lerp(
                    start = 0.7f,
                    stop = 1f,
                    fraction = 1f - pageOffset.absoluteValue.coerceIn(0f, 1f),
                )

                cameraDistance = 8 * density
                rotationY = lerp(
                    start = 0f,
                    stop = 0f,
                    fraction = pageOffset.coerceIn(-1f, 1f),
                )

                lerp(
                    start = 0.8f,
                    stop = 1f,
                    fraction = 1f - pageOffset.absoluteValue.coerceIn(0f, 1f),
                ).also { scale ->
                    scaleX = scale
                    scaleY = scale
                }
            }
            .padding(10.dp)
            .wrapContentSize(),
        shape = MaterialTheme.shapes.medium,
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://image.tmdb.org/t/p/w500${ movie.posterPath }")
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.Fit,
                contentDescription = null,
            )
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .size(16.dp)
                    .clip(CircleShape)
                    .background(colorResource(id = R.color.black))
                    .align(Alignment.TopEnd),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = (pageIndex + 1).toString(),
                    color = colorResource(id = R.color.white),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 10.sp
                )
            }
            androidx.compose.animation.AnimatedVisibility(
                modifier = Modifier
                    .align(Alignment.BottomCenter),
                visible = true,
                enter = slideInVertically { it },
                exit = slideOutVertically { it }
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(colorResource(id = R.color.black))
                        .padding(vertical = 5.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Pesan Sekarang",
                        color = colorResource(id = R.color.white),
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            }
        }
    }
}