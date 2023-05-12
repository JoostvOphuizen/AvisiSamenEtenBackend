package nl.han.oose.scala.scalasameneten.service.restaurant

import nl.han.oose.scala.scalasameneten.datasource.gebruiker.GebruikerDAO
import nl.han.oose.scala.scalasameneten.datasource.restaurant.RestaurantDAO
import nl.han.oose.scala.scalasameneten.dto.gebruiker.GebruikersDTO
import nl.han.oose.scala.scalasameneten.dto.restaurant.GroepDTO
import nl.han.oose.scala.scalasameneten.dto.restaurant.RestaurantDTO
import org.springframework.context.annotation.ComponentScan
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

@Service
@Component
@ComponentScan("nl.han.oose.scala.scalasameneten.datasource.restaurant")
@ComponentScan("nl.han.oose.scala.scalasameneten.datasource.gebruiker")
class RestaurantService(private val restaurantDAO: RestaurantDAO, private val gebruikerDAO: GebruikerDAO){
    //TODO
    fun bepaalRestaurant(geselecteerdeGebruikers: GroepDTO): ResponseEntity<RestaurantDTO> {
        //foreach loop
        for (token in geselecteerdeGebruikers.ledenTokens){
            val gebruikerDTO = gebruikerDAO.makeGebruiker(token)
        //        logica moet hier komen te staan om het restaurant te bepalen aan de hand van de geselecteerde gebruikers

        }

//        return ResponseEntity.ok(restaurantDAO!!.makeRestaurantDTO(/*?restaurantId?*/))
        return ResponseEntity.ok(restaurantDAO!!.makeRestaurantDTO(1))
    }
}