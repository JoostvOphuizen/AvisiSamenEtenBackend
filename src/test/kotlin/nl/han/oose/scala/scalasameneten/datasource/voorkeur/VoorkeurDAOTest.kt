/*
package nl.han.oose.scala.scalasameneten.datasource.voorkeur

import nl.han.oose.scala.scalasameneten.datasource.connection.ConnectionService
import nl.han.oose.scala.scalasameneten.datasource.connection.DatabaseProperties
import nl.han.oose.scala.scalasameneten.datasource.util.ScriptRunner
import nl.han.oose.scala.scalasameneten.datasource.voedingsrestrictie.VoedingsrestrictieDAO
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.InputStreamReader
import java.sql.Connection
import java.sql.DriverManager
import java.util.*


class VoorkeurDAOTest {
    lateinit var voorkeurDAO: VoorkeurDAO
    lateinit var mockedConnectedService: ConnectionService
    val databaseProperties = DatabaseProperties()
    lateinit var connection: Connection

    @BeforeEach
    fun setup()    {
        connection = DriverManager.getConnection(databaseProperties.getConnectionString())
        val scriptRunner = ScriptRunner(connection, true, true)
        scriptRunner.runScript(InputStreamReader(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("import.sql"))))

        connection.createStatement().executeUpdate("INSERT INTO voorkeur(voorkeur_naam) VALUES ('kip')")
        connection.createStatement().executeUpdate("INSERT INTO voorkeur(voorkeur_naam) VALUES ('mexicaans')")
        connection.createStatement().executeUpdate("INSERT INTO voorkeur(voorkeur_naam) VALUES ('pizza')")

        mockedConnectedService = ConnectionService()
        voorkeurDAO = VoorkeurDAO(mockedConnectedService,databaseProperties)
        connection.close()
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
        assertEquals(3, counter)
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
}*/
