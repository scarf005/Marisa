package marisa.cards.Marisa

import marisa.cards.derivations.Exhaustion_MRS
import marisa.patches.AbstractCardEnum
import marisa.powers.Marisa.MPPower
import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.GainEnergyAction
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.powers.Marisa.ChargeUpPower

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
    }

    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        if (p.hasPower(ChargeUpPower.POWER_ID)) {
            if (p.getPower(ChargeUpPower.POWER_ID).amount > 0) {
                AbstractDungeon.actionManager.addToBottom(
                    GainEnergyAction(p.getPower(ChargeUpPower.POWER_ID).amount)
                )
                p.getPower(ChargeUpPower.POWER_ID).amount = 0
            }
        }
        AbstractDungeon.actionManager.addToBottom(
            ApplyPowerAction(
                p,
                p,
                MPPower(p, 1),
                1
            )
        )
        AbstractDungeon.actionManager.addToBottom(
            MakeTempCardInHandAction(
                Exhaustion_MRS(),
                1
            )
        )
    }

    override fun makeCopy(): AbstractCard = MaximisePower()

    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            updateCost(-1)
            //upgradeMagicNumber(1);
            //this.rawDescription = DESCRIPTION_UPG;
            //initializeDescription();
            //this.exhaust = false;
        }
    }

    companion object {
        const val ID = "MaximisePower"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        val DESCRIPTION_UPG = cardStrings.UPGRADE_DESCRIPTION
        const val IMG_PATH = "img/cards/maxPower.png"
        private const val COST = 3
    }
}
