package fr.isen.vincent.planetzoo.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import fr.isen.vincent.planetzoo.R
import kotlin.math.sqrt


const val IMAGE_WIDTH_ORIG = 1800f
const val IMAGE_HEIGHT_ORIG = 850f

data class PointInteret(val id: Int, val name: String, val x: Float, val y: Float)

val pointsZoo = listOf(
    PointInteret(1, "01 - Café Nomade", 211.10895f, 676.3764f),
    PointInteret(2, "02 - Toilettes 1", 147.50395f, 662.51376f),
    PointInteret(3, "03 - Entrée Bergerie", 180.93735f, 560.58267f),
    PointInteret(4, "04 - Accueil / Boutique", 239.64966f, 515.73297f),
    PointInteret(5, "05 - Poste de Sécurité", 219.26344f, 435.819f),
    PointInteret(6, "06 - Entrée du Parc", 372.5678f, 447.2353f),
    PointInteret(7, "07 - Gare des Cascades", 405.18576f, 402.3856f),
    PointInteret(8, "08 - Ara / Perroquet", 349.73524f, 354.27414f),
    PointInteret(9, "09 - Grand Hocco", 393.76947f, 317.57895f),
    PointInteret(10, "10 - Panthère", 458.18994f, 345.3042f),
    PointInteret(11, "11 - Point de Vue 1", 527.50305f, 346.11963f),
    PointInteret(12, "12 - Suricate", 593.55444f, 306.16266f),
    PointInteret(13, "13 - Rhinocéros / Onyx / Gnou", 576.43f, 234.40317f),
    PointInteret(14, "14 - Point de Vue 2", 432.09558f, 250.71214f),
    PointInteret(15, "15 - Fennec", 609.8634f, 271.9138f),
    PointInteret(16, "16 - Samiri", 698.7473f, 283.33008f),
    PointInteret(17, "17 - Tapir", 780.2922f, 266.20566f),
    PointInteret(18, "18 - Casoar", 854.49805f, 214.01695f),
    PointInteret(19, "19 - Crocodile Nain", 900.16315f, 155.30463f),
    PointInteret(20, "20 - Guépard", 820.2492f, 116.97854f),
    PointInteret(21, "21 - Autruche / Gazelle", 737.88885f, 147.15015f),
    PointInteret(22, "22 - Point de Vue 1", 856.1289f, 55.819885f),
    PointInteret(23, "23 - Lion", 947.45917f, 235.21861f),
    PointInteret(24, "24 - Hippopotame", 1019.2187f, 275.1756f),
    PointInteret(25, "25 - Zèbre", 1020.84955f, 350.1969f),
    PointInteret(26, "26 - Tente pédagogique", 988.2316f, 394.23112f),
    PointInteret(27, "27 - Hyène", 947.45917f, 417.0637f),
    PointInteret(28, "28 - Loup à crinière", 1053.4675f, 470.8833f),
    PointInteret(29, "29 - Girafe", 918.91846f, 492.085f),
    PointInteret(30, "30 - Grivet ", 837.3736f, 535.3038f),
    PointInteret(31, "31 - Eléphant", 848.7899f, 488.82318f),
    PointInteret(32, "32 - Varan de Komodo", 774.58405f, 487.1923f),
    PointInteret(33, "33 - Paillotte", 699.56274f, 506.76306f),
    PointInteret(34, "34 - Gibbon", 693.0392f, 468.437f),
    PointInteret(35, "35 - Ouistiti", 674.2838f, 460.28247f),
    PointInteret(36, "36 - Capucin", 656.344f, 439.0808f),
    PointInteret(37, "37 - Tamarin", 638.4041f, 419.51004f),
    PointInteret(38, "38 - Toilette", 702.0091f, 425.21817f),
    PointInteret(39, "39 - Point d'eau", 595.1853f, 402.3856f),
    PointInteret(40, "40 - Lodge", 599.2626f, 368.9522f),
    PointInteret(41, "41 - Espace pique-nique", 679.992f, 386.07663f),
    PointInteret(42, "42 - Plateau des jeux ", 718.31805f, 343.6733f),
    PointInteret(43, "43 - Gare", 789.26215f, 377.92215f),
    PointInteret(44, "44 - Point de rassemblement", 874.0688f, 373.02945f),
    PointInteret(45, "45 - Plateau des jeux", 879.7769f, 315.94803f),
    PointInteret(46, "46 - Point d'eau ", 856.1289f, 598.0933f),
    PointInteret(47, "47 - Point de vue", 764.79865f, 574.4453f),
    PointInteret(48, "48 - Panda roux", 769.69135f, 629.0804f),
    PointInteret(49, "49 - Lémurien", 836.55817f, 646.2048f),
    PointInteret(50, "50 - Chèvre naine", 802.3093f, 713.887f),
    PointInteret(51, "51 - Tortue", 908.3176f, 681.26904f),
    PointInteret(52, "52 - Café Nomade", 898.5323f, 742.42773f),
    PointInteret(53, "53 - Mouflon", 990.678f, 670.6682f),
    PointInteret(54, "54 - Point d'eau", 974.369f, 739.9814f),
    PointInteret(55, "55 - Espace pique-nique", 969.4763f, 804.40186f),
    PointInteret(56, "56 - Espace pique-nique", 1043.6821f, 764.4449f),
    PointInteret(57, "57 - Espace pique-nique", 1113.8108f, 726.9342f),
    PointInteret(58, "58 - Loutre/Binturong", 1037.974f, 569.5526f),
    PointInteret(59, "59 - Macaque crabier", 1177.4158f, 629.0804f),
    PointInteret(60, "60 - Cerf", 1267.9305f, 712.2561f),
    PointInteret(61, "61 - Vautour", 1384.5397f, 657.62103f),
    PointInteret(62, "62 - Nigult/Daim/Antilope", 1563.123f, 548.35095f),
    PointInteret(63, "63 - Loup d'Europe", 1705.8265f, 586.67706f),
    PointInteret(64, "64 - Dromadaire/Âne de Provence", 1688.7021f, 421.9564f),
    PointInteret(65, "65 - Bison", 1695.2257f, 334.70337f),
    PointInteret(66, "66 - Espace pique-nique", 1586.771f, 445.6044f),
    PointInteret(67, "67 - Porc-épic", 1530.505f, 368.13675f),
    PointInteret(68, "68 - Futur plaine africaine", 1517.4579f, 301.26996f),
    PointInteret(69, "69 - Mouton noir/Yack", 1373.9388f, 334.70337f),
    PointInteret(70, "70 - Watusi/Âne de Somalie/Onyx", 1213.2955f, 386.07663f),
    PointInteret(71, "71 - Cigogne", 1119.5189f, 362.42862f),
    PointInteret(72, "72 - Point de vue", 1220.6345f, 251.52759f),
    PointInteret(73, "73 - Tortue/Ibis", 1182.3085f, 476.59146f),
    PointInteret(74, "74 - Pécasi", 1268.746f, 465.17517f),
    PointInteret(75, "75 - Flamant rose/Nandou/Tamanoir", 1364.969f, 442.3426f),
    PointInteret(76, "76 - Point d'eau", 1161.9222f, 516.54846f),
    PointInteret(77, "77 - Lama", 1169.2612f, 554.8745f),
    PointInteret(78, "78 - Serval", 1242.6516f, 564.6599f),
    PointInteret(79, "79 - Toilette", 1277.7159f, 536.1192f),
    PointInteret(80, "80 - Chien des buisson", 1357.6299f, 528.78015f),
    PointInteret(81, "81 - Tigre", 1376.3853f, 576.0762f),
    PointInteret(82, "82 - Point d'eau", 1427.7585f, 612.7714f),
    PointInteret(83, "83 - Restaurant du parc", 373.38327f, 523.88745f),
    PointInteret(84, "84 - Parkin handicapé", 276.34485f, 334.70337f),
    PointInteret(85, "85 - Parking handicapé", 459.00537f, 620.9259f),
    PointInteret(86, "86 - Point de rassemblement", 273.8985f, 594.01605f),
    PointInteret(87, "87 - Sortie de secours 1", 336.68805f, 408.9092f),
    PointInteret(88, "88 - Sortie de secours 2", 887.116f, 812.55634f),
    PointInteret(89, "89 - Point de rassemblement", 561.7519f, 763.6294f),
    PointInteret(90, "90 - Sortie de secours 3", 1787.3715f, 457.02066f),
    PointInteret(91, "91 - Sortie de secours 4", 1813.4658f, 293.9309f),
    PointInteret(92, "92 - Point de rassemblement", 1546.814f, 409.72464f),
    PointInteret(94, "94 - Enclos Émeu / Wallaby", 1455.4838f, 419.51004f),
    PointInteret(95, "95 - Point d’Eau / Aire de Pique-Nique", 1430.4597f, 480.50967f)
)



