package marisa.powers.Marisa

import com.badlogic.gdx.graphics.Texture
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.powers.AbstractPower
import marisa.RemoveSelfAction

class DarkMatterPower(owner: AbstractCreature?) : AbstractPower() {
    init {
        name = NAME
        ID = POWER_ID
        this.owner = owner
        amount = -1
        type = PowerType.BUFF
        updateDescription()
        img = Texture("marisa/img/powers/darkness.png")
    }

    override fun stackPower(stackAmount: Int) {}
    override fun atEndOfTurn(isPlayer: Boolean) {
        addToBot(
            RemoveSelfAction()
        )
    }

    override fun updateDescription() {
        description = DESCRIPTIONS[0]
    }

    companion object {
        const val POWER_ID = "DarkMatterPower"
        private val powerStrings = CardCrawlGame.languagePack
            .getPowerStrings(POWER_ID)
        val NAME = powerStrings.NAME
        val DESCRIPTIONS = powerStrings.DESCRIPTIONS
    }
}
