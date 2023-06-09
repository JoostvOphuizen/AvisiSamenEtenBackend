package nl.han.oose.scala.scalasameneten.dto.voedingsbehoeften

import nl.han.oose.scala.scalasameneten.dto.voorkeur.VoorkeurDTO

data class VoedingsbehoefteDTO(val naam: String?, val voorkeuren: ArrayList<VoedingsKeuzeDTO>?) {
    constructor() : this(null, null)
}