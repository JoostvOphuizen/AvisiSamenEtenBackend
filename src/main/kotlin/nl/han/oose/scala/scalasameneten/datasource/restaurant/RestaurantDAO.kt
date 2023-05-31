package nl.han.oose.scala.scalasameneten.datasource.restaurant

import nl.han.oose.scala.scalasameneten.datasource.PreparedStatementBuilder
import nl.han.oose.scala.scalasameneten.datasource.connection.ConnectionService
import nl.han.oose.scala.scalasameneten.datasource.connection.DatabaseProperties
import nl.han.oose.scala.scalasameneten.datasource.exceptions.DatabaseConnectionException
import nl.han.oose.scala.scalasameneten.dto.restaurant.ReviewDTO
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
            val sql = "SELECT restaurant_id, restaurant_naam, postcode, straatnaam, huisnummer, link, foto FROM restaurant WHERE restaurant_id=?"

            connectionService.initializeConnection(databaseProperties.getConnectionString())
            val stmt = PreparedStatementBuilder(connectionService, sql)
                    .setInt(id)
                    .build()
            stmt.executeQuery()
        } catch(e: SQLException){
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
    }}