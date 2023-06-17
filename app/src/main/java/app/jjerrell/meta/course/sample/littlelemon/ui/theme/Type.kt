package app.jjerrell.meta.course.sample.littlelemon.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import app.jjerrell.meta.course.sample.littlelemon.R

private val FontFamily.Companion.LittleLemon: FontFamily
    get() = LittleLemonFontFamily

private val LittleLemonFontFamily = FontFamily(
    Font(R.font.karla, weight = FontWeight.Light),
    Font(R.font.markazi_text, weight = FontWeight.Normal),
    Font(R.font.markazi_text_medium, weight = FontWeight.Medium),
    Font(R.font.markazi_text_semibold, weight = FontWeight.SemiBold),
    Font(R.font.markazi_text_bold, weight = FontWeight.Bold)
)

// Set of Material typography styles to start with
val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = FontFamily.LittleLemon,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily.LittleLemon,
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),
    titleSmall = TextStyle(
        fontFamily = FontFamily.LittleLemon,
        fontWeight = FontWeight.Medium,
        fontSize = 28.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.LittleLemon,
        fontWeight = FontWeight.Light,
        fontSize = 18.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.5.sp
    ),
    labelLarge = TextStyle(
        fontFamily = FontFamily.LittleLemon,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 26.sp,
        letterSpacing = 0.5.sp
    )
)