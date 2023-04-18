package nl.han.oose.scala.scalasameneten.datasource.voorkeur

import nl.han.oose.scala.scalasameneten.datasource.connection.ConnectionService
import nl.han.oose.scala.scalasameneten.datasource.exceptions.DatabaseConnectionException
import java.sql.PreparedStatement
import java.sql.SQLException


class VoorkeurSQLPreparedStatementBuilder {
    object voorkeurenStatements {
        fun buildGetAlleVoorkeurenPreparedStatement(connectionService: ConnectionService): PreparedStatement {
            val getAlleVoorkeurenPreparedStatement = "SELECT voorkeur_naam FROM voorkeur"
            return try {
                connectionService.getConnection()!!.prepareStatement(getAlleVoorkeurenPreparedStatement)
            } catch (e: SQLException) {
                throw DatabaseConnectionException()
            }
        }
        fun buildVoorkeurBestaatPreparedStatement(connectionService: ConnectionService,voorkeur: String): PreparedStatement{
            val sql = "SELECT 1 FROM voorkeur WHERE voorkeur_naam=?"
            return try {
                val stmt = connectionService.getConnection()!!.prepareStatement(sql)
                stmt.setString(1,voorkeur)
                stmt
            } catch (e: SQLException){
                throw DatabaseConnectionException()
            }
        }
    }
}