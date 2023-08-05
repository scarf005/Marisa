@file:Suppress("FunctionName", "unused", "UNUSED_PARAMETER")

package marisa.patches

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.megacrit.cardcrawl.audio.Sfx
import com.megacrit.cardcrawl.audio.SoundMaster
import com.megacrit.cardcrawl.core.Settings
import marisa.modName

@SpirePatch(
    cls = "com.megacrit.cardcrawl.audio.SoundMaster",
    method = "playA",
    paramtypes = ["java.lang.String", "float"]
)
object SoundMasterPlayAPatch {
    private val sounds = mapOf(
        "SELECT_MRS" to load("coin.ogg")
    )

    @JvmStatic
    fun Postfix(unused1: Long, unused2: SoundMaster?, key: String?, pitchAdjust: Float): Long {
        sounds[key]?.play(Settings.SOUND_VOLUME * Settings.MASTER_VOLUME, 1.0f + pitchAdjust, 0.0f)

        return 0L
    }

    private fun load(filename: String): Sfx = Sfx("$modName/audio/sound/$filename", false)
}
