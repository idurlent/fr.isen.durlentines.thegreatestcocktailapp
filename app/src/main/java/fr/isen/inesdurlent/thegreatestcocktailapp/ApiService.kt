package fr.isen.inesdurlent.thegreatestcocktailapp

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("random.php")
    suspend fun getRandomCocktail(): DrinksResponse

    @GET("list.php?c=list")
    suspend fun getCategories(): CategoriesResponse

    @GET("filter.php")
    suspend fun getDrinksByCategory(
        @Query("c") category: String
    ): DrinksResponse

    @GET("lookup.php")
    suspend fun getDrinkDetail(
        @Query("i") id: String
    ): DrinksResponse

    @GET("search.php")
    suspend fun searchCocktail(
        @Query("s") name: String
    ): DrinksResponse

    @GET("filter.php")
    suspend fun searchByIngredient(
        @Query("i") ingredient: String
    ): DrinksResponse
}