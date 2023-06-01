package nl.han.oose.scala.scalasameneten.datasource

import nl.han.oose.scala.scalasameneten.datasource.connection.ConnectionService
import java.sql.PreparedStatement


class PreparedStatementBuilder(private val connectionService: ConnectionService, private val sql: String) {
    private val params = mutableListOf<Any>()

    fun setString(value: String): PreparedStatementBuilder {
        params.add(value)
        return this
    }

    fun setInt(value: Int): PreparedStatementBuilder {
        params.add(value)
        return this
    }

    fun setBoolean(value: Boolean): PreparedStatementBuilder {
        params.add(value)
        return this
    }

    fun setParam(value: Any): PreparedStatementBuilder {
        params.add(value)
        return this
    }

    private fun generateSqlWithPlaceholders(sql: String, paramsCount: Int): String {
        val placeholders = List(paramsCount) { "?" }.joinToString(", ")
        return "$sql ($placeholders)"
    }

    fun build(): PreparedStatement {
        val preparedStatement = connectionService.getConnection()!!.prepareStatement(sql)
        var i = 1
        for (param in params) {
            preparedStatement.setObject(i++, param)
        }
        return preparedStatement
    }
}

