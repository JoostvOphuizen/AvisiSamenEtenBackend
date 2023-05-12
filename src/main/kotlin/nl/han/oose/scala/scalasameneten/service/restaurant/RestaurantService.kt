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
import java.sql.ResultSet

@Service
@Component
@ComponentScan("nl.han.oose.scala.scalasameneten.datasource.restaurant")
@ComponentScan("nl.han.oose.scala.scalasameneten.datasource.gebruiker")
class RestaurantService(private val restaurantDAO: RestaurantDAO, private val gebruikerDAO: GebruikerDAO){
    //TODO
    fun bepaalRestaurant(geselecteerdeGebruikers: GroepDTO): ResponseEntity<RestaurantDTO> {
        var restaurants = mutableListOf<Int>()
        var resultArray = mutableListOf<Int>()

        //ophalen van alle restaurants
        var allresult = restaurantDAO.getRestaurants()
        while (allresult != null && allresult.next()) {
            restaurants.add(allresult.getInt("RESTAURANT_ID"))
        }

        //ophalen van alle restaurants waar de gebruikers geen restricties voor hebben
        geselecteerdeGebruikers.leden?.forEach { id ->
            val result = restaurantDAO.getRestaurantenZonderRestricties(id)
            while (result != null && result.next()) {
                resultArray.add(result.getInt("RESTAURANT_ID"))
            }
            restaurants = restaurants.intersect(resultArray).toMutableList()
        }

        //vervang de restaurants door de restaurants waar de gebruikers geen restricties voor hebben
        allresult =



        val voorkeurenArray = mutableMapOf<String, Int>()
        val voorkeurenresult = restaurantDAO!!.getVoorkeurenPrioterisering(geselecteerdeGebruikers)
        while (voorkeurenresult != null && voorkeurenresult.next()) {
            //associative array
            val naam = mapOf("naam" to voorkeurenresult.getString("VOORKEUR_NAAM"), "aantal" to voorkeurenresult.getInt("aantal"))
            voorkeurenArray.put(naam["naam"].toString(), naam["aantal"] as Int)
        }


        for(voorkeur in voorkeurenArray){
            val result = restaurantDAO.getRestaurantenMetVoorkeur(voorkeur.key)
            while (result != null && result.next()) {
                resultArray.add(result.getInt("RESTAURANT_ID"))
            }
            restaurants = restaurants.intersect(resultArray).toMutableList()
        }

//        return ResponseEntity.ok(restaurantDAO!!.makeRestaurantDTO(/*?restaurantId?*/))
        return ResponseEntity.ok(restaurantDAO!!.makeRestaurantDTO(1))
    }
}