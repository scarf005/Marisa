package marisa.patches

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.megacrit.cardcrawl.audio.Sfx
import com.megacrit.cardcrawl.audio.SoundMaster
import com.megacrit.cardcrawl.core.Settings

@SpirePatch(
    cls = "com.megacrit.cardcrawl.audio.SoundMaster",
    method = "playA",
    paramtypes = ["java.lang.String", "float"]
)
object SoundMasterPlayAPatch {
    var map = HashMap<String?, Sfx?>()

    @JvmStatic
    fun Postfix(res: Long, _inst: SoundMaster?, key: String?, pitchAdjust: Float): Long {
        if (map.containsKey(key)) {
            /*
      if (key.equals("SELECT_MRS")) {
        return ((Sfx) map.get(key)).play(
            Settings.SOUND_VOLUME * Settings.MASTER_VOLUME * 0.25F,
            1.0F + pitchAdjust,
            0.0F
        );
      } else*/
            run {
                return map[key]!!.play(
                    Settings.SOUND_VOLUME * Settings.MASTER_VOLUME,
                    1.0f + pitchAdjust,
                    0.0f
                )
            }
        }
        return 0L
    }

    private fun load(filename: String): Sfx {
        return Sfx("audio/sound/$filename", false)
    }

    init {
        map["SELECT_MRS"] = load("se_pldead00.ogg")
    }
}
