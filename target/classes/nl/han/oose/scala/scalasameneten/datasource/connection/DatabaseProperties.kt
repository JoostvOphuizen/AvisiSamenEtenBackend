package nl.han.oose.scala.scalasameneten.datasource.connection


import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Component
import java.io.IOException
import java.util.*

@Component
class DatabaseProperties {
    private val properties: Properties = Properties()


    init {
        try {
            javaClass.classLoader.getResourceAsStream("application.properties").use { inputStream ->
                properties.load(inputStream)
                Class.forName(driver)
            }
        } catch (e: IOException) {
            throw RuntimeException(e)
        } catch (e: ClassNotFoundException) {
            throw RuntimeException(e)
        }
    }

    val driver: String
        get() = properties.getProperty("driver")
    fun getConnectionString(): String?{
        return properties.getProperty("connectionstring")
        }
}

