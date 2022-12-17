package marisa.powers.Marisa

import com.badlogic.gdx.graphics.Texture
import com.megacrit.cardcrawl.actions.common.GainEnergyAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.powers.AbstractPower
import marisa.MarisaContinued
import marisa.cards.derivations.GuidingStar

class PolarisUniquePower(owner: AbstractCreature?) : AbstractPower() {
    private val p: AbstractPlayer
    var Gain: Boolean

    init {
        MarisaContinued.logger.info("PolarisUniquePower : Init")
        name = NAME
        ID = POWER_ID
        type = PowerType.BUFF
        updateDescription()
        img = Texture("img/powers/transmute.png")
        p = AbstractDungeon.player
        Gain = false
        this.owner = owner
        MarisaContinued.logger.info("PolarisUniquePower : Done initing")
    }

    override fun stackPower(stackAmount: Int) {
        MarisaContinued.logger.info("PolarisUniquePower : StackPower")
    }

    override fun atStartOfTurnPostDraw() {
        MarisaContinued.logger.info("PolarisUniquePower : Checking")
        for (c in p.drawPile.group) {
            if (c is GuidingStar) {
                Gain = true
            }
        }
        MarisaContinued.logger.info("PolarisUniquePower : Result : $Gain")
        if (Gain) {
            flash()
            AbstractDungeon.actionManager.addToBottom(GainEnergyAction(1))
        }
        Gain = false
        MarisaContinued.logger.info("PolarisUniquePower : Done Checking")
    }

    override fun updateDescription() {
        MarisaContinued.logger.info("PolarisUniquePower : updating Description")
        description = DESCRIPTIONS[0]
        MarisaContinued.logger.info("PolarisUniquePower : Done updating Description")
    }

    companion object {
        const val POWER_ID = "PolarisUniquePower"
        private val powerStrings = CardCrawlGame.languagePack
            .getPowerStrings(POWER_ID)
        val NAME = powerStrings.NAME
        val DESCRIPTIONS = powerStrings.DESCRIPTIONS
    }
}
