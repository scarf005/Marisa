package marisa.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import marisa.MarisaContinued

@Suppress("unused")
class HandCheckAction : AbstractGameAction() {
    init {
        actionType = ActionType.CARD_MANIPULATION
        duration = Settings.ACTION_DUR_FAST
    }

    override fun update() {
        if (duration == Settings.ACTION_DUR_FAST) {
            AbstractDungeon.player.hand.group.forEach { c ->
                c.exhaust = true
                c.isEthereal = true
                MarisaContinued.logger.info(
                    """HandCheckAction : id : ${c.cardID} ; 
                                |cost for turn : ${c.costForTurn} ; 
                                |is Ethereal : ${c.isEthereal} ; 
                                |Exhaust : ${c.exhaust}""".trimMargin()
                )
            }
        }
        tickDuration()
        return
    }
}
