package nl.han.oose.scala.scalasameneten.datasource.connection

import java.io.IOException
import java.util.*


class DatabaseProperties {
    private val properties: Properties

    init {
        properties = Properties()
        try {
            javaClass.classLoader.getResourceAsStream("database.properties").use { inputStream ->
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

