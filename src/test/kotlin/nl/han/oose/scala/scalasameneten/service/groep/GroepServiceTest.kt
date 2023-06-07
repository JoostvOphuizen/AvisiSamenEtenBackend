package nl.han.oose.scala.scalasameneten.service.groep

import nl.han.oose.scala.scalasameneten.datasource.groep.GroepDAO
import nl.han.oose.scala.scalasameneten.dto.restaurant.GroepDTO
import nl.han.oose.scala.scalasameneten.dto.restaurant.GroepenDTO
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.mockito.Mock
import org.mockito.Mockito.*
import org.springframework.http.ResponseEntity
import org.springframework.test.util.TestSocketUtils
import java.sql.ResultSet

class GroepServiceTest {

    @Mock
    private lateinit var sut: GroepService
    private lateinit var groepDAO: GroepDAO

    @BeforeEach
    fun setup() {
        this.groepDAO = mock(GroepDAO::class.java)
        this.sut = GroepService(groepDAO)
    }

    @Test
    fun slaGroepOpReturnsSuccesTest() {
        // Arrange
        val expected = ResponseEntity.ok("Great Succes")
        val groepDTO = GroepDTO(arrayListOf(1, 2, 3), "groepnaam")
        // Act
        val actual = sut.slaGroepOp(groepDTO)
        // Assert
        verify(groepDAO, times(1)).insertGroep(groepDTO.naam)
        verify(groepDAO, times(1)).setGroepsleden(groepDTO)
        assertEquals(expected.statusCode, actual.statusCode)
        assertEquals(expected.body, actual.body)
    }

    @Test
    fun haalGroepenOpReturnsGroepenDTOTest() {
        // Arrange
        val groepenDTO = GroepenDTO(arrayListOf(GroepDTO(arrayListOf(1, 2, 3), "groepnaam")))
        val expected = ResponseEntity.ok(groepenDTO)
        val resultSetMock = getResultSetMock()
        `when`(groepDAO.getGroepen()).thenReturn(resultSetMock)

        // Act
        val actual = sut.haalGroepenOp()
        // Assert
        verify(groepDAO, times(1)).getGroepen()
        assertEquals(expected.statusCode, actual.statusCode)
        assertEquals(expected.body, actual.body)
    }

    private fun getResultSetMock() : ResultSet{
        val resultSetMock = mock(ResultSet::class.java)

        `when`(resultSetMock.next())
            .thenReturn(true)
            .thenReturn(false)

        `when`(resultSetMock.getString("GROEPNAAM"))
            .thenReturn("groepnaam")

        `when`(resultSetMock.getString("USER_IDS"))
            .thenReturn("1,2,3")

        return resultSetMock
    }
}