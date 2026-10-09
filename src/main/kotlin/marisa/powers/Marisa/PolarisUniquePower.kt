package marisa.powers.Marisa

import com.megacrit.cardcrawl.actions.common.GainEnergyAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import marisa.MarisaContinued
import marisa.abstracts.MarisaPower
import marisa.cards.derivations.GuidingStar

class PolarisUniquePower(owner: AbstractCreature?) : MarisaPower(POWER_ID, owner, -1, "transmute") {
    private val p: AbstractPlayer = AbstractDungeon.player
    var gain: Boolean = false

    init {
        MarisaContinued.logger.info("PolarisUniquePower : Init")
        updateDescription()
        MarisaContinued.logger.info("PolarisUniquePower : Done initing")
    }

    override fun stackPower(stackAmount: Int) {
        MarisaContinued.logger.info("PolarisUniquePower : StackPower")
    }

    override fun atStartOfTurnPostDraw() {
        MarisaContinued.logger.info("PolarisUniquePower : Checking")
        gain = p.drawPile.group.any { it is GuidingStar }
        MarisaContinued.logger.info("PolarisUniquePower : Result : $gain")
        if (gain) {
            flash()
            addToBot(GainEnergyAction(1))
        }
        gain = false
        MarisaContinued.logger.info("PolarisUniquePower : Done Checking")
    }

    override fun updateDescription() {
        MarisaContinued.logger.info("PolarisUniquePower : updating Description")
        description = descriptions[0]
        MarisaContinued.logger.info("PolarisUniquePower : Done updating Description")
    }

    companion object {
        const val POWER_ID = "marisa:PolarisUniquePower"
    }
}
