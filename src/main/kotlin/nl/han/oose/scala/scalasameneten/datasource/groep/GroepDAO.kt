package nl.han.oose.scala.scalasameneten.datasource.groep

import nl.han.oose.scala.scalasameneten.datasource.PreparedStatementBuilder
import nl.han.oose.scala.scalasameneten.datasource.connection.ConnectionService
import nl.han.oose.scala.scalasameneten.datasource.connection.DatabaseProperties
import nl.han.oose.scala.scalasameneten.datasource.exceptions.DatabaseConnectionException
import org.springframework.context.annotation.ComponentScan
import org.springframework.stereotype.Component
import java.sql.ResultSet
import java.sql.SQLException
import org.springframework.stereotype.Repository

@Component
@ComponentScan("nl.han.oose.scala.scalasameneten.datasource.connection")
class GroepDAO(private val connectionService: ConnectionService, private val databaseProperties: DatabaseProperties) {

}