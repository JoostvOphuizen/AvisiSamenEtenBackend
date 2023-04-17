package nl.han.oose.scala.scalasameneten.datasource.voorkeuren

import nl.han.oose.scala.scalasameneten.datasource.connection.ConnectionService
import nl.han.oose.scala.scalasameneten.datasource.connection.DatabaseProperties
import nl.han.oose.scala.scalasameneten.datasource.exceptions.DatabaseConnectionException
import nl.han.oose.scala.scalasameneten.datasource.factory.VoorkeurenDTOFactory
import nl.han.oose.scala.scalasameneten.dto.voorkeuren.VoorkeurenDTO
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException


class VoorkeurenDAO {

    private val connectionService: ConnectionService? = null
    private val databaseProperties: DatabaseProperties? = null
    fun getAlleVoorkeuren(): ResultSet {
        return try {
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            val statement: PreparedStatement = VoorkeurenSQLPreparedStatementBuilder.voorkeurenStatements.buildGetAlleVoorkeurenPreparedStatement(connectionService)
            statement.executeQuery()
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }
    fun getGebruikersVoorkeuren(gebruiker: Int): ResultSet {
        return try {
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            val statement: PreparedStatement = VoorkeurenSQLPreparedStatementBuilder.voorkeurenStatements.buildGetGebruikersVoorkeurPreparedStatement(connectionService,gebruiker)
            statement.executeQuery()
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }
    fun gebruikerHeeftVoorkeur(gebruiker: Int,voorkeur: String): Boolean {
        try {
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            val result = getGebruikersVoorkeuren(gebruiker)
            while(result.next()){
                if(result.getString("naam")==voorkeur){
                    return true
                }
            }
            return false
        } catch(e: SQLException){
            throw DatabaseConnectionException()
        }
    }
    fun gebruikersVoorkeurenToevoegen(gebruiker: Int,voorkeur: String) {
        try{
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            if(!gebruikerHeeftVoorkeur(gebruiker,voorkeur)) {
                val statement = VoorkeurenSQLPreparedStatementBuilder.voorkeurenStatements.buildGebruikersVoorkeurenToevoegenPreparedStatement(connectionService, gebruiker, voorkeur)
                statement.executeUpdate()
            }
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }
    fun voorkeurBestaat(voorkeur: String): ResultSet{
        return try{
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            val statement = VoorkeurenSQLPreparedStatementBuilder.voorkeurenStatements.buildVoorkeurBestaatPreparedStatement(connectionService,voorkeur)
            statement.executeQuery()
        } catch (e: SQLException){
            throw DatabaseConnectionException()
        }
    }
    fun gebruikersVoorkeurVerwijderen(gebruiker: Int,voorkeur: String){
        try{
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            if(gebruikerHeeftVoorkeur(gebruiker,voorkeur)) {
                val stmt = VoorkeurenSQLPreparedStatementBuilder.voorkeurenStatements.buildVoorkeurVerwijderenPreparedStatement(connectionService, gebruiker, voorkeur)
                stmt.executeUpdate()
            }
        } catch (e: SQLException){
            throw DatabaseConnectionException()
        }
    }
    fun makeVoorkeurenDTO(): VoorkeurenDTO? {
        val voorkeurenDTO = VoorkeurenDTOFactory.create.createVoorkeurenDTO()
        return try {
            val resultSet: ResultSet = getAlleVoorkeuren()
            while (resultSet != null && resultSet.next()) {
                voorkeurenDTO.addVoorkeur(resultSet.getString("naam"))
            }
            voorkeurenDTO
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }
}