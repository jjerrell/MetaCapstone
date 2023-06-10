package app.jjerrell.meta.course.sample.littlelemon.composables.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.jjerrell.meta.course.sample.littlelemon.R

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun LLTopAppBar(
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = {
            Image(
                modifier = Modifier
                    .height(48.dp),
                painter = painterResource(id = R.drawable.logo),
                contentDescription = stringResource(R.string.logo_description),
                contentScale = ContentScale.FillHeight
            )
        },
        modifier = modifier,
        navigationIcon = navigationIcon,
        actions = actions
    )
}
