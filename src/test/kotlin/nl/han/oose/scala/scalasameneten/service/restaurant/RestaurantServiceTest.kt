package nl.han.oose.scala.scalasameneten.service.restaurant

import jakarta.ws.rs.core.Response
import nl.han.oose.scala.scalasameneten.datasource.connection.ConnectionService
import nl.han.oose.scala.scalasameneten.datasource.connection.DatabaseProperties
import nl.han.oose.scala.scalasameneten.datasource.gebruiker.GebruikerDAO
import nl.han.oose.scala.scalasameneten.datasource.restaurant.RestaurantDAO
<<<<<<< HEAD
import nl.han.oose.scala.scalasameneten.dto.restaurant.GroepDTO
=======
>>>>>>> ee56546f5663a782cbdcb74baf8117d2b18e38a5
import nl.han.oose.scala.scalasameneten.dto.restaurant.RestaurantWithVoorkeurenAndRestrictiesDTO
import nl.han.oose.scala.scalasameneten.dto.voedingsrestrictie.VoedingsrestrictieDTO
import nl.han.oose.scala.scalasameneten.dto.voedingsrestrictie.VoedingsrestrictiesDTO
import nl.han.oose.scala.scalasameneten.dto.voorkeur.VoorkeurDTO
import nl.han.oose.scala.scalasameneten.dto.voorkeur.VoorkeurenDTO
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import java.sql.ResultSet


class RestaurantServiceTest {

    @Mock
    lateinit var restaurantDAO: RestaurantDAO
    lateinit var gebruikerDAO: GebruikerDAO
    lateinit var restaurantService: RestaurantService
    private lateinit var resultSetMock: ResultSet
    private lateinit var gebruikersResults: ResultSet
    private lateinit var reviewResults1: ResultSet
    private lateinit var reviewResults2: ResultSet
    private lateinit var reviewResults3: ResultSet
    private lateinit var reviewResults4: ResultSet
    private lateinit var reviewResults5: ResultSet
    private lateinit var reviewResults6: ResultSet

    @BeforeEach
    fun setup(){
        restaurantDAO = Mockito.mock(RestaurantDAO::class.java)
        gebruikerDAO = Mockito.mock(GebruikerDAO::class.java)
        resultSetMock = Mockito.mock(ResultSet::class.java)
        gebruikersResults = Mockito.mock(ResultSet::class.java)
        reviewResults1 = Mockito.mock(ResultSet::class.java)
        reviewResults2 = Mockito.mock(ResultSet::class.java)
        reviewResults3 = Mockito.mock(ResultSet::class.java)
        reviewResults4 = Mockito.mock(ResultSet::class.java)
        reviewResults5 = Mockito.mock(ResultSet::class.java)
        reviewResults6 = Mockito.mock(ResultSet::class.java)
        restaurantService = RestaurantService(restaurantDAO,gebruikerDAO)

        Mockito.`when`(resultSetMock.next()).thenReturn(true).thenReturn(true).thenReturn(false)
        Mockito.`when`(resultSetMock.getInt("restaurant_id")).thenReturn(1)
        Mockito.`when`(resultSetMock.getString("restaurant_naam")).thenReturn("restaurant 1").thenReturn("restaurant 2")
        Mockito.`when`(resultSetMock.getString("postcode")).thenReturn("restaurant 1").thenReturn("restaurant 2")
        Mockito.`when`(resultSetMock.getString("straatnaam")).thenReturn("restaurant 1").thenReturn("restaurant 2")
        Mockito.`when`(resultSetMock.getString("link")).thenReturn("restaurant 1").thenReturn("restaurant 2")
        Mockito.`when`(resultSetMock.getString("voorkeuren")).thenReturn("voorkeur 1").thenReturn("voorkeur 1")
        Mockito.`when`(resultSetMock.getString("restricties")).thenReturn("restrictie 1").thenReturn("restrictie 1")

        Mockito.`when`(gebruikersResults.next()).thenReturn(true).thenReturn(false)
        Mockito.`when`(gebruikersResults.getInt("GEBRUIKER_ID")).thenReturn(2)
        Mockito.`when`(gebruikersResults.getString("GEBRUIKER_NAAM")).thenReturn("user2")
        Mockito.`when`(gebruikersResults.getString("VOORKEUREN")).thenReturn("vlees")


        Mockito.`when`(reviewResults1.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false)
        Mockito.`when`(reviewResults1.getInt("beoordeling")).thenReturn(4).thenReturn(5).thenReturn(3).thenReturn(5).thenReturn(4).thenReturn(5).thenReturn(3).thenReturn(5)
        Mockito.`when`(reviewResults2.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false)
        Mockito.`when`(reviewResults2.getInt("beoordeling")).thenReturn(1).thenReturn(3).thenReturn(2).thenReturn(2).thenReturn(1).thenReturn(3).thenReturn(2).thenReturn(2)
        Mockito.`when`(reviewResults3.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false)
        Mockito.`when`(reviewResults3.getInt("beoordeling")).thenReturn(4).thenReturn(5).thenReturn(3).thenReturn(5).thenReturn(4).thenReturn(5).thenReturn(3).thenReturn(5)
        Mockito.`when`(reviewResults4.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false)
        Mockito.`when`(reviewResults4.getInt("beoordeling")).thenReturn(1).thenReturn(3).thenReturn(2).thenReturn(2).thenReturn(1).thenReturn(3).thenReturn(2).thenReturn(2)
        Mockito.`when`(reviewResults5.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false)
        Mockito.`when`(reviewResults5.getInt("beoordeling")).thenReturn(4).thenReturn(5).thenReturn(3).thenReturn(5).thenReturn(4).thenReturn(5).thenReturn(3).thenReturn(5)
        Mockito.`when`(reviewResults6.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false)
        Mockito.`when`(reviewResults6.getInt("beoordeling")).thenReturn(1).thenReturn(3).thenReturn(2).thenReturn(2).thenReturn(1).thenReturn(3).thenReturn(2).thenReturn(2)

        val leden = ArrayList<Int>()
        leden.add(2)
        val groep = GroepDTO(leden,"")
        Mockito.`when`(restaurantDAO.getAllRestaurantsWithVoorkeurenAndRestricties()).thenReturn(resultSetMock)
        Mockito.`when`(restaurantDAO.getReviews(1)).thenReturn(reviewResults1)
        Mockito.`when`(restaurantDAO.getReviews(2)).thenReturn(reviewResults2)
        Mockito.`when`(restaurantDAO.getReviews(3)).thenReturn(reviewResults1)
        Mockito.`when`(restaurantDAO.getReviews(4)).thenReturn(reviewResults2)
        Mockito.`when`(restaurantDAO.getReviews(5)).thenReturn(reviewResults1)
        Mockito.`when`(restaurantDAO.getReviews(6)).thenReturn(reviewResults2)
        Mockito.`when`(gebruikerDAO.getAllGebruikersWithVoorkeurenAndRestricties(groep)).thenReturn(gebruikersResults)
    }

