package marisa.patches

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import marisa.MarisaContinued
import marisa.powers.Marisa.SuperNovaPower

class SuperNovaDiscardPatch {
    @SpirePatch(cls = "com.megacrit.cardcrawl.cards.status.Burn", method = "triggerOnEndOfTurnForPlayingCard")
    object DisableBurn_PreFix {
        @SpirePrefixPatch
        @JvmStatic
        fun Prefix(_obj_instance: Any?): SpireReturn<*> {
            if (AbstractDungeon.player.hasPower(SuperNovaPower.POWER_ID)) {
                MarisaContinued.logger.info("SuperNovaPatch : Burn detected.")
                return SpireReturn.Return<Any?>(null)
            }
            return SpireReturn.Continue<Any>()
        }
    }
}
