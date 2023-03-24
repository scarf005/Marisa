package marisa

const val MOD_ID = "MarisaContinued"

fun modId(s: String) = "$MOD_ID:$s"

fun String.toModId() = modId(this)
