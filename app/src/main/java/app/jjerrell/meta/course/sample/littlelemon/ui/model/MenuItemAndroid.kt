package app.jjerrell.meta.course.sample.littlelemon.ui.model

data class MenuItemAndroid(
    val title: String,
    val description: String,
    val category: Category,
    val imageUri: String,
    val price: Double
) {
    enum class Category {
        Appetizer,
        Mains,
        Dessert,
        Misc;

        companion object {
            fun fromServiceName(category: String): Category {
                return when (category) {
                    "desserts" -> Dessert
                    "starters" -> Appetizer
                    "mains" -> Mains
                    else -> Misc
                }
            }
        }
    }

    companion object {
        val defaultMenu: List<MenuItemAndroid> = listOf(
            MenuItemAndroid(
                title = "Bruschetta",
                description = "Toasted Italian bread topped with tomatoes, basil, garlic, and olive oil. A burst of Mediterranean flavors in every bite.",
                category = Category.Appetizer,
                imageUri = "https://github.com/Meta-Mobile-Developer-PC/Working-With-Data-API/blob/main/images/bruschetta.jpg?raw=true",
                price = 8.99
            ),
            MenuItemAndroid(
                title = "Greek Salad",
                description = "Crisp romaine, tomatoes, cucumbers, olives, feta cheese. Tangy lemon dressing. Refreshing taste of Greece in a salad.",
                category = Category.Appetizer,
                imageUri = "https://github.com/Meta-Mobile-Developer-PC/Working-With-Data-API/blob/main/images/greekSalad.jpg?raw=true",
                price = 10.99
            ),
            MenuItemAndroid(
                title = "Grilled Fish",
                description = "Tender, seasoned fillet with roasted veggies. Zesty lemon herb sauce. A seaside delight from the Mediterranean.",
                category = Category.Mains,
                imageUri = "https://github.com/Meta-Mobile-Developer-PC/Working-With-Data-API/blob/main/images/grilledFish.jpg?raw=true",
                price = 18.99
            ),
            MenuItemAndroid(
                title = "Lemon Dessert",
                description = "Lemony cake with velvety curd. Sweet and tangy indulgence. A burst of sunshine on your palate.",
                category = Category.Dessert,
                imageUri = "https://github.com/Meta-Mobile-Developer-PC/Working-With-Data-API/blob/main/images/lemonDessert%202.jpg?raw=true",
                price = 6.99
            ),
            MenuItemAndroid(
                title = "Pasta",
                description = "Al dente pasta in tomato sauce with garlic, basil, and chili flakes. Rustic flavors from Mediterranean trattorias. Bon appétit!",
                category = Category.Mains,
                imageUri = "https://github.com/Meta-Mobile-Developer-PC/Working-With-Data-API/blob/main/images/pasta.jpg?raw=true",
                price = 14.99
            ),

        )
    }
}
