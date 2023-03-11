package xyz.shkurotopia.knote.presentation.notelistview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle

@Composable
fun EmptyScreenText(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "No Notes",
            style = TextStyle(
                color = MaterialTheme.colorScheme.surfaceVariant,
                fontStyle = MaterialTheme.typography.titleLarge.fontStyle,
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                fontFamily = MaterialTheme.typography.titleLarge.fontFamily,
                fontWeight = MaterialTheme.typography.titleLarge.fontWeight
            )
        )

        Text(
            text = "Tap Floating Button to add Note",
            style = TextStyle(
            color = MaterialTheme.colorScheme.surfaceVariant,
            fontStyle = MaterialTheme.typography.titleMedium.fontStyle,
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
            fontFamily = MaterialTheme.typography.titleMedium.fontFamily,
            fontWeight = MaterialTheme.typography.titleMedium.fontWeight
        )
        )
    }
}