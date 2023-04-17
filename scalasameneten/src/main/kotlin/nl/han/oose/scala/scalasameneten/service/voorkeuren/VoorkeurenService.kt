package nl.han.oose.scala.scalasameneten.service.voorkeuren

import jakarta.inject.Inject
import jakarta.ws.rs.core.Response
import nl.han.oose.scala.scalasameneten.datasource.voorkeuren.VoorkeurenDAO


class VoorkeurenService {
    private var voorkeurenDAO: VoorkeurenDAO? = null

    fun getVoorkeuren(): Response {
        return Response.status(200).entity(voorkeurenDAO!!.makeVoorkeurenDTO()).build()
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

    @Inject
    fun setVoorkeurenDAO(voorkeurenDao: VoorkeurenDAO) {
        this.voorkeurenDAO = voorkeurenDao
    }
}