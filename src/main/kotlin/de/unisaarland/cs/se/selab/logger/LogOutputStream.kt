package de.unisaarland.cs.se.selab.logger

interface LogOutputStream {
    fun write(s: String)
    fun format(format: String, vararg args: Any)
}
