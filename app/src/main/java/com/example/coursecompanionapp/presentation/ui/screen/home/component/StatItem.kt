package com.example.coursecompanionapp.presentation.ui.screen.home.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import com.example.coursecompanionapp.R

@Composable
fun StatItem(
    title: String,
    progress: Float,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(vertical = dimensionResource(R.dimen.padding_small))) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(title)
            Text(value)
        }
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(R.dimen.padding_small))
                .clip(RoundedCornerShape(dimensionResource(R.dimen.card_radius)))
        )
    }
}