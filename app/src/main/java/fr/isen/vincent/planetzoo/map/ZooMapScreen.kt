package fr.isen.vincent.planetzoo.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import fr.isen.vincent.planetzoo.R
import kotlin.math.sqrt

data class PointInteret(val name: String, val x: Float, val y: Float)

    val pointsZoo = listOf(
        PointInteret("Entrée du parc", 103.86534f, 529.28906f),
PointInteret("Toilettes 1", 81.06788f, 573.972f),
PointInteret("Café Nomade", 116.534256f, 577.92316f),
PointInteret("Accueil Billetterie", 128.34538f, 509.9401f),
PointInteret("Boutique", 104.72314f, 493.26367f),
PointInteret("Poste de Secours", 119.89944f, 475.94858f),
PointInteret("Restaurant du Parc Bergerie", 199.31113f, 511.27673f),
PointInteret("Parking Handicapé 1", 152.85841f, 439.28384f),
PointInteret("Parking Handicapé 2", 242.36566f, 550.61395f),
PointInteret("Gare des Cascades", 216.20302f, 464.61786f),
PointInteret("Enclos Perroquets/Ara", 190.00739f, 441.25912f),
PointInteret("Grand Hocco", 212.80484f, 430.6263f),
PointInteret("Panthère", 249.9868f, 441.25912f),
PointInteret("Panda Roux", 415.50757f, 560.6081f),
PointInteret("Lémurien", 457.73727f, 567.29034f),
PointInteret("Chèvre Naine", 429.85907f, 591.9271f),
PointInteret("Enclos Tortue", 491.52106f, 583.9661f),
PointInteret("Enclos Mouflon", 530.3856f, 571.2995f),
PointInteret("Binturong", 538.83154f, 527.9525f),
PointInteret("Loutre", 575.9805f, 535.2741f),
PointInteret("Point Pique-Nique 1", 521.93964f, 631.26434f),
PointInteret("Point Pique-Nique 2", 558.23083f, 613.30994f),
PointInteret("Point Pique-Nique 3", 600.4606f, 597.9701f),
PointInteret("Point d’Eau 1", 528.67f, 605.291f),
PointInteret("Café Nomade 2", 485.61548f, 603.9544f),
PointInteret("Cerf", 659.5822f, 591.9271f),
PointInteret("Macaque Crabier", 649.4536f, 565.9538f),
PointInteret("Vautour", 738.1361f, 569.2656f),
PointInteret("Antilope / Nilgaut / Daim", 829.3589f, 543.93164f),
PointInteret("Loup d’Europe", 902.83203f, 549.27734f),
PointInteret("Dromadaire", 902.83203f, 485.30338f),
PointInteret("Âne de Provence", 914.6432f, 467.29037f),
PointInteret("Bison", 875.8116f, 451.31186f),
PointInteret("Sortie de Secours 1", 956.0481f, 485.30338f),
PointInteret("Futur Plaine Africaine", 800.62286f, 423.3047f),
PointInteret("Mouton Noir / Yack", 755.02795f, 431.26495f),
PointInteret("Âne de Somalie / Watusi", 689.143f, 455.26303f),
PointInteret("Cigogne / Marabout", 599.6358f, 443.93228f),
PointInteret("Point de Vue 1", 650.31146f, 397.27344f),
PointInteret("Loup à Crinière", 570.07495f, 495.29752f),
PointInteret("Girafe", 512.6359f, 500.64325f),
PointInteret("Éléphant", 456.0547f, 500.64325f),
PointInteret("Varan de Komodo", 414.64975f, 501.97983f),
PointInteret("Hyène", 505.0478f, 473.2754f),
PointInteret("Tente Pédagogique", 528.67f, 460.60873f),
PointInteret("Zèbre", 551.5005f, 443.29297f),
PointInteret("Hippopotame", 551.5005f, 411.2767f),
PointInteret("Lion", 515.1763f, 395.93686f),
PointInteret("Pique-Nique 1", 472.9466f, 443.93228f),
PointInteret("Pique-Nique 2", 377.5008f, 475.94858f),
PointInteret("Lodge", 369.91263f, 459.9694f),
PointInteret("Gare du Plateau", 426.4939f, 453.2871f),
PointInteret("Ouistiti", 348.79776f, 487.97592f),
PointInteret("Crocodile Nain", 478.85214f, 360.60873f),
PointInteret("Casoar", 464.50064f, 384.60678f),
PointInteret("Tapir", 423.9535f, 410.63803f),
PointInteret("Samiri", 380.04117f, 419.2956f),
PointInteret("Suricate", 321.77734f, 421.9681f),
PointInteret("Fennec", 330.2233f, 407.96484f),
PointInteret("Gnou / Oryx / Rhinocéros", 337.81143f, 370.60287f),
PointInteret("Autruche / Gazelle", 385.12195f, 357.93622f),
PointInteret("Guépard", 443.38577f, 348.6393f),
PointInteret("Point de Vue 2", 461.10245f, 315.9837f),
PointInteret("Point de Vue 3", 284.5954f, 438.64453f),
PointInteret("Point de Vue 4", 231.37933f, 399.30728f),
PointInteret("Tigre", 747.4398f, 539.92255f),
PointInteret("Chien des Buissons", 720.3864f, 515.9251f),
PointInteret("Serval", 667.2033f, 531.9622f),
PointInteret("Lynx", 637.6425f, 529.28906f),
PointInteret("Ibis et Tortue", 625.7984f, 493.26367f),
PointInteret("Pécari", 687.46045f, 497.2728f),
PointInteret("Flamant Rose / Nandou / Tamanoir", 725.46716f, 464.61786f),
PointInteret("Émeu / Wallaby", 785.4466f, 471.3001f),
PointInteret("Porc-Épic", 808.2441f, 451.95053f)
)








