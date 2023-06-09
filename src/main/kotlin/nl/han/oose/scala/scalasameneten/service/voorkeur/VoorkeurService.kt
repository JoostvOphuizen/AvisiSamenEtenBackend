package nl.han.oose.scala.scalasameneten.service.voorkeur

import nl.han.oose.scala.scalasameneten.datasource.voorkeur.VoorkeurDAO
import nl.han.oose.scala.scalasameneten.dto.voedingsbehoeften.VoedingsbehoeftenDTO
import nl.han.oose.scala.scalasameneten.dto.voorkeur.VoorkeurenDTO
import org.springframework.context.annotation.ComponentScan
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

@Service
@Component
@ComponentScan("nl.han.oose.scala.scalasameneten.datasource.voorkeur")
class VoorkeurService(private val voorkeurDAO: VoorkeurDAO) {


    fun getVoedingsbehoeften(gebruikersToken: String): ResponseEntity<VoedingsbehoeftenDTO> {
        return ResponseEntity.ok(voorkeurDAO!!.makeVoedingsbehoeftenDTO(gebruikersToken))
    }

}