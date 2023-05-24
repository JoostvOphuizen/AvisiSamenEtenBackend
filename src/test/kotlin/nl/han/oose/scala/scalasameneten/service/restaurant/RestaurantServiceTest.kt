package nl.han.oose.scala.scalasameneten.service.restaurant

import jakarta.ws.rs.core.Response
import nl.han.oose.scala.scalasameneten.datasource.connection.ConnectionService
import nl.han.oose.scala.scalasameneten.datasource.connection.DatabaseProperties
import nl.han.oose.scala.scalasameneten.datasource.gebruiker.GebruikerDAO
import nl.han.oose.scala.scalasameneten.datasource.restaurant.RestaurantDAO
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
import java.sql.Connection
import java.sql.ResultSet


class RestaurantServiceTest {

    @Mock
    lateinit var restaurantDAO: RestaurantDAO
    lateinit var gebruikerDAO: GebruikerDAO
    lateinit var restaurantService: RestaurantService
    private lateinit var resultSetMock: ResultSet

    @BeforeEach
    fun setup(){
        restaurantDAO = Mockito.mock(RestaurantDAO::class.java)
        gebruikerDAO = Mockito.mock(GebruikerDAO::class.java)
        resultSetMock = Mockito.mock(ResultSet::class.java)
        restaurantService = RestaurantService(restaurantDAO,gebruikerDAO)
    }

    @Test
    fun getAllRestaurantsTest(){
        Mockito.`when`(resultSetMock.next()).thenReturn(true).thenReturn(true).thenReturn(false)
        Mockito.`when`(resultSetMock.getString("RESTAURANT_NAAM")).thenReturn("restaurant 1").thenReturn("restaurant 2")
        Mockito.`when`(resultSetMock.getString("POSTCODE")).thenReturn("restaurant 1").thenReturn("restaurant 2")
        Mockito.`when`(resultSetMock.getString("STRAATNAAM")).thenReturn("restaurant 1").thenReturn("restaurant 2")
        Mockito.`when`(resultSetMock.getString("LINK")).thenReturn("restaurant 1").thenReturn("restaurant 2")
        Mockito.`when`(resultSetMock.getString("VOORKEUREN")).thenReturn("voorkeur 1").thenReturn("voorkeur 1")
        Mockito.`when`(resultSetMock.getString("RESTRICTIES")).thenReturn("restrictie 1").thenReturn("restrictie 1")

        Mockito.`when`(restaurantDAO.getAllRestaurantsWithVoorkeurenAndRestricties()).thenReturn(resultSetMock)
        val result = restaurantService.getAllRestaurants()
        val i = result.body
        val expected = mutableListOf<RestaurantWithVoorkeurenAndRestrictiesDTO>()
        val voorkeuren = ArrayList<VoorkeurDTO>()
        voorkeuren.add(VoorkeurDTO("voorkeur 1"))
        val restricties = ArrayList<VoedingsrestrictieDTO>()
        restricties.add(VoedingsrestrictieDTO("restrictie 1", "null"))
        expected.add(RestaurantWithVoorkeurenAndRestrictiesDTO(0,"restaurant 1","restaurant 1","restaurant 1",0,"restaurant 1",null,VoorkeurenDTO(null,voorkeuren),VoedingsrestrictiesDTO(restricties)))
        expected.add(RestaurantWithVoorkeurenAndRestrictiesDTO(0,"restaurant 2","restaurant 2","restaurant 2",0,"restaurant 2",null,VoorkeurenDTO(null,voorkeuren),VoedingsrestrictiesDTO(restricties)))
        assertEquals(expected,i)
    }
}