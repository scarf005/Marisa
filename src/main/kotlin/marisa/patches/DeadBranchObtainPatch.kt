package marisa.patches

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.relics.AbstractRelic
import marisa.MarisaContinued
import marisa.characters.Marisa
import marisa.relics.SproutingBranch

@Suppress("unused")
class DeadBranchObtainPatch {
    @Suppress("FunctionName")
    @SpirePatch(cls = "com.megacrit.cardcrawl.relics.DeadBranch", method = "makeCopy")
    object DeadBranchObtain {
        @SpirePrefixPatch
        @JvmStatic
        fun Prefix(@Suppress("UNUSED_PARAMETER") unused: AbstractRelic?): SpireReturn<AbstractRelic> {
            return if (AbstractDungeon.player is Marisa && !MarisaContinued.isDeadBranchEnabled) {
                SpireReturn.Return(SproutingBranch())
            } else SpireReturn.Continue()
        }
    }
}
