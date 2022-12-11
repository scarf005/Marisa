package marisa.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon

class DiscToHandRandAction : AbstractGameAction() {
    init {
        actionType = ActionType.CARD_MANIPULATION
        duration = Settings.ACTION_DUR_FAST
    }

    override fun update() {
        val p = AbstractDungeon.player
        if (p.discardPile.isEmpty) {
            isDone = true
            return
        } else {
            val rng = AbstractDungeon.miscRng.random(0, p.discardPile.size() - 1)
            val card = p.discardPile.group[rng]
            p.hand.addToHand(card)
            card.lighten(false)
            p.discardPile.removeCard(card)
            p.hand.refreshHandLayout()
            isDone = true
            return
        }
    }
}
