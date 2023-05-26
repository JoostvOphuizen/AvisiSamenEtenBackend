package nl.han.oose.scala.scalasameneten.service.uitnodiging

import nl.han.oose.scala.scalasameneten.datasource.uitnodiging.UitnodigingDAO
import nl.han.oose.scala.scalasameneten.dto.gebruiker.GebruikerDTO
import nl.han.oose.scala.scalasameneten.dto.gebruiker.GebruikersDTO
import nl.han.oose.scala.scalasameneten.dto.uitnodiging.UitgenodigdenDTO
import nl.han.oose.scala.scalasameneten.dto.uitnodiging.UitnodigingDTO
import nl.han.oose.scala.scalasameneten.dto.voedingsrestrictie.VoedingsrestrictieDTO
import nl.han.oose.scala.scalasameneten.dto.voorkeur.VoorkeurDTO
import org.springframework.context.annotation.ComponentScan
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.util.*
import kotlin.collections.ArrayList

@Service
@Component
@ComponentScan("nl.han.oose.scala.scalasameneten.datasource.uitnodiging")
class UitnodigingService (private val uitnodigingDAO: UitnodigingDAO) {

    private final val min = 1000
    private final val max = 10000

    fun createUitnodiging(gebruikerToken: String): ResponseEntity<UitnodigingDTO> {
        var uitnodigingToken = generateToken()
        while (uitnodigingDAO.checkIfTokenExists(uitnodigingToken)) {
            uitnodigingToken = generateToken()
        }
        uitnodigingDAO.createUitnodiging(uitnodigingToken, gebruikerToken)
        return ResponseEntity.ok(UitnodigingDTO(uitnodigingToken))
    }

    fun getUitgenodigden(uitnodigingToken: String, gebruikerToken: String): ResponseEntity<UitgenodigdenDTO> {
        val gebruikerID = uitnodigingDAO.getGebruikerID(gebruikerToken)
        val restaurantID = uitnodigingDAO.getRestaurantID(uitnodigingToken)
        val host = getHostAndMapToGebruikerDTO(uitnodigingToken);
        val uitgenodigden = getUitgenodigdenAndMapToGebruikersDTO(uitnodigingToken)
        return ResponseEntity.ok(UitgenodigdenDTO(gebruikerID, restaurantID, host, uitgenodigden));
    }

    private fun getUitgenodigdenAndMapToGebruikersDTO(uitnodigingToken: String): GebruikersDTO {
        val uitgenodigdenResultSet = uitnodigingDAO.getUitgenodigden(uitnodigingToken)
        val uitgenodigden = ArrayList<GebruikerDTO>()
        while (uitgenodigdenResultSet.next()) {

            val voedingsrestrictiesArraylist = ArrayList<VoedingsrestrictieDTO>()
            if(uitgenodigdenResultSet.getString("restricties") != null) {
                val voedingsrestricties = uitgenodigdenResultSet.getString("restricties").split(",").toTypedArray()
                for (voedingsrestrictie in voedingsrestricties) {
                    voedingsrestrictiesArraylist.add(VoedingsrestrictieDTO(voedingsrestrictie))
                }
            }
            val voorkeurenArrayList = ArrayList<VoorkeurDTO>()
            if (uitgenodigdenResultSet.getString("voorkeuren") != null) {
                val voorkeuren = uitgenodigdenResultSet.getString("voorkeuren").split(",").toTypedArray()
                for (voorkeur in voorkeuren) {
                    voorkeurenArrayList.add(VoorkeurDTO(voorkeur))
                }
            }
            uitgenodigden.add(GebruikerDTO(uitgenodigdenResultSet.getInt("gebruiker_id"), uitgenodigdenResultSet.getString("gebruikersnaam"), uitgenodigdenResultSet.getString("foto"), voorkeurenArrayList, voedingsrestrictiesArraylist))
        }
        return GebruikersDTO(uitgenodigden)
    }

    private fun getHostAndMapToGebruikerDTO(uitnodigingToken: String): GebruikerDTO {
        val hostResultset = uitnodigingDAO.getHost(uitnodigingToken)
        while (hostResultset.next()) {
            val hostArrayListVoedingsRestricties = ArrayList<VoedingsrestrictieDTO>()
            if(hostResultset.getString("restricties") != null) {
                val hostVoedingsRestricties = hostResultset.getString("restricties").split(",").toTypedArray()
                for (voedingsrestrictie in hostVoedingsRestricties) {
                    hostArrayListVoedingsRestricties.add(VoedingsrestrictieDTO(voedingsrestrictie))
                }
            }
            val hostArrayListVoorkeuren = ArrayList<VoorkeurDTO>()
            if (hostResultset.getString("voorkeuren") != null) {
                val hostVoorkeuren = hostResultset.getString("voorkeuren").split(",").toTypedArray()
                for (voorkeur in hostVoorkeuren) {
                    hostArrayListVoorkeuren.add(VoorkeurDTO(voorkeur))
                }
            }else{
                hostArrayListVoorkeuren.add(VoorkeurDTO("Geen"))
            }
            return  GebruikerDTO(hostResultset.getInt("gebruiker_id"), hostResultset.getString("gebruikersnaam"), hostResultset.getString("foto"), hostArrayListVoorkeuren, hostArrayListVoedingsRestricties)
        }
        return GebruikerDTO()
    }

    fun accepteerUitnodiging(uitnodigingToken: String, gebruikerToken: String): ResponseEntity<String> {
        uitnodigingDAO.accepteerUitnodiging(uitnodigingToken, gebruikerToken)
        return ResponseEntity.ok("Uitnodiging geaccepteerd")
    }

    fun updateRestaurant(uitnodigingToken: String, restaurant: Int): ResponseEntity<String> {
        uitnodigingDAO.updateRestaurant(uitnodigingToken, restaurant)
        return ResponseEntity.ok("Restaurant geupdate")
    }

    fun generateToken(): String {
        var token = ""
        for (i in 0..2) {
            val r = Random()
            token += r.nextInt(max - min) + min
            if (i < 2) {
                token += "-"
            }
        }
        return token
    }

}