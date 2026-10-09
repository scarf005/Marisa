package marisa.cards

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.powers.WeakPower
import marisa.abstracts.MarisaCard

class SprinkleStarSeal : MarisaCard(ID, "sprinkleSeal", COST, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY) {
    init {
        baseMagicNumber = STC
        magicNumber = baseMagicNumber
        exhaust = true
    }

    override fun use(p: AbstractPlayer, m: AbstractMonster?) {
        addToBot(
            ApplyPowerAction(
                m,
                p,
                WeakPower(m, magicNumber, false),
                magicNumber,
                true
            )
        )
    }

    override fun makeCopy(): AbstractCard = SprinkleStarSeal()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        upgradeBaseCost(UPG_COST)
    }

    companion object {
        const val ID = "marisa:SprinkleStarSeal"
        private const val COST = 1
        private const val UPG_COST = 0
        private const val STC = 99
    }
}
