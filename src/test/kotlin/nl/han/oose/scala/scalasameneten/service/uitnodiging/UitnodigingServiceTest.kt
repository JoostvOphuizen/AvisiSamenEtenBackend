package nl.han.oose.scala.scalasameneten.service.uitnodiging

import nl.han.oose.scala.scalasameneten.datasource.gebruiker.GebruikerDAO
import nl.han.oose.scala.scalasameneten.datasource.restaurant.RestaurantDAO
import nl.han.oose.scala.scalasameneten.datasource.uitnodiging.UitnodigingDAO
import nl.han.oose.scala.scalasameneten.dto.gebruiker.GebruikerDTO
import nl.han.oose.scala.scalasameneten.dto.gebruiker.GebruikersDTO
import nl.han.oose.scala.scalasameneten.dto.uitnodiging.UitgenodigdenDTO
import nl.han.oose.scala.scalasameneten.dto.uitnodiging.UitnodigingDTO
import nl.han.oose.scala.scalasameneten.dto.voedingsrestrictie.VoedingsrestrictieDTO
import nl.han.oose.scala.scalasameneten.dto.voorkeur.VoorkeurDTO
import nl.han.oose.scala.scalasameneten.dto.voorkeur.VoorkeurenDTO
import nl.han.oose.scala.scalasameneten.service.restaurant.RestaurantService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import java.sql.ResultSet

class UitnodigingServiceTest {
    @Mock
    lateinit var uitnodigingService: UitnodigingService
    private lateinit var uitnodigingDAO: UitnodigingDAO
    private lateinit var resultSetMock: ResultSet
    private lateinit var gebruikersResultSet: ResultSet

    @BeforeEach
    fun setup() {
        uitnodigingDAO = Mockito.mock(UitnodigingDAO::class.java)
        uitnodigingService = UitnodigingService(uitnodigingDAO)
        resultSetMock = Mockito.mock(ResultSet::class.java)
        gebruikersResultSet = Mockito.mock(ResultSet::class.java)

        Mockito.`when`(uitnodigingDAO.checkIfTokenExists(anyString())).thenReturn(true).thenReturn(false)
        Mockito.`when`(uitnodigingDAO.getGebruikerID("0000-0000-0000")).thenReturn(1)
        Mockito.`when`(uitnodigingDAO.getRestaurantID("1234-1234-1234")).thenReturn(1)
        Mockito.`when`(uitnodigingDAO.getUitgenodigden("1234-1234-1234")).thenReturn(gebruikersResultSet)
        Mockito.`when`(uitnodigingDAO.getHost("1234-1234-1234")).thenReturn(gebruikersResultSet)

        Mockito.`when`(gebruikersResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false)
        Mockito.`when`(gebruikersResultSet.getString("restricties")).thenReturn("vlees,noten").thenReturn("")
        Mockito.`when`(gebruikersResultSet.getString("voorkeuren")).thenReturn("pizza").thenReturn("vlees,vis")
        Mockito.`when`(gebruikersResultSet.getInt("gebruiker_id")).thenReturn(1).thenReturn(2)
        Mockito.`when`(gebruikersResultSet.getString("gebruikersnaam")).thenReturn("user 1").thenReturn("user 2")
        Mockito.`when`(gebruikersResultSet.getString("foto")).thenReturn(null)
    }
    @Test
    fun createUitnodigingTest(){
        //arrange
        //act
        val result = uitnodigingService.createUitnodiging("0000-0000-0000")
        //assert
        assert(result.body!!.uitnodigingToken.length==14)
    }
    @Test
    fun getUitgenodigdenTest(){
        //arrange
        //act
        val result = uitnodigingService.getUitgenodigden("1234-1234-1234","0000-0000-0000")
        val voorkeuren = ArrayList<VoorkeurDTO>()
        voorkeuren.add(VoorkeurDTO("vlees"))
        voorkeuren.add(VoorkeurDTO("vis"))
        val host = GebruikerDTO(1,"user 1",null, voorkeuren,arrayOf(VoedingsrestrictieDTO("")).toCollection(ArrayList()))
        val user2 = GebruikerDTO(2,"user 2",null,voorkeuren, arrayOf(VoedingsrestrictieDTO("")).toCollection(ArrayList()))
        val verwacht = UitgenodigdenDTO(1,1,host, GebruikersDTO(arrayOf(user2).toCollection(ArrayList())))
        //assert
        assertEquals(verwacht,result.body)
    }
}