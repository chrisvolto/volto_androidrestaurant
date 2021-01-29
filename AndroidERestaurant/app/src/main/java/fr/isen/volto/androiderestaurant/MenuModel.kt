package fr.isen.volto.androiderestaurant

import java.io.Serializable;
import java.util.*

data class Menu(
    val name_fr: String,
    val name_en: String,
    val items: Array<Item>
): Serializable

data class Item(
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
    fun getPrice() = prices[0].price.toDouble()
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

data class Order(
    var product_category: String,
    var product_name: String,
    var product_price: Float,
    var product_quantity: ULong
) : Serializable