package de.unisaarland.cs.se.selab.logger

/** Target output stream of a logger. */
interface LogOutputStream {
    /** Write an unformatted message to a stream */
    fun write(s: String)
    /** Write a formatted message to a stream */
    fun format(format: String, vararg args: Any)
}