val voisinsZoo = mapOf(
    1 to listOf(2, 86, 3, 85, 89, 83),
    2 to listOf(1, 3),
    3 to listOf(2, 86, 4),
    4 to listOf(3, 5),
    5 to listOf(4, 87, 6),
    6 to listOf(5, 87, 7),
    7 to listOf(6, 8, 9, 10, 50, 48, 11),
    8 to listOf(84, 9, 87, 7),
    9 to listOf(8, 7, 10),
    10 to listOf(9, 7, 11),
    11 to listOf(10, 12, 40),
    12 to listOf(11, 13, 15, 14),
    13 to listOf(14, 15, 12),
    14 to listOf(13, 12),
    15 to listOf(13, 16, 12, 42),
    16 to listOf(17, 15, 42),
    17 to listOf(16, 18, 45),
    18 to listOf(19, 20, 23, 45),
    19 to listOf(18, 20),
    20 to listOf(22, 19, 21),
    21 to listOf(20, 13),
    22 to listOf(20),
    23 to listOf(18, 45, 24),
    24 to listOf(23, 72, 25),
    25 to listOf(71, 26),
    26 to listOf(27, 28),
    27 to listOf(26, 29, 43),
    28 to listOf(29, 73, 76),
    29 to listOf(58, 30, 31, 27, 43, 32),
    30 to listOf(46, 47, 33, 29, 58, 32),
    31 to listOf(32, 29, 38, 43),
    32 to listOf(33, 34, 43),
    33 to listOf(47, 32, 35, 43),
    34 to listOf(35, 33),
    35 to listOf(34, 36),
    36 to listOf(38, 35, 37),
    37 to listOf(38, 36),
    38 to listOf(39, 37, 41),
    39 to listOf(38, 11, 40, 41),
    40 to listOf(11, 39, 41),
    41 to listOf(40, 12, 11, 40, 42, 37),
    42 to listOf(16, 41, 43, 45),
    43 to listOf(93, 44, 38, 32, 31, 29, 27, 33, 34),
    44 to listOf(45, 93, 27),
    45 to listOf(17, 18, 23, 93, 44),
    46 to listOf(30),
    47 to listOf(33, 46),
    48 to listOf(6, 7, 49),
    49 to listOf(48, 50, 51),
    50 to listOf(7, 6, 48, 49),
    51 to listOf(49, 52, 53),
    52 to listOf(54, 50, 88, 51),
    53 to listOf(51, 54, 58, 59),
    54 to listOf(52, 51, 56, 53),
    55 to listOf(52, 54, 56),
    56 to listOf(54, 55, 57),
    57 to listOf(60, 56, 56),
    58 to listOf(29, 30, 53),
    59 to listOf(53, 57, 60, 61),
    60 to listOf(59, 61, 57),
    61 to listOf(82, 60, 59, 82),
    62 to listOf(81, 82, 66),
    63 to listOf(61, 62, 66),
    64 to listOf(66, 92, 90, 65),
    65 to listOf(64, 66, 92, 91),
    66 to listOf(62, 64, 65, 92),
    67 to listOf(68, 94, 92, 65),
    68 to listOf(69, 67),
    69 to listOf(68, 70, 75, 94),
    70 to listOf(69, 28),
    71 to listOf(25, 72, 24),
    72 to listOf(71, 24, 25),
    73 to listOf(28, 76, 74, 79),
    74 to listOf(79, 73, 80),
    75 to listOf(69, 70, 94),
    76 to listOf(73, 28, 79),
    77 to listOf(78, 96),
    78 to listOf(77, 96, 79, 82),
    79 to listOf(80, 74, 76, 73),
    80 to listOf(79, 74, 95),
    81 to listOf(78, 61, 82),
    82 to listOf(62, 61, 81, 78),
    83 to listOf(1, 86, 85, 6),
    84 to listOf(87),
    85 to listOf(83, 86, 89, 1),
    86 to listOf(1, 3, 89, 83, 4, 85),
    87 to listOf(8, 7, 6),
    88 to listOf(55, 55),
    89 to listOf(85, 88),
    90 to listOf(64, 66),
    91 to listOf(67, 92),
    92 to listOf(67, 91, 65),
    93 to listOf(44, 45, 43),
    94 to listOf(75, 67, 69, 95),
    95 to listOf(75, 80, 94),
    96 to listOf(77, 78, 59)
)


