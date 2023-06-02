package nl.han.oose.scala.scalasameneten.service.gebruiker

import nl.han.oose.scala.scalasameneten.datasource.gebruiker.GebruikerDAO
import nl.han.oose.scala.scalasameneten.dto.gebruiker.GebruikerDTO
import nl.han.oose.scala.scalasameneten.dto.gebruiker.GebruikersDTO
import nl.han.oose.scala.scalasameneten.dto.gebruiker.LoginDTO
import nl.han.oose.scala.scalasameneten.dto.gebruiker.TokenDTO
import nl.han.oose.scala.scalasameneten.dto.voorkeur.VoorkeurenDTO
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatcher
import org.mockito.ArgumentMatchers.argThat
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.sql.ResultSet

class GebruikerServiceTest {

    @Mock
    private lateinit var gebruikerDAO: GebruikerDAO

    private lateinit var gebruikerService: GebruikerService

    @BeforeEach
    fun setup() {
        MockitoAnnotations.initMocks(this)
        gebruikerService = GebruikerService(gebruikerDAO)
    }

    @Test
    fun genereerToken_ReturnsTokenWithValidFormat() {
        // Arrange
        val mockResultSet = Mockito.mock(ResultSet::class.java)
        `when`(gebruikerDAO.getAlleGebruikers()).thenReturn(mockResultSet)
        `when`(mockResultSet.next()).thenReturn(false)

        // Act
        val token = gebruikerService.genereerToken()

        // Assert
        assertEquals(14, token.length)
        val parts = token.split("-")
        assertEquals(3, parts.size)
        parts.forEach { part ->
            assertEquals(4, part.length)
            part.toIntOrNull()?.let { number ->
                assertTrue(number in 1000..9999)
            } ?: fail("Token part is not a valid number: $part")
        }
    }

    @Test
    fun loginGebruiker_ValidLogin_ReturnsTokenDTO() {
        // Arrange
        val login = LoginDTO("email", "naam", "foto")
        val expectedToken = TokenDTO("token")

        `when`(gebruikerDAO.loginGebruiker(eq(login), Mockito.anyString())).thenReturn(expectedToken)
        `when`(gebruikerDAO.loginGebruiker(argThat { it == null }, Mockito.anyString())).thenReturn(expectedToken)

        // Mock the genereerToken() method separately
        val mockToken = "mocked-token"
        `when`(gebruikerService.genereerToken()).thenReturn(mockToken)

        // Act
        val responseEntity: ResponseEntity<TokenDTO> = gebruikerService.loginGebruiker(login)

        // Assert
        assertEquals(expectedToken, responseEntity.body)
        assertEquals(HttpStatus.OK, responseEntity.statusCode)
    }


    @Test
    fun getGebruikersBaseInfo_ReturnsGebruikersDTO() {
        // Arrange
        val expectedGebruikersDTO = GebruikersDTO(ArrayList<GebruikerDTO>())
        `when`(gebruikerDAO.makeGebruikersDTOBaseInfo()).thenReturn(expectedGebruikersDTO)

        // Act
        val responseEntity: ResponseEntity<GebruikersDTO> = gebruikerService.getGebruikersBaseInfo()

        // Assert
        assertEquals(expectedGebruikersDTO, responseEntity.body)
        assertEquals(HttpStatus.OK, responseEntity.statusCode)
    }

    @Test
    fun getGebruikersVoorkeuren_ReturnsVoorkeurenDTO() {
        // Arrange
        val token = "token"
        val expectedVoorkeurenDTO = VoorkeurenDTO(null, ArrayList())
        `when`(gebruikerDAO.makeVoorkeurenDTO(token)).thenReturn(expectedVoorkeurenDTO)

        // Act
        val responseEntity: ResponseEntity<VoorkeurenDTO> = gebruikerService.getGebruikersVoorkeuren(token)

        // Assert
        assertEquals(expectedVoorkeurenDTO, responseEntity.body)
        assertEquals(HttpStatus.OK, responseEntity.statusCode)
    }


    @Test
    fun postGebruikersVoorkeuren_ReturnsOkResponseEntity() {
        // Arrange
        val token = "token"
        val voorkeurenDTO = VoorkeurenDTO(null, ArrayList())
        `when`(gebruikerDAO.setGebruikersVoorkeuren(token, voorkeurenDTO)).thenReturn(Unit)

        // Act
        val responseEntity: ResponseEntity<Void> = gebruikerService.postGebruikersVoorkeuren(token, voorkeurenDTO)

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.statusCode)
    }

    @Test
    fun postGebruikersRestricties_ReturnsOkResponseEntity() {
        // Arrange
        val token = "token"
        val restricties = VoorkeurenDTO(null, ArrayList())
        `when`(gebruikerDAO.setGebruikersRestricties(token, restricties)).thenReturn(Unit)

        gebruikerService = GebruikerService(gebruikerDAO)

        // Act
        val responseEntity: ResponseEntity<Void> = gebruikerService.postGebruikersRestricties(token, restricties)

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.statusCode)
    }

}

private fun Any.thenReturn(unit: Unit) {
    return unit
}


private class LoginDtoMatcher(private val expected: LoginDTO) : ArgumentMatcher<LoginDTO> {
    override fun matches(argument: LoginDTO?): Boolean {
        return argument != null && argument == expected
    }
}

