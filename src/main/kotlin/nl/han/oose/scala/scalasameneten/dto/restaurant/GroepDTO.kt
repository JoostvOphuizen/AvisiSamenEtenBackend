package nl.han.oose.scala.scalasameneten.dto.restaurant

data class GroepDTO(val leden: ArrayList<Int>, val naam: String){

    constructor(): this(ArrayList<Int>(), "placeholder")

}

