package de.unisaarland.cs.se.selab.logger

import java.io.PrintWriter

/**
 * A stub of the logger class. TODO: Actually fill this in.
 */
class Logger {
    companion object {
        var output: LogOutputStream = BackedLogOutputStream(PrintWriter(System.out))

        fun log(message: String) {
            output.write(message)
        }
    }
}
