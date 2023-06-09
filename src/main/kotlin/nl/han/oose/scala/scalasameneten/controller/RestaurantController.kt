package nl.han.oose.scala.scalasameneten.controller

import nl.han.oose.scala.scalasameneten.dto.restaurant.GroepDTO
import nl.han.oose.scala.scalasameneten.dto.restaurant.RestaurantWithVoorkeurenAndRestrictiesDTO
import nl.han.oose.scala.scalasameneten.dto.restaurant.ReviewDTO
import nl.han.oose.scala.scalasameneten.service.restaurant.RestaurantService
import org.springframework.context.annotation.ComponentScan
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/restaurant")
@ComponentScan("nl.han.oose.scala.scalasameneten.service.restaurant")
@CrossOrigin
class RestaurantController(private val restaurantService: RestaurantService) {
    @RequestMapping("/bepaal")
    @PostMapping(produces = ["application/json"], consumes = ["application/json"])
    fun postBepaalRestaurant(@RequestBody geselecteerdeGebruikers: ArrayList<Int>, @RequestParam gebruikerToken: String?): ResponseEntity<RestaurantWithVoorkeurenAndRestrictiesDTO>? {
        return restaurantService.bepaalRestaurant(GroepDTO(geselecteerdeGebruikers, "Placeholder"), gebruikerToken)
    }

    @RequestMapping("/alle")
    @GetMapping(produces = ["application/json"])
    fun getAlleRestaurants(): ResponseEntity<MutableList<RestaurantWithVoorkeurenAndRestrictiesDTO>> {
        return restaurantService.getAllRestaurants()
    }

    @RequestMapping("/getrestaurant")
    @PostMapping(produces = ["application/json"], consumes = ["application/json"])
    fun getRestaurant(@RequestParam id: Int): ResponseEntity<RestaurantWithVoorkeurenAndRestrictiesDTO>{
        return restaurantService.getRestaurant(id)
    }
    @RequestMapping("/randomrestaurant")
    @GetMapping(produces = ["application/json"])
    fun getRandomRestaurant(@RequestParam gebruikerToken: String): ResponseEntity<RestaurantWithVoorkeurenAndRestrictiesDTO> {
        return restaurantService.getRandomRestaurant(gebruikerToken)
    }
    @RequestMapping("/review")
    @PostMapping(produces = ["application/json"], consumes = ["application/json"])
    fun postReview(@RequestParam id: Int, @RequestBody review: ReviewDTO): ResponseEntity<String> {
        return restaurantService.postReview(id, review)
    }
    @RequestMapping("/getreviews")
    @GetMapping(produces = ["application/json"])
    fun getReview(@RequestParam id: Int): ResponseEntity<ArrayList<ReviewDTO>> {
        return restaurantService.getReviews(id)
    }

    @RequestMapping("/getrestaurantbaseinfo")
    @GetMapping(produces = ["application/json"], consumes = ["application/json"])
    fun getRestaurantBaseInfo(@RequestParam id: Int): ResponseEntity<RestaurantWithVoorkeurenAndRestrictiesDTO>{
        return restaurantService.getRestaurantBaseInfo(id)
    }
}