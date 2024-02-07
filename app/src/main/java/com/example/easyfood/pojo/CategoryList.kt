package com.example.easyfood.pojo


import com.google.gson.annotations.SerializedName

data class CategoryList(
    @SerializedName("meals")
    val meals: List<CategoryMeals> = listOf()
)