package nl.han.oose.scala.scalasameneten.datasource.voorkeur

import nl.han.oose.scala.scalasameneten.datasource.PreparedStatementBuilder
import nl.han.oose.scala.scalasameneten.datasource.connection.ConnectionService
import nl.han.oose.scala.scalasameneten.datasource.connection.DatabaseProperties
import nl.han.oose.scala.scalasameneten.datasource.exceptions.DatabaseConnectionException
import nl.han.oose.scala.scalasameneten.dto.voorkeur.VoorkeurDTO
import nl.han.oose.scala.scalasameneten.dto.voorkeur.VoorkeurenDTO
import org.springframework.context.annotation.ComponentScan
import org.springframework.stereotype.Component
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

@Component
@ComponentScan("nl.han.oose.scala.scalasameneten.datasource.connection")
class VoorkeurDAO(private val connectionService: ConnectionService,private val databaseProperties: DatabaseProperties) {

    fun getAlleVoorkeuren(): ResultSet {
        return try {
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            val stmt = PreparedStatementBuilder(connectionService, "SELECT voorkeur_naam FROM voorkeur")
                    .build()
            stmt.executeQuery()
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }
    fun voorkeurBestaat(voorkeur: String): ResultSet{
        return try{
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            val stmt = PreparedStatementBuilder(connectionService,"SELECT 1 FROM voorkeur WHERE voorkeur_naam=?")
                    .setString(voorkeur)
                    .build()
            stmt.executeQuery()
        } catch (e: SQLException){
            throw DatabaseConnectionException()
        }
    }
    fun makeVoorkeurenDTO(): VoorkeurenDTO {
        return try {
            val resultSet: ResultSet = getAlleVoorkeuren()
            var voorkeuren = ArrayList<VoorkeurDTO>()
            while (resultSet != null && resultSet.next()) {
                val x = VoorkeurDTO(resultSet.getString("voorkeur_naam"))
                voorkeuren.add(x)
            }
            VoorkeurenDTO(voorkeuren)
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }
}