package app.jjerrell.meta.course.sample.littlelemon.ui.composables.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.jjerrell.meta.course.sample.littlelemon.R

@Composable
fun ProfileIconNavItem(
    isEnabled: Boolean,
    onNavigateToProfile: () -> Unit = {}
) {
    IconButton(
        onClick = onNavigateToProfile,
        enabled = isEnabled
    ) {
        Image(
            painter = painterResource(id = R.drawable.profile_circle),
            contentDescription = stringResource(R.string.profile_image_description),
            modifier = Modifier.size(48.dp, 48.dp),
            contentScale = ContentScale.Fit
        )
    }
}