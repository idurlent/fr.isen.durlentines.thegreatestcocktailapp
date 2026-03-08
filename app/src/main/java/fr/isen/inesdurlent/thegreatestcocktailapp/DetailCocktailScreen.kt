package fr.isen.inesdurlent.thegreatestcocktailapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailCocktailScreen(navController: NavController, drink: String) {

    val context = LocalContext.current

    val darkGreen = Color(0xFF0F2E24)
    val mediumGreen = Color(0xFF184D3B)
    val accentGreen = Color(0xFF3FA37C)

    var cocktail by remember { mutableStateOf<Drink?>(null) }

    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            try {
                val response = NetworkManager.api.getDrinkDetail(drink)
                cocktail = response.drinks.first()
            } catch (_: Exception) { }
        }
    }

    val drinkName = cocktail?.strDrink ?: "Loading..."

    var isFavorite by remember {
        mutableStateOf(FavoritesManager.isFavorite(context, drink))
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Cocktail Detail", color = Color.White) },

                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null, tint = Color.White)
                    }
                },

                actions = {
                    IconButton(onClick = {

                        if (isFavorite) {
                            FavoritesManager.removeFavorite(context, drink)
                        } else {
                            FavoritesManager.addFavorite(context, drink)
                        }

                        isFavorite = !isFavorite

                    }) {

                        Icon(
                            imageVector =
                                if (isFavorite) Icons.Filled.Favorite
                                else Icons.Outlined.FavoriteBorder,
                            contentDescription = null,
                            tint = if (isFavorite) accentGreen else Color.White
                        )
                    }
                },

                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = darkGreen
                )
            )
        }

    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(listOf(darkGreen, mediumGreen))
                )
                .padding(padding)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp),

                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(20.dp))

                Card(
                    shape = RoundedCornerShape(32.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp)
                        .shadow(20.dp, RoundedCornerShape(32.dp))
                ) {

                    AsyncImage(
                        model = cocktail?.strDrinkThumb,
                        contentDescription = drinkName,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    text = drinkName,
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(20.dp))

                LuxuryCard("Ingredients") {

                    cocktail?.let { c ->

                        val ingredients = listOf(
                            c.strIngredient1 to c.strMeasure1,
                            c.strIngredient2 to c.strMeasure2,
                            c.strIngredient3 to c.strMeasure3,
                            c.strIngredient4 to c.strMeasure4,
                            c.strIngredient5 to c.strMeasure5
                        )

                        ingredients.forEach { (ingredient, measure) ->

                            if (!ingredient.isNullOrEmpty()) {

                                val text = if (!measure.isNullOrEmpty())
                                    "• $measure $ingredient"
                                else
                                    "• $ingredient"

                                Text(
                                    text = text,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                LuxuryCard("Preparation") {

                    Text(
                        text = cocktail?.strInstructions ?: "Loading...",
                        color = Color.White
                    )

                }

                Spacer(modifier = Modifier.height(40.dp))

            }
        }
    }
}

@Composable
fun LuxuryCard(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {

    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0x22FFFFFF)
        ),
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(12.dp))

            content()

        }
    }
}