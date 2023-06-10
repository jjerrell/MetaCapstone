package app.jjerrell.meta.course.sample.littlelemon.composables.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import app.jjerrell.meta.course.sample.littlelemon.R
import app.jjerrell.meta.course.sample.littlelemon.composables.components.LLHero
import app.jjerrell.meta.course.sample.littlelemon.composables.components.LLTopAppBar
import app.jjerrell.meta.course.sample.littlelemon.composables.components.MenuListItem
import app.jjerrell.meta.course.sample.littlelemon.composables.components.ProfileIconNavItem
import app.jjerrell.meta.course.sample.littlelemon.data.model.MenuItem

@Composable
@ExperimentalFoundationApi
fun MainPage(
    modifier: Modifier = Modifier,
    onNavigateToProfile: () -> Unit,
) {
    val viewModel = viewModel<MainPageViewModel>()
    val state = viewModel.stateFlow.collectAsState()
    val items = viewModel.menuItems.collectAsState(initial = MenuItem.defaultMenu)
    Column(modifier = modifier) {
        LLTopAppBar(
            actions = {
                ProfileIconNavItem(
                    isEnabled = true,
                    onNavigateToProfile
                )
            }
        )
        LazyColumn(
            modifier = modifier
        ) {
            // present the hero content
            item {
                MainPageHero()
            }
            // pin the search and filter components after the hero scrolls out of view
            stickyHeader {
                TextField(
                    value = state.value.searchContent,
                    onValueChange = { viewModel.updateSearchContent(it) },
                    modifier = Modifier.fillMaxWidth()
                )
                FilterRow(
                    categoryFilter = state.value.category,
                    onSelectCategory = { viewModel.updateCategory(it) }
                )
            }
            // list the menu items with alternating values for `usePrimaryColor`
            itemsIndexed(items.value) { index, item ->
                MenuListItem(
                    item = item,
                    usePrimaryColor = index % 2 == 0
                )
            }
        }
    }
}

@Composable
private fun MainPageHero(
    modifier: Modifier = Modifier
) {
    LLHero(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 4.dp, vertical = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(3f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Little Lemon",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "Chicago",
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(
                    text = "We are a family-owned Mediterranean restaurant, focused on traditional recipes served with a modern twist.",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Image(
                painter = painterResource(id = R.drawable.hero_image),
                modifier = Modifier
                    .weight(1f),
                contentDescription = null, // purely stylistic content
                contentScale = ContentScale.Inside
            )
        }
    }
}

@Composable
private fun FilterRow(
    modifier: Modifier = Modifier,
    categoryFilter: MenuItem.Category?,
    onSelectCategory: (MenuItem.Category?) -> Unit
) {
    LazyRow(
        modifier = modifier
            .background(MaterialTheme.colorScheme.tertiary)
    ) {
        item {
            TextButton(
                onClick = { onSelectCategory(null) },
                elevation = if (categoryFilter == null) ButtonDefaults.buttonElevation(defaultElevation = 4.dp) else null
            ) {
                Text("All")
            }
        }
        items(MenuItem.Category.values().asList()) { category ->
            TextButton(
                onClick = { onSelectCategory(category) },
                elevation = if (categoryFilter == category) ButtonDefaults.buttonElevation(defaultElevation = 4.dp) else null
            ) {
                Text(category.name)
            }
        }
    }
}