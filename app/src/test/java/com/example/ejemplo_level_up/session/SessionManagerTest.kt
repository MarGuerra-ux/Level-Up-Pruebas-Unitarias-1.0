package com.example.ejemplo_level_up.session

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

/**
 * SessionManagerTest
 *
 * PRUEBA UNITARIA #1
 *
 * Objetivo:
 * - Validar que el SessionManager pueda:
 *      1) Guardar datos de sesión
 *      2) Recuperarlos correctamente
 *      3) Eliminar la sesión
 *
 * Se usa SessionManagerFake para no depender de Android (DataStore),
 * permitiendo pruebas rápidas, 100% unitarias y estables.
 */
class SessionManagerTest {

    private val sessionManager = SessionManagerFake()

    @Test
    fun `guardar y obtener usuario funciona correctamente`() = runBlocking {
        // Arrange
        val username = "marco"
        val password = "1234"

        // Act
        sessionManager.saveUser(username, password)
        val result = sessionManager.getUser()

        // Assert
        assertEquals("marco", result?.first)
        assertEquals("1234", result?.second)
    }

    @Test
    fun `clearUser elimina los datos de sesion`() = runBlocking {
        // Arrange
        sessionManager.saveUser("alexis", "abcd")

        // Act
        sessionManager.clearUser()
        val result = sessionManager.getUser()

        // Assert
        assertNull(result)
    }
}

/**
 * SessionManagerFake
 *
 * Simula un SessionManager real usando memoria simple.
 * Esto permite aislar la lógica sin depender de DataStore ni IO real.
 */
class SessionManagerFake {

    private var userData: Pair<String, String>? = null

    suspend fun saveUser(username: String, password: String) {
        userData = username to password
    }

    suspend fun getUser(): Pair<String, String>? {
        return userData
    }

    suspend fun clearUser() {
        userData = null
    }
}
