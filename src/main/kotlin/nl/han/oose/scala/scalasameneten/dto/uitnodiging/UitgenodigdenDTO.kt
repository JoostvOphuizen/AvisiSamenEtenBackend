package nl.han.oose.scala.scalasameneten.dto.uitnodiging

import nl.han.oose.scala.scalasameneten.dto.gebruiker.GebruikerDTO
import nl.han.oose.scala.scalasameneten.dto.gebruiker.GebruikersDTO

data class UitgenodigdenDTO(val gebruikerID: Int, val restaurantID: Int?, val host: GebruikerDTO, val uitgenodigden: GebruikersDTO) {
    constructor(): this(-1, null, GebruikerDTO(), GebruikersDTO(ArrayList()))

}