package fr.isen.vincent.planetzoo.data

data class UserModel(
    val name : String,
    val email : String,
    val uid : String,
    val admin : Boolean,
    val color : String
) {
    companion object {
        var isAdmin: Boolean = false
        var name: String = ""
    }
}