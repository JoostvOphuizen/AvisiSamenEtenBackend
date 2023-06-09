package nl.han.oose.scala.scalasameneten.controller

import nl.han.oose.scala.scalasameneten.dto.restaurant.GroepDTO
import nl.han.oose.scala.scalasameneten.dto.restaurant.RestaurantWithVoorkeurenAndRestrictiesDTO
import nl.han.oose.scala.scalasameneten.dto.restaurant.ReviewDTO
import nl.han.oose.scala.scalasameneten.service.restaurant.RestaurantService
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
class RestaurantControllerTest {

    @InjectMocks
    lateinit var restaurantController: RestaurantController

    @Mock
    lateinit var restaurantService: RestaurantService

    private lateinit var testRestaurant: RestaurantWithVoorkeurenAndRestrictiesDTO
    private lateinit var testReview: ReviewDTO

    @BeforeEach
    fun setup() {
        testRestaurant = RestaurantWithVoorkeurenAndRestrictiesDTO()
        testReview = ReviewDTO("0000-0000-0000", 5, "tekst")
    }

    @Test
    fun postBepaalRestaurantTest() {
        val testGebruikers = arrayListOf<Int>()
        val testGebruikerToken = "token"

        whenMockito(restaurantService.bepaalRestaurant(GroepDTO(testGebruikers, "Placeholder"), testGebruikerToken)).thenReturn(ResponseEntity(testRestaurant, HttpStatus.OK))

        val result = restaurantController.postBepaalRestaurant(testGebruikers, testGebruikerToken)

        if (result != null) {
            assert(result.statusCode == HttpStatus.OK)
        }
        if (result != null) {
            assert(result.body == testRestaurant)
        }
    }

    @Test
    fun getAlleRestaurantsTest() {
        val testRestaurants = mutableListOf<RestaurantWithVoorkeurenAndRestrictiesDTO>()

        whenMockito(restaurantService.getAllRestaurants()).thenReturn(ResponseEntity(testRestaurants, HttpStatus.OK))

        val result = restaurantController.getAlleRestaurants()

        assert(result.statusCode == HttpStatus.OK)
        assert(result.body == testRestaurants)
    }

    @Test
    fun getRestaurantTest() {
        val testId = 1

        whenMockito(restaurantService.getRestaurant(testId)).thenReturn(ResponseEntity(testRestaurant, HttpStatus.OK))

        val result = restaurantController.getRestaurant(testId)

        assert(result.statusCode == HttpStatus.OK)
        assert(result.body == testRestaurant)
    }

    @Test
    fun getRandomRestaurantTest() {
        whenMockito(restaurantService.getRandomRestaurant("0000-0000-0000")).thenReturn(ResponseEntity(testRestaurant, HttpStatus.OK))

        val result = restaurantController.getRandomRestaurant("0000-0000-0000")

        assert(result.statusCode == HttpStatus.OK)
        assert(result.body == testRestaurant)
    }

    @Test
    fun postReviewTest() {
        val testId = 1

        whenMockito(restaurantService.postReview(testId, testReview)).thenReturn(ResponseEntity("Success", HttpStatus.OK))

        val result = restaurantController.postReview(testId, testReview)

        assert(result.statusCode == HttpStatus.OK)
        assert(result.body == "Success")
    }

    @Test
    fun getRestaurantBaseInfoTest() {
        val testId = 1

        whenMockito(restaurantService.getRestaurantBaseInfo(testId)).thenReturn(ResponseEntity(testRestaurant, HttpStatus.OK))

        val result = restaurantController.getRestaurantBaseInfo(testId)

        assert(result.statusCode == HttpStatus.OK)
        assert(result.body == testRestaurant)
    }

}
