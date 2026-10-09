package marisa.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.watcher.JudgementAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon

class FairyDestrucCullingAction(private val threshold: Int) : AbstractGameAction() {
    override fun update() {
        isDone = false
        if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
            isDone = true
            return
        }
        for (m in AbstractDungeon.getCurrRoom().monsters.monsters) {
            addToBot(JudgementAction(m, threshold))
        }
        if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
            AbstractDungeon.actionManager.clearPostCombatActions()
        }
        isDone = true
    }
}
