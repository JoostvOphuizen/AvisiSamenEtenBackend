package nl.han.oose.scala.scalasameneten.datasource.gebruiker

import nl.han.oose.scala.scalasameneten.datasource.PreparedStatementBuilder
import nl.han.oose.scala.scalasameneten.datasource.connection.ConnectionService
import nl.han.oose.scala.scalasameneten.datasource.connection.DatabaseProperties
import nl.han.oose.scala.scalasameneten.datasource.exceptions.DatabaseConnectionException
import nl.han.oose.scala.scalasameneten.dto.gebruiker.GebruikerDTO
import nl.han.oose.scala.scalasameneten.dto.gebruiker.GebruikersDTO
import nl.han.oose.scala.scalasameneten.dto.gebruiker.LoginDTO
import nl.han.oose.scala.scalasameneten.dto.gebruiker.TokenDTO
import nl.han.oose.scala.scalasameneten.dto.voedingsrestrictie.VoedingsrestrictieDTO
import nl.han.oose.scala.scalasameneten.dto.voorkeur.VoorkeurDTO
import nl.han.oose.scala.scalasameneten.dto.voorkeur.VoorkeurenDTO
import org.springframework.context.annotation.ComponentScan
import org.springframework.stereotype.Component
import java.sql.ResultSet
import java.sql.SQLException

@Component
@ComponentScan("nl.han.oose.scala.scalasameneten.datasource.connection")
class GebruikerDAO(private val connectionService: ConnectionService,private val databaseProperties: DatabaseProperties) {

