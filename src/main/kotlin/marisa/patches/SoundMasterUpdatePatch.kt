@file:Suppress("FunctionName", "UNUSED_PARAMETER")

package marisa.patches

import com.evacipated.cardcrawl.modthespire.lib.ByRef
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.megacrit.cardcrawl.audio.SoundInfo
import com.megacrit.cardcrawl.audio.SoundMaster
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@SpirePatch(cls = "com.megacrit.cardcrawl.audio.SoundMaster", method = "update")
object SoundMasterUpdatePatch {
    val logger: Logger? = LogManager.getLogger(SoundMasterUpdatePatch::class.java as Class<*>)

    @SpireInsertPatch(rloc = 4, localvars = ["e", "sfx"])
    @JvmStatic
    fun Insert(
        instance: SoundMaster?, e: SoundInfo,
        @ByRef(type = "com.megacrit.cardcrawl.audio.Sfx") sfx: Array<Any?>
    ) {
        if (sfx[0] == null) {
            sfx[0] = SoundMasterPlayPatch.sounds[e.name]
        }
    }
}
