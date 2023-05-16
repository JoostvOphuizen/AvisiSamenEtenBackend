package nl.han.oose.scala.scalasameneten.dto.restaurant

data class RestaurantWithVoorkeurenAndRestrictiesDTO(
    val restaurantId: Int,
    val restaurantNaam: String,
    val postcode: String,
    val straatnaam: String,
    val huisnummer: String,
    val voorkeuren: Array<String>?,
    val restricties: Array<String>?
) {
    constructor() : this(0, "", "", "", "", null, null)
}