@file:Suppress("unused", "FunctionName", "UNUSED_PARAMETER")

package marisa.patches

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn
import com.megacrit.cardcrawl.actions.common.DrawCardAction
import com.megacrit.cardcrawl.actions.common.HealAction
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.relics.BigShroomBag
import marisa.relics.ShroomBag

class ParasitePatch {
    @SpirePatch(cls = "com.megacrit.cardcrawl.cards.curses.Parasite", method = "use")
    object ParasiteUse {
        @SpirePrefixPatch
        @JvmStatic
        fun Prefix(
            instance: AbstractCard,
            p: AbstractPlayer,
            unused: AbstractMonster?
        ): SpireReturn<*> {
            if (!p.hasRelic(ShroomBag.ID) && !p.hasRelic(BigShroomBag.ID)) {
                return SpireReturn.Continue<Any>()
            }

            val (r, healAmt, draw) = if (p.hasRelic(BigShroomBag.ID)) {
                Triple(p.getRelic(BigShroomBag.ID), 3, 2)
            } else {
                Triple(p.getRelic(ShroomBag.ID), 2, 1)
            }

            r.flash()
            AbstractDungeon.actionManager.addToBottom(
                RelicAboveCreatureAction(p, r)
            )
            instance.exhaust = true
            AbstractDungeon.actionManager.addToBottom(
                HealAction(p, p, healAmt)
            )
            AbstractDungeon.actionManager.addToBottom(
                DrawCardAction(p, draw)
            )
            return SpireReturn.Return<Any?>(null)
        }
    }

    @SpirePatch(cls = "com.megacrit.cardcrawl.cards.AbstractCard", method = "canUse")
    object ParasiteCanUse {
        @SpirePrefixPatch
        @JvmStatic
        fun Prefix(
            instance: AbstractCard,
            p: AbstractPlayer,
            unused: AbstractMonster?
        ): SpireReturn<Boolean> {
            return if (instance.cardID == "Parasite" && (p.hasRelic(ShroomBag.ID) || p.hasRelic(BigShroomBag.ID))) {
                SpireReturn.Return(true)
            } else SpireReturn.Continue()
        }
    }
}
