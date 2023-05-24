package nl.han.oose.scala.scalasameneten.dto.uitnodiging

import nl.han.oose.scala.scalasameneten.dto.gebruiker.GebruikerDTO
import nl.han.oose.scala.scalasameneten.dto.gebruiker.GebruikersDTO

data class UitgenodigdenDTO(val host: GebruikerDTO, val uitgenodigden: GebruikersDTO) {
    constructor(): this(GebruikerDTO(), GebruikersDTO(ArrayList()))

}