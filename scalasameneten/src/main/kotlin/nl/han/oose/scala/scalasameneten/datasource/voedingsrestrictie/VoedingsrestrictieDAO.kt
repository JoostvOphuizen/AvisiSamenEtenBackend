package nl.han.oose.scala.scalasameneten.datasource.voedingsrestrictie

import nl.han.oose.scala.scalasameneten.datasource.connection.ConnectionService
import nl.han.oose.scala.scalasameneten.datasource.connection.DatabaseProperties
import nl.han.oose.scala.scalasameneten.datasource.exceptions.DatabaseConnectionException
import nl.han.oose.scala.scalasameneten.dto.factory.VoedingsrestrictieDTOFactory
import nl.han.oose.scala.scalasameneten.dto.voedingsrestrictie.VoedingsrestrictieDTO
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

class VoedingsrestrictieDAO {
    private val connectionService: ConnectionService? = null
    private val databaseProperties: DatabaseProperties? = null
    fun getGebruikersRestricties(gebruiker: Int): ResultSet {
        return try {
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            val statement: PreparedStatement = VoedingsrestrictieSQLPreparedStatementBuilder.algemeen.buildGetGebruikersRestrictiesPreparedStatement(connectionService,gebruiker)
            statement.executeQuery()
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }
    fun getAlleAllergieen(): ResultSet {
        return try {
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            val statement: PreparedStatement = VoedingsrestrictieSQLPreparedStatementBuilder.allergie.buildGetAlleAllergieenPreparedStatement(connectionService)
            statement.executeQuery()
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }
    fun getAlleGeloof(): ResultSet {
        return try {
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            val statement: PreparedStatement = VoedingsrestrictieSQLPreparedStatementBuilder.geloof.buildGetAlleGeloofPreparedStatement(connectionService)
            statement.executeQuery()
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }
    fun getAlleDieet(): ResultSet {
        return try {
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            val statement: PreparedStatement = VoedingsrestrictieSQLPreparedStatementBuilder.dieet.buildGetAlleDieetPreparedStatement(connectionService)
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
                val statement = VoedingsrestrictieSQLPreparedStatementBuilder.allergie.buildGebruikersAllergieToevoegenPreparedStatement(connectionService, gebruiker, voorkeur)
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
                val statement = VoedingsrestrictieSQLPreparedStatementBuilder.geloof.buildGebruikersGeloofToevoegenPreparedStatement(connectionService, gebruiker, voorkeur)
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
                val statement = VoedingsrestrictieSQLPreparedStatementBuilder.dieet.buildGebruikersDieetToevoegenPreparedStatement(connectionService, gebruiker, voorkeur)
                statement.executeUpdate()
            }
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }
    fun allergieBestaat(allergie: String): ResultSet {
        return try{
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            val statement = VoedingsrestrictieSQLPreparedStatementBuilder.allergie.buildAllergieBestaatPreparedStatement(connectionService,allergie)
            statement.executeQuery()
        } catch (e: SQLException){
            throw DatabaseConnectionException()
        }
    }
    fun geloofBestaat(geloof: String): ResultSet {
        return try{
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            val statement = VoedingsrestrictieSQLPreparedStatementBuilder.geloof.buildGeloofBestaatPreparedStatement(connectionService,geloof)
            statement.executeQuery()
        } catch (e: SQLException){
            throw DatabaseConnectionException()
        }
    }
    fun dieetBestaat(dieet: String): ResultSet {
        return try{
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            val statement = VoedingsrestrictieSQLPreparedStatementBuilder.dieet.buildDieetBestaatPreparedStatement(connectionService,dieet)
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
                val stmt = VoedingsrestrictieSQLPreparedStatementBuilder.allergie.buildAllergieVerwijderenPreparedStatement(connectionService, gebruiker, allergie)
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
                val stmt = VoedingsrestrictieSQLPreparedStatementBuilder.geloof.buildGeloofVerwijderenPreparedStatement(connectionService, gebruiker, geloof)
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
                val stmt = VoedingsrestrictieSQLPreparedStatementBuilder.dieet.buildDieetVerwijderenPreparedStatement(connectionService, gebruiker, dieet)
                stmt.executeUpdate()
            }
        } catch (e: SQLException){
            throw DatabaseConnectionException()
        }
    }
    fun makeRestrictiesDTO(): ArrayList<VoedingsrestrictieDTO> {
        lateinit var result: ArrayList<VoedingsrestrictieDTO>
        return try {
            var restrictiesDTO: VoedingsrestrictieDTO = VoedingsrestrictieDTOFactory.create.createAllergieDTO()
            result.add(restrictiesDTO)
            var resultSet = getAlleAllergieen()
            while (resultSet != null && resultSet.next()) {
                restrictiesDTO.addRestrictie(resultSet.getString("naam"))
            }
            restrictiesDTO = VoedingsrestrictieDTOFactory.create.createGeloofDTO()
            result.add(restrictiesDTO)
            resultSet = getAlleGeloof()
            while (resultSet != null && resultSet.next()) {
                restrictiesDTO.addRestrictie(resultSet.getString("naam"))
            }
            restrictiesDTO = VoedingsrestrictieDTOFactory.create.createDieetDTO()
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