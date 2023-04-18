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
}