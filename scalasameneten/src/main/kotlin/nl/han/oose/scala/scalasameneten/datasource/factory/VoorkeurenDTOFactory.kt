package nl.han.oose.scala.scalasameneten.datasource.factory

import nl.han.oose.scala.scalasameneten.dto.voorkeuren.VoorkeurenDTO

class VoorkeurenDTOFactory {

    object create {
        fun createVoorkeurenDTO(): VoorkeurenDTO {
            return VoorkeurenDTO()
        }
    }
}