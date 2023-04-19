package nl.han.oose.scala.scalasameneten.service.voedingsrestrictie

import nl.han.oose.scala.scalasameneten.datasource.voedingsrestrictie.VoedingsrestrictieDAO
import nl.han.oose.scala.scalasameneten.dto.voedingsrestrictie.VoedingsrestrictiesDTO
import org.springframework.http.ResponseEntity

class VoedingsrestrictieService {
    private var voedingsrestrictiesDAO: VoedingsrestrictieDAO? = null

    fun getVoedingsrestricties(): ResponseEntity<ArrayList<VoedingsrestrictiesDTO>> {
        return ResponseEntity.ok(voedingsrestrictiesDAO!!.makeRestrictiesDTO())
    }
}