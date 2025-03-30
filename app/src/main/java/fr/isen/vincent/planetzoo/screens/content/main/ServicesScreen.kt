package fr.isen.vincent.planetzoo.screens.content.main

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import fr.isen.vincent.planetzoo.R
import fr.isen.vincent.planetzoo.components.services.ServiceInfoDialog
import fr.isen.vincent.planetzoo.components.services.ServiceItem
import fr.isen.vincent.planetzoo.components.services.getIconForService
import fr.isen.vincent.planetzoo.data.BiomeModel

@Composable
fun ServiceScreen(modifier: Modifier = Modifier) {
    val firebaseHelper = FirebaseHelper()
    val serviceListState = remember { mutableStateOf<Map<String, Pair<ServiceModel, Map<String, Int>>>>(emptyMap()) }

    val selectedService = remember { mutableStateOf<Pair<ServiceModel, Map<String, Int>>?>(null) }
    val context = LocalContext.current

    val biomeList = remember { mutableStateOf<List<BiomeModel>>(emptyList()) }


    firebaseHelper.fetchZooData { zooList ->
        val serviceMap = mutableMapOf<String, Pair<ServiceModel, MutableMap<String, Int>>>()

        biomeList.value = zooList

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

    Column(modifier = modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.padding(horizontal = 20.dp)) {
            Text(
                text = ContextCompat.getString(context, R.string.can_be_find),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                color =  Color(0xFFD7725D),
            )
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            contentPadding = PaddingValues(5.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(serviceListState.value.entries.toList()) { (_, data) ->
                ServiceItem(
                    service = data.first,
                    onClick = { serviceClicked ->
                        selectedService.value = data
                    }
                )
            }
        }

        selectedService.value?.let { (service, biomes) ->
            ServiceInfoDialog(
                service = service,
                biomes = biomes,
                allBiomes = biomeList.value,
                onDismiss = { selectedService.value = null }
            )
        }

    }
}
