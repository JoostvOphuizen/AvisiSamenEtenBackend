package nl.han.oose.scala.scalasameneten.controller

import nl.han.oose.scala.scalasameneten.dto.restaurant.GroepDTO
import nl.han.oose.scala.scalasameneten.dto.restaurant.GroepenDTO
import nl.han.oose.scala.scalasameneten.service.groep.GroepService
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
class GroepControllerTest {

    @InjectMocks
    lateinit var groepController: GroepController

    @Mock
    lateinit var groepService: GroepService

    private lateinit var testGroepDTO: GroepDTO
    private lateinit var testGroepenDTO: GroepenDTO

    @BeforeEach
    fun setup() {
        testGroepDTO = GroepDTO()
        testGroepenDTO = GroepenDTO()
    }

    @Test
    fun createGroupTest() {
        whenMockito(groepService.slaGroepOp(testGroepDTO)).thenReturn(ResponseEntity("Created Group", HttpStatus.CREATED))

        val result = groepController.createGroup(testGroepDTO)

        assert(result.statusCode == HttpStatus.CREATED)
        assert(result.body == "Created Group")
    }

    @Test
    fun getGroupTest() {
        whenMockito(groepService.haalGroepenOp()).thenReturn(ResponseEntity(testGroepenDTO, HttpStatus.OK))

        val result = groepController.getGroup()

        assert(result.statusCode == HttpStatus.OK)
        assert(result.body == testGroepenDTO)
    }
}
