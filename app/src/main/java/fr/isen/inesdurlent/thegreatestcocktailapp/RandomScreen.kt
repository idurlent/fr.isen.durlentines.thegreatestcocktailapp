package fr.isen.inesdurlent.thegreatestcocktailapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import androidx.compose.ui.text.style.TextAlign

@Composable
fun RandomScreen(navController: NavController) {

    var drink by remember { mutableStateOf<Drink?>(null) }

    val scope = rememberCoroutineScope()

    val darkGreen = Color(0xFF0F2E24)
    val mediumGreen = Color(0xFF184D3B)
    val accentGreen = Color(0xFF3FA37C)

    fun loadRandomCocktail() {
        scope.launch {
            try {
                val response = NetworkManager.api.getRandomCocktail()
                drink = response.drinks.first()
            } catch (_: Exception) {}
        }
    }

    LaunchedEffect(Unit) {
        loadRandomCocktail()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(listOf(darkGreen, mediumGreen))
            ),
        contentAlignment = Alignment.Center
    ) {

        drink?.let { d ->

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Let randomness choose for you 🍸",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Tap the cocktail to discover its recipe",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(30.dp))

                Spacer(modifier = Modifier.height(30.dp))

                Card(
                    shape = RoundedCornerShape(40.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0x22FFFFFF)
                    ),
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .shadow(20.dp, RoundedCornerShape(40.dp))
                ) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(24.dp)
                    ) {

                        AsyncImage(
                            model = d.strDrinkThumb,
                            contentDescription = d.strDrink,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(280.dp)
                                .clickable {
                                    navController.navigate("detail/${d.idDrink}")
                                }
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            text = d.strDrink,
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color.White
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Button(
                            onClick = { loadRandomCocktail() },
                            shape = RoundedCornerShape(50),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = accentGreen
                            )
                        ) {
                            Text("Another cocktail 🍸")
                        }
                    }
                }
            }
        }
    }
}