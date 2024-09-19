package de.unisaarland.cs.se.selab.logger

import java.io.PrintWriter

/** Backend log for Logger */
class BackedLogOutputStream(private val output: PrintWriter) : LogOutputStream {
    override fun write(s: String) {
        output.use { out -> out.println(s) }
    }

    override fun format(format: String, vararg args: Any) {
        output.printf(format + "\n", *args)
    }
}
