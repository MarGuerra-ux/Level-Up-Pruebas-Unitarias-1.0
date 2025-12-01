package com.example.ejemplo_level_up.ui.map

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.ejemplo_level_up.ui.components.TopBar   // 👈 IMPORTA TU TOPBAR
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

// Coordenadas reales del DUOC UC Puente Alto
private val STORE_LOCATION = LatLng(-33.5983034, -70.5784048)

@SuppressLint("MissingPermission")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopBar(
                title = "Mapa de sucursales",   // 👈 TEXTO EXACTO QUE PIDE EL TEST
                onBack = onBack
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Mapa ocupa 3/4 de la pantalla
            AndroidView(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(3f),
                factory = { context ->
                    MapView(context).apply {
                        onCreate(null)
                        getMapAsync { googleMap ->
                            setupMap(googleMap)
                        }
                    }
                }
            )

            // Información de la sucursal (1/4 de pantalla)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Sucursal Puente Alto",
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Av. Concha y Toro 2557, 8150215 Puente Alto, Región Metropolitana"
                )

            }
        }
    }
}

private fun setupMap(map: GoogleMap) {
    map.uiSettings.apply {
        isZoomControlsEnabled = true
        isScrollGesturesEnabled = true
        isZoomGesturesEnabled = true
        isRotateGesturesEnabled = true
    }

    map.moveCamera(CameraUpdateFactory.newLatLngZoom(STORE_LOCATION, 18f))

    map.addMarker(
        MarkerOptions()
            .position(STORE_LOCATION)
            .title("Sucursal Level-Up")
            .snippet("Av. Concha y Toro 2557, Puente Alto")
    )
}

