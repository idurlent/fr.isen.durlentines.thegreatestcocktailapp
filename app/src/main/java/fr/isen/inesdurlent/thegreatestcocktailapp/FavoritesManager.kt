package fr.isen.inesdurlent.thegreatestcocktailapp

import android.content.Context

object FavoritesManager {

    private const val PREFS = "favorites_prefs"
    private const val KEY = "favorites"

    fun getFavorites(context: Context): List<String> {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        return prefs.getStringSet(KEY, emptySet())?.toList() ?: emptyList()
    }

    fun addFavorite(context: Context, id: String) {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val favorites = getFavorites(context).toMutableSet()
        favorites.add(id)
        prefs.edit().putStringSet(KEY, favorites).apply()
    }

    fun removeFavorite(context: Context, id: String) {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val favorites = getFavorites(context).toMutableSet()
        favorites.remove(id)
        prefs.edit().putStringSet(KEY, favorites).apply()
    }

    fun isFavorite(context: Context, id: String): Boolean {
        return getFavorites(context).contains(id)
    }
}