package nl.han.oose.scala.scalasameneten.service.groep
import nl.han.oose.scala.scalasameneten.datasource.groep.GroepDAO
import nl.han.oose.scala.scalasameneten.dto.restaurant.GroepDTO
import nl.han.oose.scala.scalasameneten.dto.restaurant.GroepenDTO
import org.springframework.context.annotation.ComponentScan
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.sql.ResultSet
import java.util.*
import kotlin.collections.ArrayList

@Service
@Component
@ComponentScan("nl.han.oose.scala.scalasameneten.datasource.groep")
class GroepService(private val groepDAO: GroepDAO) {

    fun slaGroepOp(groepDTO: GroepDTO): ResponseEntity<String> {
        groepDAO.insertGroep(groepDTO.naam)
        groepDAO.setGroepsleden(groepDTO)
        return ResponseEntity.ok("Great Succes")
    }

    fun haalGroepenOp(): ResponseEntity<GroepenDTO> {

        val groepenResult = groepDAO.getGroepen()
        var groepen = ArrayList<GroepDTO>()

        while (groepenResult.next()) {

            val naam = groepenResult.getString("GROEPNAAM")
            val GroepIdString = groepenResult.getString("USER_IDS")
            val id = GroepIdString?.split(",")?.map { it.toInt() }?.toCollection(ArrayList()) ?: ArrayList()
            val groep = GroepDTO(id,naam)
            groepen.add(groep)
        }
        val groepenDTO = GroepenDTO(groepen)
        return ResponseEntity.ok(groepenDTO)

    }


}