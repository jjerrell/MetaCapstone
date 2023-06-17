package app.jjerrell.meta.course.sample.littlelemon.ui.composables.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.jjerrell.meta.course.sample.littlelemon.ui.model.MenuItemAndroid
import app.jjerrell.meta.course.sample.littlelemon.ui.theme.LittleLemonTheme
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun MenuListItem(
    modifier: Modifier = Modifier,
    usePrimaryColor: Boolean,
    item: MenuItemAndroid
) {
    val fallbackResource = remember {
        MenuItemAndroid.defaultMenu.find {
            it.title == item.title
        }?.localResource
    }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (usePrimaryColor)
                    Modifier.background(MaterialTheme.colorScheme.primary)
                else
                    Modifier.background(MaterialTheme.colorScheme.secondary)
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Column(
            modifier = Modifier.weight(2F)
        ) {
            val textColor = with(MaterialTheme.colorScheme) {
                if (usePrimaryColor) onSecondary
                else onPrimary
            }
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleSmall,
                color = textColor
            )
            Text(
                text = item.description,
                style = MaterialTheme.typography.bodyLarge,
                color = textColor
            )
            Text(
                text = item.priceString,
                style = MaterialTheme.typography.labelLarge,
                color = textColor
            )
        }
        // capture for dishes with bad image data (as of 06/12/2023)
        val fallbackPainter = fallbackResource?.let { painterResource(id = it) }
        if (item.hasBadImage) {
            fallbackPainter?.let {
                Image(
                    painter = it,
                    contentDescription = null, // stylistic content
                    modifier = Modifier
                        .clip(RoundedCornerShape(20))
                        .weight(1f),
                    contentScale = ContentScale.Fit
                )
            }
        } else {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.imageUri)
                    .build(),
                placeholder = fallbackPainter,
                error = fallbackPainter,
                fallback = fallbackPainter,
                contentDescription = null, // stylistic content
                modifier = Modifier
                    .clip(RoundedCornerShape(20))
                    .weight(1f),
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun MenuListItem_Preview() {
    LittleLemonTheme {
        LazyColumn {
            itemsIndexed(MenuItemAndroid.defaultMenu) { index, item ->
                MenuListItem(
                    item = item,
                    usePrimaryColor = index % 2 == 0
                )
            }
        }
    }
}