package marisa.action;

import marisa.MarisaMod;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import marisa.powers.Marisa.TempStrengthLoss;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class WasteBombAction
    extends AbstractGameAction {

  private int damage;
  private int num;
  private int stacks;
  private AbstractCreature target;
  private DamageInfo info;

  public WasteBombAction(AbstractCreature target, int dmg, int numTimes, int stacks) {
    this.actionType = AbstractGameAction.ActionType.DAMAGE;
    this.duration = Settings.ACTION_DUR_FAST;
    this.damage = dmg;
    this.target = target;
    this.num = numTimes;
    this.stacks = stacks;
    this.info = new DamageInfo(AbstractDungeon.player, this.damage, DamageType.NORMAL);
  }

  public void update() {
    if (target == null) {
      this.isDone = true;
      return;
    }

    if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
      AbstractDungeon.actionManager.clearPostCombatActions();
      this.isDone = true;
      return;
    }

    if (this.target == null) {
      MarisaMod.logger.info("WasteBombAction : error : target == null !");
      this.isDone = true;
      return;
    }
    if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
      AbstractDungeon.actionManager.clearPostCombatActions();
      this.isDone = true;
      return;
    }
    if (this.target.currentHealth > 0) {
      AbstractDungeon.effectList.add(
          new FlashAtkImgEffect(
              this.target.hb.cX, this.target.hb.cY, this.attackEffect
          )
      );

      float tmp = info.output;
      for (AbstractPower p : target.powers) {
        tmp = p.atDamageReceive(tmp, info.type);
        if (info.base != (int) tmp) {
          info.isModified = true;
        }
      }
      for (AbstractPower p : target.powers) {
        tmp = p.atDamageFinalReceive(tmp, info.type);
        if (info.base != (int) tmp) {
          info.isModified = true;
        }
      }
      info.output = MathUtils.floor(tmp);
      if (info.output < 0) {
        info.output = 0;
      }

      this.target.damage(this.info);

      if ((!this.target.isDeadOrEscaped()) && (!this.target.isDying)) {
        AbstractDungeon.actionManager.addToBottom(
            new ApplyPowerAction(
                this.target,
                AbstractDungeon.player,
                new TempStrengthLoss(this.target, this.stacks),
                this.stacks
            )
        );
      }

      if ((this.num > 1) && (!AbstractDungeon.getMonsters().areMonstersBasicallyDead())) {
        this.num--;
        AbstractDungeon.actionManager.addToTop(
            new WasteBombAction(
                AbstractDungeon.getMonsters().getRandomMonster(true),
                this.damage,
                this.num,
                this.stacks
            )
        );
      }
    }

    AbstractDungeon.actionManager.addToTop(new

        WaitAction(0.2F));

    this.isDone = true;
  }
}
