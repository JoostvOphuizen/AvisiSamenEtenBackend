package nl.han.oose.scala.scalasameneten.dto.voorkeur

class VoorkeurDTO {

    lateinit var voorkeuren: ArrayList<String>
    fun addVoorkeur(voorkeur: String){
        voorkeuren.add(voorkeur)
    }
}