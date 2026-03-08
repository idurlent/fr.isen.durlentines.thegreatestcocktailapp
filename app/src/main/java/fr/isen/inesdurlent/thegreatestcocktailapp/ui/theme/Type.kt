package fr.isen.inesdurlent.thegreatestcocktailapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import fr.isen.inesdurlent.thegreatestcocktailapp.R

// Font principale luxe
val Playfair = FontFamily(
    Font(R.font.playfairdisplayregular, FontWeight.Normal),
    Font(R.font.playfairdisplaybold, FontWeight.Bold)
)

// Typography complète personnalisée
val AppTypography = Typography(

    // Titre principal (Mojito)
    headlineLarge = TextStyle(
        fontFamily = Playfair,
        fontWeight = FontWeight.Bold,
        fontSize = 34.sp,
        letterSpacing = 0.5.sp
    ),

    // Titres de section (Ingredients / Preparation)
    titleMedium = TextStyle(
        fontFamily = Playfair,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        letterSpacing = 0.3.sp
    ),

    // Sous-titre (Cocktail • Highball glass)
    bodyMedium = TextStyle(
        fontFamily = Playfair,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp
    ),

    // Texte normal (liste ingrédients)
    bodySmall = TextStyle(
        fontFamily = Playfair,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    )
)