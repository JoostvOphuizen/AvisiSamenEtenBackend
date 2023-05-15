package nl.han.oose.scala.scalasameneten.datasource.restaurant

import nl.han.oose.scala.scalasameneten.datasource.PreparedStatementBuilder
import nl.han.oose.scala.scalasameneten.datasource.connection.ConnectionService
import nl.han.oose.scala.scalasameneten.datasource.connection.DatabaseProperties
import nl.han.oose.scala.scalasameneten.datasource.exceptions.DatabaseConnectionException
import nl.han.oose.scala.scalasameneten.dto.restaurant.GroepDTO
import nl.han.oose.scala.scalasameneten.dto.restaurant.RestaurantDTO
import nl.han.oose.scala.scalasameneten.dto.restaurant.VoorstelDTO
import nl.han.oose.scala.scalasameneten.dto.voedingsrestrictie.VoedingsrestrictieDTO
import nl.han.oose.scala.scalasameneten.dto.voedingsrestrictie.VoedingsrestrictiesDTO
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

    fun getRestaurant(id: Int): ResultSet {
        return try {
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            val stmt = PreparedStatementBuilder(connectionService,"select * from RESTAURANT where restaurant_id = ?")
                .setInt(id)
                .build()
            stmt.executeQuery()
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }


    fun getRestaurantenZonderRestricties(lidId: Int): ResultSet {
        return try {
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
//            val stmt = PreparedStatementBuilder(connectionService,"select RESTAURANT_ID from RESTAURANT except select RESTAURANT_ID from VOEDINGSRESTRICTIE_IN_RESTAURANT where RESTRICTIE_NAAM in ( select v.RESTRICTIE_NAAM from VOEDINGSRESTRICTIE v inner join GEBRUIKER_HEEFT_VOEDINGSRESTRICTIE gv on v.RESTRICTIE_NAAM = gv.RESTRICTIE_NAAM where gv.GEBRUIKER_ID = ? group by v.RESTRICTIE_NAAM)")
            val stmt = PreparedStatementBuilder(connectionService,"select * from RESTAURANT where RESTAURANT_ID not in(select RESTAURANT_ID from VOEDINGSRESTRICTIE_IN_RESTAURANT where RESTRICTIE_NAAM in (select v.RESTRICTIE_NAAM from VOEDINGSRESTRICTIE v inner join GEBRUIKER_HEEFT_VOEDINGSRESTRICTIE gv on v.RESTRICTIE_NAAM = gv.RESTRICTIE_NAAM where gv.GEBRUIKER_ID = ? group by v.RESTRICTIE_NAAM))")
                .setInt(lidId)
                .build()
            stmt.executeQuery()
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }

    fun getRestaurantVoorstel(geselecteerdeGebruikers: GroepDTO, restaurants: MutableList<Int>): ResultSet {
        return try {

            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            var sql = "with matches as ( " +
                    "select distinct rv.RESTAURANT_ID, SUM(COUNT(vg.voorkeur_naam)) over (PARTITION by restaurant_id) as matchendevoorkeuren " +
                    "from VOORKEUR_VAN_GEBRUIKER vg " +
                    "inner join RESTAURANT_HEEFT_VOORKEUR rv " +
                    "on vg.VOORKEUR_NAAM = rv.VOORKEUR_NAAM " +
                    "where ( "

            for(lid in geselecteerdeGebruikers.leden!!) {
                sql += "GEBRUIKER_ID = ? or "
            }
            sql = sql.substring(0, sql.length - 3)

            sql += ") and ("

            for(restaurant in restaurants) {
                sql += "RESTAURANT_ID = ? or "
            }
            sql = sql.substring(0, sql.length - 3)

            sql += ")group by vg.VOORKEUR_NAAM, RESTAURANT_ID) " +
                    "select r.RESTAURANT_ID, RESTAURANT_NAAM, POSTCODE, STRAATNAAM, HUISNUMMER, STRING_AGG(RESTRICTIE_NAAM, ',') as RESTRICTIES " +
                    "from RESTAURANT r " +
                    "inner join matches m " +
                    "on r.RESTAURANT_ID = m.RESTAURANT_ID " +
                    "left join VOEDINGSRESTRICTIE_IN_RESTAURANT vr " +
                    "on r.RESTAURANT_ID = vr.RESTAURANT_ID " +
                    "where matchendevoorkeuren = (select MAX(matchendevoorkeuren) from matches) " +
                    "group by r.RESTAURANT_ID, RESTAURANT_NAAM, POSTCODE, STRAATNAAM, HUISNUMMER, matchendevoorkeuren " +
                    "order by matchendevoorkeuren desc"

            val stmt = PreparedStatementBuilder(connectionService, sql)
            for(lid in geselecteerdeGebruikers.leden!!) {
                stmt.setInt(lid)
            }
            for(restaurant in restaurants) {
                stmt.setInt(restaurant)
            }
            stmt.build().executeQuery()
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }

    fun makeRestaurantDTO(restaurantId: Int): RestaurantDTO {
        return try {
            val restaurantresult = getRestaurant(restaurantId)
            if (restaurantresult.next()) {
                val id = restaurantresult.getInt("restaurant_id")
                val naam = restaurantresult.getString("restaurant_naam")
                val postcode = restaurantresult.getString("postcode")
                val straatnaam = restaurantresult.getString("straatnaam")
                val huisnummer = restaurantresult.getInt("huisnummer")
                RestaurantDTO(id, naam, postcode, straatnaam, huisnummer)
            } else {
                throw DatabaseConnectionException()
            }
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }


    fun getRestricties(restaurantId: Int): ResultSet{
        return try {
            connectionService!!.initializeConnection(databaseProperties!!.getConnectionString())
            val stmt = PreparedStatementBuilder(connectionService,"SELECT * FROM VOEDINGSRESTRICTIE_IN_RESTAURANT WHERE restaurant_id=?")
                .setInt(restaurantId)
                .build()
            stmt.executeQuery()
        } catch (e: SQLException) {
            println(e)
            throw DatabaseConnectionException()
        }
    }

    fun generateVoedingsrestrictiesDTO(restaurantId: Int): VoedingsrestrictiesDTO {
        return try {
            val restricties: ResultSet = getRestricties(restaurantId)
            var voedingsrestricties = ArrayList<VoedingsrestrictieDTO>()
            while (restricties != null && restricties.next()) {
                voedingsrestricties.add(VoedingsrestrictieDTO(restricties.getString("restrictie_naam"), restricties.getString("type")))
            }
            VoedingsrestrictiesDTO(voedingsrestricties)
        } catch (e: SQLException) {
            throw DatabaseConnectionException()
        }
    }


}