package nl.han.oose.scala.scalasameneten.controller

import nl.han.oose.scala.scalasameneten.dto.uitnodiging.UitgenodigdenDTO
import nl.han.oose.scala.scalasameneten.dto.uitnodiging.UitnodigingDTO
import nl.han.oose.scala.scalasameneten.service.uitnodiging.UitnodigingService
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
class UitnodigingControllerTest {

    @InjectMocks
    lateinit var uitnodigingController: UitnodigingController

    @Mock
    lateinit var uitnodigingService: UitnodigingService

    private lateinit var testUitnodigingDTO: UitnodigingDTO
    private lateinit var testUitgenodigdenDTO: UitgenodigdenDTO

    @BeforeEach
    fun setup() {
        testUitnodigingDTO = UitnodigingDTO()
        testUitgenodigdenDTO = UitgenodigdenDTO()
    }

    @Test
    fun createUitnodigingTest() {
        whenMockito(uitnodigingService.createUitnodiging(testUitnodigingDTO.uitnodigingToken)).thenReturn(ResponseEntity(testUitnodigingDTO, HttpStatus.OK))

        val result = uitnodigingController.createUitnodiging(testUitnodigingDTO)

        assert(result.statusCode == HttpStatus.OK)
        assert(result.body == testUitnodigingDTO)
    }

    @Test
    fun getUitgenodigdenTest() {
        val testUitnodigingToken = "token"
        val testGebruikerToken = "userToken"
        whenMockito(uitnodigingService.getUitgenodigden(testUitnodigingToken, testGebruikerToken)).thenReturn(ResponseEntity(testUitgenodigdenDTO, HttpStatus.OK))

        val result = uitnodigingController.getUitgenodigden(testUitnodigingToken, testGebruikerToken)

        assert(result.statusCode == HttpStatus.OK)
        assert(result.body == testUitgenodigdenDTO)
    }

    @Test
    fun accepteerUitnodigingTest() {
        val testUitnodigingToken = testUitnodigingDTO.uitnodigingToken
        val testGebruikerToken = "userToken"
        whenMockito(uitnodigingService.accepteerUitnodiging(testUitnodigingToken, testGebruikerToken)).thenReturn(ResponseEntity("Accepted", HttpStatus.OK))

        val result = uitnodigingController.accepteerUitnodiging(testUitnodigingDTO, testGebruikerToken)

        assert(result.statusCode == HttpStatus.OK)
        assert(result.body == "Accepted")
    }

    @Test
    fun updateRestaurantTest() {
        val testUitnodigingToken = testUitnodigingDTO.uitnodigingToken
        val testRestaurantId = 1
        whenMockito(uitnodigingService.updateRestaurant(testUitnodigingToken, testRestaurantId)).thenReturn(ResponseEntity("Updated", HttpStatus.OK))

        val result = uitnodigingController.updateRestaurant(testUitnodigingDTO, testRestaurantId)

        assert(result.statusCode == HttpStatus.OK)
        assert(result.body == "Updated")
    }

    @Test
    fun kickUitgenodigdeTest() {
        val testUitnodigingToken = testUitnodigingDTO.uitnodigingToken
        val testUitgenodigdeID = 1
        whenMockito(uitnodigingService.kickUitgenodigde(testUitnodigingToken, testUitgenodigdeID)).thenReturn(ResponseEntity("Kicked", HttpStatus.OK))

        val result = uitnodigingController.kickUitgenodigde(testUitnodigingDTO, testUitgenodigdeID)

        assert(result.statusCode == HttpStatus.OK)
        assert(result.body == "Kicked")
    }
}
