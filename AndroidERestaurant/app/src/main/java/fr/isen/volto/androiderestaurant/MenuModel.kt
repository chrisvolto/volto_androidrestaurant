package fr.isen.volto.androiderestaurant

import java.io.Serializable;

data class User(
        val id: Int,
        val code: String,
        val id_shop: Int,
        val email: String,
        val firstname: String,
        val lastname: String,
        val phone: String,
        val address: String,
        val postal_code: String,
        val birth_date: String,
        val town: String,
        val points: Int,
        val token: String,
        val gcmtoken: String,
        val create_date: String,
        val update_date: String
): Serializable

data class Category(
    val name_fr: String,
    val name_en: String,
    val items: Array<Product>
): Serializable

data class Product(
    val id: ULong,
    val name_fr: String,
    val name_en: String,
    val id_category: ULong,
    val categ_name_fr: String,
    val categ_name_en: String,
    val images: Array<String>,
    val ingredients: Array<Ingredient>,
    val prices: Array<Price>
): Serializable {
    fun getPrice() = prices[0].price
    fun getFormattedPrice() = prices[0].price.toString() + "€"
    fun getFirstPicture() = if (images.isNotEmpty() && images[0].isNotEmpty()) {
        images[0]
    } else
    {
        null
    }

    fun getFormattedIngredients() = if (ingredients.isNotEmpty() && ingredients.any(){ it.name_fr.isNotEmpty() }) {
        var ingredients: String = "";
        val iIngredients: Iterator<Ingredient> = this.ingredients.iterator()
        while (iIngredients.hasNext())
        {
            ingredients = ingredients.plus(iIngredients.next().name_fr)

            if (iIngredients.hasNext()) {
                ingredients = ingredients.plus(", ")
            }
        }
        ingredients
    } else
    {
        null
    }

    fun getAllPictures() = if (images.isNotEmpty() && images.any(){ it.isNotEmpty() }) {
        images.filter { it.isNotEmpty() }
    } else
    {
        null
    }
}

data class Ingredient(
        var id: ULong,
        var id_shop: ULong,
        var name_fr: String,
        var name_en: String,
        var create_date: String,
        var update_date: String,
        var id_pizza: ULong
): Serializable

data class Price(
    var id: ULong,
    var id_pizza: ULong,
    var id_size: ULong,
    var price: Float,
    var create_date: String,
    var update_date: String,
    var size: String
): Serializable

data class ProductOrder(
        var product: Product,
        var quantity: ULong
) : Serializable