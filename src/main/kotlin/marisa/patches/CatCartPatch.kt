package marisa.patches

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.relics.MarkOfTheBloom
import marisa.relics.CatCart

@Suppress("unused")
class CatCartPatch {
    @SpirePatch(clz = AbstractPlayer::class, method = "damage", paramtypez = [DamageInfo::class])
    object CatCartResurrect {
        @SpireInsertPatch(rloc = 149)
        @JvmStatic
        fun insert(p: AbstractPlayer, unused: DamageInfo?): SpireReturn<*> {
            if (p.hasRelic(CatCart.ID) && !p.hasRelic(MarkOfTheBloom.ID)) {
                if (p.getRelic(CatCart.ID).counter > 0 && !p.hasRelic(MarkOfTheBloom.ID)) {
                    p.currentHealth = 0
                    p.getRelic(CatCart.ID).onTrigger()
                    return SpireReturn.Return<Any?>(null)
                }
            }
            return SpireReturn.Continue<Any>()
        }
    }
}
