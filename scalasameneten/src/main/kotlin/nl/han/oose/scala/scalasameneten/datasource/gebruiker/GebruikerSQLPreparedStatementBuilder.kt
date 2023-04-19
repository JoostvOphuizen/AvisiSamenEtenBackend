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
    }

    object voeding {
        fun buildGetGebruikersVoedingsestrictiesPreparedStatement(connectionService: ConnectionService, gebruiker: Int): PreparedStatement {
            val getGebruikersVoorkeurPreparedStatement = "SELECT restrictie_naam,type FROM gebruiker_heeft_voedingsrestrictie WHERE gebruiker_id=?"
            return try {
                val preparedStatement = connectionService.getConnection()!!.prepareStatement(getGebruikersVoorkeurPreparedStatement)
                preparedStatement.setInt(1, gebruiker)
                preparedStatement
            } catch (e: SQLException) {
                throw DatabaseConnectionException()
            }
        }


        fun buildGebruikersVoedingsrestrictieToevoegenPreparedStatement(connectionService: ConnectionService, gebruiker: Int, restrictie: String,type: String): PreparedStatement {
            val sql = "INSERT INTO gebruiker_heeft_voedingsrestrictie(gebruiker_id,restrictie_naam,type) VALUES (?,?,?)"
            return try {
                val stmt = connectionService.getConnection()!!.prepareStatement(sql)
                stmt.setInt(1,gebruiker)
                stmt.setString(2,restrictie)
                stmt.setString(3,type)
                stmt
            } catch (e: SQLException){
                throw DatabaseConnectionException()
            }
        }

        fun buildVoedingsrestrictieVerwijderenPreparedStatement(connectionService: ConnectionService,gebruiker: Int,restrictie: String,type: String): PreparedStatement{
            val sql = "DELETE FROM gebruiker_heeft_voedingsrestrictie WHERE gebruiker_id=? AND restrictie_naam=? AND type=?"
            return try {
                val stmt = connectionService.getConnection()!!.prepareStatement(sql)
                stmt.setInt(1,gebruiker)
                stmt.setString(2,restrictie)
                stmt.setString(3,type)
                stmt
            } catch (e: SQLException){
                throw DatabaseConnectionException()
            }
        }

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
        fun buildGebruikersVoorkeurToevoegenPreparedStatement(connectionService: ConnectionService,gebruiker: Int,voorkeur: String): PreparedStatement {
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
        fun buildGebruikersVoorkeurVerwijderenPreparedStatement(connectionService: ConnectionService,gebruiker: Int,voorkeur: String): PreparedStatement{
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