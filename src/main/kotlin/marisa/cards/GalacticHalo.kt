package marisa.cards

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.GainBlockAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.MarisaContinued
import marisa.abstracts.MarisaCard
import marisa.powers.Marisa.ChargeUpPower

class GalacticHalo : MarisaCard(ID, "halo", COST, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF) {
    init {
        baseMagicNumber = STC
        magicNumber = baseMagicNumber
        baseBlock = BLC
        block = baseBlock
    }

    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        MarisaContinued.logger.info(
            """GalacticHalo : use : magicNumber : $magicNumber baseMagicNumber : $baseMagicNumber"""
        )
        addToBot(
            GainBlockAction(p, p, block)
        )
        addToBot(
            ApplyPowerAction(
                p, p,
                ChargeUpPower(p, magicNumber),
                magicNumber
            )
        )
    }

    override fun makeCopy(): AbstractCard = GalacticHalo()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        upgradeMagicNumber(UPG_STC)
        upgradeBlock(UPG_BLC)
    }

    companion object {
        const val ID = "marisa:GalacticHalo"
        private const val COST = 2
        private const val STC = 2
        private const val UPG_STC = 1
        private const val BLC = 12
        private const val UPG_BLC = 2
    }
}
