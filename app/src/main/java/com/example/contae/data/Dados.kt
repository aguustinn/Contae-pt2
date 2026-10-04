package com.example.contae.data

import androidx.compose.runtime.mutableStateListOf
import com.example.contae.model.Categoria
import com.example.contae.model.Despesa

object Dados {

    val categorias = mutableStateListOf(
        Categoria(1, "Alimentação", 850.0),
        Categoria(2, "Transporte", 500.0),
        Categoria(3, "Moradia", 1200.0),
        Categoria(4, "Lazer", 250.0),
        Categoria(5, "Saúde", 200.0)
    )

    val despesas = mutableStateListOf(
        Despesa(1, "Mercado", 250.0, 1, "04/10/2026"),
        Despesa(2, "Uber", 35.0, 2, "03/10/2026"),
        Despesa(3, "Cinema", 50.0, 4, "02/10/2026"),
        Despesa(4, "Farmácia", 80.0, 5, "01/10/2026")
    )

    fun proximoIdCategoria(): Int {
        return if (categorias.isEmpty()) {
            1
        } else {
            categorias.maxOf { it.id } + 1
        }
    }

    fun proximoIdDespesa(): Int {
        return if (despesas.isEmpty()) {
            1
        } else {
            despesas.maxOf { it.id } + 1
        }
    }
}