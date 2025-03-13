package fr.isen.vincent.planetzoo.data

data class EnclosureModel(
    val id: String = "",
    val id_biomes: String = "",
    val meal: String = "",
    val animals: List<AnimalModel> = emptyList()
)
