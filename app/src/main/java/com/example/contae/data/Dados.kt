package com.example.contae.data

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import com.example.contae.data.dao.CategoriaDao
import com.example.contae.data.dao.DespesaDao
import com.example.contae.model.Categoria
import com.example.contae.model.Despesa
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Cache observável da interface alimentado exclusivamente pelo Room.
 * Todas as consultas e mutações do banco são executadas em Dispatchers.IO.
 */
object Dados {
    private const val TAG = "ContaêRoom"
    private lateinit var database: AppDatabase
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    val categorias = mutableStateListOf<Categoria>()
    val despesas = mutableStateListOf<Despesa>()

    fun inicializar(context: Context) {
        database = AppDatabase.getDatabase(context)
        val preferencias = context.getSharedPreferences("contae_local", Context.MODE_PRIVATE)

        scope.launch {
            try {
                if (!preferencias.getBoolean("dados_iniciais_criados", false)) {
                    inserirDadosDemonstracaoSeNecessario()
                    preferencias.edit().putBoolean("dados_iniciais_criados", true).apply()
                }
                sincronizarListas()
            } catch (e: Exception) {
                Log.e(TAG, "Erro ao preparar o banco local", e)
            }
        }
    }

    private suspend fun inserirDadosDemonstracaoSeNecessario() {
        val categoriaDao = database.categoriaDao()
        val despesaDao = database.despesaDao()

        if (categoriaDao.buscarTodos().isEmpty()) {
            listOf(
                Categoria(nome = "Alimentação", limite = 850.0),
                Categoria(nome = "Transporte", limite = 500.0),
                Categoria(nome = "Moradia", limite = 1200.0),
                Categoria(nome = "Lazer", limite = 250.0),
                Categoria(nome = "Saúde", limite = 200.0)
            ).forEach { categoriaDao.inserir(it) }
        }

        if (despesaDao.buscarTodos().isEmpty()) {
            val idsPorNome = categoriaDao.buscarTodos().associate { it.nome to it.id }
            val exemplos = listOf(
                Triple("Mercado", 250.0, "Alimentação") to "04/10/2026",
                Triple("Uber", 35.0, "Transporte") to "03/10/2026",
                Triple("Cinema", 50.0, "Lazer") to "02/10/2026",
                Triple("Farmácia", 80.0, "Saúde") to "01/10/2026"
            )
            exemplos.forEach { (dados, data) ->
                val categoriaId = idsPorNome[dados.third] ?: return@forEach
                despesaDao.inserir(
                    Despesa(
                        descricao = dados.first,
                        valor = dados.second,
                        categoriaId = categoriaId,
                        data = data
                    )
                )
            }
        }
    }

    private suspend fun sincronizarListas() {
        val (listaCategorias, listaDespesas) = withContext(Dispatchers.IO) {
            database.categoriaDao().buscarTodos() to database.despesaDao().buscarTodos()
        }
        withContext(Dispatchers.Main) {
            categorias.clear()
            categorias.addAll(listaCategorias)
            despesas.clear()
            despesas.addAll(listaDespesas)
        }
    }

    suspend fun carregarTudo() {
        try {
            val (listaCategorias, listaDespesas) = withContext(Dispatchers.IO) {
                database.categoriaDao().buscarTodos() to database.despesaDao().buscarTodos()
            }
            categorias.clear()
            categorias.addAll(listaCategorias)
            despesas.clear()
            despesas.addAll(listaDespesas)
        } catch (e: Exception) {
            Log.e(TAG, "Erro ao carregar os dados", e)
        }
    }

    suspend fun carregarCategorias() {
        try {
            val lista = withContext(Dispatchers.IO) {
                database.categoriaDao().buscarTodos()
            }
            categorias.clear()
            categorias.addAll(lista)
        } catch (e: Exception) {
            Log.e(TAG, "Erro ao carregar categorias", e)
        }
    }

    suspend fun carregarDespesas() {
        try {
            val lista = withContext(Dispatchers.IO) {
                database.despesaDao().buscarTodos()
            }
            despesas.clear()
            despesas.addAll(lista)
        } catch (e: Exception) {
            Log.e(TAG, "Erro ao carregar despesas", e)
        }
    }

    suspend fun buscarCategoriaPorId(id: Int): Categoria? = withContext(Dispatchers.IO) {
        try {
            database.categoriaDao().buscarPorId(id)
        } catch (e: Exception) {
            Log.e(TAG, "Erro ao buscar categoria id=$id", e)
            null
        }
    }

    suspend fun buscarDespesaPorId(id: Int): Despesa? = withContext(Dispatchers.IO) {
        try {
            database.despesaDao().buscarPorId(id)
        } catch (e: Exception) {
            Log.e(TAG, "Erro ao buscar despesa id=$id", e)
            null
        }
    }

    fun salvarCategoria(categoria: Categoria) {
        scope.launch {
            try {
                if (categoria.id == 0) database.categoriaDao().inserir(categoria)
                else database.categoriaDao().atualizar(categoria)
                sincronizarListas()
            } catch (e: Exception) {
                Log.e(TAG, "Erro ao salvar categoria", e)
            }
        }
    }

    fun deletarCategoria(categoria: Categoria) {
        scope.launch {
            try {
                // Remove despesas associadas para não deixar registros órfãos.
                database.despesaDao().buscarPorCategoria(categoria.id)
                    .forEach { database.despesaDao().deletar(it) }
                database.categoriaDao().deletar(categoria)
                sincronizarListas()
            } catch (e: Exception) {
                Log.e(TAG, "Erro ao excluir categoria id=${categoria.id}", e)
            }
        }
    }

    fun salvarDespesa(despesa: Despesa) {
        scope.launch {
            try {
                if (despesa.id == 0) database.despesaDao().inserir(despesa)
                else database.despesaDao().atualizar(despesa)
                sincronizarListas()
            } catch (e: Exception) {
                Log.e(TAG, "Erro ao salvar despesa", e)
            }
        }
    }

    fun deletarDespesa(despesa: Despesa) {
        scope.launch {
            try {
                database.despesaDao().deletar(despesa)
                sincronizarListas()
            } catch (e: Exception) {
                Log.e(TAG, "Erro ao excluir despesa id=${despesa.id}", e)
            }
        }
    }
}
