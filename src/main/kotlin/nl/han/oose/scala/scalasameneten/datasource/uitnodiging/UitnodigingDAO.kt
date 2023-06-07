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


    fun kickUitgenodigde(uitnodigingToken: String, gebruikerID: Int) {
        try {
            connectionService.initializeConnection(databaseProperties.getConnectionString())
            val stmt = PreparedStatementBuilder(connectionService,
                "DELETE FROM GEBRUIKER_IN_UITNODINGSGROEP\n" +
                    "WHERE UITNODIGING_TOKEN = ?\n" +
                    "AND GEBRUIKER_ID = ?")
                .setString(uitnodigingToken)
                .setInt(gebruikerID)
                .build()
            stmt.executeUpdate()
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }

    fun getRestaurantID(uitnodigingToken: String): Int? {
        try {
            connectionService.initializeConnection(databaseProperties.getConnectionString())
            val stmt = PreparedStatementBuilder(connectionService,
                "SELECT RESTAURANT_ID, UITNODIGING_TOKEN, GEBRUIKER_ID  \n" +
                    "FROM UITNODIGINGSGROEP\n" +
                    "WHERE " +
                        "UITNODIGING_TOKEN = ?")

                .setString(uitnodigingToken)
                .build()
            val rs = stmt.executeQuery()
            if (!rs.next()) {
                return null
            }
            val id = rs.getInt("RESTAURANT_ID")
            if (rs.wasNull()) {
                return null
            }
            return id
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }


    fun updateRestaurant(uitnodigingToken: String, restaurant: Int) {
        try {
            connectionService.initializeConnection(databaseProperties.getConnectionString())
            val stmt = PreparedStatementBuilder(connectionService,
                "UPDATE UITNODIGINGSGROEP \n" +
                    "SET RESTAURANT_ID = ?\n" +
                    "WHERE UITNODIGING_TOKEN " +
                        "= ?")
                .setInt(restaurant)
                .setString(uitnodigingToken)
                .build()
            stmt.executeUpdate()
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }

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
                    "    (\n" +
                    "        SELECT STRING_AGG(vvg.VOORKEUR_NAAM, ',')\n" +
                    "        FROM VOORKEUR_VAN_GEBRUIKER vvg\n" +
                    "        WHERE vvg.GEBRUIKER_ID = g.GEBRUIKER_ID\n" +
                    "    ) AS VOORKEUREN,\n" +
                    "    (\n" +
                    "        SELECT STRING_AGG(ghv.RESTRICTIE_NAAM, ',')\n" +
                    "        FROM GEBRUIKER_HEEFT_VOEDINGSRESTRICTIE ghv\n" +
                    "        WHERE ghv.GEBRUIKER_ID = g.GEBRUIKER_ID\n" +
                    "    ) AS RESTRICTIES\n" +
                    "FROM UITNODIGINGSGROEP ug\n" +
                    "INNER JOIN GEBRUIKER g ON g.GEBRUIKER_ID = ug.GEBRUIKER_ID\n" +
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
                    "SELECT\n" +
                        "  g.GEBRUIKERSNAAM,\n" +
                        "  g.FOTO,\n" +
                        "  g.EMAIL,\n" +
                        "  g.GEBRUIKER_ID,\n" +
                        "  (\n" +
                        "    SELECT STRING_AGG(vvg.VOORKEUR_NAAM, ',')\n" +
                        "    FROM (\n" +
                        "      SELECT DISTINCT vvg.VOORKEUR_NAAM\n" +
                        "      FROM VOORKEUR_VAN_GEBRUIKER vvg\n" +
                        "      WHERE vvg.GEBRUIKER_ID = g.GEBRUIKER_ID\n" +
                        "    ) vvg\n" +
                        "  ) AS VOORKEUREN,\n" +
                        "  (\n" +
                        "    SELECT STRING_AGG(ghv.RESTRICTIE_NAAM, ',')\n" +
                        "    FROM (\n" +
                        "      SELECT DISTINCT ghv.RESTRICTIE_NAAM\n" +
                        "      FROM GEBRUIKER_HEEFT_VOEDINGSRESTRICTIE ghv\n" +
                        "      WHERE ghv.GEBRUIKER_ID = g.GEBRUIKER_ID\n" +
                        "    ) ghv\n" +
                        "  ) AS RESTRICTIES\n" +
                        "FROM\n" +
                        "  GEBRUIKER_IN_UITNODINGSGROEP ug\n" +
                        "  INNER JOIN GEBRUIKER g ON g.GEBRUIKER_ID = ug.GEBRUIKER_ID\n" +
                        "WHERE\n" +
                        "  ug.UITNODIGING_TOKEN = ?\n" +
                        "GROUP BY\n" +
                        "  g.GEBRUIKERSNAAM,\n" +
                        "  g.FOTO,\n" +
                        "  g.EMAIL,\n" +
                        "  g.GEBRUIKER_ID;")
                .setString(uitnodigingToken)
                .build()
            return stmt.executeQuery()
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }


    fun getGebruikerID(gebruikerToken: String): Int {
        try {
            connectionService.initializeConnection(databaseProperties.getConnectionString())
            val stmt = PreparedStatementBuilder(connectionService,
                "SELECT GEBRUIKER_ID\n" +
                    "FROM GEBRUIKER\n" +
                    "WHERE TOKEN = ?")
                .setString(gebruikerToken)
                .build()
            val rs = stmt.executeQuery()
            rs.next()
            return rs.getInt("GEBRUIKER_ID")
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }

    fun accepteerUitnodiging(uitnodigingToken: String, gebruikerToken: String) {
        try {
            connectionService.initializeConnection(databaseProperties.getConnectionString())
            val stmt = PreparedStatementBuilder(connectionService,
                "INSERT INTO GEBRUIKER_IN_UITNODINGSGROEP (UITNODIGING_TOKEN, GEBRUIKER_ID)\n" +
                    "SELECT ?, GEBRUIKER_ID\n" +
                    "FROM GEBRUIKER\n" +
                    "WHERE TOKEN = ?")
                .setString(uitnodigingToken)
                .setString(gebruikerToken)
                .build()
            stmt.executeUpdate()
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }

}