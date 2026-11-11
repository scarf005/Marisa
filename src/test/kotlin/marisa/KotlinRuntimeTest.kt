package marisa

import com.evacipated.cardcrawl.modthespire.Loader
import javassist.bytecode.ClassFile
import java.io.DataInputStream
import java.io.File
import java.util.jar.JarInputStream
import kotlin.test.Test
import kotlin.test.assertEquals

class KotlinRuntimeTest {
    @Test
    fun `mod classes use only the kotlin-stdlib classes that ModTheSpire ships`() {
        val shipped = JarInputStream(Loader::class.java.getResourceAsStream("/kotlin.jar")).use { jar ->
            generateSequence { jar.nextJarEntry }.map { it.name.removeSuffix(".class").replace('/', '.') }.toSet()
        }
        val classes = File(MarisaContinued::class.java.protectionDomain.codeSource.location.toURI())
        val missing = classes.walk().filter { it.extension == "class" }.flatMap { file ->
            val classFile = file.inputStream().use { ClassFile(DataInputStream(it)) }
            classFile.constPool.classNames.map { (it as String).replace('/', '.') }
                .filter { it.startsWith("kotlin.") && it !in shipped }
                .map { "${classFile.name} -> $it" }
        }.sorted().toList()
        assertEquals(emptyList(), missing)
    }
}
