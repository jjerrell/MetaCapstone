package app.jjerrell.meta.course.sample.littlelemon.ui.model

import android.icu.text.NumberFormat
import android.os.Build
import androidx.annotation.DrawableRes
import app.jjerrell.meta.course.sample.littlelemon.R
import java.util.*

data class MenuItemAndroid(
    val title: String,
    val description: String,
    val category: Category,
    val imageUri: String,
    @DrawableRes
    val localResource: Int? = null,
    val price: Double
) {
    val priceString: String
        get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            NumberFormat
                .getCurrencyInstance(Locale("US", "en"))
                .format(price)
        } else {
            "$${price}"
        }

    /**
     * For some reason, some images from the server are completely black and the calls are successful.
     * So fallback and error handling are useless. Therefor, I've coded checks for the problematic
     * dishes.
     *
     * This is not a best practice. Furthermore, it isn't guaranteed that these images won't be fixed
     * or that other images may become invalid.
     */
    val hasBadImage = when (title) {
        "Lemon Desert",
        "Grilled Fish" -> true
        else -> false
    }

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
                localResource = R.drawable.bruschetta,
                price = 8.99
            ),
            MenuItemAndroid(
                title = "Greek Salad",
                description = "Crisp romaine, tomatoes, cucumbers, olives, feta cheese. Tangy lemon dressing. Refreshing taste of Greece in a salad.",
                category = Category.Appetizer,
                imageUri = "https://github.com/Meta-Mobile-Developer-PC/Working-With-Data-API/blob/main/images/greekSalad.jpg?raw=true",
                localResource = R.drawable.greek_salad,
                price = 10.99
            ),
            MenuItemAndroid(
                title = "Grilled Fish",
                description = "Tender, seasoned fillet with roasted veggies. Zesty lemon herb sauce. A seaside delight from the Mediterranean.",
                category = Category.Mains,
                imageUri = "https://github.com/Meta-Mobile-Developer-PC/Working-With-Data-API/blob/main/images/grilledFish.jpg?raw=true",
                localResource = R.drawable.grilled_fish,
                price = 18.99
            ),
            MenuItemAndroid(
                title = "Lemon Desert",
                description = "Lemony cake with velvety curd. Sweet and tangy indulgence. A burst of sunshine on your palate.",
                category = Category.Dessert,
                imageUri = "https://github.com/Meta-Mobile-Developer-PC/Working-With-Data-API/blob/main/images/lemonDessert%202.jpg?raw=true",
                localResource = R.drawable.lemon_dessert,
                price = 6.99
            ),
            MenuItemAndroid(
                title = "Pasta",
                description = "Al dente pasta in tomato sauce with garlic, basil, and chili flakes. Rustic flavors from Mediterranean trattorias. Bon appétit!",
                category = Category.Mains,
                imageUri = "https://github.com/Meta-Mobile-Developer-PC/Working-With-Data-API/blob/main/images/pasta.jpg?raw=true",
                localResource = R.drawable.pasta,
                price = 14.99
            ),

        )
    }
}
