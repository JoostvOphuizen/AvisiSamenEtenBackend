package nl.han.oose.scala.scalasameneten.dto.restaurant

import nl.han.oose.scala.scalasameneten.dto.voedingsrestrictie.VoedingsrestrictiesDTO
import nl.han.oose.scala.scalasameneten.dto.voorkeur.VoorkeurenDTO

data class RestaurantWithVoorkeurenAndRestrictiesDTO(
    val restaurantId: Int,
    val restaurantNaam: String,
    val postcode: String,
    val straatnaam: String,
    val huisnummer: Int,
    val link: String,
    val foto: String?,
    val voorkeuren: VoorkeurenDTO?,
    val restricties: VoedingsrestrictiesDTO?
) {
    constructor() : this(0, "", "", "", 0, "", null, null, null)
}