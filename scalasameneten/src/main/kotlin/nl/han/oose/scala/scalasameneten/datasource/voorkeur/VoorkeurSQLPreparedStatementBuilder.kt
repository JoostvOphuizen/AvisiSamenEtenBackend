package nl.han.oose.scala.scalasameneten.datasource.voorkeur

import nl.han.oose.scala.scalasameneten.datasource.connection.ConnectionService
import nl.han.oose.scala.scalasameneten.datasource.exceptions.DatabaseConnectionException
import java.sql.PreparedStatement
import java.sql.SQLException


class VoorkeurSQLPreparedStatementBuilder {
    object voorkeurenStatements {

        fun buildGetGebruikersVoorkeurPreparedStatement(connectionService: ConnectionService, gebruiker: Int): PreparedStatement {
            val getGebruikersVoorkeurPreparedStatement = "SELECT voorkeur_naam FROM voorkeur_van_gebruiker v INNER JOIN gebruiker g on v.gebruiker_id=g.gebruiker_id WHERE g.gebruiker_id=?"
            return try {
                val preparedStatement = connectionService.getConnection()!!.prepareStatement(getGebruikersVoorkeurPreparedStatement)
                preparedStatement.setInt(1, gebruiker)
                preparedStatement
            } catch (e: SQLException) {
                throw DatabaseConnectionException()
            }
        }
        fun buildGetAlleVoorkeurenPreparedStatement(connectionService: ConnectionService): PreparedStatement {
            val getAlleVoorkeurenPreparedStatement = "SELECT voorkeur_naam FROM voorkeur"
            return try {
                connectionService.getConnection()!!.prepareStatement(getAlleVoorkeurenPreparedStatement)
            } catch (e: SQLException) {
                throw DatabaseConnectionException()
            }
        }
        fun buildGebruikersVoorkeurenToevoegenPreparedStatement(connectionService: ConnectionService,gebruiker: Int,voorkeur: String): PreparedStatement {
            val sql = "INSERT INTO voorkeur_van_gebruiker(gebruiker_id,voorkeur_naam) VALUES (?,?)"
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
            val sql = "SELECT 1 FROM voorkeur WHERE voorkeur_naam=?"
            return try {
                val stmt = connectionService.getConnection()!!.prepareStatement(sql)
                stmt.setString(1,voorkeur)
                stmt
            } catch (e: SQLException){
                throw DatabaseConnectionException()
            }
        }
        fun buildVoorkeurVerwijderenPreparedStatement(connectionService: ConnectionService,gebruiker: Int,voorkeur: String): PreparedStatement{
            val sql = "DELETE FROM voorkeur_van_gebruiker WHERE gebruiker_id=? AND voorkeur_naam=?"
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