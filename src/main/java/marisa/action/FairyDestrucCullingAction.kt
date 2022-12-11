package marisa.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.watcher.JudgementAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FairyDestrucCullingAction extends AbstractGameAction {

  private int threshold;

  public FairyDestrucCullingAction(int threshold) {
    this.threshold = threshold;
  }

  @Override
  public void update() {
    // TODO Auto-generated method stub
    this.isDone = false;
    if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
      this.isDone = true;
      return;
    }
    for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
      addToBot(new JudgementAction(m, this.threshold));
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
      AbstractDungeon.actionManager.clearPostCombatActions();
    }
    this.isDone = true;
  }
}
