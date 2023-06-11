package app.jjerrell.meta.course.sample.littlelemon.ui.composables.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.jjerrell.meta.course.sample.littlelemon.ui.model.MenuItemAndroid
import app.jjerrell.meta.course.sample.littlelemon.ui.theme.LittleLemonTheme

@Composable
fun MenuListItem(
    modifier: Modifier = Modifier,
    usePrimaryColor: Boolean,
    item: MenuItemAndroid
) {
    Row(
        modifier = modifier
            .fillMaxWidth().then(
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
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleLarge,
                color = with(MaterialTheme.colorScheme) {
                    if (usePrimaryColor) Color.Unspecified
                    else onSecondary
                }
            )
            Text(
                text = item.description,
                style = MaterialTheme.typography.bodyLarge,
                color = with(MaterialTheme.colorScheme) {
                    if (usePrimaryColor) Color.Unspecified
                    else onSecondary
                }
            )
            Text(
                text = "$" + "${item.price}",
                style = MaterialTheme.typography.headlineLarge,
                color = with(MaterialTheme.colorScheme) {
                    if (usePrimaryColor) Color.Unspecified
                    else onSecondary
                }
            )
        }
        // TODO: Glide
//        Image(
//            painterResource(id = item.imageRes),
//            modifier = Modifier.weight(1F),
//            contentDescription = "An image of the ${item.title} dish"
//        )
    }
}

@Preview(showBackground = true)
@Composable
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