    fun getAlleGebruikers(): ResultSet {
        return try {
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            val stmt = PreparedStatementBuilder(connectionService,"SELECT gebruikersnaam,gebruiker_id,email,token FROM gebruiker")
                    .build()
            stmt.executeQuery()
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }
    fun getNaamVanGebruiker(token: String): String? {
        return try {
            connectionService.initializeConnection((databaseProperties.getConnectionString()))
            val stmt = PreparedStatementBuilder(connectionService,"SELECT gebruikersnaam FROM gebruiker WHERE token=?")
                    .setString(token)
                    .build()
            val result = stmt.executeQuery()
            if(result.next()) {
                result.getString("gebruikersnaam")
            } else {
                null
            }
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }

    fun getIdVanGebruiker(token: String): Int? {
        return try {
            connectionService.initializeConnection((databaseProperties.getConnectionString()))
            val stmt = PreparedStatementBuilder(connectionService,"SELECT gebruiker_id FROM gebruiker WHERE token=?")
                .setString(token)
                .build()
            val result = stmt.executeQuery()
            if(result.next()) {
                result.getInt("gebruiker_id")
            } else {
                null
            }
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }

    fun getGebruikersVoedingsrestricties(token: String): ResultSet {
        return try {
            val id = getIdVanGebruiker(token)!!
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            val stmt = PreparedStatementBuilder(connectionService,"SELECT restrictie_naam,type FROM gebruiker_heeft_voedingsrestrictie WHERE gebruiker_id=?")
                    .setInt(id)
                    .build()
            stmt.executeQuery()
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }
    fun getGebruikersVoorkeuren(token: String): ResultSet {
        return try {
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            val sql = "SELECT voorkeur_naam FROM voorkeur_van_gebruiker v INNER JOIN gebruiker g on v.gebruiker_id=g.gebruiker_id WHERE g.token=?"
            val stmt = PreparedStatementBuilder(connectionService,sql)
                    .setString(token)
                    .build()
            stmt.executeQuery()
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }

    fun alleGebruikersVoorkeurenVerwijderen(token: String) {
        try {
            val id = getIdVanGebruiker(token)!!
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            val sql = "DELETE FROM voorkeur_van_gebruiker WHERE gebruiker_id=?"
            val stmt = PreparedStatementBuilder(connectionService,sql)
                .setInt(id)
                .build()
            stmt.executeUpdate()
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }
    fun setGebruikersVoorkeuren(token: String, voorkeurenDTO: VoorkeurenDTO): Void? {
        try{
            val id = getIdVanGebruiker(token)!!
            connectionService.initializeConnection(databaseProperties.getConnectionString())
            var sql = "INSERT INTO voorkeur_van_gebruiker(gebruiker_id,voorkeur_naam) VALUES "
            val voorkeuren = voorkeurenDTO.voorkeuren
            if (voorkeuren != null) {
                alleGebruikersVoorkeurenVerwijderen(token)
                if (voorkeuren.size > 0) {
                    for(i in 0 until voorkeuren.size){
                        val voorkeur = voorkeuren[i]
                        if (voorkeur.naam != null) {
                            if (i != 0) { sql += "," }
                            sql += "(?,?)"
                        }
                    }
                    val stmt = PreparedStatementBuilder(connectionService, sql)
                    for (voorkeur in voorkeuren) {
                        stmt.setInt(id)
                            .setString(voorkeur.naam!!)
                    }
                    stmt.build().executeUpdate()
                }
            }
        } catch (e: Exception){
            throw DatabaseConnectionException()
        }
        return null
    }

    fun makeVoorkeurenDTO(token: String): VoorkeurenDTO {
        return try{
            val result: ResultSet = getGebruikersVoorkeuren(token)
            var voorkeuren = ArrayList<VoorkeurDTO>()
            while (result != null && result.next()) {
                voorkeuren.add(VoorkeurDTO(result.getString("voorkeur_naam")))
            }
            VoorkeurenDTO(voorkeuren)
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }

    }

    fun makeGebruikersDTO(): GebruikersDTO {
        return try {
            val result: ResultSet = getAlleGebruikers()
            var gebruikers = ArrayList<GebruikerDTO>()
            while (result != null && result.next()) {
                var result2 = getGebruikersVoorkeuren(result.getString("token"))
                var voorkeuren = ArrayList<VoorkeurDTO>()
                while(result2 != null && result2.next()){
                    voorkeuren.add(VoorkeurDTO(result2.getString("voorkeur_naam")))
                }
                result2 = getGebruikersVoedingsrestricties(result.getString("token"))
                var restricties = ArrayList<VoedingsrestrictieDTO>()
                while(result2 != null && result2.next()){
                    restricties.add(VoedingsrestrictieDTO(result2.getString("restrictie_naam"),result2.getString("type")))
                }
                val x = makeGebruiker(result.getString("token"))
                gebruikers.add(x)
            }
            GebruikersDTO(gebruikers)
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }
    fun makeGebruiker(token: String): GebruikerDTO {
        var result = getGebruikersVoorkeuren(token)
        val voorkeuren = ArrayList<VoorkeurDTO>()
        while(result.next()){
            voorkeuren.add(VoorkeurDTO(result.getString("voorkeur_naam")))
        }
        result = getGebruikersVoedingsrestricties(token)
        val restricties = ArrayList<VoedingsrestrictieDTO>()
        while(result.next()){
            restricties.add(VoedingsrestrictieDTO(result.getString("restrictie_naam"),result.getString("type")))
        }
        val id = getIdVanGebruiker(token)!!
        return GebruikerDTO(id,getNaamVanGebruiker(token)!!,voorkeuren,restricties)
    }


    fun setGebruikersToken(id: Int, token: String){
        try {
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            val sql = "UPDATE gebruiker SET token=? WHERE gebruiker_id=?"
            val stmt = PreparedStatementBuilder(connectionService,sql)
                .setString(token)
                .setInt(id)
                .build()
            stmt.executeUpdate()
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }

    fun setNieuweGebruiker(login: LoginDTO){
        try {
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            val sql = "INSERT INTO gebruiker (gebruikersnaam, email, token) VALUES (?,?,?)"
            val stmt = PreparedStatementBuilder(connectionService,sql)
                .setString(login.naam)
                .setString(login.email)
                .setString("0000-0000-0000")
                .build()
            stmt.executeUpdate()
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }
    fun getGebruikerID(login: LoginDTO): Int{
        try {
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            val sql = "SELECT gebruiker_id FROM gebruiker WHERE gebruikersnaam=?"
            val stmt = PreparedStatementBuilder(connectionService,sql)
                .setString(login.naam)
                .build()
            val result = stmt.executeQuery()
            if(result.next()){
                return result.getInt("gebruiker_id")
            }
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
        return -1
    }

    fun loginGebruiker(login: LoginDTO, token: String): TokenDTO {
        var nieuweGebruiker = true
        val result = getAlleGebruikers()
        while (result.next()){
            if(result.getString("email") == login.email){
                nieuweGebruiker = false
            }
        }
        if(nieuweGebruiker){
            setNieuweGebruiker(login)
        }
        val id = getGebruikerID(login)
        setGebruikersToken(id, token)
        return TokenDTO(token)
    }

}