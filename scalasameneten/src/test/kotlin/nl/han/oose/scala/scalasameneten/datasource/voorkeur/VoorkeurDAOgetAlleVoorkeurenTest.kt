package nl.han.oose.scala.scalasameneten.datasource.voorkeur

import nl.han.oose.scala.scalasameneten.datasource.connection.ConnectionService
import nl.han.oose.scala.scalasameneten.datasource.connection.DatabaseProperties
import nl.han.oose.scala.scalasameneten.datasource.util.ScriptRunner
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.InputStreamReader
import java.sql.DriverManager
import java.util.*

class VoorkeurDAOgetAlleVoorkeurenTest() {

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
    fun getAlleVoorkeuren(){
        //arrange
        //act
        val returnValue = voorkeurDAO.getAlleVoorkeuren()
        var counter = 0
        while (returnValue.next()) {
            counter++
        }
        //assert
        assertEquals(9, counter)
    }
}