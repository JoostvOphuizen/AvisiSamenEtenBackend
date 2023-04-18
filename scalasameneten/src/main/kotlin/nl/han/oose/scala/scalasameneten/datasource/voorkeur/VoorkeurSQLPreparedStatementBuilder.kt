package nl.han.oose.scala.scalasameneten.datasource.voorkeur

import nl.han.oose.scala.scalasameneten.datasource.connection.ConnectionService
import nl.han.oose.scala.scalasameneten.datasource.exceptions.DatabaseConnectionException
import java.sql.PreparedStatement
import java.sql.SQLException


class VoorkeurSQLPreparedStatementBuilder {
    object voorkeurenStatements {

        fun buildGetGebruikersVoorkeurPreparedStatement(connectionService: ConnectionService, gebruiker: Int): PreparedStatement {
            val getGebruikersVoorkeurPreparedStatement = "SELECT naam FROM gebruiker_heeft_voorkeur WHERE gebruiker_id=?"
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
        fun buildGebruikersVoorkeurenToevoegenPreparedStatement(connectionService: ConnectionService,gebruiker: Int,voorkeur: String): PreparedStatement {
            val sql = "INSERT INTO gebruiker_heeft_voorkeur(gebruiker_id,naam) VALUES (?,?)"
            return try {
                val stmt = connectionService.getConnection()!!.prepareStatement(sql)
                stmt.setInt(1,gebruiker)
                stmt.setString(2,voorkeur)
                stmt
            } catch (e: SQLException){
                throw DatabaseConnectionException()
            }
        }
        fun buildVoorkeurBestaatPreparedStatement(connectionService: ConnectionService,voorkeur: String): PreparedStatement{
            val sql = "SELECT 1 FROM voorkeur WHERE naam=?"
            return try {
                val stmt = connectionService.getConnection()!!.prepareStatement(sql)
                stmt.setString(1,voorkeur)
                stmt
            } catch (e: SQLException){
                throw DatabaseConnectionException()
            }
        }
        fun buildVoorkeurVerwijderenPreparedStatement(connectionService: ConnectionService,gebruiker: Int,voorkeur: String): PreparedStatement{
            val sql = "DELETE FROM gebruiker_heeft_voorkeur WHERE gebruiker_id=? AND voorkeur=?"
            return try {
                val stmt = connectionService.getConnection()!!.prepareStatement(sql)
                stmt.setInt(1,gebruiker)
                stmt.setString(2,voorkeur)
                stmt
            } catch (e: SQLException){
                throw DatabaseConnectionException()
            }
        }
    }
}