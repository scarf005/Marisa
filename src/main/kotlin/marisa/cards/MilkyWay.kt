package marisa.cards

import com.megacrit.cardcrawl.actions.common.DrawCardAction
import com.megacrit.cardcrawl.actions.common.GainBlockAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.abstracts.MarisaCard
import marisa.action.DamageUpAction

class MilkyWay : MarisaCard(ID, "MilkWay", COST, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF) {
    init {
        baseBlock = BLOCK_AMT
        baseMagicNumber = TEMP_STR
        magicNumber = baseMagicNumber
    }

    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        addToBot(
            GainBlockAction(p, p, block)
        )
        addToBot(
            DrawCardAction(p, 1)
        )
        addToBot(
            DamageUpAction(magicNumber)
        )
    }

    override fun makeCopy(): AbstractCard = MilkyWay()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        upgradeBlock(UPGRADE_PLUS_BLOCK)
        upgradeMagicNumber(1)
    }

    companion object {
        const val ID = "marisa:MilkyWay"
        private const val COST = 1
        private const val BLOCK_AMT = 5
        private const val UPGRADE_PLUS_BLOCK = 2
        private const val TEMP_STR = 1
    }
}
