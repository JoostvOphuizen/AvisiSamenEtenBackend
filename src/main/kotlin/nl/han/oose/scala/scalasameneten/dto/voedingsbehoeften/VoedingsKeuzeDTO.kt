package nl.han.oose.scala.scalasameneten.dto.voedingsbehoeften

data class VoedingsKeuzeDTO(val naam: String?, val keuze: Boolean) {
    constructor() : this(null, false)
}