package marisa.testing

import com.evacipated.cardcrawl.modthespire.*
import java.io.File
import java.util.Properties
import java.util.jar.JarEntry
import java.util.jar.JarOutputStream

/**
 * Writes the game classes as ModTheSpire patches them for the given mods into `<out>/desktop-1.0.jar`,
 * and the ModTheSpire core patches they call into `<out>/corepatches.jar`,
 * following the patching steps of `Loader.runMods` without launching the game.
 *
 * Usage: `PatchGame <out> <desktop-1.0.jar> <mod.jar>...`
 */
fun main(args: Array<String>) {
    val (out, game) = args
    val mods = args.drop(2).map { ModInfo.ReadModInfo(File(it)) }.toTypedArray()
    Loader.MTS_VERSION = Properties()
        .apply { load(Loader::class.java.getResourceAsStream("/META-INF/version.prop")) }
        .getProperty("version").let(ModInfo::safeVersion)
    Loader.MODINFOS = mods
    Loader.OUT_JAR = true
    Loader::class.java.getDeclaredField("ALLMODINFOS").apply { isAccessible = true }.set(null, mods)

    val urls = (listOf(File(game).toURI().toURL()) + mods.map { it.jarURL }).toTypedArray()
    val core = Loader::class.java.getResource(Loader.COREPATCHES_JAR)!!
    fun classLoader() = MTSClassLoader(core.openStream(), urls, Loader::class.java.classLoader)
    val loader = classLoader()
    val patchingLoader = classLoader()
    val pool = MTSClassPool(patchingLoader)

    Patcher.patchEnums(patchingLoader, pool, core)
    Patcher.patchEnums(patchingLoader, pool, mods)
    Patcher.injectPatches(patchingLoader, pool, Patcher.findPatches(arrayOf(core)))
    Patcher.injectPatches(patchingLoader, pool, Patcher.findPatches(mods))
    Patcher::class.java.getDeclaredMethod(
        "patchOverrides", ClassLoader::class.java, javassist.ClassPool::class.java, Array<ModInfo>::class.java,
    ).apply { isAccessible = true }.invoke(null, patchingLoader, pool, mods)
    Patcher.finalizePatches(patchingLoader)
    Patcher.compilePatches(loader, pool)
    File(out).mkdirs()
    core.openStream().use { File(out, "corepatches.jar").outputStream().use(it::copyTo) }
    JarOutputStream(File(out, "desktop-1.0.jar").outputStream()).use { jar ->
        pool.outJarClasses.forEach { cls ->
            jar.putNextEntry(JarEntry(cls.name.replace('.', '/') + ".class"))
            jar.write(cls.toBytecode())
        }
    }
}
