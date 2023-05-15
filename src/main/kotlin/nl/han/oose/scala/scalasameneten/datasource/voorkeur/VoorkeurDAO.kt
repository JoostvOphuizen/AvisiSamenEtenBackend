package nl.han.oose.scala.scalasameneten.datasource.voorkeur

import nl.han.oose.scala.scalasameneten.datasource.PreparedStatementBuilder
import nl.han.oose.scala.scalasameneten.datasource.connection.ConnectionService
import nl.han.oose.scala.scalasameneten.datasource.connection.DatabaseProperties
import nl.han.oose.scala.scalasameneten.datasource.exceptions.DatabaseConnectionException
import nl.han.oose.scala.scalasameneten.dto.voedingsbehoeften.VoedingsKeuzeDTO
import nl.han.oose.scala.scalasameneten.dto.voedingsbehoeften.VoedingsbehoefteDTO
import nl.han.oose.scala.scalasameneten.dto.voedingsbehoeften.VoedingsbehoeftenDTO
import nl.han.oose.scala.scalasameneten.dto.voorkeur.VoorkeurDTO
import nl.han.oose.scala.scalasameneten.dto.voorkeur.VoorkeurenDTO
import org.springframework.context.annotation.ComponentScan
import org.springframework.stereotype.Component
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

    fun getAlleVoedingsrestricties(): ResultSet {
        return try {
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            val stmt = PreparedStatementBuilder(connectionService, "SELECT RESTRICTIE_NAAM FROM voedingsrestrictie")
                    .build()
            stmt.executeQuery()
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }

    fun getAllVoorkeurVoedingsbehoefte(gebruikerToken: String): ResultSet {
        return try {
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            val stmt = PreparedStatementBuilder(connectionService,
                "SELECT v.voorkeur_naam,\n" +
                        "CASE WHEN vo.GEBRUIKER_ID IS NULL THEN 0 ELSE 1 END AS has_voorkeur\n" +
                        "FROM voorkeur v\n" +
                        "LEFT JOIN (SELECT * FROM VOORKEUR_VAN_GEBRUIKER WHERE GEBRUIKER_ID = (SELECT GEBRUIKER_ID FROM GEBRUIKER WHERE token = ?)) vo\n" +
                        "ON vo.VOORKEUR_NAAM = v.VOORKEUR_NAAM")
                    .setString(gebruikerToken)
                    .build()
            stmt.executeQuery()
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }

    fun getAllVoedingsrestrictieVoedingsbehoefte(gebruikerToken: String): ResultSet {
        return try {
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            val stmt = PreparedStatementBuilder(connectionService,
                "SELECT \n" +
                        "\tv.RESTRICTIE_NAAM, \n" +
                        "\tCASE WHEN vo.GEBRUIKER_ID IS NULL THEN 0 ELSE 1 END AS has_voorkeur\n" +
                        "FROM voedingsrestrictie v\n" +
                        "LEFT JOIN (SELECT * FROM VOORKEUR_VAN_GEBRUIKER WHERE GEBRUIKER_ID = (SELECT GEBRUIKER_ID FROM GEBRUIKER WHERE token = ?)) vo\n" +
                        "ON vo.VOORKEUR_NAAM = v.RESTRICTIE_NAAM")
                    .setString(gebruikerToken)
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
            val voorkeuren = ArrayList<VoorkeurDTO>()
            while (resultSet.next()) {
                val x = VoorkeurDTO(resultSet.getString("voorkeur_naam"))
                voorkeuren.add(x)
            }
            VoorkeurenDTO("Voorkeuren", voorkeuren)
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }

    fun makeVoedingsrestrictiesDTO(): VoorkeurenDTO {
        return try {
            val resultSet: ResultSet = getAlleVoedingsrestricties()
            val voedingsrestricties = ArrayList<VoorkeurDTO>()
            while (resultSet.next()) {
                val x = VoorkeurDTO(resultSet.getString("RESTRICTIE_NAAM"))
                voedingsrestricties.add(x)
            }
            VoorkeurenDTO("Voedingsrestricties", voedingsrestricties)
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }

    fun makeVoorkeurenVoedingsbehoefteDTO(gebruikerToken: String): VoedingsbehoefteDTO {
        return try {
            val resultSet: ResultSet = getAllVoorkeurVoedingsbehoefte(gebruikerToken)
            val VoedingsKeuze = ArrayList<VoedingsKeuzeDTO>()
            while (resultSet.next()) {
                val voedingsKeuze = VoedingsKeuzeDTO(resultSet.getString("voorkeur_naam"), resultSet.getBoolean("has_voorkeur"))
                VoedingsKeuze.add(voedingsKeuze)
            }
            VoedingsbehoefteDTO("Voorkeuren", VoedingsKeuze)
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }

    fun makeVoedingsrestrictieVoedingsbehoefteDTO(gebruikerToken: String): VoedingsbehoefteDTO {
        return try {
            val resultSet: ResultSet = getAllVoedingsrestrictieVoedingsbehoefte(gebruikerToken)
            val VoedingsKeuze = ArrayList<VoedingsKeuzeDTO>()
            while (resultSet.next()) {
                val voedingsKeuze = VoedingsKeuzeDTO(resultSet.getString("RESTRICTIE_NAAM"), resultSet.getBoolean("has_voorkeur"))
                VoedingsKeuze.add(voedingsKeuze)
            }
            VoedingsbehoefteDTO("Voedingsrestrictie", VoedingsKeuze)
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }


    fun makeVoedingsbehoeftenDTO(gebruikerToken: String): VoedingsbehoeftenDTO {
        val voorkeuren = makeVoorkeurenVoedingsbehoefteDTO(gebruikerToken)
        val voedingsrestricties = makeVoedingsrestrictieVoedingsbehoefteDTO(gebruikerToken)
        val voedingsbehoeften = ArrayList<VoedingsbehoefteDTO>()
        voedingsbehoeften.add(voorkeuren)
        voedingsbehoeften.add(voedingsrestricties)

        return VoedingsbehoeftenDTO(voedingsbehoeften)
    }
}