package nl.han.oose.scala.scalasameneten.service.restaurant

import nl.han.oose.scala.scalasameneten.datasource.exceptions.DatabaseConnectionException
import nl.han.oose.scala.scalasameneten.datasource.gebruiker.GebruikerDAO
import nl.han.oose.scala.scalasameneten.datasource.restaurant.RestaurantDAO
import nl.han.oose.scala.scalasameneten.datasource.voedingsrestrictie.VoedingsrestrictieDAO
import nl.han.oose.scala.scalasameneten.dto.gebruiker.GebruikerWithVoorkeurenAndRestrictiesDTO
import nl.han.oose.scala.scalasameneten.dto.gebruiker.GebruikersDTO
import nl.han.oose.scala.scalasameneten.dto.restaurant.GroepDTO
import nl.han.oose.scala.scalasameneten.dto.restaurant.RestaurantWithVoorkeurenAndRestrictiesDTO
import nl.han.oose.scala.scalasameneten.dto.restaurant.VoorstelDTO
import org.springframework.context.annotation.ComponentScan
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

@Service
@Component
@ComponentScan("nl.han.oose.scala.scalasameneten.datasource.restaurant")
@ComponentScan("nl.han.oose.scala.scalasameneten.datasource.voedingsrestrictie")
class RestaurantService(private val restaurantDAO: RestaurantDAO, private val voedingsrestrictieDAO: VoedingsrestrictieDAO, private val gebruikerDAO: GebruikerDAO){

    fun bepaalRestaurant(geselecteerdeGebruikers: GroepDTO): ResponseEntity<VoorstelDTO> {
        var restaurants = mutableListOf<Int>()
//        var mogelijkerestaurants = mutableListOf<Int>()
        var voorstellen = mutableListOf<Int>()

        //ophalen van alle restaurants
        var allresult = restaurantDAO.getRestaurants()
        while (allresult != null && allresult.next()) {
            restaurants.add(allresult.getInt("RESTAURANT_ID"))
        }

        //ophalen van alle restaurants waar de gebruikers geen restricties voor hebben
        //restaurants wordt gefilterd zodat alleen mogelijke restaurants voorkomen
//        geselecteerdeGebruikers.leden?.forEach { id ->
//            val filterresult = restaurantDAO.getRestaurantenZonderRestricties(id)
//            while (filterresult != null && filterresult.next()) {
//                mogelijkerestaurants.add(filterresult.getInt("RESTAURANT_ID"))
//            }
//            restaurants = restaurants.intersect(mogelijkerestaurants).toMutableList()
//            mogelijkerestaurants.clear()
//        }

        //aan de hand van de voorkeuren van de gebruikers wordt een restaurant voorgesteld (uit de gefilterde restaurants)
        if(restaurants.size > 0) {
            val voorstelresult = restaurantDAO!!.getRestaurantVoorstel(geselecteerdeGebruikers, restaurants)
            while (voorstelresult.next()) {
                voorstellen.add(voorstelresult.getInt("RESTAURANT_ID"))
            }
            //random restaurant uit de voorstellen (voor als er meerdere voorstellen zijn)
            val random = (0 until voorstellen.size).random()
            val gekozenRestaurantId = voorstellen[random]

            val restaurantDTO = restaurantDAO!!.makeRestaurantDTO(gekozenRestaurantId)
            val restrictiesDTO = restaurantDAO!!.generateVoedingsrestrictiesDTO(gekozenRestaurantId)

            return ResponseEntity.ok(VoorstelDTO(restaurantDTO, restrictiesDTO))
        }
        else {
            throw DatabaseConnectionException()
        }
    }

    fun bepaalRestaurant2(geselecteerdeGebruikers: GroepDTO): ResponseEntity<VoorstelDTO>? {
        val restaurants = getAllRestaurantsWithVoorkeurenAndResticties()
        val gebruikers = getAllGebruikersWithVoorkeurenAndRestricties()

        val prioritizedVoorkeuren = mutableMapOf<String, Int>()
        for (gebruiker in gebruikers) {
            gebruiker.voorkeuren?.forEach { voorkeur ->
                val count = prioritizedVoorkeuren[voorkeur] ?: 0
                prioritizedVoorkeuren[voorkeur] = count + 1
            }
        }
        val SortedPrioList = prioritizedVoorkeuren.toList().sortedByDescending { (_, value) -> value }.toMap()
        println("SortedPrioList: $SortedPrioList")
        var remainingRestaurants = restaurants.toMutableList()
        var selectedRestaurantId: Int? = null

        for (voorkeur in SortedPrioList) {
            val filteredRestaurants = remainingRestaurants.filter { it.voorkeuren?.contains(voorkeur.key) == true }
            if (filteredRestaurants.isNotEmpty()) {
                remainingRestaurants = filteredRestaurants.toMutableList()
                selectedRestaurantId = remainingRestaurants.random().restaurantId
            } else {
                if (selectedRestaurantId != null) {
                    println("Selected restaurant id: $selectedRestaurantId")
                    return null
                }
            }
        }

        return null
    }







    private fun getAllGebruikersWithVoorkeurenAndRestricties(): MutableList<GebruikerWithVoorkeurenAndRestrictiesDTO> {
        val gebruikers = mutableListOf<GebruikerWithVoorkeurenAndRestrictiesDTO>()
        val gebruikersResult = gebruikerDAO.getAllGebruikersWithVoorkeurenAndRestricties()
        while (gebruikersResult.next()) {
            val gebruikerId = gebruikersResult.getInt("GEBRUIKER_ID")
            val gebruikerNaam = gebruikersResult.getString("GEBRUIKERSNAAM")
            val email = gebruikersResult.getString("EMAIL")
            val token = gebruikersResult.getString("TOKEN")
            val foto = gebruikersResult.getString("FOTO")

            val voorkeurenString = gebruikersResult.getString("VOORKEUREN")
            val restrictiesString = gebruikersResult.getString("RESTRICTIES")

            val voorkeuren = voorkeurenString?.split(",")?.toTypedArray()
            val restricties = restrictiesString?.split(",")?.toTypedArray()

            val gebruiker = GebruikerWithVoorkeurenAndRestrictiesDTO(
                gebruikerId,
                gebruikerNaam,
                email,
                token,
                foto,
                voorkeuren,
                restricties
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
            val huisnummer = allResult.getString("HUISNUMMER")

            val voorkeurenString = allResult.getString("VOORKEUREN")
            val restrictiesString = allResult.getString("RESTRICTIES")

            val voorkeuren = voorkeurenString?.split(",")?.toTypedArray()
            val restricties = restrictiesString?.split(",")?.toTypedArray()

            val restaurant = RestaurantWithVoorkeurenAndRestrictiesDTO(
                restaurantId,
                restaurantNaam,
                postcode,
                straatnaam,
                huisnummer,
                voorkeuren,
                restricties
            )

            restaurants.add(restaurant)
        }
        return restaurants
    }

}