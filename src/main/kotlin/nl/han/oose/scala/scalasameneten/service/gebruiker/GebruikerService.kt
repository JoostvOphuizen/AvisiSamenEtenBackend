package nl.han.oose.scala.scalasameneten.service.gebruiker

import nl.han.oose.scala.scalasameneten.datasource.gebruiker.GebruikerDAO
import nl.han.oose.scala.scalasameneten.dto.gebruiker.GebruikerDTO
import nl.han.oose.scala.scalasameneten.dto.gebruiker.GebruikersDTO
import nl.han.oose.scala.scalasameneten.dto.gebruiker.LoginDTO
import nl.han.oose.scala.scalasameneten.dto.voorkeur.VoorkeurenDTO
import org.springframework.context.annotation.ComponentScan
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.util.*

@Service
@Component
@ComponentScan("nl.han.oose.scala.scalasameneten.datasource.gebruiker")
class GebruikerService(private val gebruikerDAO: GebruikerDAO) {

    fun genereerToken(): String {
        var token = ""
        val MIN = 1000
        val MAX = 10000
        for (i in 0..2) {
            val r = Random()
            token += r.nextInt(MAX - MIN) + MIN
            if (i < 2) {
                token += "-"
            }
        }
        var nieuweToken = true
        val result = gebruikerDAO.getAlleGebruikers()
        while (result.next()){
            if(result.getString("token") == token){
                nieuweToken = false
            }
        }
        if(nieuweToken) {
            return token
        }
        else{
            return genereerToken()
        }
    }

    fun loginGebruiker(login: LoginDTO): ResponseEntity<GebruikerDTO> {
        val id = gebruikerDAO!!.loginGebruiker(login, genereerToken())
        return ResponseEntity.ok(gebruikerDAO!!.makeGebruiker(id))
    }

    fun getGebruikers(): ResponseEntity<GebruikersDTO> {
        return ResponseEntity.ok(gebruikerDAO!!.makeGebruikersDTO())
    }
    fun getGebruiker(id: Int): ResponseEntity<GebruikerDTO> {
        return ResponseEntity.ok(gebruikerDAO!!.makeGebruiker(id))
    }



    fun getGebruikersVoorkeuren(id: Int): ResponseEntity<VoorkeurenDTO> {
        return ResponseEntity.ok(gebruikerDAO!!.makeVoorkeurenDTO(id))
    }

    fun postGebruikersVoorkeuren(id: Int, voorkeurenDTO: VoorkeurenDTO): ResponseEntity<Void> {
        return ResponseEntity.ok(gebruikerDAO!!.setGebruikersVoorkeuren(id, voorkeurenDTO))
    }
}