package nl.han.oose.scala.scalasameneten.dto.restaurant

data class RestaurantReviewDTO(
    val restaurantId: Int,
    val restaurantNaam: String,
    val reviewId: Int?
)