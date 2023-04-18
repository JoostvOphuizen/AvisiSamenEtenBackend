package nl.han.oose.scala.scalasameneten.service.voorkeur

import jakarta.inject.Inject
import jakarta.ws.rs.core.Response
import nl.han.oose.scala.scalasameneten.datasource.voorkeur.VoorkeurDAO
import nl.han.oose.scala.scalasameneten.dto.voorkeur.VoorkeurDTO
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class VoorkeurService(private val voorkeurenDAO: VoorkeurDAO) {

    fun getVoorkeuren(): ResponseEntity<VoorkeurDTO> {
        return ResponseEntity.ok(voorkeurenDAO!!.makeVoorkeurenDTO())
    }

    fun getAlleVoorkeuren(): Response {
        return Response.status(200).entity(voorkeurenDAO!!.getAlleVoorkeuren()).build()
    }

    fun getVoorkeurenVanGebruiker(gebruiker: Int): Response {
        return Response.status(200).entity(voorkeurenDAO!!.getGebruikersVoorkeuren(gebruiker)).build()
    }

    fun gebruikersVoorkeurToevoegen(gebruiker: Int,voorkeur: String): Response{
        voorkeurenDAO!!.gebruikersVoorkeurenToevoegen(gebruiker,voorkeur)
        return Response.status(200).build()
    }

    fun voorkeurBestaat(voorkeur: String): Response {
        return Response.status(200).entity(voorkeurenDAO!!.voorkeurBestaat(voorkeur)).build()
    }
    fun gebruikersVoorkeurVerwijderen(gebruiker: Int,voorkeur: String): Response{
        voorkeurenDAO!!.gebruikersVoorkeurVerwijderen(gebruiker,voorkeur)
        return Response.status(200).build()
    }

}