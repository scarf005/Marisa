package marisa.powers.Marisa

import com.megacrit.cardcrawl.actions.common.GainEnergyAction
import com.megacrit.cardcrawl.actions.utility.UseCardAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.powers.AbstractPower
import marisa.MarisaContinued
import marisa.abstracts.MarisaPower

class SatelIllusPower(owner: AbstractCreature?, amount: Int) : MarisaPower(POWER_ID, owner, amount, "satelIllu") {
    private var counter: Int

    fun checkDrawPile() {
        val temp = AbstractDungeon.player.drawPile.size()
        MarisaContinued.logger.info(
            """SatelIllusPower : checkDrawPile : counter : $counter ;
                |drawPile size $temp ; grant energy :${temp > counter}""".trimMargin()
        )
        if (temp > counter) {
            flash()
            addToBot(
                GainEnergyAction(amount)
            )
        }
        if (temp != counter) {
            counter = temp
        }
    }

    init {
        updateDescription()
        counter = AbstractDungeon.player.drawPile.size()
    }

    override fun onDrawOrDiscard() {
        MarisaContinued.logger.info("SatelIllusPower : onDrawOrDiscard : checkDrawPile")
        checkDrawPile()
    }

    override fun onApplyPower(
        power: AbstractPower,
        target: AbstractCreature,
        source: AbstractCreature
    ) {
        MarisaContinued.logger.info("SatelIllusPower : onApplyPower : checkDrawPile")
        checkDrawPile()
    }

    override fun onInitialApplication() {
        MarisaContinued.logger.info("SatelIllusPower : onInitialApplication : checkDrawPile")
        checkDrawPile()
    }

    override fun atEndOfRound() {
        MarisaContinued.logger.info("SatelIllusPower : checkDrawPile : atEndOfRound ")
        checkDrawPile()
    }

    override fun onAfterUseCard(card: AbstractCard, action: UseCardAction) {
        MarisaContinued.logger.info("""SatelIllusPower : checkDrawPile : onAfterUseCard : ${card.cardID}""")
        checkDrawPile()
    }

    override fun atStartOfTurnPostDraw() {
        MarisaContinued.logger.info("SatelIllusPower : checkDrawPile : atStartOfTurnPostDraw ")
        checkDrawPile()
    }

    override fun updateDescription() {
        description = descriptions[0] + amount + descriptions[1]
    }

    companion object {
        const val POWER_ID = "marisa:SatelIllusPower"
    }
}
