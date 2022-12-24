package marisa.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.core.Settings
import marisa.cards.FinalSpark
import marisa.cards.allGameCards

class SparkCostAction : AbstractGameAction() {
    init {
        actionType = ActionType.CARD_MANIPULATION
        duration = Settings.ACTION_DUR_FAST
    }

    override fun update() {
        isDone = false
        allGameCards().filterIsInstance<FinalSpark>().forEach { it.updateCost(-1) }
        isDone = true
    }
}