val voisinsZoo = mapOf(
    "Café Nomade" to listOf("Toilettes 1"),
    "Restaurant du Parc Bergerie" to listOf("Boutique"),
    "Boutique" to listOf("Poste de Secours"),
    "Poste de Secours" to listOf("Entrée du parc"),
    "Entrée du parc" to listOf("Gare des Cascades"),
    "Gare des Cascades" to listOf("Enclos Perroquets/Ara", "Grand Hocco", "Panthère"),
    "Point de Vue 1" to listOf("Suricate"),

    "Point de Vue 2" to listOf("Gnou / Oryx / Rhinocéros"),
    "Suricate" to listOf("Gnou / Oryx / Rhinocéros", "Fennec", "Point Pique-Nique 1"),
    "Fennec" to listOf("Gnou / Oryx / Rhinocéros", "Samiri"),
    "Samiri" to listOf("Tapir"),
    "Gnou / Oryx / Rhinocéros" to listOf("Autruche / Gazelle"),
    "Autruche / Gazelle" to listOf("Guépard"),
    "Tapir" to listOf("Casoar"),
    "Casoar" to listOf("Crocodile Nain", "Lion"),
    "Guépard" to listOf("Crocodile Nain", "Point de Vue 3"),
    "Lion" to listOf("Hippopotame"),
    "Hippopotame" to listOf("Zèbre", "Point de Vue 4"),
    "Zèbre" to listOf("Tente Pédagogique"),
    "Tente Pédagogique" to listOf("Hyène"),
    "Hyène" to listOf("Loup à Crinière", "Girafe"),
    "Loup à Crinière" to listOf("Girafe"),
    "Girafe" to listOf("Éléphant", "Grives / Cercopithèque"),
    "Éléphant" to listOf("Varan de Komodo"),
    "Varan de Komodo" to listOf("Paillote 1"),
    "Paillote 1" to listOf("Gibbon"),
    "Gibbon" to listOf("Ouistiti"),
    "Ouistiti" to listOf("Capucin"),
    "Capucin" to listOf("Tamarin"),
    "Tamarin" to listOf("Toilettes 2"),

    "Toilettes 1" to listOf("Point d’Eau 2", "Lodge"),
    "Lodge" to listOf("Point Pique-Nique 2"),
    "Gare du Plateau" to listOf("Éléphant", "Varan de Komodo", "Point Pique-Nique 3"),
    "Point de Vue 4" to listOf("Marabout"),
    "Marabout" to listOf("Cigogne"),
    "Cigogne" to listOf("Âne de Somalie / Watusi"),
    "Âne de Somalie / Watusi" to listOf("Mouton Noir / Yack"),
    "Mouton Noir / Yack" to listOf("Futur Plaine Africaine"),
    "Futur Plaine Africaine" to listOf("Porc-Épic"),
    "Porc-Épic" to listOf("Émeu / Wallaby"),
    "Émeu / Wallaby" to listOf("Flamant Rose / Nandou / Tamanoir"),
    "Flamant Rose / Nandou / Tamanoir" to listOf("Pécari"),
    "Pécari" to listOf("Ibis et Tortue"),
    "Ibis et Tortue" to listOf("Point d’Eau 3"),
    "Point d’Eau 3" to listOf("Lynx"),
    "Lynx" to listOf("Serval"),
    "Serval" to listOf("Toilettes 3"),
    "Toilettes 3" to listOf("Chien des Buissons", "Pécari"),
    "Chien des Buissons" to listOf("Flamant Rose / Nandou / Tamanoir", "Tigre"),
    "Tigre" to listOf("Point d’Eau 4"),
    "Point d’Eau 4" to listOf("Vautour"),
    "Vautour" to listOf("Macaque Crabier"),
    "Macaque Crabier" to listOf("Cerf"),
    "Vautour" to listOf("Antilope / Nilgaut / Daim"),
    "Antilope / Nilgaut / Daim" to listOf("Loup d’Europe"),
    "Antilope / Nilgaut / Daim" to listOf("Dromadaire / Âne de Provence"),
    "Dromadaire / Âne de Provence" to listOf("Sortie de Secours 1"),
    "Bison" to listOf("Sortie de Secours 1")
)










