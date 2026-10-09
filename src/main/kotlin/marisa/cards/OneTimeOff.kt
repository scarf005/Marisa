package marisa.cards

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.GainBlockAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower
import marisa.ApplyPowerToPlayerAction
import marisa.abstracts.MarisaCard
import marisa.powers.Marisa.OneTimeOffPlusPower
import marisa.powers.Marisa.OneTimeOffPower

class OneTimeOff : MarisaCard(ID, "MoraleDelpletion", COST, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF) {
    init {
        baseBlock = BLOCK_AMT
        baseMagicNumber = DRAW
        magicNumber = baseMagicNumber
    }

    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        addToBot(
            GainBlockAction(p, p, block)
        )
        addToBot(
            ApplyPowerAction(p, p, DrawCardNextTurnPower(p, magicNumber), magicNumber)
        )
        val powerToAdd = if (upgraded) OneTimeOffPlusPower::class else OneTimeOffPower::class
        addToBot(ApplyPowerToPlayerAction(powerToAdd))
    }

    override fun makeCopy(): AbstractCard = OneTimeOff()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        upgradeBlock(UPGRADE_PLUS_BLOCK)
        upgradeMagicNumber(UPGRADE_PLUS_DRAW)
        rawDescription = strings.UPGRADE_DESCRIPTION
        initializeDescription()
    }

    companion object {
        const val ID = "marisa:OneTimeOff"
        private const val COST = 1
        private const val BLOCK_AMT = 5
        private const val UPGRADE_PLUS_BLOCK = 2
        private const val DRAW = 1
        private const val UPGRADE_PLUS_DRAW = 1
    }
}
