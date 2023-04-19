package nl.han.oose.scala.scalasameneten.datasource.voorkeur

import nl.han.oose.scala.scalasameneten.datasource.connection.ConnectionService
import nl.han.oose.scala.scalasameneten.datasource.connection.DatabaseProperties
import nl.han.oose.scala.scalasameneten.datasource.util.ScriptRunner
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.InputStreamReader
import java.sql.DriverManager
import java.sql.SQLException
import java.util.*


class VoorkeurDAOTest {
    lateinit var voorkeurDAO: VoorkeurDAO
    lateinit var mockedConnectionService: ConnectionService

    @BeforeEach
    fun setup()    {
        val databaseProperties = DatabaseProperties()
        val connection = DriverManager.getConnection(databaseProperties.getConnectionString())
        val scriptRunner = ScriptRunner(connection, true, true)
        scriptRunner.runScript(InputStreamReader(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("import.sql"))));
        connection.close()

        mockedConnectionService = ConnectionService()
        voorkeurDAO = VoorkeurDAO(mockedConnectionService,databaseProperties)
    }

    @Test
    fun voorkeurBestaat() {
        //arrage
        //act
        val returnValue = voorkeurDAO.voorkeurBestaat("kip")
        var counter = 0
        while (returnValue.next()) {
            counter++
        }
        //assert
        assertEquals(1, counter)
    }
    @Test
    fun voorkeurBestaatNiet() {
        //arrage
        //act
        val returnValue = voorkeurDAO.voorkeurBestaat("kipnugget")
        var counter = 0
        while (returnValue.next()) {
            counter++
        }
        //assert
        assertEquals(0, counter)
    }
}