package nl.han.oose.scala.scalasameneten.service.voorkeur

import jakarta.inject.Inject
import jakarta.json.JsonArray
import jakarta.ws.rs.core.Response
import nl.han.oose.scala.scalasameneten.datasource.voorkeur.VoorkeurDAO
import nl.han.oose.scala.scalasameneten.dto.voorkeur.VoorkeurDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.ComponentScan
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.sql.ResultSet

@Service
@Component
@ComponentScan("nl.han.oose.scala.scalasameneten.datasource.voorkeur")
class VoorkeurService(private val voorkeurDAO: VoorkeurDAO) {

    fun getVoorkeuren(): ResponseEntity<VoorkeurDTO> {
        return ResponseEntity.ok(voorkeurDAO!!.makeVoorkeurenDTO())
    }

}