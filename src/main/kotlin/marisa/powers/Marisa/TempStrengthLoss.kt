package marisa.powers.Marisa

import com.badlogic.gdx.graphics.Texture
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.powers.AbstractPower

class TempStrengthLoss(owner: AbstractCreature?, amount: Int) : AbstractPower() {
    init {
        name = NAME
        ID = POWER_ID
        this.owner = owner
        this.amount = amount
        type = PowerType.DEBUFF
        updateDescription()
        img = Texture("marisa/img/powers/dance.png")
    }

    override fun atDamageGive(damage: Float, type: DamageType): Float {
        return if (type == DamageType.NORMAL) {
            damage - amount
        } else damage
    }

    override fun atEndOfTurn(isPlayer: Boolean) {
        if (!isPlayer) {
            AbstractDungeon.actionManager
                .addToBottom(RemoveSpecificPowerAction(owner, owner, "TempStrengthLoss"))
        }
    }

    override fun updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1]
    }

    companion object {
        const val POWER_ID = "marisa:TempStrengthLoss"
        private val powerStrings = CardCrawlGame.languagePack
            .getPowerStrings(POWER_ID)
        val NAME = powerStrings.NAME
        val DESCRIPTIONS = powerStrings.DESCRIPTIONS
    }
}
