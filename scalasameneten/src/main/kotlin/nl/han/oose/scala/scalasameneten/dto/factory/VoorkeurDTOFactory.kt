package nl.han.oose.scala.scalasameneten.dto.factory

import nl.han.oose.scala.scalasameneten.dto.voorkeur.VoorkeurDTO

class VoorkeurDTOFactory {

    object create {
        fun createVoorkeurenDTO(): VoorkeurDTO {
            return VoorkeurDTO()
        }
    }
}