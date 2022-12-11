package marisa.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.ModHelper
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent
import com.megacrit.cardcrawl.powers.FlightPower
import com.megacrit.cardcrawl.powers.MinionPower
import com.megacrit.cardcrawl.powers.SlowPower
import com.megacrit.cardcrawl.powers.StrengthPower
import com.megacrit.cardcrawl.vfx.TintEffect
import marisa.MarisaMod
import marisa.monsters.ZombieFairy
import marisa.powers.monsters.LimboContactPower

//public class ReviveFairyAction {
class ReviveFairyAction(target: AbstractMonster, source: AbstractCreature?) : AbstractGameAction() {
    init {
        this.setValues(target, source, 0)
        actionType = ActionType.SPECIAL
        /*
    target.addPower(new LimboContactPower(target));
    target.addPower(new FlightPower(target, 99));
    */if (AbstractDungeon.player.hasRelic("Philosopher's Stone")) {
            target.addPower(StrengthPower(target, 1))
            AbstractDungeon.onModifyPower()
        }
    }

    override fun update() {
        if (target is ZombieFairy) {
            MarisaMod.logger.info("ReviveFairyAction : setting values;")
            val fairy = target as ZombieFairy
            target.isDying = false
            target.heal(target.maxHealth, false)
            target.healthBarRevivedEvent()
            fairy.deathTimer = 0.0f
            fairy.tint = TintEffect()
            fairy.tintFadeOutCalled = false
            fairy.isDead = false
            //this.target.powers.clear();
            fairy.revive()
            fairy.turnNum = 0
            MarisaMod.logger.info("ReviveFairyAction : applying powers;")
            AbstractDungeon.actionManager.addToTop(
                ApplyPowerAction(target, target, MinionPower(target))
            )
            AbstractDungeon.actionManager.addToTop(
                ApplyPowerAction(target, target, LimboContactPower(target))
            )
            AbstractDungeon.actionManager.addToTop(
                ApplyPowerAction(target, target, FlightPower(fairy, 99))
            )
            //fairy.usePreBattleAction();
            if (ModHelper.isModEnabled("Lethality")) {
                AbstractDungeon.actionManager.addToBottom(
                    ApplyPowerAction(target, target, StrengthPower(target, 3), 3)
                )
            }
            if (ModHelper.isModEnabled("Time Dilation")) {
                AbstractDungeon.actionManager.addToBottom(
                    ApplyPowerAction(target, target, SlowPower(target, 0))
                )
            }
            fairy.intent = Intent.NONE
            fairy.rollMove()
            MarisaMod.logger.info("ReviveFairyAction : done applying power;")
        } else {
            MarisaMod.logger.info("ReviveFairyAction : error : target is not ZombieFairy : " + target.name)
        }
        isDone = true
    }
}
