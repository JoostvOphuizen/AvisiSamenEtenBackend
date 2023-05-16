package nl.han.oose.scala.scalasameneten.service.groep
import nl.han.oose.scala.scalasameneten.datasource.groep.GroepDAO
import nl.han.oose.scala.scalasameneten.dto.restaurant.GroepDTO
import org.springframework.context.annotation.ComponentScan
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.util.*

@Service
@Component
@ComponentScan("nl.han.oose.scala.scalasameneten.datasource.groep")
class GroepService(private val groepDAO: GroepDAO) {

    fun slaGroepOp(groepDTO: GroepDTO){
        groepDAO.insertGroep(groepDTO.naam)
        groepDAO.setGroepsleden(groepDTO)
    }
}