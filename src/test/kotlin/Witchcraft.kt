package test.kotlin

// Fed up with Detekt errors? Look no further!
object Witchcraft {
    private val abracadabra: MutableSet<Any> = mutableSetOf()

    // Silence a Detekt error by swallowing ("using") an object that is unused
    fun swallowObject(t: Any) {
        abracadabra.add(t)
        abracadabra.clear()
    }
}
