package nl.han.oose.scala.scalasameneten.dto.voedingsbehoeften

import nl.han.oose.scala.scalasameneten.dto.voorkeur.VoorkeurenDTO

data class VoedingsbehoeftenDTO(val voedingsbehoeften: ArrayList<VoedingsbehoefteDTO>?) {
    constructor() : this(null)
}
