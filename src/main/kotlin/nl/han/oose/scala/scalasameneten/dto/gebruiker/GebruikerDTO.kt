package nl.han.oose.scala.scalasameneten.dto.gebruiker

import nl.han.oose.scala.scalasameneten.dto.voedingsrestrictie.VoedingsrestrictieDTO
import nl.han.oose.scala.scalasameneten.dto.voorkeur.VoorkeurDTO


data class GebruikerDTO(val id: Int, val naam: String, val foto: String?, var voorkeuren: ArrayList<VoorkeurDTO>?,var restricties: ArrayList<VoedingsrestrictieDTO>?){

    constructor() : this(0, "", "", null, null)

    fun slaVoorkeurenOp(voorkeuren: ArrayList<VoorkeurDTO>){
        this.voorkeuren = voorkeuren
    }
    fun slaVoedingsrestrictiesOp(restricties: ArrayList<VoedingsrestrictieDTO>){
        this.restricties = restricties
    }
}
