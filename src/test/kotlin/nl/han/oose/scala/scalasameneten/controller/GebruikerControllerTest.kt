package nl.han.oose.scala.scalasameneten.controller

import nl.han.oose.scala.scalasameneten.dto.gebruiker.GebruikerDTO
import nl.han.oose.scala.scalasameneten.dto.gebruiker.GebruikersDTO
import nl.han.oose.scala.scalasameneten.dto.gebruiker.LoginDTO
import nl.han.oose.scala.scalasameneten.dto.gebruiker.TokenDTO
import nl.han.oose.scala.scalasameneten.dto.restaurant.RestaurantWithVoorkeurenAndRestrictiesDTO
import nl.han.oose.scala.scalasameneten.dto.voorkeur.VoorkeurenDTO
import nl.han.oose.scala.scalasameneten.service.gebruiker.GebruikerService
import nl.han.oose.scala.scalasameneten.service.restaurant.RestaurantService
import org.modelmapper.ModelMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.mockito.Mockito.`when` as whenMockito

@ExtendWith(MockitoExtension::class)
class GebruikerControllerTest {

    @InjectMocks
    lateinit var gebruikerController: GebruikerController

    @Mock
    lateinit var gebruikerService: GebruikerService

    @Mock
    lateinit var restaurantService: RestaurantService

    @Mock
    lateinit var modelMapper: ModelMapper

    private lateinit var testLoginDTO: LoginDTO
    private lateinit var testGebruikersDTO: GebruikersDTO
    private lateinit var testVoorkeurenDTO: VoorkeurenDTO
    private lateinit var testRestaurantDTO: RestaurantWithVoorkeurenAndRestrictiesDTO

    @BeforeEach
    fun setup() {
        testLoginDTO = LoginDTO("email@email.com", "naam", "foto.png")
        testGebruikersDTO = GebruikersDTO(ArrayList<GebruikerDTO>())
        testVoorkeurenDTO = VoorkeurenDTO()
        testRestaurantDTO = RestaurantWithVoorkeurenAndRestrictiesDTO()
    }

    @Test
    fun postLoginGebruikerTest() {
        val response = ResponseEntity(TokenDTO("1"), HttpStatus.OK)
        whenMockito(gebruikerService.loginGebruiker(testLoginDTO)).thenReturn(response)

        val result = gebruikerController.postLoginGebruiker(testLoginDTO)

        assert(result == response)
    }

    @Test
    fun getGebruikersBaseInfoTest() {
        whenMockito(gebruikerService.getGebruikersBaseInfo()).thenReturn(ResponseEntity(testGebruikersDTO, HttpStatus.OK))

        val result = gebruikerController.getGebruikersBaseInfo()

        assert(result.statusCode == HttpStatus.OK)
        assert(result.body == testGebruikersDTO)
    }

    @Test
    fun getVoorkeurenTest() {
        val testId = "1"
        whenMockito(gebruikerService.getGebruikersVoorkeuren(testId)).thenReturn(ResponseEntity(testVoorkeurenDTO, HttpStatus.OK))

        val result = gebruikerController.getVoorkeuren(testId)

        assert(result.statusCode == HttpStatus.OK)
        assert(result.body == testVoorkeurenDTO)
    }

    @Test
    fun postVoorkeurenTest() {
        val testId = "1"
        whenMockito(modelMapper.map(testVoorkeurenDTO, VoorkeurenDTO::class.java)).thenReturn(testVoorkeurenDTO)
        whenMockito(gebruikerService.postGebruikersVoorkeuren(testId, testVoorkeurenDTO)).thenReturn(ResponseEntity(HttpStatus.OK))

        val result = gebruikerController.postVoorkeuren(testId, testVoorkeurenDTO)

        assert(result.statusCode == HttpStatus.OK)
    }

    @Test
    fun postRestrictiesTest() {
        val testId = "1"
        whenMockito(modelMapper.map(testVoorkeurenDTO, VoorkeurenDTO::class.java)).thenReturn(testVoorkeurenDTO)
        whenMockito(gebruikerService.postGebruikersRestricties(testId, testVoorkeurenDTO)).thenReturn(ResponseEntity(HttpStatus.OK))

        val result = gebruikerController.postRestricties(testId, testVoorkeurenDTO)

        assert(result.statusCode == HttpStatus.OK)
    }

    @Test
    fun getRestaurantHistorieTest() {
        val testId = "1"
    //    whenMockito(restaurantService.getRecentBezochteRestaurant(testId)).thenReturn(ResponseEntity(testRestaurantDTO, HttpStatus.OK))

        val result = gebruikerController.getRestaurantHistorie(testId)

        if (result != null) {
            assert(result.statusCode == HttpStatus.OK)
        }
        if (result != null) {
 //           assert(result.body == testRestaurantDTO)
        }
    }
}
