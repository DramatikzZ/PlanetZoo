package fr.isen.vincent.planetzoo.components.services

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.isen.vincent.planetzoo.R
import fr.isen.vincent.planetzoo.data.ServiceModel


@Composable
fun ServiceItem(
    service: ServiceModel,
    onClick: (ServiceModel) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            contentAlignment = Alignment.TopEnd
        ) {
            Surface(color = Color.Transparent) {
                Button(
                    onClick = { onClick(service) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                ) {
                    Image(
                        painter = painterResource(id = getIconForService(service)),
                        contentDescription = service.name,
                        modifier = Modifier.size(48.dp)
                    )
                }
            }

            if (service.count > 0) {
                Box(
                    modifier = Modifier
                        .offset(x = -17.dp, y = (-2).dp)
                        .size(20.dp)
                        .background(Color(0xFFD7725D), CircleShape)
                        .border(1.dp, Color.White, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier
                            .offset(y = (-1).dp),
                        text = service.count.toString(),
                        fontSize = 12.sp,
                        color = Color.White
                    )
                }
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
