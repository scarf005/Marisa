package marisa.cards

import com.megacrit.cardcrawl.actions.common.DrawCardAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.abstracts.MarisaCard
import marisa.action.OrbitalAction

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
        addToBot(
            OrbitalAction()
        )
        if (upgraded) {
            addToBot(
                OrbitalAction()
            )
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
