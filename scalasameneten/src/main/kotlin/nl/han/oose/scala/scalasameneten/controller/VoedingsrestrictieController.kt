package nl.han.oose.scala.scalasameneten.controller

import nl.han.oose.scala.scalasameneten.dto.voedingsrestrictie.VoedingsrestrictiesDTO
import nl.han.oose.scala.scalasameneten.dto.voorkeur.VoorkeurenDTO
import nl.han.oose.scala.scalasameneten.service.voedingsrestrictie.VoedingsrestrictieService
import nl.han.oose.scala.scalasameneten.service.voorkeur.VoorkeurService
import org.springframework.context.annotation.ComponentScan
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/voedingsrestrictie")
@ComponentScan("nl.han.oose.scala.scalasameneten.service.voedingsrestrictie")
@CrossOrigin
class VoedingsrestrictieController(private val voedingsrestrictieService: VoedingsrestrictieService) {
    @GetMapping(produces = ["application/json"])
    fun getVoedinsrestricties(): ResponseEntity<VoedingsrestrictiesDTO> = voedingsrestrictieService.getVoedingsrestricties()
}