@Composable
fun ZooMapScreen() {
    val context = LocalContext.current
    var imageWidth by remember { mutableStateOf(1) }
    var imageHeight by remember { mutableStateOf(1) }
    var selectedStart by remember { mutableStateOf<PointInteret?>(null) }
    var selectedEnd by remember { mutableStateOf<PointInteret?>(null) }
    var shortestPath by remember { mutableStateOf<List<PointInteret>>(emptyList()) }

    LaunchedEffect(selectedStart, selectedEnd) {
        if (selectedStart != null && selectedEnd != null) {
            shortestPath = dijkstra(voisinsZoo, pointsZoo, selectedStart!!, selectedEnd!!)
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Clique sur un point pour voir son nom et tracer un chemin")

        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.mapzoo),
                contentDescription = "Carte du zoo",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)
                    .onGloballyPositioned { layoutCoordinates ->
                        imageWidth = layoutCoordinates.size.width
                        imageHeight = layoutCoordinates.size.height
                    }
            )

            Canvas(modifier = Modifier.fillMaxSize()) {
                if (shortestPath.isNotEmpty()) {
                    for (i in 0 until shortestPath.size - 1) {
                        val start = Offset(
                            x = (shortestPath[i].x / 1000) * imageWidth,
                            y = (shortestPath[i].y / 1000) * imageHeight
                        )
                        val end = Offset(
                            x = (shortestPath[i + 1].x / 1000) * imageWidth,
                            y = (shortestPath[i + 1].y / 1000) * imageHeight
                        )

                        drawLine(
                            color = Color.Blue,
                            start = start,
                            end = end,
                            strokeWidth = 6f
                        )
                    }
                }

                for (point in pointsZoo) {
                    drawCircle(
                        color = when {
                            point == selectedStart -> Color.Green
                            point == selectedEnd -> Color.Green
                            else -> Color.Red
                        },
                        radius = 10f,
                        center = Offset(
                            x = (point.x / 1000) * imageWidth,
                            y = (point.y / 1000) * imageHeight
                        )
                    )
                }
            }


            Box(modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures { tapOffset ->
                        val clickedX = (tapOffset.x / imageWidth) * 1000
                        val clickedY = (tapOffset.y / imageHeight) * 1000

                        val nearestPoint = pointsZoo.minByOrNull { point ->
                            distance(point.x, point.y, clickedX, clickedY)
                        }

                        nearestPoint?.let {
                            if (selectedStart == null) {
                                selectedStart = it
                                Toast.makeText(context, "Départ sélectionné: ${it.name}", Toast.LENGTH_SHORT).show()
                            } else if (selectedEnd == null) {
                                selectedEnd = it
                                Toast.makeText(context, "Arrivée sélectionnée: ${it.name}", Toast.LENGTH_SHORT).show()
                            } else {

                                selectedStart = it
                                selectedEnd = null
                                shortestPath = emptyList()
                                Toast.makeText(context, "Nouveau départ sélectionné: ${it.name}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            )
        }
    }
}

// Fonction de calcul de la distance entre deux points
fun distance(x1: Float, y1: Float, x2: Float, y2: Float): Float {
    return sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2))
}

// Algorithme de Dijkstra pour trouver le plus court chemin
fun dijkstra(
    voisins: Map<String, List<String>>,
    points: List<PointInteret>,
    start: PointInteret,
    end: PointInteret
): List<PointInteret> {
    val distances = mutableMapOf<String, Float>().withDefault { Float.MAX_VALUE }
    val previous = mutableMapOf<String, String?>()
    val unvisited = points.map { it.name }.toMutableSet()

    distances[start.name] = 0f

    while (unvisited.isNotEmpty()) {
        val current = unvisited.minByOrNull { distances.getValue(it) } ?: break
        unvisited.remove(current)

        if (current == end.name) break

        val currentPoint = points.find { it.name == current } ?: continue
        val neighbors = voisins[current] ?: emptyList()

        for (neighbor in neighbors) {
            if (neighbor !in unvisited) continue
            val neighborPoint = points.find { it.name == neighbor } ?: continue

            val newDistance = distances.getValue(current) + distance(currentPoint.x, currentPoint.y, neighborPoint.x, neighborPoint.y)
            if (newDistance < distances.getValue(neighbor)) {
                distances[neighbor] = newDistance
                previous[neighbor] = current
            }
        }
    }

    val path = mutableListOf<PointInteret>()
    var step: String? = end.name

    while (step != null) {
        points.find { it.name == step }?.let { path.add(it) }
        step = previous[step]
    }

    if (path.isEmpty()) {
        Log.e("Dijkstra", "Aucun chemin trouvé entre ${start.name} et ${end.name}")
    }

    return path.reversed()
}


