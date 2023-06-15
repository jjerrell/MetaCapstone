package app.jjerrell.meta.course.sample.littlelemon.ui.composables.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun PageLoadingIndicator(
    modifier: Modifier = Modifier,
    isLoading: Boolean = true,
    onLoadingStateChange: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxWidth(), // Makes sure the indicator is centered but allows the parent to control height
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // This will only run on the first composition and any
        // recomposition resulting from a new `isLoading` value
        LaunchedEffect(key1 = isLoading) {
            onLoadingStateChange()
        }
        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
    }
}