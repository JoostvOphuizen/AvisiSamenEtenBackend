package nl.han.oose.scala.scalasameneten.datasource.util

import java.io.IOException
import java.io.LineNumberReader
import java.io.PrintWriter
import java.io.Reader
import java.sql.Connection
import java.sql.SQLException

/*
* Slightly modified version of the com.ibatis.common.jdbc.ScriptRunner class
* from the iBATIS Apache project. Only removed dependency on Resource class
* and a constructor
*/
/*
 *  Copyright 2004 Clinton Begin
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */ /**
 * Tool to run database scripts
 */
class ScriptRunner
/**
 * Default constructor
 */(private val connection: Connection, private val autoCommit: Boolean,
    private val stopOnError: Boolean) {
    private var logWriter: PrintWriter? = PrintWriter(System.out)
    private var errorLogWriter: PrintWriter? = PrintWriter(System.err)
    private var delimiter = DEFAULT_DELIMITER
    private var fullLineDelimiter = false
    fun setDelimiter(delimiter: String, fullLineDelimiter: Boolean) {
        this.delimiter = delimiter
        this.fullLineDelimiter = fullLineDelimiter
    }

    /**
     * Setter for logWriter property
     *
     * @param logWriter
     * - the new value of the logWriter property
     */
    fun setLogWriter(logWriter: PrintWriter?) {
        this.logWriter = logWriter
    }

    /**
     * Setter for errorLogWriter property
     *
     * @param errorLogWriter
     * - the new value of the errorLogWriter property
     */
    fun setErrorLogWriter(errorLogWriter: PrintWriter?) {
        this.errorLogWriter = errorLogWriter
    }

    /**
     * Runs an SQL script (read in using the Reader parameter)
     *
     * @param reader
     * - the source of the script
     */
    @Throws(IOException::class, SQLException::class)
    fun runScript(reader: Reader) {
        try {
            val originalAutoCommit = connection.autoCommit
            try {
                if (originalAutoCommit != autoCommit) {
                    connection.autoCommit = autoCommit
                }
                runScript(connection, reader)
            } finally {
                connection.autoCommit = originalAutoCommit
            }
        } catch (e: IOException) {
            throw e
        } catch (e: SQLException) {
            throw e
        } catch (e: Exception) {
            throw RuntimeException("Error running script.  Cause: $e", e)
        }
    }

    /**
     * Runs an SQL script (read in using the Reader parameter) using the
     * connection passed in
     *
     * @param conn
     * - the connection to use for the script
     * @param reader
     * - the source of the script
     * @throws SQLException
     * if any SQL errors occur
     * @throws IOException
     * if there is an error reading from the Reader
     */
    @Throws(IOException::class, SQLException::class)
    private fun runScript(conn: Connection, reader: Reader) {
        var command: StringBuffer? = null
        try {
            val lineReader = LineNumberReader(reader)
            var line: String? = null
            while (lineReader.readLine().also { line = it } != null) {
                if (command == null) {
                    command = StringBuffer()
                }
                val trimmedLine = line!!.trim { it <= ' ' }
                if (trimmedLine.startsWith("--")) {
                    println(trimmedLine)
                } else if (trimmedLine.length < 1
                        || trimmedLine.startsWith("//")) {
                    // Do nothing
                } else if (trimmedLine.length < 1
                        || trimmedLine.startsWith("--")) {
                    // Do nothing
                } else if (!fullLineDelimiter
                        && trimmedLine.endsWith(delimiter)
                        || fullLineDelimiter && trimmedLine == delimiter) {
                    command.append(line!!.substring(0, line!!
                            .lastIndexOf(delimiter)))
                    command.append(" ")
                    val statement = conn.createStatement()
                    println(command)
                    var hasResults = false
                    if (stopOnError) {
                        hasResults = statement.execute(command.toString())
                    } else {
                        try {
                            statement.execute(command.toString())
                        } catch (e: SQLException) {
                            e.fillInStackTrace()
                            printlnError("Error executing: $command")
                            printlnError(e)
                        }
                    }
                    if (autoCommit && !conn.autoCommit) {
                        conn.commit()
                    }
                    val rs = statement.resultSet
                    if (hasResults && rs != null) {
                        val md = rs.metaData
                        val cols = md.columnCount
                        for (i in 0 until cols) {
                            val name = md.getColumnLabel(i)
                            print(name + "\t")
                        }
                        println("")
                        while (rs.next()) {
                            for (i in 0 until cols) {
                                val value = rs.getString(i)
                                print(value + "\t")
                            }
                            println("")
                        }
                    }
                    command = null
                    try {
                        statement.close()
                    } catch (e: Exception) {
                        // Ignore to workaround a bug in Jakarta DBCP
                    }
                    Thread.yield()
                } else {
                    command.append(line)
                    command.append(" ")
                }
            }
            if (!autoCommit) {
                conn.commit()
            }
        } catch (e: SQLException) {
            e.fillInStackTrace()
            printlnError("Error executing: $command")
            printlnError(e)
            throw e
        } catch (e: IOException) {
            e.fillInStackTrace()
            printlnError("Error executing: $command")
            printlnError(e)
            throw e
        } finally {
            //conn.rollback()
            flush()
        }
    }

    private fun print(o: Any) {
        if (logWriter != null) {
            kotlin.io.print(o)
        }
    }

    private fun println(o: Any) {
        if (logWriter != null) {
            logWriter!!.println(o)
        }
    }

    private fun printlnError(o: Any) {
        if (errorLogWriter != null) {
            errorLogWriter!!.println(o)
        }
    }

    private fun flush() {
        if (logWriter != null) {
            logWriter!!.flush()
        }
        if (errorLogWriter != null) {
            errorLogWriter!!.flush()
        }
    }

    companion object {
        private const val DEFAULT_DELIMITER = ";"
    }
}