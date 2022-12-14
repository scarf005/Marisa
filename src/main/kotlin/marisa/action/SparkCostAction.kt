package marisa.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import marisa.cards.FinalSpark

class SparkCostAction : AbstractGameAction() {
    init {
        actionType = ActionType.CARD_MANIPULATION
        duration = Settings.ACTION_DUR_FAST
    }

    override fun update() {
        isDone = false
        for (c in AbstractDungeon.player.discardPile.group) {
            (c as? FinalSpark)?.updateCost(-1)
        }
        for (c in AbstractDungeon.player.drawPile.group) {
            (c as? FinalSpark)?.updateCost(-1)
        }
        for (c in AbstractDungeon.player.hand.group) {
            (c as? FinalSpark)?.updateCost(-1)
        }
        isDone = true
    }
}
