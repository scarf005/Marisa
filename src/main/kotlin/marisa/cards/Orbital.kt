package marisa.cards

import com.megacrit.cardcrawl.actions.common.DrawCardAction
import com.megacrit.cardcrawl.cards.CardGroup
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.powers.CorruptionPower
import marisa.abstracts.MarisaCard
import marisa.action.GridSelectAction
import marisa.action.cardGroupOf
import marisa.p

class Orbital : MarisaCard(ID, "Marisa/orbit", COST, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF) {
    init {
        baseMagicNumber = DRAW
        magicNumber = baseMagicNumber
    }

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        upgradeMagicNumber(UPG_DRAW)
    }

    override fun canUse(p: AbstractPlayer, unused: AbstractMonster?): Boolean = false

    override fun triggerOnExhaust() {
        repeat(if (upgraded) 2 else 1) { addToBot(exhume()) }
    }

    /** Returns a card other than Orbital from the exhaust pile to the hand. */
    private fun exhume() = GridSelectAction(CardCrawlGame.languagePack.getUIString("ExhumeAction").TEXT[0], cards = {
        if (p.hand.size() == 10) {
            p.createHandIsFullDialog()
            cardGroupOf()
        } else {
            cardGroupOf(
                p.exhaustPile.group.filter { it.cardID != ID }.onEach {
                    it.stopGlowing()
                    it.unhover()
                    it.unfadeOut()
                },
            )
        }
    }) { cards ->
        cards.forEach {
            p.hand.addToHand(it)
            if (p.hasPower(CorruptionPower.POWER_ID) && it.type == CardType.SKILL) it.setCostForTurn(-9)
            p.exhaustPile.removeCard(it)
            it.unhover()
        }
        p.exhaustPile.group.forEach {
            it.unhover()
            it.target_x = CardGroup.DISCARD_PILE_X.toFloat()
            it.target_y = 0.0f
        }
    }

    override fun triggerWhenDrawn() {
        addToBot(
            DrawCardAction(AbstractDungeon.player, DRAW)
        )
    }

    override fun use(arg0: AbstractPlayer, arg1: AbstractMonster?) {}

    companion object {
        const val ID = "marisa:Orbital"
        private const val COST = -2
        private const val UPG_DRAW = 1
        private const val DRAW = 1
    }
}
