package fr.isen.vincent.planetzoo.data

data class EnclosureModel(
    val id: String = "",
    val id_biomes: String = "",
    val meal: String = "",
    val comments: List<CommentModel> = emptyList(),
    val animals: List<AnimalModel> = emptyList()
)
