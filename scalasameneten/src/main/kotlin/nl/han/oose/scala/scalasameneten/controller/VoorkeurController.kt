package nl.han.oose.scala.scalasameneten.controller

import nl.han.oose.scala.scalasameneten.dto.voedingsrestrictie.VoedingsrestrictieDTO
import nl.han.oose.scala.scalasameneten.dto.voorkeur.VoorkeurDTO
import nl.han.oose.scala.scalasameneten.service.voedingsrestrictie.VoedingsrestrictieService
import nl.han.oose.scala.scalasameneten.service.voorkeur.VoorkeurService
import org.springframework.context.annotation.ComponentScan
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/voorkeuren")
class VoorkeurController {

    @GetMapping("/test")
    fun getVoorkeur2(): String = "test"
/*
    @GetMapping(produces = ["application/json"])
    fun getVoedingsRestrictie(@RequestParam id: Int): ResponseEntity<ArrayList<VoedingsrestrictieDTO>> = voedingsrestrictieService.getVoedingsrestricties()
    */
/*
    @PostMapping(produces = ["application/json"], consumes = ["application/json"])
    fun postVoorkeur(@RequestParam id: Int, @RequestBody voorkeurDTO: VoorkeurDTO): ResponseEntity<String> = voorkeurService.postVoorkeuren(voorkeurDTO)
*/

}