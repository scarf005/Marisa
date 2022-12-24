package marisa.cards

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.patches.AbstractCardEnum
import marisa.powers.Marisa.SatelIllusPower

class SatelliteIllusion : CustomCard(
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
        addToBot(
            ApplyPowerAction(
                p,
                p,
                SatelIllusPower(p, 1),
                1
            )
        )
    }

    override fun makeCopy(): AbstractCard = SatelliteIllusion()

    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            isInnate = true
            rawDescription = DESCRIPTION_UPG
            initializeDescription()
        }
    }

    companion object {
        const val ID = "SatelliteIllusion"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        val DESCRIPTION_UPG = cardStrings.UPGRADE_DESCRIPTION
        const val IMG_PATH = "img/cards/SatelliteIllusion.png"
        private const val COST = 2
    }
}
