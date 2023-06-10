package app.jjerrell.meta.course.sample.littlelemon.data.model

import androidx.annotation.DrawableRes
import app.jjerrell.meta.course.sample.littlelemon.R

data class MenuItem(
    val title: String,
    val description: String,
    val category: Category,
    @DrawableRes
    val image: Int,
    val price: Double
) {
    enum class Category {
        Appetizer,
        Salad,
        Seafood,
        Dessert,
        Pasta;
    }

    companion object {
        val defaultMenu: List<MenuItem> = listOf(
            MenuItem(
                title = "Bruschetta",
                description = "Toasted Italian bread topped with tomatoes, basil, garlic, and olive oil. A burst of Mediterranean flavors in every bite.",
                category = Category.Appetizer,
                image = R.drawable.bruschetta,
                price = 8.99
            ),
            MenuItem(
                title = "Greek Salad",
                description = "Crisp romaine, tomatoes, cucumbers, olives, feta cheese. Tangy lemon dressing. Refreshing taste of Greece in a salad.",
                category = Category.Salad,
                image = R.drawable.greek_salad,
                price = 10.99
            ),
            MenuItem(
                title = "Grilled Fish",
                description = "Tender, seasoned fillet with roasted veggies. Zesty lemon herb sauce. A seaside delight from the Mediterranean.",
                category = Category.Seafood,
                image = R.drawable.grilled_fish,
                price = 18.99
            ),
            MenuItem(
                title = "Lemon Dessert",
                description = "Lemony cake with velvety curd. Sweet and tangy indulgence. A burst of sunshine on your palate.",
                category = Category.Dessert,
                image = R.drawable.lemon_dessert,
                price = 6.99
            ),
            MenuItem(
                title = "Pasta",
                description = "Al dente pasta in tomato sauce with garlic, basil, and chili flakes. Rustic flavors from Mediterranean trattorias. Bon appétit!",
                category = Category.Pasta,
                image = R.drawable.pasta,
                price = 14.99
            ),

        )
    }
}
