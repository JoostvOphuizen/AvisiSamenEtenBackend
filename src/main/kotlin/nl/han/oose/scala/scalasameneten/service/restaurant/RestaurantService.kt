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
                    voegHistoryToe(geselecteerdeGebruikers,selectedRestaurant.restaurantId)
                    return ResponseEntity.ok(selectedRestaurant)
                }
            }
        }
        selectedRestaurant = selectedRestaurant ?: restaurants.random()
        voegHistoryToe(geselecteerdeGebruikers,selectedRestaurant.restaurantId)
        return ResponseEntity.ok(selectedRestaurant)
    }
    private fun voegHistoryToe(gebruikers: GroepDTO,restaurant: Int){
        for(gebruiker in gebruikers.leden){
            restaurantDAO.voegHistoryToe(gebruiker,restaurant)
        }
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

            val voorkeurenString: String? = gebruikersResult.getString("VOORKEUREN")
            val restrictiesString: String? = gebruikersResult.getString("RESTRICTIES")

            val conversieVoorkeuren = voorkeurenString?.let { splitVoorkeuren(it) }
            val conversieRestricties = restrictiesString?.let { splitRestricties(it) }

            val gebruiker = GebruikerWithVoorkeurenAndRestrictiesDTO(
                gebruikerId,
                gebruikerNaam,
                email,
                token,
                foto,
                VoorkeurenDTO(null,conversieVoorkeuren),
                    conversieRestricties?.let { VoedingsrestrictiesDTO(it) }
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

        val voorkeurenString: String? = result.getString("voorkeuren")
        val restrictiesString: String? = result.getString("restricties")

        val conversieVoorkeuren = voorkeurenString?.let { splitVoorkeuren(it) }
        val conversieRestricties = restrictiesString?.let { splitRestricties(it) }

        return RestaurantWithVoorkeurenAndRestrictiesDTO(
                restaurantId,
                restaurantNaam,
                postcode,
                straatnaam,
                huisnummer,
                link,
                foto,
                VoorkeurenDTO(null, conversieVoorkeuren),
                conversieRestricties?.let { VoedingsrestrictiesDTO(it) }
        )
    }

    fun getRestaurant(id: Int): ResponseEntity<RestaurantWithVoorkeurenAndRestrictiesDTO>{
        val result = restaurantDAO.getRestaurant(id)
        result.next()
        return ResponseEntity.ok(makeRestaurantDTO(result))
    }
    fun getAllRestaurants(): ResponseEntity<MutableList<RestaurantWithVoorkeurenAndRestrictiesDTO>>{
        val restaurants = getAllRestaurantsWithVoorkeurenAndResticties()
        return ResponseEntity.ok(restaurants)
    }
}