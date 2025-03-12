package fr.isen.vincent.planetzoo.components

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.isen.vincent.planetzoo.R
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
            .clip(if (expanded) RoundedCornerShape(20.dp) else RoundedCornerShape(0.dp))
            .background(if (expanded) colorResource(R.color.ecru) else Color.Transparent)
            .animateContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Surface(
            color = Color.Transparent,
        ) {
            Button(
                onClick = { onExpand(service.name) },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
            ) {
                Image(
                    painter = painterResource(id = getIconForService(service)),
                    contentDescription = service.name,
                    modifier = Modifier.size(48.dp)
                )
            }
        }

        Spacer(modifier = Modifier.size(5.dp))

        Text(
            text = service.name,
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.padding(10.dp)
        )

        if (expanded) {
            Spacer(modifier = Modifier.size(8.dp))
            /*val totalInstances = biomes.values.sum()
            Text(
                text = "Instances: $totalInstances",
                fontSize = 14.sp,
                color = Color.DarkGray
            )*/
            Column (
                modifier = Modifier.padding(10.dp)
            ){
                biomes.forEach { (biome, count) ->
                    if (count > 0) {
                        HorizontalDivider()
                        Text(
                            text = "$biome: $count",
                            fontSize = 12.sp,
                            textAlign = TextAlign.Start,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}

fun getIconForService(service: ServiceModel): Int {
    return when (service.icon) {
        "small_cafe" -> R.drawable.small_cafe
        "shop" -> R.drawable.shop
        "paillote" -> R.drawable.paillote
        "station" -> R.drawable.station
        "picnic_area" -> R.drawable.picnic_area
        "water_point" -> R.drawable.water_point
        "nomadic_cafe" -> R.drawable.nomadic_cafe
        "viewpoint" -> R.drawable.viewpoint
        "educational_tent" -> R.drawable.educational_tent
        "toilets" -> R.drawable.toilets
        "lodge" -> R.drawable.lodge
        "playground" -> R.drawable.playground
        else -> R.drawable.animals_icon_clicked
    }
}
