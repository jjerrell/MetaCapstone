package app.jjerrell.meta.course.sample.littlelemon.ui.composables.components

import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable

@Composable
fun BackIconNavItem(
    onBackAction: () -> Unit
) {
    IconButton(onClick = { onBackAction() }) {
        Image(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Go back"
        )
    }
}