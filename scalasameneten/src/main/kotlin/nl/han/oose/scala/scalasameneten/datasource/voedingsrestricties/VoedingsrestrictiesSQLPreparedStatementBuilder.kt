package nl.han.oose.scala.scalasameneten.datasource.voedingsrestricties

import nl.han.oose.scala.scalasameneten.datasource.connection.ConnectionService
import nl.han.oose.scala.scalasameneten.datasource.exceptions.DatabaseConnectionException
import java.sql.PreparedStatement
import java.sql.SQLException

class VoedingsrestrictiesSQLPreparedStatementBuilder {

    object algemeen {
        fun buildGetGebruikersRestrictiesPreparedStatement(connectionService: ConnectionService, gebruiker: Int): PreparedStatement {
            val getGebruikersVoorkeurPreparedStatement = "SELECT naam,type FROM gebruiker_heeft_voedingsrestrictie WHERE gebruiker_id=?"
            return try {
                val preparedStatement = connectionService.getConnection()!!.prepareStatement(getGebruikersVoorkeurPreparedStatement)
                preparedStatement.setInt(1, gebruiker)
                preparedStatement
            } catch (e: SQLException) {
                throw DatabaseConnectionException()
            }
        }
    }
    object allergie {
        fun buildGetAlleAllergieenPreparedStatement(connectionService: ConnectionService): PreparedStatement {
            val getAlleVoorkeurenPreparedStatement = "SELECT naam FROM voedingsrestrictie WHERE type='allergie'"
            return try {
                connectionService.getConnection()!!.prepareStatement(getAlleVoorkeurenPreparedStatement)
            } catch (e: SQLException) {
                throw DatabaseConnectionException()
            }
        }

        fun buildGebruikersAllergieToevoegenPreparedStatement(connectionService: ConnectionService, gebruiker: Int, allergie: String): PreparedStatement {
            val sql = "INSERT INTO gebruiker_heeft_voedingsrestrictie(gebruiker_id,naam,type) VALUES (?,?,'allergie')"
            return try {
                val stmt = connectionService.getConnection()!!.prepareStatement(sql)
                stmt.setInt(1,gebruiker)
                stmt.setString(2,allergie)
                stmt
            } catch (e: SQLException){
                throw DatabaseConnectionException()
            }
        }

        fun buildAllergieBestaatPreparedStatement(connectionService: ConnectionService, allergie: String): PreparedStatement {
            val sql = "SELECT 1 FROM voedingsrestrictie WHERE naam=? AND type='allergie'"
            return try {
                val stmt = connectionService.getConnection()!!.prepareStatement(sql)
                stmt.setString(1,allergie)
                stmt
            } catch (e: SQLException){
                throw DatabaseConnectionException()
            }
        }
        fun buildAllergieVerwijderenPreparedStatement(connectionService: ConnectionService,gebruiker: Int,allergie: String): PreparedStatement{
            val sql = "DELETE FROM gebruiker_heeft_voedingsrestrictie WHERE gebruiker_id=? AND restrictie_naam=? AND type='allergie'"
            return try {
                val stmt = connectionService.getConnection()!!.prepareStatement(sql)
                stmt.setInt(1,gebruiker)
                stmt.setString(2,allergie)
                stmt
            } catch (e: SQLException){
                throw DatabaseConnectionException()
            }
        }
    }

    object geloof {
        fun buildGetAlleGeloofPreparedStatement(connectionService: ConnectionService): PreparedStatement {
            val getAlleVoorkeurenPreparedStatement = "SELECT naam FROM voedingsrestrictie WHERE type='geloof'"
            return try {
                connectionService.getConnection()!!.prepareStatement(getAlleVoorkeurenPreparedStatement)
            } catch (e: SQLException) {
                throw DatabaseConnectionException()
            }
        }

        fun buildGebruikersGeloofToevoegenPreparedStatement(connectionService: ConnectionService, gebruiker: Int, geloof: String): PreparedStatement {
            val sql = "INSERT INTO gebruiker_heeft_voedingsrestrictie(gebruiker_id,naam,type) VALUES (?,?,'geloof')"
            return try {
                val stmt = connectionService.getConnection()!!.prepareStatement(sql)
                stmt.setInt(1,gebruiker)
                stmt.setString(2,geloof)
                stmt
            } catch (e: SQLException){
                throw DatabaseConnectionException()
            }
        }

        fun buildGeloofBestaatPreparedStatement(connectionService: ConnectionService, geloof: String): PreparedStatement {
            val sql = "SELECT 1 FROM voedingsrestrictie WHERE naam=? AND type='geloof'"
            return try {
                val stmt = connectionService.getConnection()!!.prepareStatement(sql)
                stmt.setString(1,geloof)
                stmt
            } catch (e: SQLException){
                throw DatabaseConnectionException()
            }
        }
        fun buildGeloofVerwijderenPreparedStatement(connectionService: ConnectionService,gebruiker: Int,geloof: String): PreparedStatement{
            val sql = "DELETE FROM gebruiker_heeft_voedingsrestrictie WHERE gebruiker_id=? AND restrictie_naam=? AND type='geloof'"
            return try {
                val stmt = connectionService.getConnection()!!.prepareStatement(sql)
                stmt.setInt(1,gebruiker)
                stmt.setString(2,geloof)
                stmt
            } catch (e: SQLException){
                throw DatabaseConnectionException()
            }
        }
    }

    object dieet {
        fun buildGetAlleDieetPreparedStatement(connectionService: ConnectionService): PreparedStatement {
            val getAlleVoorkeurenPreparedStatement = "SELECT naam FROM voedingsrestrictie WHERE type='dieet'"
            return try {
                connectionService.getConnection()!!.prepareStatement(getAlleVoorkeurenPreparedStatement)
            } catch (e: SQLException) {
                throw DatabaseConnectionException()
            }
        }

        fun buildGebruikersDieetToevoegenPreparedStatement(connectionService: ConnectionService, gebruiker: Int, dieet: String): PreparedStatement {
            val sql = "INSERT INTO gebruiker_heeft_voedingsrestrictie(gebruiker_id,naam,type) VALUES (?,?,'dieet')"
            return try {
                val stmt = connectionService.getConnection()!!.prepareStatement(sql)
                stmt.setInt(1,gebruiker)
                stmt.setString(2,dieet)
                stmt
            } catch (e: SQLException){
                throw DatabaseConnectionException()
            }
        }

        fun buildDieetBestaatPreparedStatement(connectionService: ConnectionService, dieet: String): PreparedStatement {
            val sql = "SELECT 1 FROM voedingsrestrictie WHERE naam=? AND type='dieet'"
            return try {
                val stmt = connectionService.getConnection()!!.prepareStatement(sql)
                stmt.setString(1,dieet)
                stmt
            } catch (e: SQLException){
                throw DatabaseConnectionException()
            }
        }
        fun buildDieetVerwijderenPreparedStatement(connectionService: ConnectionService,gebruiker: Int,dieet: String): PreparedStatement{
            val sql = "DELETE FROM gebruiker_heeft_voedingsrestrictie WHERE gebruiker_id=? AND restrictie_naam=? AND type='dieet'"
            return try {
                val stmt = connectionService.getConnection()!!.prepareStatement(sql)
                stmt.setInt(1,gebruiker)
                stmt.setString(2,dieet)
                stmt
            } catch (e: SQLException){
                throw DatabaseConnectionException()
            }
        }
    }
}