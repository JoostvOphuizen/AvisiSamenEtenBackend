package nl.han.oose.scala.scalasameneten.datasource.gebruiker

import nl.han.oose.scala.scalasameneten.datasource.PreparedStatementBuilder
import nl.han.oose.scala.scalasameneten.datasource.connection.ConnectionService
import nl.han.oose.scala.scalasameneten.datasource.connection.DatabaseProperties
import nl.han.oose.scala.scalasameneten.datasource.exceptions.DatabaseConnectionException
import nl.han.oose.scala.scalasameneten.dto.gebruiker.GebruikerDTO
import nl.han.oose.scala.scalasameneten.dto.gebruiker.GebruikersDTO
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
            val stmt = PreparedStatementBuilder(connectionService,"SELECT gebruikersnaam,gebruiker_id FROM gebruiker")
                    .build()
            stmt.executeQuery()
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }
    fun getNaamVanGebruiker(id: Int): String? {
        return try {
            connectionService.initializeConnection((databaseProperties.getConnectionString()))
            val stmt = PreparedStatementBuilder(connectionService,"SELECT gebruikersnaam FROM gebruiker WHERE gebruiker_id=?")
                    .setInt(id)
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
    fun getGebruikersVoedingsrestricties(gebruiker: Int): ResultSet {
        return try {
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            val stmt = PreparedStatementBuilder(connectionService,"SELECT restrictie_naam,type FROM gebruiker_heeft_voedingsrestrictie WHERE gebruiker_id=?")
                    .setInt(gebruiker)
                    .build()
            stmt.executeQuery()
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }
    fun getGebruikersVoorkeuren(gebruiker: Int): ResultSet {
        return try {
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            val sql = "SELECT voorkeur_naam FROM voorkeur_van_gebruiker v INNER JOIN gebruiker g on v.gebruiker_id=g.gebruiker_id WHERE g.gebruiker_id=?"
            val stmt = PreparedStatementBuilder(connectionService,sql)
                    .setInt(gebruiker)
                    .build()
            stmt.executeQuery()
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }
    fun gebruikersVoedingsrestrictieToevoegen(gebruiker: Int,restrictie: String,type: String) {
        try {
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            val sql = "INSERT INTO gebruiker_heeft_voedingsrestrictie(gebruiker_id,restrictie_naam,type) VALUES (?,?,?)"
            val stmt = PreparedStatementBuilder(connectionService,sql)
                    .setInt(gebruiker)
                    .setString(restrictie)
                    .setString(type)
                    .build()
            stmt.executeUpdate()
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }
    fun gebruikersVoorkeurToevoegen(gebruiker: Int,voorkeur: String) {
        try {
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            val sql = "INSERT INTO voorkeur_van_gebruiker(gebruiker_id,voorkeur_naam) VALUES (?,?)"
            val stmt = PreparedStatementBuilder(connectionService,sql)
                    .setInt(gebruiker)
                    .setString(voorkeur)
                    .build()
            stmt.executeUpdate()
        } catch (e: SQLException) {
            e.printStackTrace()
            throw DatabaseConnectionException()
        }
    }

    fun alleGebruikersVoorkeurenVerwijderen(gebruiker: Int) {
        try {
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            val sql = "DELETE FROM voorkeur_van_gebruiker WHERE gebruiker_id=?"
            val stmt = PreparedStatementBuilder(connectionService,sql)
                .setInt(gebruiker)
                .build()
            stmt.executeUpdate()
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }
    fun setGebruikersVoorkeuren(id: Int, voorkeurenDTO: VoorkeurenDTO): Void? {
        try{
            connectionService.initializeConnection(databaseProperties.getConnectionString())
            var sql = "INSERT INTO voorkeur_van_gebruiker(gebruiker_id,voorkeur_naam) VALUES "
            val voorkeuren = voorkeurenDTO.voorkeuren
            if (voorkeuren != null) {
                alleGebruikersVoorkeurenVerwijderen(id)
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
            e.printStackTrace()
            throw DatabaseConnectionException()
        }
        return null
    }



    fun gebruikersVoedingsrestrictieVerwijderen(gebruiker: Int,restrictie: String,type: String) {
        try {
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            val sql = "DELETE FROM gebruiker_heeft_voedingsrestrictie WHERE gebruiker_id=? AND restrictie_naam=? AND type=?"
            val stmt = PreparedStatementBuilder(connectionService,sql)
                    .setInt(gebruiker)
                    .setString(restrictie)
                    .setString(type)
                    .build()
            stmt.executeUpdate()
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }
    fun gebruikersVoorkeurVerwijderen(gebruiker: Int,voorkeur: String) {
        try {
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            val sql = "DELETE FROM voorkeur_van_gebruiker WHERE gebruiker_id=? AND voorkeur_naam=?"
            val stmt = PreparedStatementBuilder(connectionService,sql)
                    .setInt(gebruiker)
                    .setString(voorkeur)
                    .build()
            stmt.executeUpdate()
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }

    fun makeVoorkeurenDTO(id: Int): VoorkeurenDTO {
        return try{
            val result: ResultSet = getGebruikersVoorkeuren(id)
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
                var result2 = getGebruikersVoorkeuren(result.getInt("gebruiker_id"))
                var voorkeuren = ArrayList<VoorkeurDTO>()
                while(result2 != null && result2.next()){
                    voorkeuren.add(VoorkeurDTO(result2.getString("voorkeur_naam")))
                }
                result2 = getGebruikersVoedingsrestricties(result.getInt("gebruiker_id"))
                var restricties = ArrayList<VoedingsrestrictieDTO>()
                while(result2 != null && result2.next()){
                    restricties.add(VoedingsrestrictieDTO(result2.getString("restrictie_naam"),result2.getString("type")))
                }
                val x = makeGebruiker(result.getInt("gebruiker_id"))
                gebruikers.add(x)
            }
            GebruikersDTO(gebruikers)
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }
    fun makeGebruiker(id: Int): GebruikerDTO {
        var result = getGebruikersVoorkeuren(id)
        val voorkeuren = ArrayList<VoorkeurDTO>()
        while(result.next()){
            voorkeuren.add(VoorkeurDTO(result.getString("voorkeur_naam")))
        }
        result = getGebruikersVoedingsrestricties(id)
        val restricties = ArrayList<VoedingsrestrictieDTO>()
        while(result.next()){
            restricties.add(VoedingsrestrictieDTO(result.getString("restrictie_naam"),result.getString("type")))
        }
        return GebruikerDTO(id,getNaamVanGebruiker(id)!!,voorkeuren,restricties)
    }
}