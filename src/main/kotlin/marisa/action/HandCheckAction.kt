package marisa.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import marisa.MarisaContinued

class HandCheckAction(upgraded: Boolean) : AbstractGameAction() {
    init {
        actionType = ActionType.CARD_MANIPULATION
        duration = Settings.ACTION_DUR_FAST
    }

    override fun update() {
        if (duration == Settings.ACTION_DUR_FAST) {
            for (c in AbstractDungeon.player.hand.group) {
                c.exhaust = true
                c.isEthereal = true
                MarisaContinued.logger.info(
                    "HandCheckAction : id : " + c.cardID +
                            " ; cost for turn : " + c.costForTurn +
                            " ; is Ethereal : " + c.isEthereal +
                            " ; Exhaust : " + c.exhaust
                )
            }
        }
        tickDuration()
        return
    }
}
