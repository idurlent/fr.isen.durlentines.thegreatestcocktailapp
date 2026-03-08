package fr.isen.inesdurlent.thegreatestcocktailapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun CategoriesScreen(navController: NavController) {

    val darkGreen = Color(0xFF0F2E24)
    val mediumGreen = Color(0xFF184D3B)
    val accentGreen = Color(0xFF3FA37C)

    var categories by remember { mutableStateOf<List<Category>>(emptyList()) }
    var results by remember { mutableStateOf<List<Drink>>(emptyList()) }
    var search by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    // Load categories
    LaunchedEffect(Unit) {
        scope.launch {
            try {
                val response = NetworkManager.api.getCategories()
                categories = response.drinks
            } catch (_: Exception) {}
        }
    }

    // Search cocktail OR ingredient
    LaunchedEffect(search) {

        if (search.isEmpty()) {
            results = emptyList()
            return@LaunchedEffect
        }

        scope.launch {
            try {

                val byName = NetworkManager.api.searchCocktail(search)

                if (byName.drinks != null) {
                    results = byName.drinks
                } else {
                    val byIngredient = NetworkManager.api.searchByIngredient(search)
                    results = byIngredient.drinks
                }

            } catch (_: Exception) {
                results = emptyList()
            }
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
                .padding(horizontal = 20.dp)
        ) {

            item {

                Spacer(modifier = Modifier.height(40.dp))

                Card(
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .shadow(10.dp, RoundedCornerShape(24.dp))
                ) {
                    Image(
                        painter = painterResource(R.drawable.cocktail_image),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Welcome 🍸",
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White
                )

                Text(
                    text = "Search cocktail or ingredient",
                    style = MaterialTheme.typography.bodyMedium,
                    color = accentGreen
                )

                Spacer(modifier = Modifier.height(20.dp))

                // SEARCH BAR
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0x22FFFFFF)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(8.dp, RoundedCornerShape(20.dp))
                ) {

                    TextField(
                        value = search,
                        onValueChange = { search = it },
                        placeholder = {
                            Text(
                                "Search cocktail or ingredient 🍸",
                                color = Color.White.copy(alpha = 0.7f)
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null,
                                tint = Color.White
                            )
                        },
                        textStyle = TextStyle(color = Color.White),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = Color.White
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))
            }

            // SEARCH RESULTS
            if (search.isNotEmpty()) {

                items(results) { drink ->

                    Card(
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .shadow(8.dp, RoundedCornerShape(20.dp))
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
                                modifier = Modifier.size(100.dp)
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            Text(
                                text = drink.strDrink,
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }

            } else {

                // CATEGORIES
                items(categories) { category ->

                    CategoryCard(category.strCategory) {

                        val encoded = URLEncoder.encode(
                            category.strCategory,
                            StandardCharsets.UTF_8.toString()
                        )

                        navController.navigate("drinks/$encoded")
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            item { Spacer(modifier = Modifier.height(40.dp)) }
        }
    }
}

@Composable
fun CategoryCard(category: String, onClick: () -> Unit) {

    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .shadow(8.dp, RoundedCornerShape(20.dp))
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = Color(0x22FFFFFF)
        )
    ) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.CenterStart
        ) {

            Text(
                text = category,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
                modifier = Modifier.padding(start = 20.dp)
            )
        }
    }
}