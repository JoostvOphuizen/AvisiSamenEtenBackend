package nl.han.oose.scala.scalasameneten.dto.voedingsrestricties

abstract class VoedingsrestrictiesDTO {
    lateinit var restricties: ArrayList<String>

    fun addRestrictie(restrictie: String){
        restricties.add(restrictie)
    }
}