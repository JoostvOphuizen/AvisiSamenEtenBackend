package nl.han.oose.scala.scalasameneten.dto.gebruiker

import nl.han.oose.scala.scalasameneten.dto.voedingsrestrictie.VoedingsrestrictiesDTO
import nl.han.oose.scala.scalasameneten.dto.voorkeur.VoorkeurenDTO

data class GebruikerWithVoorkeurenAndRestrictiesDTO(
    var id: Int?,
    var naam: String?,
    var email: String?,
    var token: String?,
    var foto: String?,
    var voorkeuren: VoorkeurenDTO?,
    var restricties: VoedingsrestrictiesDTO?
) {
    constructor() : this(null, null, null, null, null, null, null)
}