@Composable
fun ZooMapScreen(startInMode: String? = null, navController: NavController) {
    val context = LocalContext.current
    var imageWidth by remember { mutableStateOf(1) }
    var imageHeight by remember { mutableStateOf(1) }
    var selectedStart by remember { mutableStateOf<PointInteret?>(null) }
    var selectedEnd by remember { mutableStateOf<PointInteret?>(null) }
    var shortestPath by remember { mutableStateOf<List<PointInteret>>(emptyList()) }
    var currentMode by remember { mutableStateOf(startInMode) }

    val imageBitmap = ImageBitmap.imageResource(R.drawable.mapzoo2)
    val originalWidth = imageBitmap.width.toFloat()
    val originalHeight = imageBitmap.height.toFloat()

    val scrollState = rememberScrollState()


    LaunchedEffect(selectedStart, selectedEnd) {
        if (selectedStart != null && selectedEnd != null) {
            shortestPath = dijkstra(voisinsZoo, pointsZoo, selectedStart!!.id, selectedEnd!!.id)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp).padding(top=20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (currentMode == null) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top,
            ) {

                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.Clear, contentDescription = "Retour", tint =Color(0xFF796D47), )
                }
                Spacer(modifier = Modifier.size(16.dp))
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "Choisissez un mode d'itinéraire :",
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    color = Color(0xFFD7725D),
                )
            }


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceEvenly,
            ) {
                ModeCard("🗺 Voir la carte \uD83D\uDDFA", Color(0xFFE2A59D)) {
                    currentMode = "map"
                }
                Spacer(modifier = Modifier.size(16.dp))
                ModeCard("📋 Choisir un lieu \uD83D\uDCCB", Color(0xFFE2CA9D)) {
                    currentMode = "list"
                }
            }
        } else {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "🦓 Plan du zoo",
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    color = Color(0xFFD7725D),
                )
                Text(
                    text = "←",
                    fontSize = 22.sp,
                    modifier = Modifier
                        .clickable {
                            currentMode = null
                            selectedStart = null
                            selectedEnd = null
                            shortestPath = emptyList()
                        }
                        .padding(8.dp),
                    color = Color(0xFF796D47),
                    fontWeight = FontWeight.Bold
                )

            }

            Spacer(modifier = Modifier.height(16.dp))

            if (currentMode == "map") {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(originalWidth / originalHeight)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.mapzoo2),
                        contentDescription = "Carte du zoo",
                        modifier = Modifier
                            .fillMaxSize()
                            .onGloballyPositioned {
                                imageWidth = it.size.width
                                imageHeight = it.size.height
                            }
                    )

                    Canvas(modifier = Modifier.fillMaxSize()) {
                        if (shortestPath.isNotEmpty()) {
                            for (i in 0 until shortestPath.size - 1) {
                                val start = Offset(
                                    x = (shortestPath[i].x / originalWidth) * imageWidth,
                                    y = (shortestPath[i].y / originalHeight) * imageHeight
                                )
                                val end = Offset(
                                    x = (shortestPath[i + 1].x / originalWidth) * imageWidth,
                                    y = (shortestPath[i + 1].y / originalHeight) * imageHeight
                                )
                                drawLine(
                                    color = Color(0xFF7AFFE7),
                                    start = start,
                                    end = end,
                                    strokeWidth = 6f
                                )
                            }
                        }

                        for (point in pointsZoo) {
                            drawCircle(
                                color = getPointColor(point, selectedStart, selectedEnd)
                                ,
                                radius = 10f,
                                center = Offset(
                                    x = (point.x / originalWidth) * imageWidth,
                                    y = (point.y / originalHeight) * imageHeight
                                )
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .pointerInput(Unit) {
                                detectTapGestures { tapOffset ->
                                    val clickedX = (tapOffset.x / imageWidth) * originalWidth
                                    val clickedY = (tapOffset.y / imageHeight) * originalHeight

                                    val nearestPoint = pointsZoo.minByOrNull { point ->
                                        distance(point.x, point.y, clickedX, clickedY)
                                    }

                                    nearestPoint?.let {
                                        if (selectedStart == null) {
                                            selectedStart = it
                                            Toast.makeText(context, "Départ : ${it.name}", Toast.LENGTH_SHORT).show()
                                        } else if (selectedEnd == null) {
                                            selectedEnd = it
                                            Toast.makeText(context, "Arrivée : ${it.name}", Toast.LENGTH_SHORT).show()
                                        } else {
                                            selectedStart = it
                                            selectedEnd = null
                                            shortestPath = emptyList()
                                            Toast.makeText(context, "Nouveau départ : ${it.name}", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }
                            }
                    )

                }
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Canvas(modifier = Modifier.size(8.dp)) {
                            drawCircle(Color(0xFF4F96FF))
                        }
                        Spacer(modifier = Modifier.width(4.dp))

                        Text("Point de rassemblement", fontSize = 8.sp)
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Canvas(modifier = Modifier.size(8.dp)) {
                            drawCircle(Color(0xFFFF4E41))
                        }
                        Spacer(modifier = Modifier.width(4.dp))

                        Text("Poste de Sécurité", fontSize = 8.sp)
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Canvas(modifier = Modifier.size(8.dp)) {
                            drawCircle(Color(0xFF38FF78))
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Sortie de secours", fontSize = 8.sp)
                    }
                }

            } else if (currentMode == "list") {
                DropdownSelector("Départ", selectedStart) { selectedStart = it }
                Spacer(modifier = Modifier.height(8.dp))
                DropdownSelector("Arrivée", selectedEnd) { selectedEnd = it }
            }

            if (shortestPath.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))


                var totalDistance = 0f
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(scrollState)
                        .padding(top = 8.dp)
                ) {
                    Text("🧭 Itinéraire :",
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        color = Color(0xFFD7725D)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    shortestPath.windowed(2).forEachIndexed { i, (a, b) ->
                        totalDistance += distance(a.x, a.y, b.x, b.y)

                        Column {

                            Text(
                                text = "➡️ Étape ${i + 1} :",
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFD6725D),
                                fontSize = 16.sp
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Row{
                                Text(
                                    text = "De : ",
                                    fontSize = 16.sp
                                )
                                Text(
                                    text = "« ${a.name} »",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                            }
                            Row{
                                Text(
                                    text = "À  : ",
                                    fontSize = 16.sp
                                )
                                Text(
                                    text = "« ${b.name} »",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                            }


                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    val distanceMeters = totalDistance
                    val walkingSpeed = 1.33f
                    val estimatedTimeSeconds = distanceMeters / walkingSpeed
                    val minutes = (estimatedTimeSeconds / 60).toInt()
                    val seconds = (estimatedTimeSeconds % 60).toInt()

                    Text("📏 Distance estimée : ${"%.0f".format(distanceMeters)} m",
                        fontWeight = FontWeight.Bold,
                        color =Color(0xFF796D47),
                        fontSize = 16.sp)
                    Text("⏱ Temps estimé : ${minutes} min ${seconds} sec",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF796D47),
                        fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(20.dp))
                }




            }
        }
    }
}



