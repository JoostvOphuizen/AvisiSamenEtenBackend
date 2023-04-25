package nl.han.oose.scala.scalasameneten.service.gebruiker

import nl.han.oose.scala.scalasameneten.datasource.gebruiker.GebruikerDAO
import nl.han.oose.scala.scalasameneten.dto.gebruiker.GebruikerDTO
import nl.han.oose.scala.scalasameneten.dto.gebruiker.GebruikersDTO
import nl.han.oose.scala.scalasameneten.dto.voorkeur.VoorkeurenDTO
import org.springframework.context.annotation.ComponentScan
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

@Service
@Component
@ComponentScan("nl.han.oose.scala.scalasameneten.datasource.gebruiker")
class GebruikerService(private val gebruikerDAO: GebruikerDAO) {
    fun getGebruikers(): ResponseEntity<GebruikersDTO> {
        return ResponseEntity.ok(gebruikerDAO!!.makeGebruikersDTO())
    }
    fun getGebruiker(id: Int): ResponseEntity<GebruikerDTO> {
        return ResponseEntity.ok(gebruikerDAO!!.makeGebruiker(id))
    }



    fun getGebruikersVoorkeuren(id: Int): ResponseEntity<VoorkeurenDTO> {
        return ResponseEntity.ok(gebruikerDAO!!.makeVoorkeurenDTO(id))
    }

    fun postGebruikersVoorkeuren(id: Int, voorkeurenDTO: VoorkeurenDTO): ResponseEntity<Void> {
        return ResponseEntity.ok(gebruikerDAO!!.setGebruikersVoorkeuren(id, voorkeurenDTO))
    }
}