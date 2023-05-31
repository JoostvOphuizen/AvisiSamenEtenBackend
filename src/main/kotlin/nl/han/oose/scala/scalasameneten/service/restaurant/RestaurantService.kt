package nl.han.oose.scala.scalasameneten.service.restaurant

import nl.han.oose.scala.scalasameneten.datasource.gebruiker.GebruikerDAO
import nl.han.oose.scala.scalasameneten.datasource.restaurant.RestaurantDAO
import nl.han.oose.scala.scalasameneten.dto.gebruiker.GebruikerWithVoorkeurenAndRestrictiesDTO
import nl.han.oose.scala.scalasameneten.dto.restaurant.GroepDTO
import nl.han.oose.scala.scalasameneten.dto.restaurant.RestaurantWithVoorkeurenAndRestrictiesDTO
import nl.han.oose.scala.scalasameneten.dto.voedingsrestrictie.VoedingsrestrictieDTO
import nl.han.oose.scala.scalasameneten.dto.voedingsrestrictie.VoedingsrestrictiesDTO
import nl.han.oose.scala.scalasameneten.dto.voorkeur.VoorkeurDTO
import nl.han.oose.scala.scalasameneten.dto.voorkeur.VoorkeurenDTO
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

    fun bepaalRestaurant(geselecteerdeGebruikers: GroepDTO): ResponseEntity<RestaurantWithVoorkeurenAndRestrictiesDTO>? {
        val restaurants = getAllRestaurantsWithVoorkeurenAndResticties()
        val gebruikers = getAllGebruikersWithVoorkeurenAndRestricties(geselecteerdeGebruikers)

        val prioritizedVoorkeuren = mutableMapOf<String, Int>()
        for (gebruiker in gebruikers) {
            gebruiker.voorkeuren?.voorkeuren?.forEach { voorkeur ->
                val count = prioritizedVoorkeuren[voorkeur.naam] ?: 0
                prioritizedVoorkeuren[voorkeur.naam!!] = count + 1
            }
        }
        val sortedPrioList = prioritizedVoorkeuren.toList().sortedByDescending { (_, value) -> value }.toMap()

        if (sortedPrioList.isEmpty()) {
            return ResponseEntity.ok(restaurants.random())
        }

        var remainingRestaurants = restaurants.toMutableList()
        var selectedRestaurant: RestaurantWithVoorkeurenAndRestrictiesDTO? = null

        for (voorkeur in sortedPrioList) {
            val filteredRestaurants = remainingRestaurants.filter { it.voorkeuren?.voorkeuren?.contains(VoorkeurDTO(voorkeur.key)) == true }

            if (filteredRestaurants.isNotEmpty()) {
                remainingRestaurants = filteredRestaurants.toMutableList()
                selectedRestaurant = remainingRestaurants.random()
            } else {
                if (selectedRestaurant != null) {
                    println("Selected restaurant id: ${selectedRestaurant.restaurantId}")
                    return ResponseEntity.ok(selectedRestaurant)
                }
            }
        }
        return ResponseEntity.ok(selectedRestaurant ?: restaurants.random())
    }

    private fun getAllGebruikersWithVoorkeurenAndRestricties(groep: GroepDTO): MutableList<GebruikerWithVoorkeurenAndRestrictiesDTO> {
        val gebruikers = mutableListOf<GebruikerWithVoorkeurenAndRestrictiesDTO>()
        val gebruikersResult = gebruikerDAO.getAllGebruikersWithVoorkeurenAndRestricties(groep)
        while (gebruikersResult.next()) {
            val gebruikerId = gebruikersResult.getInt("GEBRUIKER_ID")
            val gebruikerNaam = gebruikersResult.getString("GEBRUIKERSNAAM")
            val email = gebruikersResult.getString("EMAIL")
            val token = gebruikersResult.getString("TOKEN")
            val foto = gebruikersResult.getString("FOTO")

            val voorkeurenString = gebruikersResult.getString("VOORKEUREN")
            val restrictiesString = gebruikersResult.getString("RESTRICTIES")

            val conversieVoorkeuren = splitVoorkeuren(voorkeurenString)
            val conversieRestricties = splitRestricties(restrictiesString)

            val gebruiker = GebruikerWithVoorkeurenAndRestrictiesDTO(
                gebruikerId,
                gebruikerNaam,
                email,
                token,
                foto,
                VoorkeurenDTO(null,conversieVoorkeuren),
                VoedingsrestrictiesDTO(conversieRestricties)
            )
            gebruikers.add(gebruiker)
        }
        return gebruikers
    }

    private fun getAllRestaurantsWithVoorkeurenAndResticties(): MutableList<RestaurantWithVoorkeurenAndRestrictiesDTO> {
        val restaurants = mutableListOf<RestaurantWithVoorkeurenAndRestrictiesDTO>()
        val allResult = restaurantDAO.getAllRestaurantsWithVoorkeurenAndRestricties()
        while (allResult.next()) {
            val restaurant = makeRestaurantDTO(allResult)
            restaurants.add(restaurant)
        }
        return restaurants
    }

    private fun splitVoorkeuren(voorkeurenString: String): ArrayList<VoorkeurDTO>{
        val voorkeuren = voorkeurenString.split(",").toTypedArray()
        val conversieVoorkeuren = ArrayList<VoorkeurDTO>()
        voorkeuren.forEach { voorkeur ->
            conversieVoorkeuren.add(VoorkeurDTO(voorkeur))
        }
        return conversieVoorkeuren
    }

    private fun splitRestricties(restrictiesString: String): ArrayList<VoedingsrestrictieDTO>{
        val restricties = restrictiesString.split(",").toTypedArray()
        val conversieRestricties = ArrayList<VoedingsrestrictieDTO>()
        restricties.forEach { restrictie ->
            conversieRestricties.add(VoedingsrestrictieDTO(restrictie, "null"))
        }
        return conversieRestricties
    }

    private fun makeRestaurantDTO(result: ResultSet): RestaurantWithVoorkeurenAndRestrictiesDTO {
        val restaurantId = result.getInt("restaurant_id")
        val restaurantNaam = result.getString("restaurant_naam")
        val postcode = result.getString("postcode")
        val straatnaam = result.getString("straatnaam")
        val huisnummer = result.getInt("huisnummer")
        val link = result.getString("link")
        val foto = result.getString("foto")

        val voorkeurenString = result.getString("voorkeuren")
        val restrictiesString = result.getString("restricties")

        val conversieVoorkeuren = splitVoorkeuren(voorkeurenString)
        val conversieRestricties = splitRestricties(restrictiesString)

        return RestaurantWithVoorkeurenAndRestrictiesDTO(
                restaurantId,
                restaurantNaam,
                postcode,
                straatnaam,
                huisnummer,
                link,
                foto,
                VoorkeurenDTO(null, conversieVoorkeuren),
                VoedingsrestrictiesDTO(conversieRestricties)
        )
    }

    fun getRestaurant(id: Int): ResponseEntity<RestaurantWithVoorkeurenAndRestrictiesDTO>{
        val result = restaurantDAO.getRestaurant(id)
        result.next()
        return ResponseEntity.ok(makeRestaurantDTO(result))
    }
    fun getRestaurantBaseInfo(id: Int): ResponseEntity<RestaurantWithVoorkeurenAndRestrictiesDTO>{
        val result = restaurantDAO.getRestaurant(id)
        result.next()
        return ResponseEntity.ok(makeRestaurantDTOWithoutVoorkeurenAndRestricties(result))
    }

    fun getRecentBezochteRestaurant(id: String): ResponseEntity<RestaurantWithVoorkeurenAndRestrictiesDTO>? {
        val restaurantResult = restaurantDAO.getRecentBezochteRestaurant(id)
        if(restaurantResult.next()){
            if(restaurantResult.getString("datum") == null){
                return null
            }

            return ResponseEntity.ok(makeRestaurantDTOWithoutVoorkeurenAndRestricties(restaurantResult))
        }
        return null
    }


    fun makeRestaurantDTOWithoutVoorkeurenAndRestricties(result: ResultSet) : RestaurantWithVoorkeurenAndRestrictiesDTO{
        val restaurant = RestaurantWithVoorkeurenAndRestrictiesDTO(
            result.getInt("restaurant_id"),
            result.getString("restaurant_naam"),
            result.getString("postcode"),
            result.getString("straatnaam"),
            result.getInt("huisnummer"),
            result.getString("link"),
            result.getString("foto"),
            null,
            null
        )
        return restaurant
    }

    fun getAllRestaurants(): ResponseEntity<MutableList<RestaurantWithVoorkeurenAndRestrictiesDTO>>{
        val restaurants = getAllRestaurantsWithVoorkeurenAndResticties()
        return ResponseEntity.ok(restaurants)
    }
}