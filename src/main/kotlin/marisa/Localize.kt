package marisa

import com.badlogic.gdx.Gdx
import com.megacrit.cardcrawl.core.Settings
import java.nio.charset.StandardCharsets

fun localize(
    name: String,
    fallback: Settings.GameLanguage = Settings.GameLanguage.ENG
): String {
    fun Settings.GameLanguage.toFile() = Gdx.files.internal("localization/$this/$name.json")

    val file = Settings.language.toFile()
    val fallbackFile = fallback.toFile()

    val toLoad = if (file.exists()) file else run {
        MarisaContinued.logger.warn("No localization file for ${Settings.language}, using fallback $fallback")
        fallbackFile
    }
    return toLoad.readString(StandardCharsets.UTF_8.toString())
}
