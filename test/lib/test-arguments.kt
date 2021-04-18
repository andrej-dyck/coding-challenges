package lib

import org.junit.jupiter.params.converter.*

class IntArrayArg : TypedArgumentConverter<String, Array<Int>>(
    String::class.java, Array<Int>::class.java
) {
    override fun convert(source: String?) =
        source?.toArray { toInt() } ?: emptyArray()
}

class IntMatrixArg : TypedArgumentConverter<String, Array<Array<Int>>>(
    String::class.java, Array<Array<Int>>::class.java
) {
    override fun convert(source: String?) =
        source?.toArray { toArray { toInt() } } ?: emptyArray()
}

class StringArrayArg : TypedArgumentConverter<String, Array<String>>(
    String::class.java, Array<String>::class.java
) {
    override fun convert(source: String?) =
        source?.toArray { this.removeSurrounding("'") } ?: emptyArray()
}

inline fun <reified T> String.toArray(
    parseElement: String.() -> T
): Array<T> =
    if (matches("\\[\\s*]".toRegex())) emptyArray()
    else this.require { it.startsWith('[') && it.endsWith(']') }
        .removeSurrounding("[", "]")
        .let {
            if (it.any { c -> c in "[]" }) // nested array
                it.split("],").map { e -> if (e.endsWith(']')) e else "$e]" }
            else
                it.split(',')
        }
        .map { it.trim() }
        .filter { it.isNotEmpty() }
        .map(parseElement)
        .toTypedArray()