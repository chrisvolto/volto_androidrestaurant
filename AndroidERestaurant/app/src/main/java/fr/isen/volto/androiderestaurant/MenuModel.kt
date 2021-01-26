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
): Serializable

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