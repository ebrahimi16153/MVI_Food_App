package com.github.ebrahimi16153.mvifoodapp.data.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.ebrahimi16153.mvifoodapp.util.Constant
import com.google.gson.annotations.SerializedName


data class FoodList(
    @SerializedName("meals")
    val meals: List<Meal>
) {
    @Entity(Constant.FOOD_TABLE)
    data class Meal(
        @SerializedName("dateModified")
        val dateModified: String?= "", // null
        @SerializedName("idMeal")

        @PrimaryKey(autoGenerate = false)
        val idMeal: String, // 52851
        @SerializedName("strArea")
        val strArea: String?, // Indian
        @SerializedName("strCategory")
        val strCategory: String?, // Chicken
        @SerializedName("strCreativeCommonsConfirmed")
        val strCreativeCommonsConfirmed: String? ="", // null
        @SerializedName("strDrinkAlternate")
        val strDrinkAlternate:String? = "", // null
        @SerializedName("strImageSource")
        val strImageSource: String? ="", // null
        @SerializedName("strIngredient1")
        val strIngredient1: String?, // Red Chilli
        @SerializedName("strIngredient10")
        val strIngredient10: String?,
        @SerializedName("strIngredient11")
        val strIngredient11: String?,
        @SerializedName("strIngredient12")
        val strIngredient12: String?,
        @SerializedName("strIngredient13")
        val strIngredient13: String?,
        @SerializedName("strIngredient14")
        val strIngredient14: String?,
        @SerializedName("strIngredient15")
        val strIngredient15: String?,
        @SerializedName("strIngredient16")
        val strIngredient16: String?,
        @SerializedName("strIngredient17")
        val strIngredient17: String?,
        @SerializedName("strIngredient18")
        val strIngredient18: String?,
        @SerializedName("strIngredient19")
        val strIngredient19: String?,
        @SerializedName("strIngredient2")
        val strIngredient2: String?, // Ginger
        @SerializedName("strIngredient20")
        val strIngredient20: String?,
        @SerializedName("strIngredient3")
        val strIngredient3: String?, // Coriander
        @SerializedName("strIngredient4")
        val strIngredient4: String?, // Coriander
        @SerializedName("strIngredient5")
        val strIngredient5: String?, // Sunflower Oil
        @SerializedName("strIngredient6")
        val strIngredient6: String?, // Chicken Breasts
        @SerializedName("strIngredient7")
        val strIngredient7: String?, // Peanut Butter
        @SerializedName("strIngredient8")
        val strIngredient8: String?, // Chicken Stock
        @SerializedName("strIngredient9")
        val strIngredient9: String?, // Greek Yogurt
        @SerializedName("strInstructions")
        val strInstructions: String?, // Finely slice a quarter of the chilli, then put the rest in a food processor with the ginger, garlic, coriander stalks and one-third of the leaves. Whizz to a rough paste with a splash of water if needed.Heat the oil in a frying pan, then quickly brown the chicken chunks for 1 min. Stir in the paste for another min, then add the peanut butter, stock and yogurt. When the sauce is gently bubbling, cook for 10 mins until the chicken is just cooked through and sauce thickened. Stir in most of the remaining coriander, then scatter the rest on top with the chilli, if using. Eat with rice or mashed sweet potato.
        @SerializedName("strMeal")
        val strMeal: String?, // Nutty Chicken Curry
        @SerializedName("strMealThumb")
        val strMealThumb: String?, // https://www.themealdb.com/images/media/meals/yxsurp1511304301.jpg
        @SerializedName("strMeasure1")
        val strMeasure1: String?, // 1 large
        @SerializedName("strMeasure10")
        val strMeasure10: String?,
        @SerializedName("strMeasure11")
        val strMeasure11: String?,
        @SerializedName("strMeasure12")
        val strMeasure12: String?,
        @SerializedName("strMeasure13")
        val strMeasure13: String?,
        @SerializedName("strMeasure14")
        val strMeasure14: String?,
        @SerializedName("strMeasure15")
        val strMeasure15: String?,
        @SerializedName("strMeasure16")
        val strMeasure16: String?,
        @SerializedName("strMeasure17")
        val strMeasure17: String?,
        @SerializedName("strMeasure18")
        val strMeasure18: String?,
        @SerializedName("strMeasure19")
        val strMeasure19: String?,
        @SerializedName("strMeasure2")
        val strMeasure2: String?, // 0.5
        @SerializedName("strMeasure20")
        val strMeasure20: String?,
        @SerializedName("strMeasure3")
        val strMeasure3: String?, // 1 large
        @SerializedName("strMeasure4")
        val strMeasure4: String?, // Bunch
        @SerializedName("strMeasure5")
        val strMeasure5: String?, // 1 tbsp
        @SerializedName("strMeasure6")
        val strMeasure6: String?, // 4
        @SerializedName("strMeasure7")
        val strMeasure7: String?, // 5 tblsp
        @SerializedName("strMeasure8")
        val strMeasure8: String?, // 150ml
        @SerializedName("strMeasure9")
        val strMeasure9: String?, // 200g
        @SerializedName("strSource")
        val strSource: String?, // https://www.bbcgoodfood.com/recipes/11753/nutty-chicken-curry
        @SerializedName("strTags")
        val strTags: String? = "", // null
        @SerializedName("strYoutube")
        val strYoutube: String? // https://www.youtube.com/watch?v=nSQNfZxOdeU
    )
}