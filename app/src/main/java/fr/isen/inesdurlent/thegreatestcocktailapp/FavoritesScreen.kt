package fr.isen.inesdurlent.thegreatestcocktailapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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

@Composable
fun FavoritesScreen(navController: NavController) {

    val context = LocalContext.current

    val darkGreen = Color(0xFF0F2E24)
    val mediumGreen = Color(0xFF184D3B)

    var drinks by remember { mutableStateOf<List<Drink>>(emptyList()) }

    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {

        scope.launch {

            val ids = FavoritesManager.getFavorites(context)

            val loadedDrinks = mutableListOf<Drink>()

            ids.forEach { id ->

                try {

                    val response = NetworkManager.api.getDrinkDetail(id)

                    loadedDrinks.add(response.drinks.first())

                } catch (_: Exception) {}

            }

            drinks = loadedDrinks
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(listOf(darkGreen, mediumGreen))
            )
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {

            item {

                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    text = "Favorites ❤️",
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(30.dp))
            }

            items(drinks) { drink ->

                Card(
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .shadow(10.dp, RoundedCornerShape(24.dp))
                        .clickable {
                            navController.navigate("detail/${drink.idDrink}")
                        },
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0x22FFFFFF)
                    )
                ) {

                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        AsyncImage(
                            model = drink.strDrinkThumb,
                            contentDescription = drink.strDrink,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(100.dp)
                        )

                        Spacer(modifier = Modifier.width(20.dp))

                        Text(
                            text = drink.strDrink,
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}