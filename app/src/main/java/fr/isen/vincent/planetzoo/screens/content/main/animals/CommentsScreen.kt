package fr.isen.vincent.planetzoo.screens.content.main.animals

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import fr.isen.vincent.planetzoo.data.CommentModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentsScreen(navController: NavController) {
    val fakeComments = listOf(
        CommentModel(1, "Super enclos, mais les animaux ont pas l'air heureux !"),
        CommentModel(2, "Pas ouf !"),
        CommentModel(3, "Super !"),
        CommentModel(4, "Nul !")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Commentaires") }, // Plus de référence à enclosureId
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.Clear, contentDescription = "Retour")
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            LazyColumn(modifier = Modifier.padding(16.dp)) {
                items(fakeComments) { comment ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        elevation = CardDefaults.cardElevation(6.dp)
                    ) {
                        Text(
                            text = comment.comment,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}

