package fr.isen.vincent.planetzoo.data

data class EnclosureModel(
    val id: String = "",
    val id_biomes: String = "",
    val meal: String = "",
    val is_open: Boolean = true,
    val comments: List<CommentModel> = emptyList(),
    val animals: List<AnimalModel> = emptyList()
)
