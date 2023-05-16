package nl.han.oose.scala.scalasameneten.dto.restaurant

data class GroepenDTO( val groepen:ArrayList<GroepDTO> ) {
    constructor(): this(ArrayList<GroepDTO>())

}