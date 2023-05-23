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
        val SortedPrioList = prioritizedVoorkeuren.toList().sortedByDescending { (_, value) -> value }.toMap()
        println("SortedPrioList: $SortedPrioList")

        if (SortedPrioList.isEmpty()) {
            return ResponseEntity.ok(restaurants.random())
        }

        var remainingRestaurants = restaurants.toMutableList()
        var selectedRestaurant: RestaurantWithVoorkeurenAndRestrictiesDTO? = null

        for (voorkeur in SortedPrioList) {
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

            val voorkeuren = voorkeurenString?.split(",")?.toTypedArray()
            val conversieVoorkeuren = ArrayList<VoorkeurDTO>()
            voorkeuren?.forEach { voorkeur ->
                conversieVoorkeuren.add(VoorkeurDTO(voorkeur))
            }

            val restricties = restrictiesString?.split(",")?.toTypedArray()
            val conversieRestricties = ArrayList<VoedingsrestrictieDTO>()
            restricties?.forEach { restrictie ->
                conversieRestricties.add(VoedingsrestrictieDTO(restrictie, "null"))
            }

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
            val restaurantId = allResult.getInt("RESTAURANT_ID")
            val restaurantNaam = allResult.getString("RESTAURANT_NAAM")
            val postcode = allResult.getString("POSTCODE")
            val straatnaam = allResult.getString("STRAATNAAM")
            val huisnummer = allResult.getInt("HUISNUMMER")
            val link = allResult.getString("LINK")
            val foto = allResult.getString("FOTO")

            val voorkeurenString = allResult.getString("VOORKEUREN")
            val restrictiesString = allResult.getString("RESTRICTIES")

            val voorkeuren = voorkeurenString?.split(",")?.toTypedArray()
            val conversieVoorkeuren = ArrayList<VoorkeurDTO>()
            voorkeuren?.forEach { voorkeur ->
                conversieVoorkeuren.add(VoorkeurDTO(voorkeur))
            }

            val restricties = restrictiesString?.split(",")?.toTypedArray()
            val conversieRestricties = ArrayList<VoedingsrestrictieDTO>()
            restricties?.forEach { restrictie ->
                conversieRestricties.add(VoedingsrestrictieDTO(restrictie, "null"))
            }

            val restaurant = RestaurantWithVoorkeurenAndRestrictiesDTO(
                restaurantId,
                restaurantNaam,
                postcode,
                straatnaam,
                huisnummer,
                link,
                foto,
                VoorkeurenDTO(null,conversieVoorkeuren),
                VoedingsrestrictiesDTO(conversieRestricties)
            )

            restaurants.add(restaurant)
        }
        return restaurants
    }

}