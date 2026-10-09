package marisa.testing

import marisa.MarisaContinued
import java.io.File
import java.lang.reflect.Modifier

/** Concrete top-level subclasses of [T] compiled from the mod sources in [pkg] and its subpackages, sorted by name. */
inline fun <reified T> concreteClasses(pkg: String): List<Class<out T>> {
    val loader = Game::class.java.classLoader
    val classes = File(MarisaContinued::class.java.protectionDomain.codeSource.location.toURI())
    val root = classes.resolve(pkg.replace('.', File.separatorChar))
    return root.walk()
        .filter { it.extension == "class" && '$' !in it.name && !it.name.endsWith("Kt.class") }
        .map { "$pkg." + it.toRelativeString(root).removeSuffix(".class").replace(File.separatorChar, '.') }
        .map { Class.forName(it, false, loader) }
        .filter { T::class.java.isAssignableFrom(it) && !Modifier.isAbstract(it.modifiers) }
        .map { @Suppress("UNCHECKED_CAST") (it as Class<out T>) }
        .sortedBy { it.name }
        .toList()
}
