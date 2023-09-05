package com.example.thenewmoviedbcompose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FilterCard(text: String, isActive: Boolean = false, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .background(
                color = if (isActive) MaterialTheme.colorScheme.primary else Color.Transparent,
                shape = RoundedCornerShape(22.dp)
            )
            .padding(vertical = 10.dp, horizontal = 20.dp)
            .clickable(onClick = onClick),
    ) {
        Text(
            text = text, style = TextStyle(
                color = if (isActive) MaterialTheme.colorScheme.tertiary else Color.Black,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
fun FilterCardSpacer() {
    Box(
        Modifier.padding(3.dp)
    ) {
        Box(
            modifier = Modifier
                .width(1.dp)
                .height(20.dp)
                .background(color = Color.Gray)
        )
    }
}


@Preview
@Composable
fun FilterCardPreview() {
    FilterCard(text = "Popular", isActive = true) {}
}