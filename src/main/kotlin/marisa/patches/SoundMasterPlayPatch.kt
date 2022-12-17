@file:Suppress("FunctionName", "UNUSED_PARAMETER", "unused")

package marisa.patches

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.megacrit.cardcrawl.audio.Sfx
import com.megacrit.cardcrawl.audio.SoundMaster
import com.megacrit.cardcrawl.core.Settings


@SpirePatch(
    cls = "com.megacrit.cardcrawl.audio.SoundMaster",
    method = "play",
    paramtypes = ["java.lang.String", "boolean"]
)
object SoundMasterPlayPatch {
    val sounds = mapOf(
        "SELECT_MRS" to load("se_pldead00.ogg")
    )

    @JvmStatic
    fun Postfix(res: Long, unused2: SoundMaster?, key: String?, useBgmVolume: Boolean): Long {
        val volume = if (useBgmVolume) Settings.MUSIC_VOLUME else Settings.SOUND_VOLUME

        return sounds[key]?.play(volume * Settings.MASTER_VOLUME) ?: res
    }

    private fun load(filename: String): Sfx = Sfx("audio/sound/$filename", false)
}
