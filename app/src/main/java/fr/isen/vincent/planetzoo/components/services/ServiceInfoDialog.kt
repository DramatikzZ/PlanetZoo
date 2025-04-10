package fr.isen.vincent.planetzoo.components.services

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.isen.vincent.planetzoo.data.BiomeModel
import fr.isen.vincent.planetzoo.data.ServiceModel


@Composable
fun ServiceInfoDialog(
    service: ServiceModel,
    biomes: Map<String, Int>,
    allBiomes: List<BiomeModel>,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {},
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = service.name,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFD7725D),
                    fontSize = 18.sp
                )
                IconButton(onClick = onDismiss) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Fermer"
                    )
                }
            }
        },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = getIconForService(service)),
                    contentDescription = null,
                    modifier = Modifier
                        .size(72.dp)
                        .padding(12.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    biomes.forEach { (biomeName, count) ->
                        if (count > 0) {
                            val biome = allBiomes.find { it.name == biomeName }
                            val biomeColor = try {
                                Color(android.graphics.Color.parseColor(biome?.color ?: "#CCCCCC"))
                            } catch (_: Exception) {
                                Color.LightGray
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        color = biomeColor.copy(alpha = 0.2f),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(12.dp)
                                        .background(biomeColor, CircleShape)
                                )

                                Spacer(modifier = Modifier.width(10.dp))

                                Text(
                                    text = "$biomeName : $count",
                                    fontSize = 14.sp,
                                    color = Color(0xFF796D47)
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}


