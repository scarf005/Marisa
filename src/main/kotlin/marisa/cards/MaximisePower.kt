package marisa.cards

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.GainEnergyAction
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.cards.derivations.Exhaustion_MRS
import marisa.patches.AbstractCardEnum
import marisa.powers.Marisa.ChargeUpPower
import marisa.powers.Marisa.MPPower

class MaximisePower : CustomCard(
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
        baseMagicNumber = 2
        magicNumber = baseMagicNumber
        exhaust = true
        cardsToPreview = Exhaustion_MRS()
    }

    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        p.getPower(ChargeUpPower.POWER_ID)
            ?.takeIf { it.amount > 0 }
            ?.let { power ->
                addToBot(
                    GainEnergyAction(p.getPower(ChargeUpPower.POWER_ID).amount)
                )
                power.amount = 0
            }

        addToBot(
            ApplyPowerAction(
                p,
                p,
                MPPower(p, 1),
                1
            )
        )
        addToBot(
            MakeTempCardInHandAction(
                Exhaustion_MRS(),
                1
            )
        )
    }

    override fun makeCopy(): AbstractCard = MaximisePower()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        updateCost(-1)
    }

    companion object {
        const val ID = "marisa:MaximisePower"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        val DESCRIPTION_UPG = cardStrings.UPGRADE_DESCRIPTION
        const val IMG_PATH = "marisa/img/cards/maxPower.png"
        private const val COST = 3
    }
}
