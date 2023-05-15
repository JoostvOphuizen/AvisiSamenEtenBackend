package nl.han.oose.scala.scalasameneten.dto.restaurant

import nl.han.oose.scala.scalasameneten.dto.voedingsrestrictie.VoedingsrestrictiesDTO

data class VoorstelDTO(val restaurant: RestaurantDTO, val restricties: VoedingsrestrictiesDTO){

}