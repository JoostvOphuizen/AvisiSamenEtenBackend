package nl.han.oose.scala.scalasameneten.service.voedingsrestrictie

import nl.han.oose.scala.scalasameneten.datasource.voedingsrestrictie.VoedingsrestrictieDAO
import nl.han.oose.scala.scalasameneten.datasource.voorkeur.VoorkeurDAO
import nl.han.oose.scala.scalasameneten.dto.voedingsrestrictie.VoedingsrestrictiesDTO
import org.springframework.context.annotation.ComponentScan
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

@Service
@Component
@ComponentScan("nl.han.oose.scala.scalasameneten.datasource.voedingsrestrictie")
class VoedingsrestrictieService(private val voedingsrestrictieDAO: VoedingsrestrictieDAO) {

    fun getVoedingsrestricties(): ResponseEntity<VoedingsrestrictiesDTO> {
        return ResponseEntity.ok(voedingsrestrictieDAO!!.makeRestrictiesDTO())
    }

}