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

        Mockito.`when`(resultSetMock.next()).thenReturn(true).thenReturn(true).thenReturn(false)
        Mockito.`when`(resultSetMock.getInt("restaurant_id")).thenReturn(1)
        Mockito.`when`(resultSetMock.getString("restaurant_naam")).thenReturn("restaurant 1").thenReturn("restaurant 2")
        Mockito.`when`(resultSetMock.getString("postcode")).thenReturn("restaurant 1").thenReturn("restaurant 2")
        Mockito.`when`(resultSetMock.getString("straatnaam")).thenReturn("restaurant 1").thenReturn("restaurant 2")
        Mockito.`when`(resultSetMock.getString("link")).thenReturn("restaurant 1").thenReturn("restaurant 2")
        Mockito.`when`(resultSetMock.getString("voorkeuren")).thenReturn("voorkeur 1").thenReturn("voorkeur 1")
        Mockito.`when`(resultSetMock.getString("restricties")).thenReturn("restrictie 1").thenReturn("restrictie 1")

    }

    @Test
    fun getAllRestaurantsTest(){
        //arrange
        Mockito.`when`(restaurantDAO.getRestaurant(1)).thenReturn(resultSetMock)
        //act
        val result = restaurantService.getRestaurant(1)//assert
        val restaurantnaam = result.body?.restaurantNaam
        assertEquals("restaurant 1",restaurantnaam)
    }
}