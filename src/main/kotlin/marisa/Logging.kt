package marisa

import org.apache.logging.log4j.Logger


fun Logger.runInfo(name: String, block: () -> Any) {
    info("beginning $name".toTitle())
    block()
    info("finished $name".toTitle())
}

private fun String.toTitle(fill: String = "=", amount: Int = 24) =
    fill.repeat(amount).let { "<<$it $this $it>>" }
