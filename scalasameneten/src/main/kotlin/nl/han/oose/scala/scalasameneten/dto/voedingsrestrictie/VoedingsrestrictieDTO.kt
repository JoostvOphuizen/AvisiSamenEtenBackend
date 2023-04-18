package nl.han.oose.scala.scalasameneten.dto.voedingsrestrictie

abstract class VoedingsrestrictieDTO {
    lateinit var restricties: ArrayList<String>

    fun addRestrictie(restrictie: String){
        restricties.add(restrictie)
    }
}