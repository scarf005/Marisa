package marisa.powers.Marisa

import com.badlogic.gdx.graphics.Texture
import com.megacrit.cardcrawl.actions.common.DrawCardAction
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction
import com.megacrit.cardcrawl.cards.status.Burn
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.powers.AbstractPower

class EscapeVelocityPower(owner: AbstractCreature?, amount: Int) : AbstractPower() {
    init {
        name = NAME
        ID = POWER_ID
        this.owner = owner
        this.amount = amount
        type = PowerType.BUFF
        updateDescription()
        img = Texture("img/powers/drawCardRed.png")
    }

    override fun atStartOfTurnPostDraw() {
        AbstractDungeon.actionManager.addToBottom(
            DrawCardAction(owner, amount * 2)
        )
        AbstractDungeon.actionManager.addToBottom(
            MakeTempCardInHandAction(Burn(), amount)
        )
    }

    override fun updateDescription() {
        description = (DESCRIPTIONS[0] + amount * 2 + DESCRIPTIONS[1] + amount
                + DESCRIPTIONS[2])
    }

    companion object {
        const val POWER_ID = "ExtraDraw"
        private val powerStrings = CardCrawlGame.languagePack
            .getPowerStrings(POWER_ID)
        val NAME = powerStrings.NAME
        val DESCRIPTIONS = powerStrings.DESCRIPTIONS
    }
}
