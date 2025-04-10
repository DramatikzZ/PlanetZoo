package fr.isen.vincent.planetzoo.screens.content.main

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import fr.isen.vincent.planetzoo.R

@Composable
fun SecurityScreen(modifier: Modifier = Modifier, navController: NavController) {
    val context = LocalContext.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = ContextCompat.getString(context, R.string.need_help),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            color = Color(0xFFD6725D),
        )
        Spacer(modifier = Modifier.size(15.dp))
        Column (
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceEvenly
        ){
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.clickable{
                    navController.navigate("zooMap/map")
                }
            ){
                Button(
                    onClick = { navController.navigate("zooMap/map") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.first_aid_station_icon),
                        contentDescription = ContextCompat.getString(context, R.string.first_aid_station),
                        modifier = Modifier.size(48.dp)
                    )
                }

                Spacer(modifier = Modifier.size(5.dp))

                Text(
                    text = ContextCompat.getString(context, R.string.first_aid_station),
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            Spacer(modifier = Modifier.size(5.dp))

            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.clickable{
                    navController.navigate("zooMap/map")
                }
            ){
                Button(
                    onClick = { navController.navigate("zooMap/map") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.emergency_exit_icon),
                        contentDescription = ContextCompat.getString(context, R.string.emergency_exit),
                        modifier = Modifier.size(48.dp)
                    )
                }

                Spacer(modifier = Modifier.size(5.dp))

                Text(
                    text = ContextCompat.getString(context, R.string.emergency_exit),
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            Spacer(modifier = Modifier.size(5.dp))
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.clickable{
                    navController.navigate("zooMap/map")
                }
            ){
                Button(
                    onClick = { navController.navigate("zooMap/map") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.assembly_point_icon),
                        contentDescription = ContextCompat.getString(context, R.string.assembly_point),
                        modifier = Modifier.size(48.dp)
                    )
                }

                Spacer(modifier = Modifier.size(5.dp))

                Text(
                    text = ContextCompat.getString(context, R.string.assembly_point),
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

        }
        Spacer(modifier = Modifier.size(15.dp))
        EmergencyAlertSection(ContextCompat.getString(context, R.string.phone_number))

    }
}

@Composable
fun EmergencyAlertSection(phoneNumber: String) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardColors(
            containerColor =  Color(0xFFF5EEE2),
            contentColor = Color.Black,
            disabledContentColor =  Color(0xFFD2C6A1),
            disabledContainerColor =  Color(0xFFD2C6A1),

        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.warning_icon),
                    contentDescription =ContextCompat.getString(context, R.string.warning),
                    tint = Color.Red,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = ContextCompat.getString(context, R.string.cas_urgence),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color(0xFFD6725D),
                )
            }

            Text(
                text = ContextCompat.getString(context, R.string.merci),
                textAlign = TextAlign.Start,
                fontSize = 14.sp,
                color = Color.Black
            )

            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_DIAL).apply {
                        data = Uri.parse("tel:$phoneNumber")
                    }
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.phone_icon),
                    contentDescription = ContextCompat.getString(context, R.string.appeler),
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = phoneNumber,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}