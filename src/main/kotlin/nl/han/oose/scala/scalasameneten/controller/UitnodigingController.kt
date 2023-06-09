package nl.han.oose.scala.scalasameneten.controller

import nl.han.oose.scala.scalasameneten.dto.uitnodiging.UitgenodigdenDTO
import nl.han.oose.scala.scalasameneten.dto.uitnodiging.UitnodigingDTO
import nl.han.oose.scala.scalasameneten.service.uitnodiging.UitnodigingService
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
    fun getUitgenodigden(@RequestParam uitnodigingToken: String, @RequestParam gebruikerToken: String): ResponseEntity<UitgenodigdenDTO> {
        return uitnodigingService.getUitgenodigden(uitnodigingToken, gebruikerToken)
    }

    @PostMapping(consumes = ["application/json"])
    @RequestMapping("/accepteer")
    fun accepteerUitnodiging(@RequestBody uitnodigingToken: UitnodigingDTO, @RequestParam gebruikerToken: String): ResponseEntity<String> {
        return uitnodigingService.accepteerUitnodiging(uitnodigingToken.uitnodigingToken, gebruikerToken)
    }

    @PostMapping(consumes = ["application/json"])
    @RequestMapping("/restaurant")
    fun updateRestaurant(@RequestBody uitnodigingToken: UitnodigingDTO, @RequestParam restaurantId: Int): ResponseEntity<String> {
        return uitnodigingService.updateRestaurant(uitnodigingToken.uitnodigingToken, restaurantId)
    }

    @PostMapping(consumes = ["application/json"])
    @RequestMapping("/kick")
    fun kickUitgenodigde(@RequestBody uitnodigingToken: UitnodigingDTO, @RequestParam uitgenodigdeID: Int): ResponseEntity<String> {
        return uitnodigingService.kickUitgenodigde(uitnodigingToken.uitnodigingToken, uitgenodigdeID)
    }

}