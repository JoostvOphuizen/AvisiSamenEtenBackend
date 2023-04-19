package nl.han.oose.scala.scalasameneten.controller

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
    fun getVoorkeur(): ResponseEntity<VoorkeurenDTO> = voorkeurService.getVoorkeuren()

    /*@GetMapping(produces = ["application/json"])
    fun getVoorkeur2(@RequestParam id: Int): ResponseEntity<VoorkeurDTO> = voorkeurService.getVoorkeuren()
*/

/*
    @GetMapping(produces = ["application/json"])
    fun getVoedingsRestrictie(@RequestParam id: Int): ResponseEntity<ArrayList<VoedingsrestrictieDTO>> = voedingsrestrictieService.getVoedingsrestricties()
    */
/*
    @PostMapping(produces = ["application/json"], consumes = ["application/json"])
    fun postVoorkeur(@RequestParam id: Int, @RequestBody voorkeurDTO: VoorkeurDTO): ResponseEntity<String> = voorkeurService.postVoorkeuren(voorkeurDTO)
*/

}