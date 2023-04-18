package nl.han.oose.scala.scalasameneten.datasource.voedingsrestrictie

import nl.han.oose.scala.scalasameneten.datasource.connection.ConnectionService
import nl.han.oose.scala.scalasameneten.datasource.exceptions.DatabaseConnectionException
import java.sql.PreparedStatement
import java.sql.SQLException

class VoedingsrestrictieSQLPreparedStatementBuilder {

    object allergie {
        fun buildGetAlleAllergieenPreparedStatement(connectionService: ConnectionService): PreparedStatement {
            val getAlleVoorkeurenPreparedStatement = "SELECT restrictie_naam FROM voedingsrestrictie WHERE type='allergie'"
            return try {
                connectionService.getConnection()!!.prepareStatement(getAlleVoorkeurenPreparedStatement)
            } catch (e: SQLException) {
                throw DatabaseConnectionException()
            }
        }

        fun buildAllergieBestaatPreparedStatement(connectionService: ConnectionService, allergie: String): PreparedStatement {
            val sql = "SELECT 1 FROM voedingsrestrictie WHERE restrictie_naam=? AND type='allergie'"
            return try {
                val stmt = connectionService.getConnection()!!.prepareStatement(sql)
                stmt.setString(1,allergie)
                stmt
            } catch (e: SQLException){
                throw DatabaseConnectionException()
            }
        }
    }

    object geloof {
        fun buildGetAlleGeloofPreparedStatement(connectionService: ConnectionService): PreparedStatement {
            val getAlleVoorkeurenPreparedStatement = "SELECT restrictie_naam FROM voedingsrestrictie WHERE type='geloof'"
            return try {
                connectionService.getConnection()!!.prepareStatement(getAlleVoorkeurenPreparedStatement)
            } catch (e: SQLException) {
                throw DatabaseConnectionException()
            }
        }

        fun buildGeloofBestaatPreparedStatement(connectionService: ConnectionService, geloof: String): PreparedStatement {
            val sql = "SELECT 1 FROM voedingsrestrictie WHERE restrictie_naam=? AND type='geloof'"
            return try {
                val stmt = connectionService.getConnection()!!.prepareStatement(sql)
                stmt.setString(1,geloof)
                stmt
            } catch (e: SQLException){
                throw DatabaseConnectionException()
            }
        }
    }

    object dieet {
        fun buildGetAlleDieetPreparedStatement(connectionService: ConnectionService): PreparedStatement {
            val getAlleVoorkeurenPreparedStatement = "SELECT restrictie_naam FROM voedingsrestrictie WHERE type='dieet'"
            return try {
                connectionService.getConnection()!!.prepareStatement(getAlleVoorkeurenPreparedStatement)
            } catch (e: SQLException) {
                throw DatabaseConnectionException()
            }
        }

        fun buildDieetBestaatPreparedStatement(connectionService: ConnectionService, dieet: String): PreparedStatement {
            val sql = "SELECT 1 FROM voedingsrestrictie WHERE restrictie_naam=? AND type='dieet'"
            return try {
                val stmt = connectionService.getConnection()!!.prepareStatement(sql)
                stmt.setString(1,dieet)
                stmt
            } catch (e: SQLException){
                throw DatabaseConnectionException()
            }
        }
    }
}