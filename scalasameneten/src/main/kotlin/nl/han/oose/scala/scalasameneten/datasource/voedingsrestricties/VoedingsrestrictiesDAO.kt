package nl.han.oose.scala.scalasameneten.datasource.voedingsrestricties

import nl.han.oose.scala.scalasameneten.datasource.connection.ConnectionService
import nl.han.oose.scala.scalasameneten.datasource.connection.DatabaseProperties
import nl.han.oose.scala.scalasameneten.datasource.exceptions.DatabaseConnectionException
import nl.han.oose.scala.scalasameneten.datasource.factory.VoedingsrestrictiesDTOFactory
import nl.han.oose.scala.scalasameneten.datasource.factory.VoorkeurenDTOFactory
import nl.han.oose.scala.scalasameneten.datasource.voorkeuren.VoorkeurenSQLPreparedStatementBuilder
import nl.han.oose.scala.scalasameneten.dto.voedingsrestricties.VoedingsrestrictiesDTO
import nl.han.oose.scala.scalasameneten.dto.voorkeuren.VoorkeurenDTO
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

class VoedingsrestrictiesDAO {
    private val connectionService: ConnectionService? = null
    private val databaseProperties: DatabaseProperties? = null
    fun getGebruikersRestricties(gebruiker: Int): ResultSet {
        return try {
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            val statement: PreparedStatement = VoedingsrestrictiesSQLPreparedStatementBuilder.algemeen.buildGetGebruikersRestrictiesPreparedStatement(connectionService,gebruiker)
            statement.executeQuery()
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }
    fun getAlleAllergieen(): ResultSet {
        return try {
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            val statement: PreparedStatement = VoedingsrestrictiesSQLPreparedStatementBuilder.allergie.buildGetAlleAllergieenPreparedStatement(connectionService)
            statement.executeQuery()
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }
    fun getAlleGeloof(): ResultSet {
        return try {
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            val statement: PreparedStatement = VoedingsrestrictiesSQLPreparedStatementBuilder.geloof.buildGetAlleGeloofPreparedStatement(connectionService)
            statement.executeQuery()
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }
    fun getAlleDieet(): ResultSet {
        return try {
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            val statement: PreparedStatement = VoedingsrestrictiesSQLPreparedStatementBuilder.dieet.buildGetAlleDieetPreparedStatement(connectionService)
            statement.executeQuery()
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }
    fun gebruikersAllergieToevoegen(gebruiker: Int,voorkeur: String) {
        try{
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            val result = allergieBestaat(voorkeur)
            if(result.next()) {
                val statement = VoedingsrestrictiesSQLPreparedStatementBuilder.allergie.buildGebruikersAllergieToevoegenPreparedStatement(connectionService, gebruiker, voorkeur)
                statement.executeUpdate()
            }
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }
    fun gebruikersGeloofToevoegen(gebruiker: Int,voorkeur: String) {
        try{
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            val result = geloofBestaat(voorkeur)
            if(result.next()) {
                val statement = VoedingsrestrictiesSQLPreparedStatementBuilder.geloof.buildGebruikersGeloofToevoegenPreparedStatement(connectionService, gebruiker, voorkeur)
                statement.executeUpdate()
            }
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }
    fun gebruikersDieetToevoegen(gebruiker: Int,voorkeur: String) {
        try{
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            val result = dieetBestaat(voorkeur)
            if(result.next()) {
                val statement = VoedingsrestrictiesSQLPreparedStatementBuilder.dieet.buildGebruikersDieetToevoegenPreparedStatement(connectionService, gebruiker, voorkeur)
                statement.executeUpdate()
            }
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }
    fun allergieBestaat(allergie: String): ResultSet {
        return try{
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            val statement = VoedingsrestrictiesSQLPreparedStatementBuilder.allergie.buildAllergieBestaatPreparedStatement(connectionService,allergie)
            statement.executeQuery()
        } catch (e: SQLException){
            throw DatabaseConnectionException()
        }
    }
    fun geloofBestaat(geloof: String): ResultSet {
        return try{
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            val statement = VoedingsrestrictiesSQLPreparedStatementBuilder.geloof.buildGeloofBestaatPreparedStatement(connectionService,geloof)
            statement.executeQuery()
        } catch (e: SQLException){
            throw DatabaseConnectionException()
        }
    }
    fun dieetBestaat(dieet: String): ResultSet {
        return try{
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            val statement = VoedingsrestrictiesSQLPreparedStatementBuilder.dieet.buildDieetBestaatPreparedStatement(connectionService,dieet)
            statement.executeQuery()
        } catch (e: SQLException){
            throw DatabaseConnectionException()
        }
    }
    fun gebruikerHeeftAllergie(gebruiker: Int,allergie: String): Boolean {
        try {
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            val result = getGebruikersRestricties(gebruiker)
            while(result.next()){
                if(result.getString("naam")==allergie && result.getString("type")=="allergie"){
                    return true
                }
            }
            return false
        } catch(e: SQLException){
            throw DatabaseConnectionException()
        }
    }
    fun gebruikerHeeftGeloof(gebruiker: Int,geloof: String): Boolean {
        try {
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            val result = getGebruikersRestricties(gebruiker)
            while(result.next()){
                if(result.getString("naam")==geloof && result.getString("type")=="geloof"){
                    return true
                }
            }
            return false
        } catch(e: SQLException){
            throw DatabaseConnectionException()
        }
    }
    fun gebruikerHeeftDieet(gebruiker: Int,dieet: String): Boolean {
        try {
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            val result = getGebruikersRestricties(gebruiker)
            while(result.next()){
                if(result.getString("naam")==dieet && result.getString("type")=="dieet"){
                    return true
                }
            }
            return false
        } catch(e: SQLException){
            throw DatabaseConnectionException()
        }
    }
    fun gebruikersAllergieVerwijderen(gebruiker: Int,allergie: String){
        try{
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            if(gebruikerHeeftAllergie(gebruiker,allergie)) {
                val stmt = VoedingsrestrictiesSQLPreparedStatementBuilder.allergie.buildAllergieVerwijderenPreparedStatement(connectionService, gebruiker, allergie)
                stmt.executeUpdate()
            }
        } catch (e: SQLException){
            throw DatabaseConnectionException()
        }
    }
    fun gebruikersGeloofVerwijderen(gebruiker: Int,geloof: String){
        try{
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            if(gebruikerHeeftGeloof(gebruiker,geloof)) {
                val stmt = VoedingsrestrictiesSQLPreparedStatementBuilder.geloof.buildGeloofVerwijderenPreparedStatement(connectionService, gebruiker, geloof)
                stmt.executeUpdate()
            }
        } catch (e: SQLException){
            throw DatabaseConnectionException()
        }
    }
    fun gebruikersDieetVerwijderen(gebruiker: Int,dieet: String){
        try{
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            if(gebruikerHeeftAllergie(gebruiker,dieet)) {
                val stmt = VoedingsrestrictiesSQLPreparedStatementBuilder.dieet.buildDieetVerwijderenPreparedStatement(connectionService, gebruiker, dieet)
                stmt.executeUpdate()
            }
        } catch (e: SQLException){
            throw DatabaseConnectionException()
        }
    }
    fun makeRestrictiesDTO(): ArrayList<VoedingsrestrictiesDTO> {
        lateinit var result: ArrayList<VoedingsrestrictiesDTO>
        return try {
            var restrictiesDTO: VoedingsrestrictiesDTO = VoedingsrestrictiesDTOFactory.create.createAllergieDTO()
            result.add(restrictiesDTO)
            var resultSet = getAlleAllergieen()
            while (resultSet != null && resultSet.next()) {
                restrictiesDTO.addRestrictie(resultSet.getString("naam"))
            }
            restrictiesDTO = VoedingsrestrictiesDTOFactory.create.createGeloofDTO()
            result.add(restrictiesDTO)
            resultSet = getAlleGeloof()
            while (resultSet != null && resultSet.next()) {
                restrictiesDTO.addRestrictie(resultSet.getString("naam"))
            }
            restrictiesDTO = VoedingsrestrictiesDTOFactory.create.createDieetDTO()
            result.add(restrictiesDTO)
            resultSet = getAlleDieet()
            while (resultSet != null && resultSet.next()) {
                restrictiesDTO.addRestrictie(resultSet.getString("naam"))
            }
            result
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }
}