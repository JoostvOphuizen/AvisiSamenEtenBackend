package nl.han.oose.scala.scalasameneten.datasource.factory

import nl.han.oose.scala.scalasameneten.dto.voedingsrestricties.AllergieDTO
import nl.han.oose.scala.scalasameneten.dto.voedingsrestricties.DieetDTO
import nl.han.oose.scala.scalasameneten.dto.voedingsrestricties.GeloofDTO

class VoedingsrestrictiesDTOFactory {
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