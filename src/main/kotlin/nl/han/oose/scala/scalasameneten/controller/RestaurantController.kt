package nl.han.oose.scala.scalasameneten.controller

import nl.han.oose.scala.scalasameneten.dto.gebruiker.GebruikersDTO
import nl.han.oose.scala.scalasameneten.dto.gebruiker.LoginDTO
import nl.han.oose.scala.scalasameneten.service.gebruiker.GebruikerService
import nl.han.oose.scala.scalasameneten.service.restaurant.RestaurantService
import org.modelmapper.ModelMapper
import org.springframework.context.annotation.ComponentScan
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/restaurant")
@ComponentScan("nl.han.oose.scala.scalasameneten.service.restaurant")
@CrossOrigin
class RestaurantController(private val restaurantService: RestaurantService) {
    @RequestMapping("/bepaal")
    @PostMapping(produces = ["application/json"], consumes = ["application/json"])
    fun postBepaalRestaurant(@RequestBody geselecteerdeGebruikers: GebruikersDTO) = restaurantService.bepaalRestaurant(geselecteerdeGebruikers)
}