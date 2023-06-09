package nl.han.oose.scala.scalasameneten.controller

import nl.han.oose.scala.scalasameneten.dto.voedingsbehoeften.VoedingsbehoeftenDTO
import nl.han.oose.scala.scalasameneten.service.voorkeur.VoorkeurService
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
class VoorkeurControllerTest {

    @InjectMocks
    lateinit var voorkeurController: VoorkeurController

    @Mock
    lateinit var voorkeurService: VoorkeurService

    private lateinit var testVoedingsbehoeftenDTO: VoedingsbehoeftenDTO

    @BeforeEach
    fun setup() {
        testVoedingsbehoeftenDTO = VoedingsbehoeftenDTO()
    }

    @Test
    fun getVoedingsbehoeftenTest() {
        val testGebruikersToken = "token"
        whenMockito(voorkeurService.getVoedingsbehoeften(testGebruikersToken)).thenReturn(ResponseEntity(testVoedingsbehoeftenDTO, HttpStatus.OK))

        val result = voorkeurController.getVoedingsbehoeften(testGebruikersToken)

        assert(result.statusCode == HttpStatus.OK)
        assert(result.body == testVoedingsbehoeftenDTO)
    }
}
