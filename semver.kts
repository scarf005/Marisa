#!/bin/env kscript

import java.io.File

val types = listOf("build", "ci", "docs", "feat", "fix", "perf", "refactor", "style", "test")
val initial = "1d058628729d77644a151f6aa82b380479a1a941"

enum class Version { MAJOR, MINOR, PATCH }
data class SemVer(val major: Int, val minor: Int, val patch: Int) {
    override fun toString(): String = "$major.$minor.$patch"
}

@JvmInline
value class CommitTitle(private val value: String) {
    val sha get() = value.substringBefore(' ')
    val message get() = value.substringAfter(' ')
    val type get() = message.substringBefore(':')
    val title get() = message.substringAfter(':').trim()
    val version
        get() = when {
            type.contains("!") -> Version.MAJOR
            type == "feat" -> Version.MINOR
            else -> Version.PATCH
        }
}

/**
 * @param begin inclusive
 * @param end inclusive
 */
fun commitsBetween(begin: String, end: String = "HEAD") =
    listOf("git", "log", "--pretty=format:'%h %s'", "$begin^..$end")
        .runCommand().lines()
        .filter { title -> types.any { title.substringAfter(' ').startsWith(it) } }
        .map(::CommitTitle)

fun List<String>.runCommand(workingDir: File = File(".")) =
    ProcessBuilder(this)
        .directory(workingDir)
        .redirectOutput(ProcessBuilder.Redirect.PIPE)
        .redirectError(ProcessBuilder.Redirect.PIPE)
        .start()
        .inputStream.bufferedReader().readText()

fun <T, R> Iterable<T>.groupCountBy(selector: (T) -> R): Map<R, Int> =
    groupBy(selector).mapValues { it.value.size }


fun List<CommitTitle>.toSemVer() =
    groupCountBy { it.version }
        .let { SemVer(it[Version.MAJOR] ?: 0, it[Version.MINOR] ?: 0, it[Version.PATCH] ?: 0) }

println(commitsBetween(initial).toSemVer())
