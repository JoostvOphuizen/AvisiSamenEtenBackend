package nl.han.oose.scala.scalasameneten.datasource.voorkeuren

import nl.han.oose.scala.scalasameneten.datasource.connection.ConnectionService
import nl.han.oose.scala.scalasameneten.datasource.exceptions.DatabaseConnectionException
import java.sql.PreparedStatement
import java.sql.SQLException


class VoorkeurenSQLPreparedStatementBuilder {
    object voorkeurenStatements {

        fun buildGetGebruikersVoorkeurPreparedStatement(connectionService: ConnectionService, gebruiker: Int): PreparedStatement {
            val getGebruikersVoorkeurPreparedStatement = "SELECT naam FROM gebruiker_heeft_voorkeur WHERE gebruikers_id=$gebruiker"
            return try {
                val preparedStatement = connectionService.getConnection()!!.prepareStatement(getGebruikersVoorkeurPreparedStatement)
                preparedStatement.setInt(1, gebruiker)
                preparedStatement
            } catch (e: SQLException) {
                throw DatabaseConnectionException()
            }
        }


        fun buildGetAlleVoorkeurenPreparedStatement(connectionService: ConnectionService): PreparedStatement {
            val getAlleVoorkeurenPreparedStatement = "SELECT naam FROM voorkeur"
            return try {
                connectionService.getConnection()!!.prepareStatement(getAlleVoorkeurenPreparedStatement)
            } catch (e: SQLException) {
                throw DatabaseConnectionException()
            }
        }
    }
}