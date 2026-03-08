package fr.isen.inesdurlent.thegreatestcocktailapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import fr.isen.inesdurlent.thegreatestcocktailapp.ui.theme.TheGreatestCocktailAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {

            TheGreatestCocktailAppTheme {

                val navController = rememberNavController()
                val currentRoute =
                    navController.currentBackStackEntryAsState().value?.destination?.route

                val darkGreen = Color(0xFF0F2E24)
                val accentGreen = Color(0xFF3FA37C)

                Scaffold(

                    bottomBar = {

                        NavigationBar(
                            containerColor = darkGreen
                        ) {

                            NavigationBarItem(
                                selected = currentRoute == "random",
                                onClick = { navController.navigate("random") },
                                icon = { Text("🍸") },
                                label = { Text("Random") },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = accentGreen,
                                    selectedTextColor = accentGreen,
                                    unselectedIconColor = Color.White,
                                    unselectedTextColor = Color.White,
                                    indicatorColor = Color(0x3325A18E)
                                )
                            )

                            NavigationBarItem(
                                selected = currentRoute == "list",
                                onClick = { navController.navigate("list") },
                                icon = { Text("📋") },
                                label = { Text("List") },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = accentGreen,
                                    selectedTextColor = accentGreen,
                                    unselectedIconColor = Color.White,
                                    unselectedTextColor = Color.White,
                                    indicatorColor = Color(0x3325A18E)
                                )
                            )

                            NavigationBarItem(
                                selected = currentRoute == "favorites",
                                onClick = { navController.navigate("favorites") },
                                icon = { Text("❤️") },
                                label = { Text("Favorites") },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = accentGreen,
                                    selectedTextColor = accentGreen,
                                    unselectedIconColor = Color.White,
                                    unselectedTextColor = Color.White,
                                    indicatorColor = Color(0x3325A18E)
                                )
                            )
                        }
                    }

                ) { padding ->

                    NavHost(
                        navController = navController,
                        startDestination = "splash",
                        modifier = Modifier.padding(padding)
                    ) {

                        composable("random") {
                            RandomScreen(navController)
                        }

                        composable("list") {
                            CategoriesScreen(navController)
                        }

                        composable("splash") {
                            SplashScreen(navController)
                        }

                        composable("favorites") {
                            FavoritesScreen(navController)
                        }

                        composable(
                            route = "drinks/{category}",
                            arguments = listOf(
                                navArgument("category") {
                                    type = NavType.StringType
                                }
                            )
                        ) { backStackEntry ->

                            val category =
                                backStackEntry.arguments?.getString("category") ?: ""

                            DrinksScreen(category, navController)
                        }

                        composable(
                            route = "detail/{drink}",
                            arguments = listOf(
                                navArgument("drink") {
                                    type = NavType.StringType
                                }
                            )
                        ) { backStackEntry ->

                            val drink =
                                backStackEntry.arguments?.getString("drink") ?: ""

                            DetailCocktailScreen(navController, drink)
                        }
                    }
                }
            }
        }
    }
}