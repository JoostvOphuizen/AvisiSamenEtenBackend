package nl.han.oose.scala.scalasameneten.controller

import nl.han.oose.scala.scalasameneten.dto.gebruiker.GebruikerDTO
import nl.han.oose.scala.scalasameneten.dto.gebruiker.GebruikersDTO
import nl.han.oose.scala.scalasameneten.dto.gebruiker.LoginDTO
import nl.han.oose.scala.scalasameneten.dto.restaurant.RestaurantWithVoorkeurenAndRestrictiesDTO
import nl.han.oose.scala.scalasameneten.dto.voorkeur.VoorkeurenDTO
import nl.han.oose.scala.scalasameneten.service.gebruiker.GebruikerService
import nl.han.oose.scala.scalasameneten.service.restaurant.RestaurantService
import org.modelmapper.ModelMapper
import org.springframework.context.annotation.ComponentScan
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/gebruiker")
@ComponentScan("nl.han.oose.scala.scalasameneten.service.gebruiker")
@CrossOrigin
class GebruikerController(private val gebruikerService: GebruikerService, private val restaurantService: RestaurantService, private val modelMapper: ModelMapper) {

    @RequestMapping("/login")
    @PostMapping(produces = ["application/json"], consumes = ["application/json"])
    fun postLoginGebruiker(@RequestBody login: LoginDTO) = gebruikerService.loginGebruiker(login)

    @GetMapping(produces = ["application/json"])
    fun getGebruikers(): ResponseEntity<GebruikersDTO> = gebruikerService.getGebruikers()

    @GetMapping(produces = ["application/json"])
    @RequestMapping("/baseinfo")
    fun getGebruikersBaseInfo(): ResponseEntity<GebruikersDTO> = gebruikerService.getGebruikersBaseInfo()

    @RequestMapping("/profiel")
    @GetMapping(produces = ["application/json"])
    fun getGebruiker(@RequestParam id: String): ResponseEntity<GebruikerDTO> = gebruikerService.getGebruiker(id)

    @RequestMapping("/haalvoorkeurenop")
    @GetMapping(produces = ["application/json"], consumes = ["application/json"])
    fun postVoorkeuren(@RequestParam id: String) : ResponseEntity<VoorkeurenDTO> = gebruikerService.getGebruikersVoorkeuren(id)

    @RequestMapping("/slavoorkeurenop")
    @PostMapping(produces = ["application/json"], consumes = ["application/json"])
    fun postVoorkeuren(@RequestParam id: String, @RequestBody voorkeuren: VoorkeurenDTO) : ResponseEntity<Void> {
        val voorkeurenDTO = modelMapper.map(voorkeuren, VoorkeurenDTO::class.java)
        return gebruikerService.postGebruikersVoorkeuren(id, voorkeurenDTO)
    }

    @RequestMapping("/slarestrictiesop")
    @PostMapping(produces = ["application/json"], consumes = ["application/json"])
    fun postRestricties(@RequestParam id: String, @RequestBody restricties: VoorkeurenDTO) : ResponseEntity<Void> {
        val restrictiesDTO = modelMapper.map(restricties, VoorkeurenDTO::class.java)
        return gebruikerService.postGebruikersRestricties(id, restrictiesDTO)
    }

    @RequestMapping("/historie")
    @GetMapping(produces = ["application/json"], consumes = ["application/json"])
    fun getRestaurantHistorie(@RequestParam id: Int): ResponseEntity<RestaurantWithVoorkeurenAndRestrictiesDTO>{
        return restaurantService.getRecentBezochteRestaurant(id)
    }

}