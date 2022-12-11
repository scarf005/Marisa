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
import com.megacrit.cardcrawl.relics.AbstractRelic

class ParasitePatch {
    @SpirePatch(cls = "com.megacrit.cardcrawl.cards.curses.Parasite", method = "use")
    object ParasiteUse {
        @SpirePrefixPatch
        @JvmStatic
        fun Prefix(
            _inst: AbstractCard,
            p: AbstractPlayer,
            m: AbstractMonster?
        ): SpireReturn<*> {
            if (AbstractDungeon.player.hasRelic("ShroomBag") || AbstractDungeon.player.hasRelic("BigShroomBag")) {
                val r: AbstractRelic
                val heal_amt: Int
                val draw: Int
                if (AbstractDungeon.player.hasRelic("BigShroomBag")) {
                    r = p.getRelic("BigShroomBag")
                    heal_amt = 3
                    draw = 2
                } else {
                    r = p.getRelic("ShroomBag")
                    heal_amt = 2
                    draw = 1
                }
                r.flash()
                AbstractDungeon.actionManager.addToBottom(
                    RelicAboveCreatureAction(p, r)
                )
                _inst.exhaust = true
                AbstractDungeon.actionManager.addToBottom(
                    HealAction(p, p, heal_amt)
                )
                AbstractDungeon.actionManager.addToBottom(
                    DrawCardAction(p, draw)
                )
                return SpireReturn.Return<Any?>(null)
            }
            return SpireReturn.Continue<Any>()
        }
    }

    @SpirePatch(cls = "com.megacrit.cardcrawl.cards.AbstractCard", method = "canUse")
    object ParasiteCanUse {
        @SpirePrefixPatch
        @JvmStatic
        fun Prefix(
            _inst: AbstractCard,
            p: AbstractPlayer,
            m: AbstractMonster?
        ): SpireReturn<Boolean> {
            return if (_inst.cardID == "Parasite" && (p.hasRelic("ShroomBag") || p.hasRelic("BigShroomBag"))) {
                SpireReturn.Return(true)
            } else SpireReturn.Continue()
        }
    }
}
