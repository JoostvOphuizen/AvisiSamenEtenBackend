package nl.han.oose.scala.scalasameneten.datasource.gebruiker

import nl.han.oose.scala.scalasameneten.datasource.connection.ConnectionService
import nl.han.oose.scala.scalasameneten.datasource.connection.DatabaseProperties
import nl.han.oose.scala.scalasameneten.datasource.exceptions.DatabaseConnectionException
import nl.han.oose.scala.scalasameneten.datasource.voorkeur.VoorkeurSQLPreparedStatementBuilder
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

class GebruikerDAO {

    private val connectionService: ConnectionService? = null
    private val databaseProperties: DatabaseProperties? = null
    fun getAlleGebruikers(): ResultSet {
        return try {
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            val statement: PreparedStatement = GebruikerSQLPreparedStatementBuilder.gebruiker.buildGetAlleGebruikersPreparedStatement(connectionService)
            statement.executeQuery()
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }
}