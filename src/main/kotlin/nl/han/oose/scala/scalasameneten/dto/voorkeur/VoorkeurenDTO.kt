package nl.han.oose.scala.scalasameneten.dto.voorkeur

data class VoorkeurenDTO(var naam: String?, var voorkeuren: ArrayList<VoorkeurDTO>?) {
    constructor() : this(null, null)

}