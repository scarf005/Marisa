package marisa.cards

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.cards.derivations.Spark
import marisa.patches.AbstractCardEnum
import marisa.powers.Marisa.CasketOfStarPlusPower
import marisa.powers.Marisa.CasketOfStarPower

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
    init {
        cardsToPreview = Spark()
    }

    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        if (upgraded) {
            addToBot(
                ApplyPowerAction(
                    p,
                    p,
                    CasketOfStarPlusPower(p, 1),
                    1
                )
            )
        } else {
            addToBot(
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
        if (upgraded) return
        upgradeName()
        rawDescription = DESCRIPTION_UPG
        cardsToPreview = Spark().upgraded()
        initializeDescription()
    }

    companion object {
        const val ID = "marisa:CasketOfStar"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        val DESCRIPTION_UPG = cardStrings.UPGRADE_DESCRIPTION
        const val IMG_PATH = "marisa/img/cards/CasketOfStar.png"
        private const val COST = 2
    }
}
