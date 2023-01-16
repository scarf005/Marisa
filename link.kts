#!/bin/env kscript

import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.*

val jar = Path("build/libs") / "MarisaContinued.jar"
val image = Path("docs/thumbnail") / "image.jpg"

val home = Path(System.getProperty("user.home"))
val mod = home / ".steam/steam/steamapps/common/SlayTheSpire" / "MarisaContinued"

fun Path.inode(): Long = Files.getAttribute(this, "unix:ino") as Long

fun Path.forceCreateLinkTo(to: Path) {
    val target = to / fileName

    println("${this.inode()} :: $this")
    println("${target.inode()} :: $target")

    when {
        target.exists() && target.inode() == inode() -> return
        target.exists() -> target.deleteExisting()
    }
    target.createLinkPointingTo(this)
}

jar.forceCreateLinkTo(mod / "content")
image.forceCreateLinkTo(mod)
