package marisa.powers.monsters

import com.badlogic.gdx.graphics.Texture
import com.megacrit.cardcrawl.actions.common.ExhaustAction
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.powers.AbstractPower

class WraithPower(owner: AbstractCreature?, amount: Int) : AbstractPower() {
    init {
        name = NAME
        ID = POWER_ID
        this.owner = owner
        type = PowerType.DEBUFF
        updateDescription()
        img = Texture("marisa/img/powers/exhaustion.png")
        this.amount = amount
    }

    override fun atStartOfTurnPostDraw() {
        flash()
        addToBot(ExhaustAction(1, true, false, false))
        amount--
        if (amount <= 0) {
            addToBot(
                RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, this)
            )
        }
    }

    override fun updateDescription() {
        description = DESCRIPTIONS[0]
    }

    companion object {
        const val POWER_ID = "marisa:Wraith"
        private val powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID)
        val NAME = powerStrings.NAME
        val DESCRIPTIONS = powerStrings.DESCRIPTIONS
    }
}
