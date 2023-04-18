package nl.han.oose.scala.scalasameneten.datasource.gebruiker

import nl.han.oose.scala.scalasameneten.datasource.connection.ConnectionService
import nl.han.oose.scala.scalasameneten.datasource.exceptions.DatabaseConnectionException
import java.sql.PreparedStatement
import java.sql.SQLException

class GebruikerSQLPreparedStatementBuilder {
    object gebruiker {
        fun buildGetAlleGebruikersPreparedStatement(connectionService: ConnectionService): PreparedStatement {
            val sql = "SELECT gebruikersnaam FROM gebruiker"
            return try {
                val preparedStatement = connectionService.getConnection()!!.prepareStatement(sql)
                preparedStatement
            } catch (e: SQLException) {
                throw DatabaseConnectionException()
            }
        }
    }
}