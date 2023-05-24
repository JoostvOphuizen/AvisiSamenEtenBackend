package nl.han.oose.scala.scalasameneten.datasource.restaurant

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

class RestaurantDAOTest {
    lateinit var restaurantDAO: RestaurantDAO
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
        restaurantDAO = RestaurantDAO(mockedConnectedService,databaseProperties)
        connection.close()
    }
    @Test
    fun getAlleRestaurants(){
        //arrange
        //act
        val restaurants = restaurantDAO.getAllRestaurantsWithVoorkeurenAndRestricties()
        var counter = 0
        while(restaurants.next()){
            counter++
        }
        //assert
        assertEquals(5,counter)
    }
}