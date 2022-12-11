package marisa.cards.Marisa

import marisa.patches.AbstractCardEnum
import marisa.powers.Marisa.EventHorizonPower
import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster

class EventHorizon : CustomCard(
    ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.POWER,
    AbstractCardEnum.MARISA_COLOR, CardRarity.UNCOMMON, CardTarget.SELF
) {
    init {
        baseMagicNumber = STC_GAIN
        magicNumber = baseMagicNumber
    }

    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        AbstractDungeon.actionManager.addToBottom(
            ApplyPowerAction(p, p, EventHorizonPower(p, magicNumber), magicNumber)
        )
    }

    override fun makeCopy(): AbstractCard {
        return EventHorizon()
    }

    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            upgradeMagicNumber(UPG_STC)
        }
    }

    companion object {
        const val ID = "EventHorizon"
        const val IMG_PATH = "img/cards/EventHorizon.png"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        private const val COST = 1
        private const val STC_GAIN = 1
        private const val UPG_STC = 1
    }
}
