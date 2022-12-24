package marisa.powers.Marisa

import com.badlogic.gdx.graphics.Texture
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.AbstractCard.CardType
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.powers.AbstractPower
import marisa.MarisaContinued
import marisa.RemoveSelfAction

class BlazeAwayPower(owner: AbstractCreature?, amount: Int) : AbstractPower() {
    init {
        name = NAME
        ID = POWER_ID
        this.owner = owner
        this.amount = amount
        type = PowerType.BUFF
        updateDescription()
        img = Texture("img/powers/burst.png")
    }

    override fun onPlayCard(card: AbstractCard, unused: AbstractMonster?) {
        MarisaContinued.logger.info("BlazeWayPower : card type check")
        if (card.type != CardType.ATTACK) {
            return
        }
        MarisaContinued.logger.info("BlazeWayPower : done checking")
        flash()
        val c = card.makeStatEquivalentCopy()
        c.costForTurn = 0
        MarisaContinued.logger.info("BlazeWayPower : adding " + amount + " : " + c.cardID)
        addToBot(
            MakeTempCardInHandAction(c, amount)
        )
        MarisaContinued.logger.info("BlazeWayPower : removing power")
        addToBot(
            RemoveSelfAction()
        )
        MarisaContinued.logger.info("BlazeWayPower : all done")
    }

    override fun updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1]
    }

    override fun atEndOfTurn(isPlayer: Boolean) {
        if (isPlayer) {
            addToBot(
                RemoveSelfAction()
            )
        }
    }

    companion object {
        const val POWER_ID = "BlazeAwayPower"
        private val powerStrings = CardCrawlGame.languagePack
            .getPowerStrings(POWER_ID)
        val NAME = powerStrings.NAME
        val DESCRIPTIONS = powerStrings.DESCRIPTIONS
    }
}
