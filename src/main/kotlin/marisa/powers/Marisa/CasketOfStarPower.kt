package marisa.powers.Marisa

import com.badlogic.gdx.graphics.Texture
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.powers.AbstractPower
import marisa.cards.derivations.Spark

class CasketOfStarPower(owner: AbstractCreature?, amount: Int) : AbstractPower() {
    init {
        name = NAME
        ID = POWER_ID
        this.owner = owner
        this.amount = amount
        type = PowerType.BUFF
        updateDescription()
        img = Texture("img/powers/energyNext.png")
    }

    override fun onGainedBlock(blockAmount: Float) {
        addToBot(
            MakeTempCardInHandAction(Spark(), amount)
        )
    }

    override fun updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1]
    }

    companion object {
        const val POWER_ID = "CasketOfStarPower"
        private val powerStrings = CardCrawlGame.languagePack
            .getPowerStrings(POWER_ID)
        val NAME = powerStrings.NAME
        val DESCRIPTIONS = powerStrings.DESCRIPTIONS
    }
}
