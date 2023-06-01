package nl.han.oose.scala.scalasameneten.service.restaurant

import nl.han.oose.scala.scalasameneten.datasource.gebruiker.GebruikerDAO
import nl.han.oose.scala.scalasameneten.datasource.restaurant.RestaurantDAO
import nl.han.oose.scala.scalasameneten.dto.gebruiker.GebruikerWithVoorkeurenAndRestrictiesDTO
import nl.han.oose.scala.scalasameneten.dto.restaurant.GroepDTO
import nl.han.oose.scala.scalasameneten.dto.restaurant.RestaurantWithVoorkeurenAndRestrictiesDTO
import nl.han.oose.scala.scalasameneten.dto.restaurant.ReviewDTO
import nl.han.oose.scala.scalasameneten.dto.voedingsrestrictie.VoedingsrestrictieDTO
import nl.han.oose.scala.scalasameneten.dto.voedingsrestrictie.VoedingsrestrictiesDTO
import nl.han.oose.scala.scalasameneten.dto.voorkeur.VoorkeurDTO
import nl.han.oose.scala.scalasameneten.dto.voorkeur.VoorkeurenDTO
import org.springframework.context.annotation.ComponentScan
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import kotlin.math.log
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.random.Random
import java.sql.ResultSet

@Service
@Component
@ComponentScan("nl.han.oose.scala.scalasameneten.datasource.restaurant")
@ComponentScan("nl.han.oose.scala.scalasameneten.datasource.gebruiker")
class RestaurantService(private val restaurantDAO: RestaurantDAO, private val gebruikerDAO: GebruikerDAO){

    fun bepaalRestaurant(geselecteerdeGebruikers: GroepDTO, gebruikerToken: String?): ResponseEntity<RestaurantWithVoorkeurenAndRestrictiesDTO>? {
        val restaurants = getAllRestaurantsWithVoorkeurenAndResticties()
        val gebruikers = getAllGebruikersWithVoorkeurenAndRestricties(geselecteerdeGebruikers, gebruikerToken)
        val maxAantal = 5

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

        var remainingRestaurants: MutableList<RestaurantWithVoorkeurenAndRestrictiesDTO> = restaurants.toMutableList()
        var selectedRestaurant: RestaurantWithVoorkeurenAndRestrictiesDTO? = null

        for (voorkeur in sortedPrioList) {
            val filteredRestaurants = remainingRestaurants.filter { it.voorkeuren?.voorkeuren?.contains(VoorkeurDTO(voorkeur.key)) == true }

            if (filteredRestaurants.size > maxAantal) {
                remainingRestaurants = filteredRestaurants.toMutableList()
                selectedRestaurant = bepaalRestaurantMetReviews(remainingRestaurants.toMutableList())
            } else {
                if (selectedRestaurant != null) {
                    voegHistoryToe(gebruikers, selectedRestaurant.restaurantId)
                    return ResponseEntity.ok(selectedRestaurant)
                }
            }
        }
        selectedRestaurant = selectedRestaurant ?: restaurants.random()
        voegHistoryToe(gebruikers, selectedRestaurant.restaurantId)
        return ResponseEntity.ok(selectedRestaurant)
    }
    private fun voegHistoryToe(gebruikers: MutableList<GebruikerWithVoorkeurenAndRestrictiesDTO>, restaurant: Int){
        for(gebruiker in gebruikers){
            gebruiker.id?.let { restaurantDAO.voegHistoryToe(it, restaurant) }
        }
    }

    private fun getReviewGemiddelde(id: Int): Double{
        val reviews = restaurantDAO.getReviews(id)
        val x = ArrayList<Int>()
        while(reviews.next()){
            x.add(reviews.getInt("beoordeling"))
        }
        return x.sum()/x.count().toDouble()
    }

    private fun bepaalRestaurantMetReviews(restaurants: MutableList<RestaurantWithVoorkeurenAndRestrictiesDTO>): RestaurantWithVoorkeurenAndRestrictiesDTO{
        val restaurantScore = mutableMapOf<Int,Double>()
        val permillage = mutableMapOf<Int,Double>()
        val mille = 1000
        for(restaurant in restaurants){
            val reviews = restaurantDAO.getReviews(restaurant.restaurantId)
            val gemiddelde = getReviewGemiddelde(restaurant.restaurantId)
            val x = ArrayList<Double>()
            while(reviews.next()){
                val xpow = (reviews.getInt("beoordeling")-gemiddelde).pow(2)
                x.add(xpow)
            }
            restaurantScore[restaurant.restaurantId] = restaurantScoreBerekenen(gemiddelde,x)
        }
        for(restaurant in restaurants){
            val id = restaurant.restaurantId
            permillage[id] = (restaurantScore[id]!!.div(restaurantScore.values.sum()))*mille
        }
        val random = Random.nextInt(0,mille)
        var i = 0.0;
        for(permille in permillage){
            if(random<=permille.value+i){
                return restaurants[permille.key-1]
            }
            i += permille.value
        }
        return restaurants.random()
    }
    private fun restaurantScoreBerekenen(gemiddelde:Double,x:ArrayList<Double>):Double{
        val standaarddeviatie = standaardDeviatieBerekenen(x)
        val macht = 3
        val log = 5.0
        val standaardScore = 1.0
        var restaurantScore = sqrt((gemiddelde.pow(macht)*log(x.count().toDouble(),log))/standaarddeviatie)+1
        if(restaurantScore.isNaN()){
            restaurantScore = standaardScore
        }
        return restaurantScore
    }
    private fun standaardDeviatieBerekenen(x: ArrayList<Double>): Double{
        val standaardStandaard = 0.1
        var standaarddeviatie = x.sum()/(x.count())
        if(standaarddeviatie==0.0|| standaarddeviatie.isNaN()){
            standaarddeviatie = standaardStandaard
        }
        return standaarddeviatie
    }

    private fun getAllGebruikersWithVoorkeurenAndRestricties(groep: GroepDTO, gebruikerToken: String?): MutableList<GebruikerWithVoorkeurenAndRestrictiesDTO> {
        val gebruikers = mutableListOf<GebruikerWithVoorkeurenAndRestrictiesDTO>()
        val gebruikersResult = gebruikerDAO.getAllGebruikersWithVoorkeurenAndRestricties(groep, gebruikerToken)
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
            conversieRestricties.add(VoedingsrestrictieDTO(restrictie))
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
                conversieRestricties?.let { VoedingsrestrictiesDTO(it) }?: VoedingsrestrictiesDTO(ArrayList())
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
    fun getRandomRestaurant(): ResponseEntity<RestaurantWithVoorkeurenAndRestrictiesDTO>{
        val result = restaurantDAO.getRandomRestaurant()
        result.next()
        return ResponseEntity.ok(makeRestaurantDTO(result))
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


    fun postReview(restaurantId: Int, review: ReviewDTO): ResponseEntity<String>{
        try {
            val id = gebruikerDAO.getIdVanGebruiker(review.gebruikerToken)
            restaurantDAO.postReview(restaurantId, review, id!!)
        } catch (e: Exception) {
            return ResponseEntity.badRequest().body("Er is iets misgegaan bij het toevoegen van de review")
        }
        return ResponseEntity.ok("Review is toegevoegd")
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