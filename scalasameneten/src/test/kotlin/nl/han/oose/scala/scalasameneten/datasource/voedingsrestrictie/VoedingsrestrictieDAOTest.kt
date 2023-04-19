package nl.han.oose.scala.scalasameneten.datasource.voedingsrestrictie

import nl.han.oose.scala.scalasameneten.datasource.connection.ConnectionService
import nl.han.oose.scala.scalasameneten.datasource.connection.DatabaseProperties
import nl.han.oose.scala.scalasameneten.datasource.util.ScriptRunner
import nl.han.oose.scala.scalasameneten.datasource.voorkeur.VoorkeurDAO
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.InputStreamReader
import java.sql.DriverManager
import java.util.*

class VoedingsrestrictieDAOTest {
    lateinit var voedingsrestrictieDAO: VoedingsrestrictieDAO
    lateinit var mockedConnectionService: ConnectionService

    @BeforeEach
    fun setup()    {
        val databaseProperties = DatabaseProperties()
        val connection = DriverManager.getConnection(databaseProperties.getConnectionString())
        val scriptRunner = ScriptRunner(connection, true, true)
        scriptRunner.runScript(InputStreamReader(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("import.sql"))));
        connection.close()

        mockedConnectionService = ConnectionService()
        voedingsrestrictieDAO = VoedingsrestrictieDAO(mockedConnectionService,databaseProperties)
    }
    @Test
    fun getAlleAllergieen(){
        //arrange
        //act
        val returnValue = voedingsrestrictieDAO.getAlleAllergieen()
        var counter = 0
        while (returnValue.next()) {
            counter++
        }
        //assert
        Assertions.assertEquals(1, counter)
    }
    @Test
    fun getAlleGeloof(){
        //arrange
        //act
        val returnValue = voedingsrestrictieDAO.getAlleGeloof()
        var counter = 0
        while (returnValue.next()) {
            counter++
        }
        //assert
        Assertions.assertEquals(1, counter)
    }
    @Test
    fun getAlleDieet(){
        //arrange
        //act
        val returnValue = voedingsrestrictieDAO.getAlleDieet()
        var counter = 0
        while (returnValue.next()) {
            counter++
        }
        //assert
        Assertions.assertEquals(1, counter)
    }
    @Test
    fun allergieBestaat() {
        //arrage
        //act
        val returnValue = voedingsrestrictieDAO.allergieBestaat("noten")
        var counter = 0
        while (returnValue.next()) {
            counter++
        }
        //assert
        Assertions.assertEquals(1, counter)
    }
    @Test
    fun allergieBestaatNiet() {
        //arrage
        //act
        val returnValue = voedingsrestrictieDAO.allergieBestaat("notendop")
        var counter = 0
        while (returnValue.next()) {
            counter++
        }
        //assert
        Assertions.assertEquals(0, counter)
    }
    @Test
    fun geloofBestaat() {
        //arrage
        //act
        val returnValue = voedingsrestrictieDAO.geloofBestaat("varkensvlees")
        var counter = 0
        while (returnValue.next()) {
            counter++
        }
        //assert
        Assertions.assertEquals(1, counter)
    }
    @Test
    fun geloofBestaatNiet() {
        //arrage
        //act
        val returnValue = voedingsrestrictieDAO.geloofBestaat("eenhoornvlees")
        var counter = 0
        while (returnValue.next()) {
            counter++
        }
        //assert
        Assertions.assertEquals(0, counter)
    }
    @Test
    fun dieetBestaat() {
        //arrage
        //act
        val returnValue = voedingsrestrictieDAO.dieetBestaat("vlees")
        var counter = 0
        while (returnValue.next()) {
            counter++
        }
        //assert
        Assertions.assertEquals(1, counter)
    }
    @Test
    fun dieetBestaatNiet() {
        //arrage
        //act
        val returnValue = voedingsrestrictieDAO.dieetBestaat("vleesch")
        var counter = 0
        while (returnValue.next()) {
            counter++
        }
        //assert
        Assertions.assertEquals(0, counter)
    }
}