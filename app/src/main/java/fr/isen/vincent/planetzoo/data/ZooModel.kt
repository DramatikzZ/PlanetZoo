package fr.isen.vincent.planetzoo.data

data class ZooModel(
    val id: String = "",
    val color: String = "",
    val name: String = "",
    val enclosures: List<EnclosureModel> = emptyList(),
    val services: List<ServiceModel> = emptyList()
)