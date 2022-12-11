package marisa.powers.monsters

import com.badlogic.gdx.graphics.Texture
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType
import com.megacrit.cardcrawl.cards.status.Burn
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.powers.AbstractPower

class InfernoClaw(owner: AbstractCreature?) : AbstractPower() {
    init {
        name = NAME
        ID = POWER_ID
        this.owner = owner
        amount = -1
        updateDescription()
        img = Texture("img/powers/thrillseeker.png")
    }

    override fun updateDescription() {
        description = DESCRIPTIONS[0]
    }

    override fun stackPower(amount: Int) {}
    override fun onInflictDamage(info: DamageInfo, damageAmount: Int, target: AbstractCreature) {
        if (damageAmount > 0 && info.type != DamageType.THORNS) {
            AbstractDungeon.actionManager.addToBottom(
                MakeTempCardInDiscardAction(Burn(), 1)
            )
        }
    }

    companion object {
        const val POWER_ID = "InfernoClaw"
        private val powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID)
        val NAME = powerStrings.NAME
        val DESCRIPTIONS = powerStrings.DESCRIPTIONS
    }
}
