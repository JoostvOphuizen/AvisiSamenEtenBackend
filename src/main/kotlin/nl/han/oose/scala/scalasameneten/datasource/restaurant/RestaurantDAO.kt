package nl.han.oose.scala.scalasameneten.datasource.restaurant

import nl.han.oose.scala.scalasameneten.datasource.PreparedStatementBuilder
import nl.han.oose.scala.scalasameneten.datasource.connection.ConnectionService
import nl.han.oose.scala.scalasameneten.datasource.connection.DatabaseProperties
import nl.han.oose.scala.scalasameneten.datasource.exceptions.DatabaseConnectionException
import nl.han.oose.scala.scalasameneten.dto.restaurant.ReviewDTO
import nl.han.oose.scala.scalasameneten.dto.restaurant.RestaurantWithVoorkeurenAndRestrictiesDTO
import org.springframework.context.annotation.ComponentScan
import org.springframework.stereotype.Component
import java.sql.ResultSet
import java.sql.SQLException


@Component
@ComponentScan("nl.han.oose.scala.scalasameneten.datasource.connection")
class RestaurantDAO (private val connectionService: ConnectionService, private val databaseProperties: DatabaseProperties){
    private final val maxDayCount = 2

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

    fun postReview(restaurantId: Int, review: ReviewDTO, id: Int){
        try {
            val sql = "INSERT INTO review (restaurant_id, gebruiker_id, beoordeling, tekst) VALUES (?, ?, ?, ?)"

            connectionService.initializeConnection(databaseProperties.getConnectionString())
            val stmt = PreparedStatementBuilder(connectionService, sql)
                    .setInt(restaurantId)
                    .setInt(id)
                    .setInt(review.score)
                    .setString(review.tekst)
                    .build()
            stmt.executeUpdate()
        } catch(e: SQLException){
            throw DatabaseConnectionException()
        }
    }

    fun getRecentBezochteRestaurant(gebruikersToken: String): ResultSet {
        return try {


            val sql = "SELECT TOP 1 h.RESTAURANT_ID, restaurant_naam, postcode, straatnaam, huisnummer, link, r.foto, datum\n" +
                    "FROM HIST_BEZOEK h\n" +
                    "INNER JOIN RESTAURANT r ON h.RESTAURANT_ID = r.RESTAURANT_ID\n" +
                    "INNER JOIN GEBRUIKER g ON h.GEBRUIKER_ID = g.GEBRUIKER_ID\n" +
                    "WHERE g.TOKEN = ?\n" +
                    "  AND h.DATUM >= DATEADD(day, -?, GETDATE())\n" +
                    "ORDER BY DATUM DESC"

            connectionService.initializeConnection(databaseProperties.getConnectionString())
            val stmt = PreparedStatementBuilder(connectionService, sql)
                .setString(gebruikersToken)
                .setInt(maxDayCount)
                .build()
            stmt.executeQuery()
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }
    fun getReviews(id: Int): ResultSet{
        return try {
            connectionService.initializeConnection(databaseProperties.getConnectionString())
            val sql = "SELECT beoordeling FROM review WHERE restaurant_id=?"
            val stmt = PreparedStatementBuilder(connectionService,sql)
                    .setInt(id)
                    .build()
            stmt.executeQuery()
        } catch(e: SQLException){
            throw DatabaseConnectionException()
        }
    }
}
