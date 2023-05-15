package nl.han.oose.scala.scalasameneten.controller

import nl.han.oose.scala.scalasameneten.dto.groep.GroepDTO
import nl.han.oose.scala.scalasameneten.service.groep.GroepService
import org.modelmapper.ModelMapper
import org.springframework.context.annotation.ComponentScan
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/groep")
@ComponentScan("nl.han.oose.scala.scalasameneten.service.groep")
@CrossOrigin
class GroepController(private val groupService: GroupService) {
    @PostMapping
    fun createGroup(@RequestBody groupRequest: GroupRequestDto) {
        groupService.createGroup(groupRequest)
        // Return appropriate response or status code
    }
}
