package de.unisaarland.cs.se.selab.logger

import java.io.PrintWriter

class BackedLogOutputStream(private val output: PrintWriter) : LogOutputStream {
    override fun write(s: String) {
        output.println(s)
    }

    override fun format(format: String, vararg args: Any) {
        output.printf(format + "\n", *args)
    }
}
