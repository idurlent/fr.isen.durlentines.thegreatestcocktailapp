package fr.isen.inesdurlent.thegreatestcocktailapp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DrinksScreen(
    category: String,
    navController: NavController
) {

    val darkGreen = Color(0xFF0F2E24)
    val mediumGreen = Color(0xFF184D3B)
    val accentGreen = Color(0xFF3FA37C)

    // Decode category sent by navigation
    val decodedCategory = URLDecoder.decode(
        category,
        StandardCharsets.UTF_8.toString()
    )

    var drinks by remember { mutableStateOf<List<Drink>>(emptyList()) }

    val scope = rememberCoroutineScope()

    LaunchedEffect(decodedCategory) {
        scope.launch {
            try {
                val response = NetworkManager.api.getDrinksByCategory(decodedCategory)
                drinks = response.drinks ?: emptyList()
            } catch (_: Exception) {
                drinks = emptyList()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = decodedCategory,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = darkGreen
                )
            )
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(darkGreen, mediumGreen)
                    )
                )
                .padding(padding)
        ) {

            if (drinks.isEmpty()) {

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = "No cocktails found 🍸",
                        color = Color.White
                    )

                }

            } else {

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp)
                ) {

                    items(drinks) { drink ->

                        Card(
                            shape = RoundedCornerShape(24.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(160.dp)
                                .shadow(12.dp, RoundedCornerShape(24.dp))
                                .clickable {
                                    navController.navigate("detail/${drink.idDrink}")
                                },
                            colors = CardDefaults.cardColors(
                                containerColor = Color.Transparent
                            )
                        ) {

                            Box {

                                AsyncImage(
                                    model = drink.strDrinkThumb,
                                    contentDescription = drink.strDrink,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )

                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(
                                            Brush.verticalGradient(
                                                listOf(
                                                    Color.Transparent,
                                                    Color(0xCC000000)
                                                )
                                            )
                                        )
                                )

                                Column(
                                    modifier = Modifier
                                        .align(Alignment.BottomStart)
                                        .padding(20.dp)
                                ) {

                                    Text(
                                        text = drink.strDrink,
                                        style = MaterialTheme.typography.titleLarge,
                                        color = Color.White
                                    )

                                }
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
    }
}