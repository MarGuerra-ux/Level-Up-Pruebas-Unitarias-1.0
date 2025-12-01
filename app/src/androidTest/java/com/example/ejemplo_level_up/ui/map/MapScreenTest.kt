package com.example.ejemplo_level_up.ui.map

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * MapScreenTest
 *
 * PRUEBA #4 – Renderización visual
 *
 * Objetivo:
 * - Verificar que la pantalla del mapa (MapScreen) se renderiza correctamente
 *   mostrando:
 *   1) Un título visible.
 *   2) La dirección de la sucursal debajo del mapa.
 */

@RunWith(AndroidJUnit4::class)
class MapScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun mapScreen_muestraTituloYDireccion() {
        // ----- Arrange / Act -----
        composeRule.setContent {
            // Ajusta parámetros si tu MapScreen recibe otros
            MapScreen(
                onBack = {}
            )
        }

        // ----- Assert -----

        // 1) Título del mapa (cámbialo si en tu MapScreen el texto es otro)
        composeRule
            .onNodeWithText("Mapa de sucursales", substring = true)
            .assertIsDisplayed()

        // 2) Dirección exacta usada en tu MapScreen
        composeRule
            .onNodeWithText(
                "Av. Concha y Toro 2557, 8150215 Puente Alto, Región Metropolitana",
                substring = false
            )
            .assertIsDisplayed()
    }
}
