package nl.han.oose.scala.scalasameneten.datasource.voedingsrestrictie

import nl.han.oose.scala.scalasameneten.datasource.connection.ConnectionService
import nl.han.oose.scala.scalasameneten.datasource.connection.DatabaseProperties
import nl.han.oose.scala.scalasameneten.datasource.exceptions.DatabaseConnectionException
import nl.han.oose.scala.scalasameneten.dto.factory.VoedingsrestrictieDTOFactory
import nl.han.oose.scala.scalasameneten.dto.voedingsrestrictie.VoedingsrestrictieDTO
import nl.han.oose.scala.scalasameneten.dto.voorkeur.VoorkeurDTO
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

class VoedingsrestrictieDAO {
    private val connectionService: ConnectionService? = null
    private val databaseProperties: DatabaseProperties? = null

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
    fun makeRestrictiesDTO(): VoedingsrestrictieDTO {
        return try {
            val allergieen: ResultSet = getAlleAllergieen()
            val geloof = getAlleGeloof()
            val dieet = getAlleDieet()
            var voedingsrestricties = HashMap<String, String>()
            while (allergieen != null && allergieen.next()) {
                voedingsrestricties[allergieen.getString("restrictie_naam")] = "allergie"
            }
            while (geloof != null && geloof.next()) {
                voedingsrestricties[allergieen.getString("restrictie_naam")] = "geloof"
            }
            while (dieet != null && dieet.next()) {
                voedingsrestricties[allergieen.getString("restrictie_naam")] = "dieet"
            }
            VoedingsrestrictieDTO(voedingsrestricties)
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }
}