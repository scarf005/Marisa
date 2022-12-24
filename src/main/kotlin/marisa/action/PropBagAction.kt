package marisa.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.relics.*
import marisa.MarisaContinued.Companion.logger
import marisa.powers.Marisa.PropBagPower
import marisa.random
import marisa.relics.AmplifyWand

class PropBagAction : AbstractGameAction() {
    init {
        actionType = ActionType.CARD_MANIPULATION
        duration = Settings.ACTION_DUR_FAST
    }

    override fun update() {
        val p = AbstractDungeon.player
        logger.info("PropBagAction : Checking for relics")

        val relics = listOf(
            MeatOnTheBone(), MummifiedHand(), BlueCandle(), AmplifyWand(),
            GremlinHorn(), MercuryHourglass(), Sundial(),
            // use atTurnStart to reset counter
            LetterOpener(), Shuriken(), Kunai(), OrnamentalFan(),
        )
            .onEach(AbstractRelic::atTurnStart)
            .filterNot { p.hasRelic(it.relicId) }
        // TODO: refactor with Arrow

        when (relics.size) {
            0 -> {
                logger.info("PropBagAction : No relic to give")
            }

            else -> {
                val relic = relics.random()
                logger.info("PropBagAction : Giving relic : $relic")
                addToBot(ApplyPowerAction(p, p, PropBagPower(p, relic)))
            }
        }
        isDone = true
    }
}
