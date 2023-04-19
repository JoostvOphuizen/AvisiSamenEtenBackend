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
    fun getGebruikersVoedingsrestricties(gebruiker: Int): ResultSet {
        return try {
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            val stmt = GebruikerSQLPreparedStatementBuilder.voeding.buildGetGebruikersVoedingsestrictiesPreparedStatement(connectionService,gebruiker)
            stmt.executeQuery()
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }
    fun getGebruikersVoorkeuren(gebruiker: Int): ResultSet {
        return try {
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            val stmt = GebruikerSQLPreparedStatementBuilder.voeding.buildGetGebruikersVoorkeurPreparedStatement(connectionService,gebruiker)
            stmt.executeQuery()
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }
    fun gebruikersVoedingsrestrictieToevoegen(gebruiker: Int,restrictie: String,type: String) {
        try {
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            val stmt = GebruikerSQLPreparedStatementBuilder.voeding.buildGebruikersVoedingsrestrictieToevoegenPreparedStatement(connectionService,gebruiker,restrictie,type)
            stmt.executeUpdate()
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }
    fun gebruikersVoorkeurToevoegen(gebruiker: Int,voorkeur: String) {
        try {
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            val stmt = GebruikerSQLPreparedStatementBuilder.voeding.buildGebruikersVoorkeurToevoegenPreparedStatement(connectionService,gebruiker,voorkeur)
            stmt.executeUpdate()
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }
    fun gebruikersVoedingsrestrictieVerwijderen(gebruiker: Int,restrictie: String,type: String) {
        try {
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            val stmt = GebruikerSQLPreparedStatementBuilder.voeding.buildVoedingsrestrictieVerwijderenPreparedStatement(connectionService,gebruiker,restrictie,type)
            stmt.executeUpdate()
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }
    fun gebruikersVoorkeurVerwijderen(gebruiker: Int,voorkeur: String) {
        try {
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            val stmt = GebruikerSQLPreparedStatementBuilder.voeding.buildGebruikersVoorkeurVerwijderenPreparedStatement(connectionService,gebruiker,voorkeur)
            stmt.executeUpdate()
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }
}