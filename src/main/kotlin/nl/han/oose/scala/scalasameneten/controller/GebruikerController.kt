package nl.han.oose.scala.scalasameneten.controller

import nl.han.oose.scala.scalasameneten.dto.gebruiker.GebruikerDTO
import nl.han.oose.scala.scalasameneten.dto.gebruiker.GebruikersDTO
import nl.han.oose.scala.scalasameneten.dto.gebruiker.LoginDTO
import nl.han.oose.scala.scalasameneten.dto.voorkeur.VoorkeurenDTO
import nl.han.oose.scala.scalasameneten.service.gebruiker.GebruikerService
import org.modelmapper.ModelMapper
import org.springframework.context.annotation.ComponentScan
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/gebruiker")
@ComponentScan("nl.han.oose.scala.scalasameneten.service.gebruiker")
@CrossOrigin
class GebruikerController(private val gebruikerService: GebruikerService, private val modelMapper: ModelMapper) {

    @RequestMapping("/login")
    @PostMapping(produces = ["application/json"], consumes = ["application/json"])
    fun postLoginGebruiker(@RequestBody login: LoginDTO) = gebruikerService.loginGebruiker(login)


    @GetMapping(produces = ["application/json"])
    fun getGebruikers(): ResponseEntity<GebruikersDTO> = gebruikerService.getGebruikers()

    @RequestMapping("/profiel")
    @GetMapping(produces = ["application/json"])
    fun getGebruiker(@RequestParam id: Int): ResponseEntity<GebruikerDTO> = gebruikerService.getGebruiker(id)

    @RequestMapping("/haalvoorkeurenop")
    @GetMapping(produces = ["application/json"], consumes = ["application/json"])
    fun postVoorkeuren(@RequestParam id: Int) : ResponseEntity<VoorkeurenDTO> = gebruikerService.getGebruikersVoorkeuren(id)

    @RequestMapping("/slavoorkeurenop")
    @PostMapping(produces = ["application/json"], consumes = ["application/json"])
    fun postVoorkeuren(@RequestParam id: Int, @RequestBody voorkeuren: VoorkeurenDTO) : ResponseEntity<Void> {
        val voorkeurenDTO = modelMapper.map(voorkeuren, VoorkeurenDTO::class.java)
        return gebruikerService.postGebruikersVoorkeuren(id, voorkeurenDTO)
    }
}