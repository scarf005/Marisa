package marisa.cards

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.status.Burn
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.patches.AbstractCardEnum
import marisa.powers.Marisa.EscapeVelocityPower

class EscapeVelocity : CustomCard(
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
        baseMagicNumber = 1
        magicNumber = baseMagicNumber
        cardsToPreview = Burn()
    }

    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        addToBot(
            ApplyPowerAction(
                p,
                p,
                EscapeVelocityPower(p, magicNumber),
                magicNumber
            )
        )
    }

    override fun makeCopy(): AbstractCard = EscapeVelocity()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        upgradeBaseCost(1)
    }

    companion object {
        const val ID = "EscapeVelocity"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        val DESCRIPTION_UPG = cardStrings.UPGRADE_DESCRIPTION
        const val IMG_PATH = "marisa/img/cards/EscapeVelocity.png"
        private const val COST = 2
    }
}
