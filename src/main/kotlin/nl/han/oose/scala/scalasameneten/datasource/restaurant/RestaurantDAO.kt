package nl.han.oose.scala.scalasameneten.datasource.restaurant

import nl.han.oose.scala.scalasameneten.datasource.PreparedStatementBuilder
import nl.han.oose.scala.scalasameneten.datasource.connection.ConnectionService
import nl.han.oose.scala.scalasameneten.datasource.connection.DatabaseProperties
import nl.han.oose.scala.scalasameneten.datasource.exceptions.DatabaseConnectionException
import nl.han.oose.scala.scalasameneten.dto.restaurant.GroepDTO
import nl.han.oose.scala.scalasameneten.dto.restaurant.RestaurantDTO
import nl.han.oose.scala.scalasameneten.service.restaurant.RestaurantService
import org.springframework.context.annotation.ComponentScan
import org.springframework.stereotype.Component
import java.sql.ResultSet
import java.sql.SQLException


@Component
@ComponentScan("nl.han.oose.scala.scalasameneten.datasource.connection")
class RestaurantDAO (private val connectionService: ConnectionService, private val databaseProperties: DatabaseProperties){

    fun getRestaurants(): ResultSet? {
        return try {
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            val stmt = PreparedStatementBuilder(connectionService,"select * from RESTAURANT")
                .build()
            stmt.executeQuery()
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }

    fun getRestaurantenZonderRestricties(lidId: Int): ResultSet {
        return try {
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            val stmt = PreparedStatementBuilder(connectionService,"select RESTAURANT_ID from RESTAURANT except select RESTAURANT_ID from VOEDINGSRESTRICTIE_IN_RESTAURANT where RESTRICTIE_NAAM in ( select v.RESTRICTIE_NAAM from VOEDINGSRESTRICTIE v inner join GEBRUIKER_HEEFT_VOEDINGSRESTRICTIE gv on v.RESTRICTIE_NAAM = gv.RESTRICTIE_NAAM where gv.GEBRUIKER_ID = ? group by v.RESTRICTIE_NAAM)")
                .setInt(lidId)
                .build()
            stmt.executeQuery()
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }

    fun getVoorkeurenPrioterisering(geselecteerdeGebruikers: GroepDTO): ResultSet {
        return try {
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            var sql = "select COUNT (*) as aantal, VOORKEUR_NAAM from VOORKEUR_VAN_GEBRUIKER where "

            for(lid in geselecteerdeGebruikers.leden!!) {
                sql += "GEBRUIKER_ID = ? or "
            }
            sql = sql.substring(0, sql.length - 3)
            sql += " group by VOORKEUR_NAAM order by aantal desc"

            val stmt = PreparedStatementBuilder(connectionService, sql)
            for(lid in geselecteerdeGebruikers.leden!!) {
                stmt.setInt(lid)
            }
            stmt.build().executeQuery()
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }


//    fun getRestaurantInformatie(): ResultSet? {
//        return try {
//            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
//            val stmt = PreparedStatementBuilder(connectionService,"select r.RESTAURANT_ID,VOORKEUR_NAAM,RESTRICTIE_NAAM,TYPE from RESTAURANT r\n" +
//                    "inner join RESTAURANT_HEEFT_VOORKEUR rvoorkeur\n" +
//                    "on r.RESTAURANT_ID = rvoorkeur.RESTAURANT_ID\n" +
//                    "inner join VOEDINGSRESTRICTIE_IN_RESTAURANT rrestrictie\n" +
//                    "on r.RESTAURANT_ID = rrestrictie.RESTAURANT_ID\n")
//                .build()
//            stmt.executeQuery()
//        } catch (e: SQLException) {
//            throw DatabaseConnectionException()
//        }
//    }

    fun makeRestaurantDTO(restaurantId: Int): RestaurantDTO {
        return RestaurantDTO(1,"naam","1234AB","straat",1)
    }
}