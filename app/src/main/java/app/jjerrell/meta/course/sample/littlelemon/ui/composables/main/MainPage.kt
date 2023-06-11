package app.jjerrell.meta.course.sample.littlelemon.ui.composables.main

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import app.jjerrell.meta.course.sample.littlelemon.R
import app.jjerrell.meta.course.sample.littlelemon.ui.composables.components.LLHero
import app.jjerrell.meta.course.sample.littlelemon.ui.composables.components.LLTopAppBar
import app.jjerrell.meta.course.sample.littlelemon.ui.composables.components.MenuListItem
import app.jjerrell.meta.course.sample.littlelemon.ui.composables.components.PageLoadingIndicator
import app.jjerrell.meta.course.sample.littlelemon.ui.composables.components.ProfileIconNavItem
import app.jjerrell.meta.course.sample.littlelemon.ui.model.MenuItemAndroid

@Composable
@ExperimentalFoundationApi
fun MainPage(
    modifier: Modifier = Modifier,
    onNavigateToProfile: () -> Unit,
) {
    val viewModel = viewModel<MainPageViewModel>()
    val context = LocalContext.current
    val state = viewModel.stateFlow.collectAsState()
    val items = viewModel.menuItems.collectAsState(emptyList())
    Column(modifier = modifier) {
        LLTopAppBar(
            actions = {
                ProfileIconNavItem(
                    isEnabled = true,
                    onNavigateToProfile
                )
            }
        )
        if (state.value.isLoading) {
            PageLoadingIndicator(
                onInitialize = {
                    viewModel.fetchMenuItems(context = context)
                }
            )
        } else {
            LazyColumn {
                // present the hero content
                item {
                    MainPageHero()
                }
                // pin the search and filter components after the hero scrolls out of view
                stickyHeader {
                    val availableCategories by remember {
                        mutableStateOf(
                            state.value.menuItems.map { it.category }.toSet()
                        )
                    }
                    TextField(
                        value = state.value.searchContent,
                        onValueChange = { viewModel.updateSearchContent(it) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    FilterRow(
                        modifier = Modifier.fillMaxWidth(),
                        categories = availableCategories
                            .filter { category ->
                                state.value.menuItems.any { it.category == category }
                            }
                            .sortedBy { it.ordinal }
                            .toSet(),
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
    categories: Set<MenuItemAndroid.Category>,
    categoryFilter: MenuItemAndroid.Category?,
    onSelectCategory: (MenuItemAndroid.Category?) -> Unit
) {
    LazyRow(
        modifier = modifier
            .background(MaterialTheme.colorScheme.tertiary),
        horizontalArrangement = Arrangement.Center
    ) {
        item {
            TextButton(
                onClick = { onSelectCategory(null) },
                elevation = if (categoryFilter == null) ButtonDefaults.buttonElevation(defaultElevation = 4.dp) else null
            ) {
                Text("All")
            }
        }
        items(categories.toList()) { category ->
            TextButton(
                onClick = { onSelectCategory(category) },
                elevation = if (categoryFilter == category) ButtonDefaults.buttonElevation(defaultElevation = 4.dp) else null
            ) {
                Text(category.name)
            }
        }
    }
}