package nl.han.oose.scala.scalasameneten.controller

import nl.han.oose.scala.scalasameneten.dto.uitnodiging.UitgenodigdenDTO
import nl.han.oose.scala.scalasameneten.dto.uitnodiging.UitnodigingDTO
import nl.han.oose.scala.scalasameneten.dto.voedingsrestrictie.VoedingsrestrictiesDTO
import nl.han.oose.scala.scalasameneten.service.uitnodiging.UitnodigingService
import nl.han.oose.scala.scalasameneten.service.voedingsrestrictie.VoedingsrestrictieService
import org.springframework.context.annotation.ComponentScan
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/uitnodiging")
@ComponentScan("nl.han.oose.scala.scalasameneten.service.uitnodiging")
@CrossOrigin
class UitnodigingController(private val uitnodigingService: UitnodigingService) {
    @PostMapping(produces = ["application/json"], consumes = ["application/json"])
    fun createUitnodiging(@RequestBody gebruikerToken: UitnodigingDTO): ResponseEntity<UitnodigingDTO> {
        return uitnodigingService.createUitnodiging(gebruikerToken.uitnodigingToken)
    }

    @GetMapping(produces = ["application/json"])
    fun getUitgenodigden(@RequestBody gebruikerToken: UitnodigingDTO): ResponseEntity<UitgenodigdenDTO> {
        return uitnodigingService.getUitgenodigden(gebruikerToken.uitnodigingToken)
    }

}