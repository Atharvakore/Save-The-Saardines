package de.unisaarland.cs.se.selab.logger

import java.io.PrintStream

/** Backend log for Logger */
class BackedLogOutputStream(private val output: PrintStream) : LogOutputStream {
    override fun write(s: String) {
        output.println(s)
    }

    override fun format(format: String, vararg args: Any) {
        output.printf(format + "\n", *args)
    }
}
