package marisa

import org.apache.logging.log4j.Logger


fun Logger.runInfo(name: String, block: () -> Any) {
    info("beginning $name".toTitle())
    block()
    info("finished $name")
}

fun String.toTitle(fill: String = "=", amount: Int = 20) =
    fill.repeat(amount).let { "$it $this $it" }
