package marisa.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon

class RefreshHandAction : AbstractGameAction() {
    override fun update() {
        AbstractDungeon.player.hand.refreshHandLayout()
        isDone = true
    }
}
