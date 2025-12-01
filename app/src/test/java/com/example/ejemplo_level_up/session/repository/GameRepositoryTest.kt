package com.example.ejemplo_level_up.data.session.repository



import com.example.ejemplo_level_up.data.dao.GameDao
import com.example.ejemplo_level_up.data.model.Game
import com.example.ejemplo_level_up.data.repository.GameRepository
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import org.junit.jupiter.api.Assertions.assertEquals

/**
 * GameRepositoryTest
 *
 * PRUEBA UNITARIA #2
 *
 * Objetivo:
 * 1) Verificar que GameRepository entrega la lista de juegos desde el DAO.
 * 2) Verificar que GameRepository obtiene correctamente un juego por ID.
 *
 * Estrategia:
 * - Mockeamos GameDao (sin usar Room real).
 * - Simulamos Flows con values fake.
 * - Comprobamos allGames() y gameById() del repositorio.
 */
class GameRepositoryTest : StringSpec({

    "allGames debe devolver la lista simulada de juegos" {

        // ----- Arrange -----
        val fakeGames = listOf(
            Game(
                id = "1",
                title = "Catan",
                price = 29990,
                offerPrice = null,
                description = "Juego de mesa de estrategia",
                imageResName = "catan"
            ),
            Game(
                id = "2",
                title = "Elden Ring",
                price = 59990,
                offerPrice = 59990,
                description = "Action RPG mundo abierto",
                imageResName = "elden_ring"
            )
        )

        // Mock del DAO
        val gameDao = mockk<GameDao>()

        // getAll() devuelve Flow<List<Game>>
        every { gameDao.getAll() } returns flowOf(fakeGames)

        // Crear repositorio usando DAO mockeado
        val repository = GameRepository(gameDao)

        // ----- Act -----
        val result = repository.allGames().first()

        // ----- Assert -----
        result shouldContainExactly fakeGames
    }

    "gameById debe devolver el juego correcto según el id" {

        // ----- Arrange -----
        val fakeGame = Game(
            id = "10",
            title = "The Legend of Zelda: BOTW",
            price = 59990,
            offerPrice = null,
            description = "Aventura mundo abierto",
            imageResName = "zelda_botw"
        )

        val gameDao = mockk<GameDao>()

        // getById() devuelve Flow<Game?>
        every { gameDao.getById("10") } returns flowOf(fakeGame)

        val repository = GameRepository(gameDao)

        // ----- Act -----
        val result = repository.gameById("10").first()

        // ----- Assert -----
        assertEquals("The Legend of Zelda: BOTW", result?.title)
        assertEquals("10", result?.id)
    }
})
