package marisa.cards.Marisa

import marisa.patches.AbstractCardEnum
import marisa.powers.Marisa.ChargeUpPower
import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.GainEnergyAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster

class UltraShortWave : CustomCard(
    ID,
    NAME,
    IMG_PATH,
    COST,
    DESCRIPTION,
    CardType.SKILL,
    AbstractCardEnum.MARISA_COLOR,
    CardRarity.RARE,
    CardTarget.SELF
) {
    init {
        baseMagicNumber = GAIN
        magicNumber = baseMagicNumber
        baseBlock = ENERGY
        block = baseBlock
        baseDamage = GROW
        damage = baseDamage
    }

    override fun applyPowers() {}
    override fun calculateCardDamage(mo: AbstractMonster) {}
    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        AbstractDungeon.actionManager.addToBottom(
            GainEnergyAction(block)
        )
        AbstractDungeon.actionManager.addToBottom(
            ApplyPowerAction(
                p,
                p,
                ChargeUpPower(p, magicNumber),
                magicNumber
            )
        )
        upgradeMagicNumber(damage)
        upgradeBlock(1)
    }

    override fun makeCopy(): AbstractCard {
        return UltraShortWave()
    }

    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            upgradeDamage(GAIN_GROW)
        }
    }

    companion object {
        const val ID = "UltimateShortwave"
        const val IMG_PATH = "img/cards/ShortWave.png"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        private const val COST = 1
        private const val GAIN = 1
        private const val GROW = 1
        private const val GAIN_GROW = 1
        private const val ENERGY = 1
    }
}
