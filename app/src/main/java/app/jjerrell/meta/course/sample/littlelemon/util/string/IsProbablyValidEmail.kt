package app.jjerrell.meta.course.sample.littlelemon.util.string

fun String.isProbablyValidEmail(): Boolean = if (this.isBlank()) {
    false
} else {
    val parts = this.split('@')
    println("_DEBUG: $parts")
    if (parts.count() != 2)
        false
    else {
        val domainParts = parts[1].split('.')
        domainParts.count() >= 2 && domainParts[1].isNotBlank()
    }
}