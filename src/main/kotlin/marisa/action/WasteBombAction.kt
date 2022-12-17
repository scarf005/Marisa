package marisa.action

import com.badlogic.gdx.math.MathUtils
import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.utility.WaitAction
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect
import marisa.MarisaContinued
import marisa.powers.Marisa.TempStrengthLoss

class WasteBombAction(private val target: AbstractCreature?, dmg: Int, numTimes: Int, stacks: Int) :
    AbstractGameAction() {
    private val damage: Int
    private var num: Int
    private val stacks: Int
    private val info: DamageInfo

    init {
        actionType = ActionType.DAMAGE
        duration = Settings.ACTION_DUR_FAST
        damage = dmg
        num = numTimes
        this.stacks = stacks
        info = DamageInfo(AbstractDungeon.player, damage, DamageType.NORMAL)
    }

    override fun update() {
        if (target == null) {
            isDone = true
            return
        }
        if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
            AbstractDungeon.actionManager.clearPostCombatActions()
            isDone = true
            return
        }
        if (this.target == null) {
            MarisaContinued.logger.info("WasteBombAction : error : target == null !")
            isDone = true
            return
        }
        if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
            AbstractDungeon.actionManager.clearPostCombatActions()
            isDone = true
            return
        }
        if (this.target.currentHealth > 0) {
            AbstractDungeon.effectList.add(
                FlashAtkImgEffect(
                    this.target.hb.cX, this.target.hb.cY, attackEffect
                )
            )
            var tmp = info.output.toFloat()
            for (p in target.powers) {
                tmp = p.atDamageReceive(tmp, info.type)
                if (info.base != tmp.toInt()) {
                    info.isModified = true
                }
            }
            for (p in target.powers) {
                tmp = p.atDamageFinalReceive(tmp, info.type)
                if (info.base != tmp.toInt()) {
                    info.isModified = true
                }
            }
            info.output = MathUtils.floor(tmp)
            if (info.output < 0) {
                info.output = 0
            }
            this.target.damage(info)
            if (!this.target.isDeadOrEscaped && !this.target.isDying) {
                AbstractDungeon.actionManager.addToBottom(
                    ApplyPowerAction(
                        this.target,
                        AbstractDungeon.player,
                        TempStrengthLoss(this.target, stacks),
                        stacks
                    )
                )
            }
            if (num > 1 && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                num--
                AbstractDungeon.actionManager.addToTop(
                    WasteBombAction(
                        AbstractDungeon.getMonsters().getRandomMonster(true),
                        damage,
                        num,
                        stacks
                    )
                )
            }
        }
        AbstractDungeon.actionManager.addToTop(WaitAction(0.2f))
        isDone = true
    }
}
