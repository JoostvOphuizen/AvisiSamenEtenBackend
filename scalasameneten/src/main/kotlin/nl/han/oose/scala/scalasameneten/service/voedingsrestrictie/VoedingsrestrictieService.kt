package nl.han.oose.scala.scalasameneten.service.voedingsrestrictie

import jakarta.inject.Inject
import jakarta.ws.rs.core.Response
import nl.han.oose.scala.scalasameneten.datasource.voedingsrestrictie.VoedingsrestrictieDAO
import nl.han.oose.scala.scalasameneten.dto.voedingsrestrictie.VoedingsrestrictieDTO
import nl.han.oose.scala.scalasameneten.dto.voorkeur.VoorkeurDTO
import org.springframework.http.ResponseEntity

class VoedingsrestrictieService {
    private var voedingsrestrictiesDAO: VoedingsrestrictieDAO? = null

    fun getVoedingsrestricties(): ResponseEntity<ArrayList<VoedingsrestrictieDTO>> {
        return ResponseEntity.ok(voedingsrestrictiesDAO!!.makeRestrictiesDTO())
    }
    fun getAlleAllergieen(): Response {
        return Response.status(200).entity(voedingsrestrictiesDAO!!.getAlleAllergieen()).build()
    }
    fun getAlleGeloof(): Response {
        return Response.status(200).entity(voedingsrestrictiesDAO!!.getAlleGeloof()).build()
    }
    fun getAlleDieet(): Response {
        return Response.status(200).entity(voedingsrestrictiesDAO!!.getAlleDieet()).build()
    }
    fun getVoedingsrestrictiesVanGebruiker(gebruiker: Int): Response {
        return Response.status(200).entity(voedingsrestrictiesDAO!!.getGebruikersRestricties(gebruiker)).build()
    }
    fun gebruikersAllergieToevoegen(gebruiker: Int,allergie: String): Response {
        voedingsrestrictiesDAO!!.gebruikersAllergieToevoegen(gebruiker,allergie)
        return Response.status(200).build()
    }
    fun gebruikersGeloofToevoegen(gebruiker: Int,geloof: String): Response {
        voedingsrestrictiesDAO!!.gebruikersGeloofToevoegen(gebruiker,geloof)
        return Response.status(200).build()
    }
    fun gebruikersDieetToevoegen(gebruiker: Int,dieet: String): Response {
        voedingsrestrictiesDAO!!.gebruikersDieetToevoegen(gebruiker,dieet)
        return Response.status(200).build()
    }
    fun allergieBestaat(allergie: String): Response {
        return Response.status(200).entity(voedingsrestrictiesDAO!!.allergieBestaat(allergie)).build()
    }
    fun geloofBestaat(geloof: String): Response {
        return Response.status(200).entity(voedingsrestrictiesDAO!!.geloofBestaat(geloof)).build()
    }
    fun dieetBestaat(dieet: String): Response {
        return Response.status(200).entity(voedingsrestrictiesDAO!!.dieetBestaat(dieet)).build()
    }
    fun gebruikersAllergieVerwijderen(gebruiker: Int,allergie: String): Response {
        voedingsrestrictiesDAO!!.gebruikersAllergieVerwijderen(gebruiker,allergie)
        return Response.status(200).build()
    }
    fun gebruikersGeloofVerwijderen(gebruiker: Int,geloof: String): Response {
        voedingsrestrictiesDAO!!.gebruikersGeloofVerwijderen(gebruiker,geloof)
        return Response.status(200).build()
    }
    fun gebruikersDieetVerwijderen(gebruiker: Int,dieet: String): Response {
        voedingsrestrictiesDAO!!.gebruikersAllergieVerwijderen(gebruiker,dieet)
        return Response.status(200).build()
    }
    @Inject
    fun setVoedingsrestrictiesDAO(voedingsrestrictiesDAO: VoedingsrestrictieDAO) {
        this.voedingsrestrictiesDAO = voedingsrestrictiesDAO
    }
}