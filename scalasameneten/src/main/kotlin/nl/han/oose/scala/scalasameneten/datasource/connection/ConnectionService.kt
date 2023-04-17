package nl.han.oose.scala.scalasameneten.datasource.connection

import jakarta.inject.Inject
import nl.han.oose.scala.scalasameneten.datasource.exceptions.DatabaseConnectionException
import java.sql.Connection
import java.sql.DriverManager


class ConnectionService {
    private var connection: Connection? = null
    private var databaseProperties: DatabaseProperties? = null
    @Inject
    fun setDatabaseProperties(databaseProperties: DatabaseProperties?) {
        this.databaseProperties = databaseProperties
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
