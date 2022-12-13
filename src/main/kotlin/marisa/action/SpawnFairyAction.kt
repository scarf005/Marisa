package marisa.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.ModHelper
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.powers.FlightPower
import com.megacrit.cardcrawl.powers.MinionPower
import com.megacrit.cardcrawl.powers.SlowPower
import com.megacrit.cardcrawl.powers.StrengthPower
import marisa.monsters.ZombieFairy
import marisa.powers.monsters.LimboContactPower

//public class SpawnFairyAction
class SpawnFairyAction @JvmOverloads constructor(x: Float, y: Float, private val targetSlot: Int = -99) :
    AbstractGameAction() {
    private var used = false
    private val m = ZombieFairy(x, y)

    init {
        actionType = ActionType.SPECIAL
        duration = DURATION
        if (AbstractDungeon.player.hasRelic("Philosopher's Stone")) {
            m.addPower(StrengthPower(m, 1))
            AbstractDungeon.onModifyPower()
        }
    }

    override fun update() {
        if (!used) {
            m.init()
            m.applyPowers()
            if (targetSlot < 0) {
                AbstractDungeon.getCurrRoom().monsters.addSpawnedMonster(m)
            } else {
                AbstractDungeon.getCurrRoom().monsters.addMonster(targetSlot, m)
            }
            m.showHealthBar()
            if (ModHelper.isModEnabled("Lethality")) {
                AbstractDungeon.actionManager.addToBottom(
                    ApplyPowerAction(m, m, StrengthPower(m, 3), 3)
                )
            }
            if (ModHelper.isModEnabled("Time Dilation")) {
                AbstractDungeon.actionManager.addToBottom(
                    ApplyPowerAction(m, m, SlowPower(m, 0))
                )
            }
            AbstractDungeon.actionManager.addToTop(
                ApplyPowerAction(m, m, MinionPower(m))
            )
            AbstractDungeon.actionManager.addToTop(
                ApplyPowerAction(m, m, LimboContactPower(m))
            )
            AbstractDungeon.actionManager.addToTop(
                ApplyPowerAction(m, m, FlightPower(m, 99))
            )
            used = true
        }
        tickDuration()
    }

    companion object {
        private const val DURATION = 0.1f
    }
}
