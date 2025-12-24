package com.example.littlelemon

data class Dish(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val image: String,
    val category: String
) {
    companion object {
        fun fromMenuItemRoom(item: MenuItemRoom): Dish {
            return Dish(
                id = item.id,
                name = item.title,
                description = item.description,
                price = item.price.toDoubleOrNull() ?: 0.0,
                image = item.image,
                category = item.category
            )
        }
    }
}
