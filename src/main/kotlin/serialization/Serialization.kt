package serialization

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class B(val v: String)

@Serializable
data class Data(val a: Int, val b: B)


fun main() {
    val json = Json.encodeToString(Data(42, B("str")))
    println(json)
    val obj = Json.decodeFromString<Data>("""{"a":42, "b":{"v":"str"}}""")
    println(obj)
}