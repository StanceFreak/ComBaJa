package com.stancefreak.combaja.view.home

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.stancefreak.combaja.R
import com.stancefreak.combaja.data.response.Genre

@Composable
fun HomePopularGenres (
    genre: Genre,
    genreId: Int,
    viewModel: HomeViewModel,
    onSelectId: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .wrapContentSize()
            .padding(10.dp)
            .shadow(elevation = 0.dp)
            .selectable(
                selected = genreId == genre.id,
                onClick = {
                    onSelectId(
                        if (genreId != genre.id) {
                            genre.id
                        } else {
                            -1
                        }
                    )
                    viewModel.fetchMoviesByGenre(genre.id)
                    Log.d("test genre", genreId.toString())
                }
            ),
        colors = CardDefaults.cardColors(
            containerColor = if (genreId == genre.id) Color.Red else Color.Transparent
        ),
        border = BorderStroke(width = 1.dp, color = colorResource(id = R.color.midnight_blue)),
        shape = MaterialTheme.shapes.medium,
    ) {
        Text(
            modifier = Modifier
                .padding(vertical = 5.dp, horizontal = 10.dp),
            text = genre.name,
            color = colorResource(id = R.color.midnight_blue),
            fontWeight = FontWeight.SemiBold,
        )
    }
}