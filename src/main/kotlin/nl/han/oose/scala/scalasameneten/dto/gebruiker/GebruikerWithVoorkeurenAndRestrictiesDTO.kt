package nl.han.oose.scala.scalasameneten.dto.gebruiker

data class GebruikerWithVoorkeurenAndRestrictiesDTO(
    var id: Int?,
    var naam: String?,
    var email: String?,
    var token: String?,
    var foto: String?,
    var voorkeuren: Array<String>?,
    var restricties: Array<String>?
) {
    constructor() : this(null, null, null, null, null, null, null)
}