package xyz.shkurotopia.knote.presentation.notelistview

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Note
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import xyz.shkurotopia.knote.data.Note
import java.time.Instant
import java.time.format.DateTimeFormatter

@Composable
fun NoteItem(
    note: Note,
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit
) {
    val ico: ImageVector
    val date = DateTimeFormatter.ISO_LOCAL_DATE.format(Instant.ofEpochSecond(note.timestamp))

    @Suppress("LiftReturnOrAssignment")
    when(note.category) {
        0 -> {
            ico = Icons.Default.Check
        }
        1 -> {
            ico = Icons.Default.Favorite
        }
        2 -> {
            ico = Icons.Default.Note
        }
        else -> {
            ico = Icons.Default.Error
        }
    }


    Row(modifier = Modifier.padding(all = 8.dp)) {
        Image(
            imageVector = ico,
            contentDescription = "Category Icon",
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colorScheme.secondary)
        )
        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(text = note.title, color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.bodyLarge, fontSize = MaterialTheme.typography.bodyLarge.fontSize, maxLines = 1)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = date.toString(), color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.bodySmall, fontSize = MaterialTheme.typography.bodySmall.fontSize)
            Divider(thickness = 1.dp, color = MaterialTheme.colorScheme.tertiary)
            Surface (shape = MaterialTheme.shapes.medium, shadowElevation = 1.dp) {
                Text(text = note.content, modifier = Modifier.padding(all = 2.dp), style = MaterialTheme.typography.bodyMedium)
            }
        }

       Column() {
           val context = LocalContext.current

           IconButton(onClick = {
               val sendIntent: Intent = Intent().apply {
                   action = Intent.ACTION_SEND
                   putExtra(Intent.EXTRA_TEXT, note.content)
                   type = "text/plain"
               }
               val shareIntent = Intent.createChooser(sendIntent, null)

               context.startActivity(shareIntent)
           }) {
               Icon(imageVector = Icons.Default.Share, contentDescription = "Share Note", tint = MaterialTheme.colorScheme.onSurface)
           }
           IconButton(onClick = onDeleteClick) {
               Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Note", tint = MaterialTheme.colorScheme.onSurface)
           }
       }

    }
}