@Composable
fun ModeCard(text: String, color: Color, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(color, RoundedCornerShape(16.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(text, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
    }
}

@Composable
fun DropdownSelector(label: String, selected: PointInteret?, onSelect: (PointInteret) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(label,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFD6725D),
            fontSize = 20.sp)
        Spacer(modifier = Modifier.size(16.dp))
        Button(onClick = { expanded = true },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF796D47),
                contentColor = Color.White
            )) {
            Text(selected?.name ?: "Sélectionner")
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            pointsZoo.forEach { point ->
                DropdownMenuItem(
                    text = { Text(point.name) },
                    onClick = {
                        onSelect(point)
                        expanded = false
                    }
                )
            }
        }
    }
}

fun distance(x1: Float, y1: Float, x2: Float, y2: Float): Float {
    return sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2))
}

fun dijkstra(
    voisins: Map<Int, List<Int>>,
    points: List<PointInteret>,
    startId: Int,
    endId: Int
): List<PointInteret> {
    val distances = mutableMapOf<Int, Float>().withDefault { Float.MAX_VALUE }
    val previous = mutableMapOf<Int, Int?>()
    val unvisited = points.map { it.id }.toMutableSet()

    distances[startId] = 0f

    while (unvisited.isNotEmpty()) {
        val current = unvisited.minByOrNull { distances.getValue(it) } ?: break
        unvisited.remove(current)

        if (current == endId) break

        val currentPoint = points.find { it.id == current } ?: continue
        val neighbors = voisins[current] ?: emptyList()

        for (neighbor in neighbors) {
            if (neighbor !in unvisited) continue
            val neighborPoint = points.find { it.id == neighbor } ?: continue

            val newDist = distances.getValue(current) +
                    distance(currentPoint.x, currentPoint.y, neighborPoint.x, neighborPoint.y)
            if (newDist < distances.getValue(neighbor)) {
                distances[neighbor] = newDist
                previous[neighbor] = current
            }
        }
    }

    val path = mutableListOf<PointInteret>()
    var step: Int? = endId

    while (step != null) {
        val point = points.find { it.id == step }
        if (point != null) path.add(point)
        step = previous[step]
    }

    return path.reversed()
}

fun getPointColor(point: PointInteret, selectedStart: PointInteret?, selectedEnd: PointInteret?): Color {
    return when {
        point == selectedStart || point == selectedEnd -> Color(0xFFD6725D)
        point.name.contains("Poste de Sécurité", ignoreCase = true) -> Color(0xFFFF4E41)
        point.name.contains("Point de rassemblement", ignoreCase = true) -> Color(0xFF4F96FF)
        point.name.contains("Sortie de secours", ignoreCase = true) -> Color(0xFF38FF78)
        else -> Color(0xFFD6725D).copy(alpha = 0.4f)
    }
}