    @Test
    fun bepaalRestaurantsMetReviews(){
        //arrange
        //act
        val leden = ArrayList<Int>()
        leden.add(2)
        val groep = GroepDTO(leden,"")
        val aanwezige_ids = ArrayList<Int>()
        val restaurant_id = intArrayOf(1,2,3,4,5,6)
        for(i in 0..50){
            val restaurant = restaurantService.bepaalRestaurant((groep))
            setup()
            if (restaurant != null) {
                restaurant.body?.let { aanwezige_ids.add(it.restaurantId) }
            }
        }
        var alle_ids_aanwezig = true
        for(id in restaurant_id){
            if(!aanwezige_ids.contains(id)){
                alle_ids_aanwezig = false
            }
        }
        //assert
        assert(alle_ids_aanwezig)
    }
    @Test
    fun getAllRestaurantsTest() {
        //arrange
        Mockito.`when`(restaurantDAO.getAllRestaurantsWithVoorkeurenAndRestricties()).thenReturn(resultSetMock)
        //act
        val result = restaurantService.getAllRestaurants()
        val i = result.body
        val expected = mutableListOf<RestaurantWithVoorkeurenAndRestrictiesDTO>()
        val voorkeuren = ArrayList<VoorkeurDTO>()
        voorkeuren.add(VoorkeurDTO("voorkeur 1"))
        val restricties = ArrayList<VoedingsrestrictieDTO>()
        restricties.add(VoedingsrestrictieDTO("restrictie 1", "null"))
        expected.add(RestaurantWithVoorkeurenAndRestrictiesDTO(1, "restaurant 1", "restaurant 1", "restaurant 1", 0, "restaurant 1", null, VoorkeurenDTO(null, voorkeuren), VoedingsrestrictiesDTO(restricties)))
        expected.add(RestaurantWithVoorkeurenAndRestrictiesDTO(1, "restaurant 2", "restaurant 2", "restaurant 2", 0, "restaurant 2", null, VoorkeurenDTO(null, voorkeuren), VoedingsrestrictiesDTO(restricties)))
        //assert
        assertEquals(expected, i)
    }

    @Test
    fun getRestaurantTest(){
        Mockito.`when`(restaurantDAO.getRestaurant(1)).thenReturn(resultSetMock)
        //act
        val result = restaurantService.getRestaurant(1)
        val restaurantnaam = result.body?.restaurantNaam
        //assert
        assertEquals("restaurant 1",restaurantnaam)
    }
}
