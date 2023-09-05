package com.example.thenewmoviedbcompose.ui.popups

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.thenewmoviedbcompose.R

@Composable
fun ErrorPopup(
    message: String?,
    onDismiss: () -> Unit
) {
    message?.let {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.baseline_report_gmailerrorred_24), // Replace with your error icon
                        modifier = Modifier.size(34.dp),
                        contentDescription = "Error Icon",
                        tint = MaterialTheme.colorScheme.error
                    )
                    Text(text = "Error", fontWeight = FontWeight.Bold)
                }
            },
            text = { Text(text = message) },
            confirmButton = {
                Button(
                    onClick = { onDismiss() },
                    colors = ButtonDefaults.buttonColors(
                        MaterialTheme.colorScheme.error
                    )
                ) {
                    Text(text = "OK", color = Color.White)
                }
            }
        )
    }
}

@Preview
@Composable
fun ErrorPopupPreview(){
    ErrorPopup( "message"){
    }
}