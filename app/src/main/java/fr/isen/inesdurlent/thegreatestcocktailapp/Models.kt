package fr.isen.inesdurlent.thegreatestcocktailapp

data class DrinksResponse(
    val drinks: List<Drink>
)

data class Drink(
    val idDrink: String,
    val strDrink: String,
    val strDrinkThumb: String?,
    val strInstructions: String?,

    val strIngredient1: String?,
    val strIngredient2: String?,
    val strIngredient3: String?,
    val strIngredient4: String?,
    val strIngredient5: String?,
    val strIngredient6: String?,
    val strIngredient7: String?,
    val strIngredient8: String?,
    val strIngredient9: String?,
    val strIngredient10: String?,

    val strMeasure1: String?,
    val strMeasure2: String?,
    val strMeasure3: String?,
    val strMeasure4: String?,
    val strMeasure5: String?
)

data class CategoriesResponse(
    val drinks: List<Category>
)

data class Category(
    val strCategory: String
)