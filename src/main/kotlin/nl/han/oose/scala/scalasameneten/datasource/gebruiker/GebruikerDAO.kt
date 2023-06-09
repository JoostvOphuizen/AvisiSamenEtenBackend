package nl.han.oose.scala.scalasameneten.datasource.gebruiker

import nl.han.oose.scala.scalasameneten.datasource.PreparedStatementBuilder
import nl.han.oose.scala.scalasameneten.datasource.connection.ConnectionService
import nl.han.oose.scala.scalasameneten.datasource.connection.DatabaseProperties
import nl.han.oose.scala.scalasameneten.datasource.exceptions.DatabaseConnectionException
import nl.han.oose.scala.scalasameneten.dto.gebruiker.GebruikerDTO
import nl.han.oose.scala.scalasameneten.dto.gebruiker.GebruikersDTO
import nl.han.oose.scala.scalasameneten.dto.gebruiker.LoginDTO
import nl.han.oose.scala.scalasameneten.dto.gebruiker.TokenDTO
import nl.han.oose.scala.scalasameneten.dto.restaurant.GroepDTO
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

    fun makeVoorkeurenDTO(gebruikerToken: String): VoorkeurenDTO {
        return try{
            val result: ResultSet = getGebruikersVoorkeuren(gebruikerToken)
            val voorkeuren = ArrayList<VoorkeurDTO>()
            while (result.next()) {
                voorkeuren.add(VoorkeurDTO(result.getString("voorkeur_naam")))
            }
            VoorkeurenDTO("Voorkeuren", voorkeuren)
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }

    fun getGebruikersVoorkeuren(gebruikerToken: String): ResultSet {
        return try {
            connectionService.initializeConnection(databaseProperties.getConnectionString())
            val sql = "SELECT voorkeur_naam FROM voorkeur_van_gebruiker v INNER JOIN gebruiker g on v.gebruiker_id=g.gebruiker_id WHERE g.token=?"
            val stmt = PreparedStatementBuilder(connectionService,sql)
                .setString(gebruikerToken)
                .build()
            stmt.executeQuery()
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }

    fun getAlleGebruikers(): ResultSet {
        return try {
            connectionService.initializeConnection(databaseProperties.getConnectionString())
            val stmt = PreparedStatementBuilder(connectionService,"SELECT gebruikersnaam,gebruiker_id,email,token,foto FROM gebruiker")
                    .build()
            stmt.executeQuery()
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }

    fun getAllGebruikersWithVoorkeurenAndRestricties(groep: GroepDTO, gebruikerToken: String?): ResultSet {
        return try {
            connectionService.initializeConnection(databaseProperties.getConnectionString())

            var sql ="SELECT gebruikersnaam,gebruiker_id,email,token,foto,\n" +
                    "        (\n" +
                    "            SELECT STRING_AGG(V.VOORKEUR_NAAM, ',')\n" +
                    "            FROM VOORKEUR_VAN_GEBRUIKER RV\n" +
                    "            JOIN VOORKEUR V ON RV.VOORKEUR_NAAM = V.VOORKEUR_NAAM\n" +
                    "            WHERE RV.GEBRUIKER_ID = g.GEBRUIKER_ID\n" +
                    "        ) AS VOORKEUREN,\n" +
                    "        (\n" +
                    "            SELECT STRING_AGG(VR.RESTRICTIE_NAAM, ',')\n" +
                    "            FROM GEBRUIKER_HEEFT_VOEDINGSRESTRICTIE VR\n" +
                    "            WHERE VR.GEBRUIKER_ID = g.GEBRUIKER_ID\n" +
                    "        ) AS RESTRICTIES\n" +
                    "FROM gebruiker g\n" +
                    "where g.TOKEN LIKE ?\n"

            for(lid in groep.leden) {
                sql += " OR GEBRUIKER_ID = ?\n"
            }

            val stmt = PreparedStatementBuilder(connectionService,sql)
            if (gebruikerToken != null) {
                stmt.setString(gebruikerToken)
            } else {
                stmt.setString("")
            }
            for(lid in groep.leden) {
                stmt.setInt(lid)
            }
            stmt.build().executeQuery()
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }

    fun getNaamVanGebruiker(gebruikerToken: String): String? {
        return try {
            connectionService.initializeConnection((databaseProperties.getConnectionString()))
            val stmt = PreparedStatementBuilder(connectionService,"SELECT gebruikersnaam FROM gebruiker WHERE token=?")
                    .setString(gebruikerToken)
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

    fun getIdVanGebruiker(gebruikerToken: String): Int {
        try {
            connectionService.initializeConnection((databaseProperties.getConnectionString()))
            val stmt = PreparedStatementBuilder(connectionService,"SELECT gebruiker_id FROM gebruiker WHERE token=?")
                .setString(gebruikerToken)
                .build()
            val result = stmt.executeQuery()
            if(result.next()) {
                return result.getInt("gebruiker_id")
            }
            return 0
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }

    fun getTokenVanGebruiker(gebruikerId: Int): String {
        return try {
            connectionService.initializeConnection((databaseProperties.getConnectionString()))
            val stmt = PreparedStatementBuilder(connectionService,"SELECT token FROM gebruiker WHERE gebruiker_id=?")
                .setInt(gebruikerId)
                .build()
            val result = stmt.executeQuery()
            if(result.next()) {
                result.getString("token")
            } else {
                throw SQLException()
            }
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }


    fun getGebruikersVoedingsrestricties(gebruikerToken: String): ResultSet {
        return try {
            val gebruikerId = getIdVanGebruiker(gebruikerToken)!!
            connectionService.initializeConnection(databaseProperties.getConnectionString())
            val stmt = PreparedStatementBuilder(connectionService,"SELECT restrictie_naam,type FROM gebruiker_heeft_voedingsrestrictie WHERE gebruiker_id=?")
                    .setInt(gebruikerId)
                    .build()
            stmt.executeQuery()
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }

    fun alleGebruikersVoorkeurenVerwijderen(gebruikerToken: String) {
        try {
            val gebruikerId = getIdVanGebruiker(gebruikerToken)!!
            connectionService.initializeConnection(databaseProperties.getConnectionString())
            val sql = "DELETE FROM voorkeur_van_gebruiker WHERE gebruiker_id=?"
            val stmt = PreparedStatementBuilder(connectionService,sql)
                .setInt(gebruikerId)
                .build()
            stmt.executeUpdate()
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }

    fun alleGebruikersRestrictiesVerwijderen(gebruikerToken: String) {
        try {
            val gebruikerId = getIdVanGebruiker(gebruikerToken)!!
            connectionService.initializeConnection(databaseProperties.getConnectionString())
            val sql = "DELETE FROM gebruiker_heeft_voedingsrestrictie WHERE gebruiker_id=?"
            val stmt = PreparedStatementBuilder(connectionService,sql)
                .setInt(gebruikerId)
                .build()
            stmt.executeUpdate()
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }

    fun setGebruikersVoorkeuren(gebruikerToken: String, voorkeurenDTO: VoorkeurenDTO): Void? {
        try{
            val gebruikerId = getIdVanGebruiker(gebruikerToken)!!
            connectionService.initializeConnection(databaseProperties.getConnectionString())
            var sql = "INSERT INTO voorkeur_van_gebruiker(gebruiker_id,voorkeur_naam) VALUES "
            val voorkeuren = voorkeurenDTO.voorkeuren
            alleGebruikersVoorkeurenVerwijderen(gebruikerToken)
            if (voorkeuren != null && voorkeuren.size > 0) {
                sql = genereerSQLGebruikersVoorkeuren(voorkeuren, sql)
                val stmt = PreparedStatementBuilder(connectionService, sql)
                for (voorkeur in voorkeuren) {
                    stmt.setInt(gebruikerId)
                        .setString(voorkeur.naam!!)
                }
                stmt.build().executeUpdate()
            }
        } catch (e: Exception){
            throw DatabaseConnectionException()
        }
        return null
    }

    fun setGebruikersRestricties(gebruikerToken: String, voorkeurenDTO: VoorkeurenDTO): Void? {
        try{
            val gebruikerId = getIdVanGebruiker(gebruikerToken)!!
            connectionService.initializeConnection(databaseProperties.getConnectionString())
            var sql = "INSERT INTO gebruiker_heeft_voedingsrestrictie(gebruiker_id, restrictie_naam) VALUES "
            val restricties = voorkeurenDTO.voorkeuren
            alleGebruikersRestrictiesVerwijderen(gebruikerToken)
            if (restricties != null && restricties.size > 0) {
                sql = genereerSQLGebruikersVoorkeuren(restricties, sql)
                val stmt = PreparedStatementBuilder(connectionService, sql)
                for (restrictie in restricties) {
                    stmt.setInt(gebruikerId)
                        .setString(restrictie.naam!!)
                }
                stmt.build().executeUpdate()
            }
        } catch (e: Exception){
            throw DatabaseConnectionException()
        }
        return null
    }

    private fun genereerSQLGebruikersVoorkeuren(
        voorkeuren: ArrayList<VoorkeurDTO>,
        sql: String
    ): String {
        var sql1 = sql
        for (i in 0 until voorkeuren.size) {
            val voorkeur = voorkeuren[i]
            if (voorkeur.naam != null) {
                if (i != 0) {
                    sql1 += ","
                }
                sql1 += "(?,?)"
            }
        }
        return sql1
    }

    fun makeGebruikersDTOBaseInfo(): GebruikersDTO {
        return try {
            val result: ResultSet = getAlleGebruikers()
            val gebruikers = ArrayList<GebruikerDTO>()
            while (result.next()) {
                val x = makeGebruiker(result.getInt("gebruiker_id"), result.getString("gebruikersnaam"), result.getString("foto"))
                gebruikers.add(x)
            }
            GebruikersDTO(gebruikers)
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }

    fun makeGebruiker(gebruikersId: Int, gebruikersNaam: String, gebruikersFoto: String): GebruikerDTO {
         return GebruikerDTO(gebruikersId, gebruikersNaam, gebruikersFoto, null, null)
    }
    
    fun setGebruikersToken(id: Int, token: String){
        try {
            connectionService.initializeConnection(databaseProperties.getConnectionString())
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

    fun setNieuweGebruiker(login: LoginDTO, gebruikerToken: String){
        try {
            connectionService.initializeConnection(databaseProperties.getConnectionString())
            val sql = "INSERT INTO gebruiker (gebruikersnaam, email, token, foto) VALUES (?,?,?,?)"
            val stmt = PreparedStatementBuilder(connectionService,sql)
                .setString(login.naam)
                .setString(login.email)
                .setString(gebruikerToken)
                .setString(login.foto)
                .build()
            stmt.executeUpdate()
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }
    fun getGebruikerID(login: LoginDTO): Int{
        try {
            connectionService.initializeConnection(databaseProperties.getConnectionString())
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

    fun getGebruiker(email: String): ResultSet {
        try {
            connectionService.initializeConnection(databaseProperties.getConnectionString())
            val sql = "SELECT gebruikersnaam,gebruiker_id,email,token,foto FROM gebruiker WHERE email=?"
            val stmt = PreparedStatementBuilder(connectionService,sql)
                .setString(email)
                .build()
            return stmt.executeQuery()
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }

    fun loginGebruiker(login: LoginDTO, token: String): TokenDTO {
        val result = getGebruiker(login.email)
        if (result.next()){
            //als gebruiker bestaat
            val id = getGebruikerID(login)
            setGebruikersToken(id, token)
        } else {
            //nieuwe gebruiker
            setNieuweGebruiker(login, token)
        }
        return TokenDTO(token)
    }

}