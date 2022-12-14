@file:Suppress("unused", "UNUSED_PARAMETER")

package marisa.patches

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn
import com.megacrit.cardcrawl.actions.utility.UseCardAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.powers.DuplicationPower

class StupidDuplicatePotionPatch {
    @SpirePatch(clz = DuplicationPower::class, method = "onUseCard")
    object CapeGoldRewardPatch {
        @SpireInsertPatch(rloc = 15, localvars = ["tmp"])
        @JvmStatic
        fun insert(
            unused: DuplicationPower?,
            card: AbstractCard?,
            action: UseCardAction?,
            tmp: AbstractCard
        ): SpireReturn<*> {
            tmp.applyPowers()
            return SpireReturn.Continue<Any>()
        }
    }
}
