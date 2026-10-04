package com.example.contae.model

data class Despesa(
    val id: Int,
    val descricao: String,
    val valor: Double,
    val categoriaId: Int,
    val data: String
)