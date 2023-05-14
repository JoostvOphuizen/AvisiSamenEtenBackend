package nl.han.oose.scala.scalasameneten.service.restaurant

import nl.han.oose.scala.scalasameneten.datasource.exceptions.DatabaseConnectionException
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
class RestaurantService(private val restaurantDAO: RestaurantDAO){

    fun bepaalRestaurant(geselecteerdeGebruikers: GroepDTO): ResponseEntity<RestaurantDTO> {
        var restaurants = mutableListOf<Int>()
        var mogelijkerestaurants = mutableListOf<Int>()
        var voorstellen = mutableListOf<Int>()

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
            mogelijkerestaurants.clear()
        }

        //aan de hand van de voorkeuren van de gebruikers wordt een restaurant voorgesteld (uit de gefilterde restaurants)
        if(restaurants.size > 0) {
            val voorstelresult = restaurantDAO!!.getRestaurantVoorstel(geselecteerdeGebruikers, restaurants)
            while (voorstelresult != null && voorstelresult.next()) {
                voorstellen.add(voorstelresult.getInt("RESTAURANT_ID"))
            }
            //random restaurant uit de voorstellen (voor als er meerdere voorstellen zijn)
            val random = (0 until voorstellen.size).random()
            return ResponseEntity.ok(restaurantDAO!!.makeRestaurantDTO(voorstellen[random]))
        }
        else {
            throw DatabaseConnectionException()
        }
    }
}