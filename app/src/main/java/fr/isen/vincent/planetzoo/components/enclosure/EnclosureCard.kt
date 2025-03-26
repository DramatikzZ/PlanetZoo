package fr.isen.vincent.planetzoo.components.enclosure

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import fr.isen.vincent.planetzoo.data.EnclosureModel
import fr.isen.vincent.planetzoo.data.UserModel
import fr.isen.vincent.planetzoo.screens.content.main.animals.enclosure.AdminContent
import fr.isen.vincent.planetzoo.screens.content.main.animals.enclosure.VisitorContent

@Composable
fun EnclosureCard(
    enclosure: EnclosureModel,
    biomeColor: String,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        elevation = CardDefaults.cardElevation(6.dp),
    ) {
        Column(){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(android.graphics.Color.parseColor(biomeColor)))
                    .padding(16.dp),
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text(
                        text = "Enclos: ${enclosure.firebaseId}",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.Black
                    )
                    Text(
                        text = enclosure.animals.joinToString { it.name },
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black
                    )
                }

                IconButton(
                    onClick = {
                        navController.navigate("animals/${enclosure.id}")
                    },
                    modifier = Modifier.padding(start = 8.dp).clip(CircleShape).background(color = Color(0xFFD6725D))
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Ajouter",
                        tint = Color.White,
                    )
                }
            }


            Column(modifier = Modifier
                .padding(16.dp)) {
                if (UserModel.isAdmin) {
                    AdminContent(enclosure)
                } else {
                    VisitorContent(enclosure, biomeColor, navController)
                }
            }

        }
    }
}




