package nl.han.oose.scala.scalasameneten.dto.factory

import nl.han.oose.scala.scalasameneten.dto.voedingsrestrictie.AllergieDTO
import nl.han.oose.scala.scalasameneten.dto.voedingsrestrictie.DieetDTO
import nl.han.oose.scala.scalasameneten.dto.voedingsrestrictie.GeloofDTO

class VoedingsrestrictieDTOFactory {
    object create {
        fun createAllergieDTO(): AllergieDTO {
            return AllergieDTO()
        }
        fun createGeloofDTO(): GeloofDTO {
            return GeloofDTO()
        }
        fun createDieetDTO(): DieetDTO {
            return DieetDTO()
        }
    }
}