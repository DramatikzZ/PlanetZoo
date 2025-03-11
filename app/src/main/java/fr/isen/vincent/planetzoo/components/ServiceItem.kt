package fr.isen.vincent.planetzoo.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.isen.vincent.planetzoo.data.ServiceModel
import fr.isen.vincent.planetzoo.utils.AppUtil


@Composable
fun ServiceItem(
    service: ServiceModel,
    biomes: Map<String, Int>,
    expanded: Boolean,
    onExpand: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(if (expanded) Color.Cyan else Color.Transparent)
            .animateContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Surface(
            shape = if (expanded) MaterialTheme.shapes.medium else CircleShape,
            color = if (expanded) Color.Transparent else Color.Cyan,
            modifier = Modifier
                .fillMaxWidth(if (expanded) 1f else 0.3f)
                .height(if (expanded) 80.dp else 64.dp)

        ) {
            IconButton(
                onClick = { onExpand(service.name) },
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = service.name,
                    modifier = Modifier.size(if (expanded) 48.dp else 32.dp),
                    tint = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.size(5.dp))

        Text(
            text = service.name,
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            color = Color.Black
        )

        if (expanded) {
            Spacer(modifier = Modifier.size(8.dp))
            val totalInstances = biomes.values.sum()
            Text(
                text = "Instances: $totalInstances",
                fontSize = 14.sp,
                color = Color.DarkGray
            )
            Column {
                biomes.forEach { (biome, count) ->
                    if (count > 0) {
                        Text(
                            text = "$biome: $count",
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}
