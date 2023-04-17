package nl.han.oose.scala.scalasameneten.service.voorkeuren

import jakarta.inject.Inject
import jakarta.ws.rs.core.Response
import nl.han.oose.scala.scalasameneten.datasource.voorkeuren.VoorkeurenDAO


class VoorkeurenService {
    private var voorkeurenDAO: VoorkeurenDAO? = null


    fun getAlleVoorkeuren(): Response? {
        return Response.status(200).entity(voorkeurenDAO!!.getAlleVoorkeuren()).build()
    }

    fun getVoorkeurenVanGebruiker(gebruiker: Int): Response? {
        return Response.status(200).entity(voorkeurenDAO!!.getGebruikersVoorkeuren(gebruiker)).build()
    }

    @Inject
    fun setVoorkeurenDAO(voorkeurenDao: VoorkeurenDAO) {
        this.voorkeurenDAO = voorkeurenDao
    }
}