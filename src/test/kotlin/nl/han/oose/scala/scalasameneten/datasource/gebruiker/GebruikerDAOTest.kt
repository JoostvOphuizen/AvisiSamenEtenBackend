package nl.han.oose.scala.scalasameneten.datasource.gebruiker


import nl.han.oose.scala.scalasameneten.datasource.connection.ConnectionService
import nl.han.oose.scala.scalasameneten.datasource.connection.DatabaseProperties
import nl.han.oose.scala.scalasameneten.datasource.util.ScriptRunner
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import java.io.InputStreamReader
import java.sql.Connection
import java.sql.DriverManager
import java.util.*


class GebruikerDAOTest {

    lateinit var gebruikerDAO: GebruikerDAO
    lateinit var mockedConnectedService: ConnectionService
    val databaseProperties = DatabaseProperties()
    lateinit var connection: Connection
    @BeforeEach
    fun setup()    {
        connection = DriverManager.getConnection(databaseProperties.getConnectionString())
        val scriptRunner = ScriptRunner(connection, true, true)
        scriptRunner.runScript(InputStreamReader(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("import.sql"))))
        scriptRunner.runScript(InputStreamReader(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("insert.sql"))))

        mockedConnectedService = ConnectionService()
        gebruikerDAO = GebruikerDAO(mockedConnectedService,databaseProperties)
        connection.close()
    }

    @Test
    fun getAlleGebruikers(){
        //arrange
        //act
        val returnValue = gebruikerDAO!!.getAlleGebruikers()
        var counter = 0
        while (returnValue.next()) {
            counter++
        }
        //assert
        assertEquals(3, counter)
    }
    @Test
    fun getNaamVanBestaandeGebruiker() {
        //arrage
        //act
        val naam = gebruikerDAO?.getNaamVanGebruiker("0000-0000-0000")
        //assert
        assertEquals("user1", naam)
    }
    @Test
    fun getNaamVanNietBestaandeGebruiker() {
        //arrage
        //act
        val naam = gebruikerDAO?.getNaamVanGebruiker("1212-1212-1212")
        //assert
        assertEquals(null, naam)
    }
    @Test
    fun getGebruikersVoedingsRestricties(){
        //arrange
        //act
        val returnValue = gebruikerDAO!!.getGebruikersVoedingsrestricties("0000-0000-0000")
        var counter = 0
        while (returnValue.next()) {
            counter++
        }
        //assert
        assertEquals(2, counter)
    }
    @Test
    fun getGebruikersVoorkeuren(){
        //arrange
        //act
        val returnValue = gebruikerDAO!!.getGebruikersVoorkeuren("0000-0000-0000")
        var counter = 0
        while (returnValue.next()) {
            counter++
        }
        //assert
        assertEquals(2, counter)
    }
}