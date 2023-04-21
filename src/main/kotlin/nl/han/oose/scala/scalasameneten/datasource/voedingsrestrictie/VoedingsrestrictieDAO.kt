package nl.han.oose.scala.scalasameneten.datasource.voedingsrestrictie

import nl.han.oose.scala.scalasameneten.datasource.PreparedStatementBuilder
import nl.han.oose.scala.scalasameneten.datasource.connection.ConnectionService
import nl.han.oose.scala.scalasameneten.datasource.connection.DatabaseProperties
import nl.han.oose.scala.scalasameneten.datasource.exceptions.DatabaseConnectionException
import nl.han.oose.scala.scalasameneten.dto.voedingsrestrictie.VoedingsrestrictieDTO
import nl.han.oose.scala.scalasameneten.dto.voedingsrestrictie.VoedingsrestrictiesDTO
import org.springframework.context.annotation.ComponentScan
import org.springframework.stereotype.Component
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

@Component
@ComponentScan("nl.han.oose.scala.scalasameneten.datasource.connection")
class VoedingsrestrictieDAO(private val connectionService: ConnectionService,private val databaseProperties: DatabaseProperties) {

    fun getAlleAllergieen(): ResultSet {
        return try {
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            val stmt = PreparedStatementBuilder(connectionService,"SELECT restrictie_naam FROM voedingsrestrictie WHERE type='allergie'")
                    .build()
            stmt.executeQuery()
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }
    fun getAlleGeloof(): ResultSet {
        return try {
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            val stmt = PreparedStatementBuilder(connectionService,"SELECT restrictie_naam FROM voedingsrestrictie WHERE type='geloof'")
                    .build()
            stmt.executeQuery()
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }
    fun getAlleDieet(): ResultSet {
        return try {
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            val stmt = PreparedStatementBuilder(connectionService,"SELECT restrictie_naam FROM voedingsrestrictie WHERE type='dieet'")
                    .build()
            stmt.executeQuery()
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }
    fun allergieBestaat(allergie: String): ResultSet {
        return try{
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            val stmt = PreparedStatementBuilder(connectionService,"SELECT 1 FROM voedingsrestrictie WHERE restrictie_naam=? AND type='allergie'")
                    .setString(allergie)
                    .build()
            stmt.executeQuery()
        } catch (e: SQLException){
            throw DatabaseConnectionException()
        }
    }
    fun geloofBestaat(geloof: String): ResultSet {
        return try{
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            val stmt = PreparedStatementBuilder(connectionService,"SELECT 1 FROM voedingsrestrictie WHERE restrictie_naam=? AND type='geloof'")
                    .setString(geloof)
                    .build()
            stmt.executeQuery()
        } catch (e: SQLException){
            throw DatabaseConnectionException()
        }
    }
    fun dieetBestaat(dieet: String): ResultSet {
        return try{
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            val stmt = PreparedStatementBuilder(connectionService,"SELECT 1 FROM voedingsrestrictie WHERE restrictie_naam=? AND type='dieet'")
                    .setString(dieet)
                    .build()
            stmt.executeQuery()
        } catch (e: SQLException){
            throw DatabaseConnectionException()
        }
    }
    fun makeRestrictiesDTO(): VoedingsrestrictiesDTO {
        return try {
            val allergieen: ResultSet = getAlleAllergieen()
            val geloof = getAlleGeloof()
            val dieet = getAlleDieet()
            var voedingsrestricties = ArrayList<VoedingsrestrictieDTO>()
            while (allergieen != null && allergieen.next()) {
                voedingsrestricties.add(VoedingsrestrictieDTO(allergieen.getString("restrictie_naam"),"allergie"))
            }
            while (geloof != null && geloof.next()) {
                voedingsrestricties.add(VoedingsrestrictieDTO(geloof.getString("restrictie_naam"),"geloof"))
            }
            while (dieet != null && dieet.next()) {
                voedingsrestricties.add(VoedingsrestrictieDTO(dieet.getString("restrictie_naam"),"dieet"))
            }
            VoedingsrestrictiesDTO(voedingsrestricties)
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }
}