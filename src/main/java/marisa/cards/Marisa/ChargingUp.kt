package marisa.cards.Marisa

import marisa.patches.AbstractCardEnum
import marisa.powers.Marisa.ChargeUpPower
import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster

class ChargingUp : CustomCard(
    ID,
    NAME,
    IMG_PATH,
    COST,
    DESCRIPTION,
    CardType.SKILL,
    AbstractCardEnum.MARISA_COLOR,
    CardRarity.UNCOMMON,
    CardTarget.SELF
) {
    init {
        magicNumber = STC
        baseMagicNumber = magicNumber
        exhaust = true
    }

    override fun use(p: AbstractPlayer, m: AbstractMonster) {
        val stack = magicNumber
        /*
			if (ThMod.Amplified(this.costForTurn+AMP, AMP)) {
				stack = stack * 2 - 1;
			}
			*/AbstractDungeon.actionManager.addToBottom(
            ApplyPowerAction(
                p,
                p,
                ChargeUpPower(p, stack),
                stack
            )
        )
    }

    override fun makeCopy(): AbstractCard {
        return ChargingUp()
    }

    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            upgradeMagicNumber(UPG_STC)
            //this.rawDescription = DESCRIPTION_UPG;
            //initializeDescription();
        }
    }

    companion object {
        const val ID = "ChargingUp"
        const val IMG_PATH = "img/cards/ChargingUp.png"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        val DESCRIPTION_UPG = cardStrings.UPGRADE_DESCRIPTION
        private const val COST = 1
        private const val STC = 5

        //private static final int AMP = 1;
        private const val UPG_STC = 3
    }
}
