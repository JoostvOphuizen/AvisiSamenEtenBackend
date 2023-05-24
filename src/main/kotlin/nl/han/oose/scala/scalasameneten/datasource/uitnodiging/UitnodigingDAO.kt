package nl.han.oose.scala.scalasameneten.datasource.uitnodiging

import nl.han.oose.scala.scalasameneten.datasource.PreparedStatementBuilder
import nl.han.oose.scala.scalasameneten.datasource.connection.ConnectionService
import nl.han.oose.scala.scalasameneten.datasource.connection.DatabaseProperties
import nl.han.oose.scala.scalasameneten.datasource.exceptions.DatabaseConnectionException
import org.springframework.context.annotation.ComponentScan
import org.springframework.stereotype.Component
import java.sql.ResultSet
import java.sql.SQLException

@Component
@ComponentScan("nl.han.oose.scala.scalasameneten.datasource.connection")
class UitnodigingDAO (private val connectionService: ConnectionService, private val databaseProperties: DatabaseProperties) {

    fun createUitnodiging(uitnodigingToken: String, gebruikerToken: String) {
        try {
            connectionService.initializeConnection(databaseProperties.getConnectionString())
            val stmt = PreparedStatementBuilder(connectionService,
                "INSERT INTO UITNODIGINGSGROEP (UITNODIGING_TOKEN, GEBRUIKER_ID)\n" +
                    "SELECT ?, GEBRUIKER_ID\n" +
                    "FROM GEBRUIKER\n" +
                    "WHERE TOKEN = ?")
                .setString(uitnodigingToken)
                .setString(gebruikerToken)
                .build()
            stmt.executeUpdate()
            println("Uitnodiging created")
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }

    fun checkIfTokenExists(token: String): Boolean {
        try {
            connectionService.initializeConnection(databaseProperties.getConnectionString())
            val stmt = PreparedStatementBuilder(connectionService,
                "SELECT * \n" +
                    "FROM UITNODIGINGSGROEP\n" +
                    "WHERE UITNODIGING_TOKEN = ?")
                .setString(token)
                .build()
            return stmt.executeQuery().next()
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }

    fun getHost(uitnodigingToken: String): ResultSet {
        try {
            connectionService.initializeConnection(databaseProperties.getConnectionString())
            val stmt = PreparedStatementBuilder(connectionService,
                "SELECT g.GEBRUIKERSNAAM, g.GEBRUIKER_ID, g.EMAIL, g.FOTO,\n" +
                    "    STRING_AGG(vvg.VOORKEUR_NAAM, ',') AS VOORKEUREN,\n" +
                    "    STRING_AGG(ghv.RESTRICTIE_NAAM, ',') AS RESTRICTIES\n" +
                    "FROM UITNODIGINGSGROEP ug\n" +
                    "INNER JOIN GEBRUIKER g ON g.GEBRUIKER_ID = ug.GEBRUIKER_ID\n" +
                    "LEFT JOIN GEBRUIKER_HEEFT_VOEDINGSRESTRICTIE ghv ON ghv.GEBRUIKER_ID = g.GEBRUIKER_ID\n" +
                    "LEFT JOIN VOORKEUR_VAN_GEBRUIKER vvg ON vvg.GEBRUIKER_ID = g.GEBRUIKER_ID\n" +
                    "WHERE ug.UITNODIGING_TOKEN = ?\n" +
                    "GROUP BY g.GEBRUIKERSNAAM, g.FOTO, g.GEBRUIKER_ID, g.EMAIL;")
                .setString(uitnodigingToken)
                .build()
            return stmt.executeQuery()
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }

    fun getUitgenodigden(uitnodigingToken: String): ResultSet {
        try {
            connectionService.initializeConnection(databaseProperties.getConnectionString())
            val stmt = PreparedStatementBuilder(connectionService,
                "SELECT g.GEBRUIKERSNAAM, g.FOTO, g.EMAIL, g.GEBRUIKER_ID,\n" +
                "    STRING_AGG(vvg.VOORKEUR_NAAM, ',') AS VOORKEUREN,\n" +
                "    STRING_AGG(ghv.RESTRICTIE_NAAM, ',') AS RESTRICTIES\n" +
                "FROM GEBRUIKER_IN_UITNODINGSGROEP ug\n" +
                "INNER JOIN GEBRUIKER g ON g.GEBRUIKER_ID = ug.GEBRUIKER_ID\n" +
                "LEFT JOIN GEBRUIKER_HEEFT_VOEDINGSRESTRICTIE ghv ON ghv.GEBRUIKER_ID = g.GEBRUIKER_ID\n" +
                "LEFT JOIN VOORKEUR_VAN_GEBRUIKER vvg ON vvg.GEBRUIKER_ID = g.GEBRUIKER_ID\n" +
                "WHERE ug.UITNODIGING_TOKEN = ?\n" +
                "GROUP BY g.GEBRUIKERSNAAM, g.FOTO, g.email, g.gebruiker_id;")
                .setString(uitnodigingToken)
                .build()
            return stmt.executeQuery()
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }

}