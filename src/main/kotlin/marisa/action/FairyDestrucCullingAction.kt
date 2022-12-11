package marisa.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.watcher.JudgementAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon

class FairyDestrucCullingAction(private val threshold: Int) : AbstractGameAction() {
    override fun update() {
        // TODO Auto-generated method stub
        isDone = false
        if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
            isDone = true
            return
        }
        for (m in AbstractDungeon.getCurrRoom().monsters.monsters) {
            addToBot(JudgementAction(m, threshold))
            /*
      if (m.currentHealth <= this.threshold) {
        if (m.hasPower("Intangible")) {
          ThMod.logger.info("FairyDestrucCullingAction : Intangible detected : " + m.id);
          AbstractPower removeMe = m.getPower("Intangible");
          removeMe.onRemove();
          m.powers.remove(removeMe);
          AbstractDungeon.onModifyPower();
        }
        if (m.hasPower("IntangiblePlayer")) {
          ThMod.logger.info("FairyDestrucCullingAction : IntangiblePlayer detected : " + m.id);
          AbstractPower removeMe = m.getPower("IntangiblePlayer");
          removeMe.onRemove();
          m.powers.remove(removeMe);
          AbstractDungeon.onModifyPower();
        }
        m.damage(
            new DamageInfo(
                AbstractDungeon.player,
                Integer.MAX_VALUE,
                DamageType.HP_LOSS
            )
        );
      }
        */
        }
        if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
            AbstractDungeon.actionManager.clearPostCombatActions()
        }
        isDone = true
    }
}
