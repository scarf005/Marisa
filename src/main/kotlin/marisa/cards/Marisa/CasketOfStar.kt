package marisa.cards.Marisa

import marisa.patches.AbstractCardEnum
import marisa.powers.Marisa.CasketOfStarPlusPower
import marisa.powers.Marisa.CasketOfStarPower
import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster

class CasketOfStar : CustomCard(
    ID,
    NAME,
    IMG_PATH,
    COST,
    DESCRIPTION,
    CardType.POWER,
    AbstractCardEnum.MARISA_COLOR,
    CardRarity.RARE,
    CardTarget.SELF
) {
    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        if (upgraded) {
            AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(
                    p,
                    p,
                    CasketOfStarPlusPower(p, 1),
                    1
                )
            )
        } else {
            AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(
                    p,
                    p,
                    CasketOfStarPower(p, 1),
                    1
                )
            )
        }
    }

    override fun makeCopy(): AbstractCard = CasketOfStar()

    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            rawDescription = DESCRIPTION_UPG
            initializeDescription()
        }
    }

    companion object {
        const val ID = "CasketOfStar"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        val DESCRIPTION_UPG = cardStrings.UPGRADE_DESCRIPTION
        const val IMG_PATH = "img/cards/CasketOfStar.png"
        private const val COST = 2
    }
}
