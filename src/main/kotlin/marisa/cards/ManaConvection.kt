package marisa.cards

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.action.ManaConvectionAction
import marisa.patches.AbstractCardEnum

class ManaConvection : CustomCard(
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
        baseMagicNumber = DRAW
        magicNumber = baseMagicNumber
        exhaust = true
    }

    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        addToBot(
            ManaConvectionAction(magicNumber)
        )
    }

    override fun makeCopy(): AbstractCard = ManaConvection()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        upgradeMagicNumber(UPG_DRAW)
    }

    companion object {
        const val ID = "ManaConvection"
        const val IMG_PATH = "img/cards/ManaConvection.png"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        private const val COST = 1
        private const val DRAW = 2
        private const val UPG_DRAW = 1
    }
}
