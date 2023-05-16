package nl.han.oose.scala.scalasameneten.controller

import nl.han.oose.scala.scalasameneten.dto.restaurant.GroepDTO
import nl.han.oose.scala.scalasameneten.service.groep.GroepService
import org.springframework.context.annotation.ComponentScan
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/groep")
@ComponentScan("nl.han.oose.scala.scalasameneten.service.groep")
@CrossOrigin
class GroepController(private val groepService: GroepService) {
    @PostMapping
    fun createGroup(@RequestBody groupRequest: GroepDTO) {
        println("roept die aan?")
        groepService.slaGroepOp(groupRequest)


    }
}
