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
    var map = HashMap<String?, Sfx?>()

    @JvmStatic
    fun Postfix(
        res: Long, _inst: SoundMaster?, key: String?,
        useBgmVolume: Boolean
    ): Long {
        if (!map.containsKey(key)) {
            return res
        }
        return if (useBgmVolume) {
            map[key]!!
                .play(Settings.MUSIC_VOLUME * Settings.MASTER_VOLUME)
        } else map[key]!!
            .play(Settings.SOUND_VOLUME * Settings.MASTER_VOLUME)
    }

    private fun load(filename: String): Sfx = Sfx("audio/sound/$filename", false)

    init {
        map["SELECT_MRS"] = load("se_pldead00.ogg")
    }
}
