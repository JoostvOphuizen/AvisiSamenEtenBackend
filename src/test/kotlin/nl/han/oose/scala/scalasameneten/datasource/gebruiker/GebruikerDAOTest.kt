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

        connection.createStatement().executeUpdate("INSERT INTO gebruiker(gebruikersnaam) VALUES ('user1'),('user2')")
        connection.createStatement().executeUpdate("INSERT INTO gebruiker_heeft_voedingsrestrictie(restrictie_naam,type,gebruiker_id) VALUES ('vlees','dieet',1)")
        connection.createStatement().executeUpdate("INSERT INTO voorkeur_van_gebruiker(voorkeur_naam,gebruiker_id) VALUES ('vlees',1)")

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
        assertEquals(2, counter)
    }
    @Test
    fun getNaamVanBestaandeGebruiker() {
        //arrage
        //act
        val naam = gebruikerDAO?.getNaamVanGebruiker(1)
        //assert
        assertEquals("user1", naam)
    }
    @Test
    fun getNaamVanNietBestaandeGebruiker() {
        //arrage
        //act
        val naam = gebruikerDAO?.getNaamVanGebruiker(3)
        //assert
        assertEquals(null, naam)
    }
    @Test
    fun getGebruikersVoedingsRestricties(){
        //arrange
        //act
        val returnValue = gebruikerDAO!!.getGebruikersVoedingsrestricties(1)
        var counter = 0
        while (returnValue.next()) {
            counter++
        }
        //assert
        assertEquals(1, counter)
    }
    @Test
    fun getGebruikersVoorkeuren(){
        //arrange
        //act
        val returnValue = gebruikerDAO!!.getGebruikersVoorkeuren(1)
        var counter = 0
        while (returnValue.next()) {
            counter++
        }
        //assert
        assertEquals(1, counter)
    }
    @Test
    fun gebruikersVoedingsRestrictieToevoegen(){
        //arrange
        //act
        gebruikerDAO!!.gebruikersVoedingsrestrictieToevoegen(1,"vis","dieet")
        val returnValue = gebruikerDAO!!.getGebruikersVoedingsrestricties(1)
        var counter = 0
        while (returnValue.next()) {
            counter++
        }
        //assert
        assertEquals(2, counter)
    }
    @Test
    fun gebruikersVoorkeurToevoegen(){
        //arrange
        //act
        gebruikerDAO!!.gebruikersVoorkeurToevoegen(1,"soep")
        val returnValue = gebruikerDAO.getGebruikersVoorkeuren(1)
        var counter = 0
        while (returnValue.next()) {
            counter++
        }
        //assert
        assertEquals(2, counter)
    }
}