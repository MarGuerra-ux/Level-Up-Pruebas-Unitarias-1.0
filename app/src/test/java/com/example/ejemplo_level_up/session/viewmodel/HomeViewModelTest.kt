package com.example.ejemplo_level_up.session.viewmodel


import android.app.Application
import com.example.ejemplo_level_up.data.dao.GameDao
import com.example.ejemplo_level_up.data.model.Game
import com.example.ejemplo_level_up.data.repository.GameRepository
import com.example.ejemplo_level_up.session.MainDispatcherRule
import com.example.ejemplo_level_up.viewmodel.HomeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

/**
 * HomeViewModelTest
 *
 * PRUEBA UNITARIA #3
 *
 * Objetivo:
 * - Verificar que HomeViewModel expone la lista de juegos que viene desde el GameRepository.
 *
 * Estrategia:
 * - Creamos un GameDao en memoria (FakeGameDao).
 * - Lo envolvemos con un GameRepository real.
 * - Inyectamos ese repositorio al HomeViewModel.
 * - Comprobamos que "games" entrega exactamente la lista fake.
 */

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    // Necesario para que viewModelScope (Dispatchers.Main) funcione en tests unitarios
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `HomeViewModel expone la lista de juegos proveniente del repositorio`() = runTest {
        // ----- Arrange -----
        val fakeGames = listOf(
            Game(
                id = "1",
                title = "Catan",
                price = 29990,
                offerPrice = null,
                description = "Juego de mesa de estrategia.",
                imageResName = "catan"
            ),
            Game(
                id = "2",
                title = "Elden Ring",
                price = 59990,
                offerPrice = 49990,
                description = "Action RPG de mundo abierto.",
                imageResName = "elden_ring"
            )
        )

        // DAO en memoria
        val fakeDao = FakeGameDao()
        fakeDao.upsertAll(fakeGames)

        // Repositorio real usando el DAO fake
        val repository = GameRepository(fakeDao)

        // Application "dummy" para AndroidViewModel
        val app = Application()

        // ViewModel bajo prueba (inyectamos el repo que queremos)
        val viewModel = HomeViewModel(app, repository)

        // ----- Act -----
        // "games" es un StateFlow<List<Game>>, obtenemos el primer valor emitido
        val result = viewModel.games.first()

        // ----- Assert -----
        assertEquals(fakeGames, result)
    }
}

/**
 * FakeGameDao
 *
 * Implementación en memoria de GameDao para pruebas unitarias.
 * Usa MutableStateFlow para simular Room sin base de datos real.
 */
class FakeGameDao : GameDao {

    private val gamesFlow = MutableStateFlow<List<Game>>(emptyList())

    override fun getAll(): Flow<List<Game>> = gamesFlow

    override fun getById(id: String): Flow<Game?> =
        gamesFlow.map { list -> list.find { it.id == id } }

    override suspend fun upsertAll(items: List<Game>) {
        gamesFlow.value = items
    }
}
