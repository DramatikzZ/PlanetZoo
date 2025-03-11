package fr.isen.vincent.planetzoo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import fr.isen.vincent.planetzoo.ui.theme.PlanetZooTheme


import androidx.compose.runtime.*
import fr.isen.vincent.planetzoo.data.BiomeModel
import fr.isen.vincent.planetzoo.utils.FirebaseHelper

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val firebaseHelper = FirebaseHelper()
        val zooListState = mutableStateOf<List<BiomeModel>>(emptyList())

        firebaseHelper.fetchZooData { zooList ->
            zooListState.value = zooList
        }

        setContent {

            PlanetZooTheme {

                Scaffold (modifier = Modifier.fillMaxSize()){ innerPadding ->
                    AppNavigation(Modifier.padding(innerPadding))
                }
            }
        }
    }
}