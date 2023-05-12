package nl.han.oose.scala.scalasameneten.controller

import nl.han.oose.scala.scalasameneten.dto.gebruiker.GebruikersDTO
import nl.han.oose.scala.scalasameneten.dto.gebruiker.LoginDTO
import nl.han.oose.scala.scalasameneten.dto.restaurant.GroepDTO
import nl.han.oose.scala.scalasameneten.dto.restaurant.RestaurantDTO
import nl.han.oose.scala.scalasameneten.service.gebruiker.GebruikerService
import nl.han.oose.scala.scalasameneten.service.restaurant.RestaurantService
import org.modelmapper.ModelMapper
import org.springframework.context.annotation.ComponentScan
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/restaurant")
@ComponentScan("nl.han.oose.scala.scalasameneten.service.restaurant")
@CrossOrigin
class RestaurantController(private val restaurantService: RestaurantService, private val modelMapper: ModelMapper) {
    @RequestMapping("/bepaal")
    @PostMapping(produces = ["application/json"], consumes = ["application/json"])
    fun postBepaalRestaurant(@RequestBody geselecteerdeGebruikers: GroepDTO): ResponseEntity<RestaurantDTO> {
        val groepDTO = modelMapper.map(geselecteerdeGebruikers, GroepDTO::class.java)
        return restaurantService.bepaalRestaurant(groepDTO)
    }
}