package marisa.cards

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.powers.WeakPower
import marisa.patches.AbstractCardEnum

class SprinkleStarSeal : CustomCard(
    ID,
    NAME,
    IMG_PATH,
    COST,
    DESCRIPTION,
    CardType.SKILL,
    AbstractCardEnum.MARISA_COLOR,
    CardRarity.UNCOMMON,
    CardTarget.ENEMY
) {
    init {
        baseMagicNumber = STC
        magicNumber = baseMagicNumber
        exhaust = true
    }

    override fun use(p: AbstractPlayer, m: AbstractMonster?) {
        addToBot(
            ApplyPowerAction(
                m,
                p,
                WeakPower(m, magicNumber, false),
                magicNumber,
                true
            )
        )
    }

    override fun makeCopy(): AbstractCard = SprinkleStarSeal()

    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            upgradeBaseCost(UPG_COST)
        }
    }

    companion object {
        const val ID = "SprinkleStarSeal"
        const val IMG_PATH = "img/cards/sprinkleSeal.png"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        private const val COST = 1
        private const val UPG_COST = 0
        private const val STC = 99
    }
}
