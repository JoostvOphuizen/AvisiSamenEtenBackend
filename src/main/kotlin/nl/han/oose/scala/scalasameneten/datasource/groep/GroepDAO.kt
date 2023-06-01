package nl.han.oose.scala.scalasameneten.datasource.groep

import nl.han.oose.scala.scalasameneten.datasource.PreparedStatementBuilder
import nl.han.oose.scala.scalasameneten.datasource.connection.ConnectionService
import nl.han.oose.scala.scalasameneten.datasource.connection.DatabaseProperties
import nl.han.oose.scala.scalasameneten.datasource.exceptions.DatabaseConnectionException
import nl.han.oose.scala.scalasameneten.dto.restaurant.GroepDTO
import org.springframework.context.annotation.ComponentScan
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import java.sql.ResultSet
import java.sql.SQLException
import org.springframework.stereotype.Repository

@Component
@ComponentScan("nl.han.oose.scala.scalasameneten.datasource.connection")
class GroepDAO(private val connectionService: ConnectionService, private val databaseProperties: DatabaseProperties) {
    fun insertGroep(groepnaam: String) {
        try {
            connectionService.initializeConnection(databaseProperties.getConnectionString())
            val stmt = PreparedStatementBuilder(connectionService,"INSERT INTO GROEP (GROEPNAAM)\n" +
                    "VALUES(?)")
                .setString(groepnaam)
                .build()
            stmt.executeUpdate()
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }

    fun getGroepen(): ResultSet {
        try {
            connectionService.initializeConnection(databaseProperties.getConnectionString())
            val stmt = PreparedStatementBuilder(connectionService,"SELECT g.GROEPNAAM, STUFF((\n" +
                    "    SELECT ',' + CAST(gig.GEBRUIKER_ID AS VARCHAR(MAX))\n" +
                    "    FROM GEBRUIKER_IN_GROEP gig\n" +
                    "    WHERE gig.GROEPNAAM = g.GROEPNAAM\n" +
                    "    FOR XML PATH('')\n" +
                    "), 1, 1, '') AS USER_IDS\n" +
                    "FROM GROEP g")
                .build()
           return stmt.executeQuery()

        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }

    fun setGroepsleden(geselecteerdeGebruikers: GroepDTO) {
        try {
            connectionService.initializeConnection(databaseProperties.getConnectionString())
            var sql = "insert into gebruiker_in_groep(gebruiker_id,groepnaam) values \n"

            for(lid in geselecteerdeGebruikers.leden) {
                sql += "(?,?),"
            }
            sql = sql.substring(0, sql.length - 1)

            val stmt = PreparedStatementBuilder(connectionService, sql)
            for(lid in geselecteerdeGebruikers.leden) {
                stmt.setInt(lid)
                stmt.setString(geselecteerdeGebruikers.naam)
            }
            stmt.build().executeUpdate()
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }


}