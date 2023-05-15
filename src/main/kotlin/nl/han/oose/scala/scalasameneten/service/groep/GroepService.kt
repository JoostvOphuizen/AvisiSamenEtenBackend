package nl.han.oose.scala.scalasameneten.service.groep
import nl.han.oose.scala.scalasameneten.datasource.groep.GroepDAO
import nl.han.oose.scala.scalasameneten.dto.groep.GroepDTO
import org.springframework.context.annotation.ComponentScan
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.util.*

@Service
@Component
@ComponentScan("nl.han.oose.scala.scalasameneten.datasource.groep")
class GroepService(private val groepDAO: GroepDAO) {

}