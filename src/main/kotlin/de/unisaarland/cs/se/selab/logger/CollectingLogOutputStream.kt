package de.unisaarland.cs.se.selab.logger

class CollectingLogOutputStream: LogOutputStream {
    private val messages = mutableListOf<String>()

    override fun write(s: String) {
        messages.add(s)
    }

    override fun format(format: String, vararg args: Any) {
        messages.add(String.format(format, *args))
    }

    fun getMessages(): List<String> {
        return messages
    }
}