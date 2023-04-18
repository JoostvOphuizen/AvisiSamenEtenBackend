package nl.han.oose.scala.scalasameneten.datasource.connection

import jakarta.inject.Inject
import nl.han.oose.scala.scalasameneten.datasource.exceptions.DatabaseConnectionException
import org.springframework.stereotype.Component
import java.sql.Connection
import java.sql.DriverManager
import javax.xml.crypto.Data

@Component
class ConnectionService {
    private var connection: Connection? = null
    private var databaseProperties: DatabaseProperties? = null

    fun setDatabaseProperties(databaseProperties: DatabaseProperties?) {
        this.databaseProperties = databaseProperties
    }
    fun getDatabaseProperties():DatabaseProperties?{
        return databaseProperties
    }

    fun initializeConnection(connectionString: String?) {
        connection = try {
            DriverManager.getConnection(connectionString)
        } catch (e: Exception) {
            throw DatabaseConnectionException()
        }
    }

    fun getConnection(): Connection? {
        return connection
    }
    fun closeConnection() {
        try {
            connection!!.close()
        } catch (e: Exception) {
            throw DatabaseConnectionException()
        }
    }
}
