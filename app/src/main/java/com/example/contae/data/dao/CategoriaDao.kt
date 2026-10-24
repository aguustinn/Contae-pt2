package com.example.contae.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.contae.model.Categoria

@Dao
interface CategoriaDao {
    @Insert
    suspend fun inserir(categoria: Categoria): Long

    @Query("SELECT * FROM categorias ORDER BY nome COLLATE NOCASE")
    suspend fun buscarTodos(): List<Categoria>

    @Query("SELECT * FROM categorias WHERE id = :id LIMIT 1")
    suspend fun buscarPorId(id: Int): Categoria?

    @Update
    suspend fun atualizar(categoria: Categoria)

    @Delete
    suspend fun deletar(categoria: Categoria)
}
