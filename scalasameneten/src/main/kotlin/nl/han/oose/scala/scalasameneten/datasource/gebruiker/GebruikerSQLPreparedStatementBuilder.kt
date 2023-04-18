package nl.han.oose.scala.scalasameneten.datasource.gebruiker

import nl.han.oose.scala.scalasameneten.datasource.connection.ConnectionService
import nl.han.oose.scala.scalasameneten.datasource.exceptions.DatabaseConnectionException
import java.sql.PreparedStatement
import java.sql.SQLException

class GebruikerSQLPreparedStatementBuilder {
    object gebruiker {
        fun buildGetAlleGebruikersPreparedStatement(connectionService: ConnectionService): PreparedStatement {
            val sql = "SELECT gebruikersnaam,gebruiker_id FROM gebruiker"
            return try {
                val preparedStatement = connectionService.getConnection()!!.prepareStatement(sql)
                preparedStatement
            } catch (e: SQLException) {
                throw DatabaseConnectionException()
            }
        }
        fun buildGebruikerToevoegenPreparedStatement(connectionService: ConnectionService,naam: String): PreparedStatement {
            val sql = "INSERT INTO gebruiker(gebruikersnaam) VALUES (?)"
            return try {
                val preparedStatement = connectionService.getConnection()!!.prepareStatement(sql)
                preparedStatement.setString(1,naam)
                preparedStatement
            } catch (e: SQLException) {
                throw DatabaseConnectionException()
            }
        }
    }
}