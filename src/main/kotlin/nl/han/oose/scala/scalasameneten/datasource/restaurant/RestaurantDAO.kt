package nl.han.oose.scala.scalasameneten.datasource.restaurant

import nl.han.oose.scala.scalasameneten.datasource.PreparedStatementBuilder
import nl.han.oose.scala.scalasameneten.datasource.connection.ConnectionService
import nl.han.oose.scala.scalasameneten.datasource.connection.DatabaseProperties
import nl.han.oose.scala.scalasameneten.datasource.exceptions.DatabaseConnectionException
import org.springframework.context.annotation.ComponentScan
import org.springframework.stereotype.Component
import java.sql.ResultSet
import java.sql.SQLException


@Component
@ComponentScan("nl.han.oose.scala.scalasameneten.datasource.connection")
class RestaurantDAO (private val connectionService: ConnectionService, private val databaseProperties: DatabaseProperties){

    fun getAllRestaurantsWithVoorkeurenAndRestricties(): ResultSet {
        return try {
            connectionService.initializeConnection(databaseProperties.getConnectionString())
            val stmt = PreparedStatementBuilder(connectionService,
                "SELECT R.RESTAURANT_ID, R.RESTAURANT_NAAM, R.POSTCODE, R.STRAATNAAM, R.HUISNUMMER, R.LINK, R.FOTO,\n" +
                    "       (\n" +
                    "           SELECT STRING_AGG(V.VOORKEUR_NAAM, ',')\n" +
                    "           FROM RESTAURANT_HEEFT_VOORKEUR RV\n" +
                    "           JOIN VOORKEUR V ON RV.VOORKEUR_NAAM = V.VOORKEUR_NAAM\n" +
                    "           WHERE RV.RESTAURANT_ID = R.RESTAURANT_ID\n" +
                    "       ) AS VOORKEUREN,\n" +
                    "       (\n" +
                    "           SELECT STRING_AGG(VR.RESTRICTIE_NAAM, ',')\n" +
                    "           FROM VOEDINGSRESTRICTIE_IN_RESTAURANT VR\n" +
                    "           WHERE VR.RESTAURANT_ID = R.RESTAURANT_ID\n" +
                    "       ) AS RESTRICTIES\n" +
                    "FROM RESTAURANT R;\n")
                .build()
            stmt.executeQuery()
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }

    fun getRestaurant(id: Int): ResultSet {
        return try {
            val sql = "SELECT r.restaurant_id, r.restaurant_naam, r.postcode, r.straatnaam, r.huisnummer, r.link, r.foto,\n" +
                    "       (\n" +
                    "           SELECT STRING_AGG(rv.voorkeur_naam, ',')\n" +
                    "           FROM restaurant_heeft_voorkeur rv\n" +
                    "           WHERE rv.restaurant_id = r.restaurant_id\n" +
                    "       ) AS voorkeuren,\n" +
                    "       (\n" +
                    "           SELECT STRING_AGG(vr.restrictie_naam, ',')\n" +
                    "           FROM voedingsrestrictie_in_restaurant vr\n" +
                    "           WHERE vr.restaurant_id = r.restaurant_id\n" +
                    "       ) AS restricties\n" +
                    "FROM restaurant r " +
                    "WHERE r.restaurant_id=?"

            connectionService.initializeConnection(databaseProperties.getConnectionString())
            val stmt = PreparedStatementBuilder(connectionService, sql)
                    .setInt(id)
                    .build()
            stmt.executeQuery()
        } catch(e: SQLException){
            e.printStackTrace()
            throw DatabaseConnectionException()
        }
    }
    fun getRandomRestaurant(): ResultSet {
        return try {
            val sql = "SELECT floor((rand()*((rand()*(select count(*) from restaurant))))+1) as id"
            connectionService.initializeConnection(databaseProperties.getConnectionString())
            val stmt = PreparedStatementBuilder(connectionService, sql)
                    .build()
            val result = stmt.executeQuery()
            result.next()
            println(result.getInt("id"))
            getRestaurant(result.getInt("id"))
        } catch(e: SQLException){
            e.printStackTrace()
            throw DatabaseConnectionException()
        }
    }
}