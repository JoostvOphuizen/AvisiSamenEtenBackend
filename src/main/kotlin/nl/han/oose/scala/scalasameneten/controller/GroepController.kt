package nl.han.oose.scala.scalasameneten.controller

import nl.han.oose.scala.scalasameneten.dto.restaurant.GroepDTO
import nl.han.oose.scala.scalasameneten.dto.restaurant.GroepenDTO
import nl.han.oose.scala.scalasameneten.service.groep.GroepService
import org.springframework.context.annotation.ComponentScan
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/groep")
@ComponentScan("nl.han.oose.scala.scalasameneten.service.groep")
@CrossOrigin
class GroepController(private val groepService: GroepService) {
    @PostMapping
    fun createGroup(@RequestBody groupRequest: GroepDTO): ResponseEntity<String> = groepService.slaGroepOp(groupRequest)
    @GetMapping
    fun getGroup(): ResponseEntity<GroepenDTO> = groepService.haalGroepenOp()
}
