package nl.han.oose.scala.scalasameneten.service.restaurant

import nl.han.oose.scala.scalasameneten.datasource.gebruiker.GebruikerDAO
import nl.han.oose.scala.scalasameneten.datasource.restaurant.RestaurantDAO
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

    fun bepaalRestaurant(geselecteerdeGebruikers: GroepDTO): ResponseEntity<RestaurantDTO> {
        var restaurants = mutableListOf<Int>()
        var mogelijkerestaurants = mutableListOf<Int>()

        //ophalen van alle restaurants
        var allresult = restaurantDAO.getRestaurants()
        while (allresult != null && allresult.next()) {
            restaurants.add(allresult.getInt("RESTAURANT_ID"))
        }

        //ophalen van alle restaurants waar de gebruikers geen restricties voor hebben
        //restaurants wordt gefilterd zodat alleen mogelijke restaurants voorkomen
        geselecteerdeGebruikers.leden?.forEach { id ->
            val filterresult = restaurantDAO.getRestaurantenZonderRestricties(id)
            while (filterresult != null && filterresult.next()) {
                mogelijkerestaurants.add(filterresult.getInt("RESTAURANT_ID"))
            }
            restaurants = restaurants.intersect(mogelijkerestaurants).toMutableList()
        }

        if(restaurants.size > 0){
            val voorstelresult = restaurantDAO!!.getRestaurantVoorstel(geselecteerdeGebruikers, restaurants)
            while (voorstelresult != null && voorstelresult.next()) {

            }
        }
        else {
            //TODO: return error: Geen restaurants gevonden
        }


//        //voorkeuren van de gebruikers
//        val voorkeurenArray = mutableMapOf<String, Int>()
//        val voorkeurenresult = restaurantDAO!!.getVoorkeurenPrioterisering(geselecteerdeGebruikers)
//        while (voorkeurenresult != null && voorkeurenresult.next()) {
//            //associative array
//            val naam = mapOf("naam" to voorkeurenresult.getString("VOORKEUR_NAAM"), "aantal" to voorkeurenresult.getInt("aantal"))
//            voorkeurenArray.put(naam["naam"].toString(), naam["aantal"] as Int)
//        }


//        for(voorkeur in voorkeurenArray){
//        }

//        return ResponseEntity.ok(restaurantDAO!!.makeRestaurantDTO(/*?restaurantId?*/))
        return ResponseEntity.ok(restaurantDAO!!.makeRestaurantDTO(1))
    }
}