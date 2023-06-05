package app.jjerrell.meta.course.sample.littlelemon.composables.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.jjerrell.meta.course.sample.littlelemon.R

@Composable
fun Heading(
    modifier: Modifier = Modifier,
    heroContent: @Composable (BoxScope.() -> Unit)?
) {
    Column(modifier = modifier) {
        Image(
            modifier = Modifier
                .width(260.dp)
                .padding(top = 20.dp),
            painter = painterResource(id = R.drawable.logo),
            contentDescription = stringResource(R.string.logo_description),
            contentScale = ContentScale.FillWidth
        )
        if (heroContent != null) {
            Hero(
                content = heroContent
            )
        }
    }
}