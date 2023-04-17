package nl.han.oose.scala.scalasameneten.dto.voorkeuren

class VoorkeurenDTO {

    lateinit var voorkeuren: ArrayList<String>
    fun addVoorkeur(voorkeur: String){
        voorkeuren.add(voorkeur)
    }
}