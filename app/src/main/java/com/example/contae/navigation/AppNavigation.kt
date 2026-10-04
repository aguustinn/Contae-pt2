package com.example.contae.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

object Rotas {

    const val INICIO = "inicio"

    const val LOGIN = "login"

    const val HOME = "home"

    const val CATEGORIAS = "categorias"

    const val DETALHES_CATEGORIA = "detalhes_categoria/{id}"

    const val DESPESAS = "despesas"

    const val DETALHES_DESPESA = "detalhes_despesa/{id}"

    const val ORCAMENTO = "orcamento"

    const val RELATORIO = "relatorio"


    fun detalhesCategoria(id: Int): String {
        return "detalhes_categoria/$id"
    }


    fun detalhesDespesa(id: Int): String {
        return "detalhes_despesa/$id"
    }
}


@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Rotas.INICIO
    ) {

        composable(Rotas.INICIO) {

            TelaInicial(
                onComecar = {
                    navController.navigate(Rotas.LOGIN)
                }
            )
        }


        composable(Rotas.LOGIN) {

            TelaLogin(
                onLogin = {

                    navController.navigate(Rotas.HOME) {

                        popUpTo(Rotas.LOGIN) {
                            inclusive = true
                        }
                    }
                }
            )
        }


        composable(Rotas.HOME) {

            TelaHome(
                navController = navController
            )
        }



        composable(Rotas.CATEGORIAS) {

            TelaCategorias(
                navController = navController
            )
        }



        composable(
            route = Rotas.DETALHES_CATEGORIA
        ) { backStackEntry ->

            val id = backStackEntry
                .arguments
                ?.getString("id")
                ?.toIntOrNull()

            if (id != null) {

                TelaDetalhesCategoria(
                    id = id,
                    navController = navController
                )
            }
        }


        composable(Rotas.DESPESAS) {

            TelaDespesas(
                navController = navController
            )
        }



        composable(
            route = Rotas.DETALHES_DESPESA
        ) { backStackEntry ->

            val id = backStackEntry
                .arguments
                ?.getString("id")
                ?.toIntOrNull()

            if (id != null) {

                TelaDetalhesDespesa(
                    id = id,
                    navController = navController
                )
            }
        }



        composable(Rotas.ORCAMENTO) {

            TelaOrcamento(
                navController = navController
            )
        }



        composable(Rotas.RELATORIO) {

            TelaRelatorio(
                navController = navController
            )
        }
    }
}