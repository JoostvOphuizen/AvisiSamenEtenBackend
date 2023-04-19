package nl.han.oose.scala.scalasameneten.controller

import jakarta.ws.rs.Path
import nl.han.oose.scala.scalasameneten.dto.gebruiker.GebruikerDTO
import nl.han.oose.scala.scalasameneten.dto.gebruiker.GebruikersDTO
import nl.han.oose.scala.scalasameneten.dto.voedingsrestrictie.VoedingsrestrictiesDTO
import nl.han.oose.scala.scalasameneten.service.gebruiker.GebruikerService
import nl.han.oose.scala.scalasameneten.service.voorkeur.VoorkeurService
import org.springframework.context.annotation.ComponentScan
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.sql.ResultSet

@RestController
@RequestMapping("/gebruiker")
@ComponentScan("nl.han.oose.scala.scalasameneten.service.gebruiker")
@CrossOrigin
class GebruikerController(private val gebruikerService: GebruikerService) {
    @GetMapping(produces = ["application/json"])
    fun getGebruikers(): ResponseEntity<GebruikersDTO> = gebruikerService.getGebruikers()

    @RequestMapping("/profiel")
    @GetMapping(produces = ["application/json"])
    fun getGebruiker(@RequestParam id: Int): ResponseEntity<GebruikerDTO> = gebruikerService.getGebruiker(id)
}