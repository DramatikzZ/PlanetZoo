package fr.isen.vincent.planetzoo.screens.content.main

import androidx.compose.foundation.background
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import fr.isen.vincent.planetzoo.data.ServiceModel
import fr.isen.vincent.planetzoo.utils.FirebaseHelper
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.isen.vincent.planetzoo.components.ServiceItem
import fr.isen.vincent.planetzoo.utils.AppUtil

@Composable
fun ServiceScreen(modifier: Modifier = Modifier) {

    val firebaseHelper = FirebaseHelper()
    val serviceListState = remember { mutableStateOf<Map<String, Pair<ServiceModel, Map<String, Int>>>>(emptyMap()) }

    val expandedServiceName = remember { mutableStateOf<String?>(null) }

    firebaseHelper.fetchZooData { zooList ->
        val serviceMap = mutableMapOf<String, Pair<ServiceModel, MutableMap<String, Int>>>()

        zooList.forEach { biome ->
            biome.services.forEach { service ->
                val existingEntry = serviceMap[service.name]

                if (existingEntry != null) {
                    val updatedCount = existingEntry.first.count + service.count
                    val updatedBiomes = existingEntry.second.toMutableMap()
                    updatedBiomes[biome.name] = (updatedBiomes[biome.name] ?: 0) + service.count

                    serviceMap[service.name] = service.copy(count = updatedCount) to updatedBiomes
                } else {
                    serviceMap[service.name] = service to mutableMapOf(biome.name to service.count)
                }
            }
        }

        serviceListState.value = serviceMap
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
    ) {
        Row {
            Text(
                text = "Vous retrouverez dans le parc :",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                color = Color.Black,
            )
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            contentPadding = PaddingValues(5.dp),
            verticalArrangement = Arrangement.spacedBy(35.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(serviceListState.value.entries.toList()) { (serviceName, data) ->
                ServiceItem(
                    service = data.first,
                    biomes = data.second,
                    expanded = (expandedServiceName.value == serviceName),
                    onExpand = { selectedService ->
                        expandedServiceName.value = if (expandedServiceName.value == selectedService) null else selectedService
                    }
                )
            }
        }
    }
}
