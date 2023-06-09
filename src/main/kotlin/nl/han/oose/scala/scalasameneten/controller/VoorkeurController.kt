package nl.han.oose.scala.scalasameneten.controller

import nl.han.oose.scala.scalasameneten.dto.voedingsbehoeften.VoedingsbehoeftenDTO
import nl.han.oose.scala.scalasameneten.dto.voorkeur.VoorkeurenDTO
import nl.han.oose.scala.scalasameneten.service.voorkeur.VoorkeurService
import org.springframework.context.annotation.ComponentScan
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/voorkeuren")
@ComponentScan("nl.han.oose.scala.scalasameneten.service.voorkeur")
@CrossOrigin
class VoorkeurController(private val voorkeurService: VoorkeurService) {
    @GetMapping(produces = ["application/json"])
    @RequestMapping("/voedingsbehoeften")
    fun getVoedingsbehoeften(@RequestParam gebruikersToken: String): ResponseEntity<VoedingsbehoeftenDTO> = voorkeurService.getVoedingsbehoeften(gebruikersToken)

}