package marisa.patches

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.relics.MarkOfTheBloom
import marisa.MarisaContinued
import marisa.relics.CatCart

@Suppress("unused")
class CatCartPatch {
    @SpirePatch(clz = AbstractPlayer::class, method = "damage", paramtypez = [DamageInfo::class])
    object CatCartResurrect {
        @SpireInsertPatch(rloc = 149)
        @JvmStatic
        fun insert(p: AbstractPlayer, @Suppress("UNUSED_PARAMETER") unused: DamageInfo?): SpireReturn<*> {
            p.getRelic(CatCart.ID)
                ?.takeUnless { p.hasRelic(MarkOfTheBloom.ID) }
                ?.takeIf { it.counter > 0 }
                ?.let {
                    p.currentHealth = 0
                    it.onTrigger()
                    MarisaContinued.logger.info(
                        """MarisaModEventPatch : CatCartPatch: Resurrecting player with ${it.counter} counters"""
                    )
                    return SpireReturn.Return<Any?>(null)
                }
            return SpireReturn.Continue<Any>()
        }
    }
}
