package marisa.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.ModHelper
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.powers.FlightPower
import com.megacrit.cardcrawl.powers.MinionPower
import com.megacrit.cardcrawl.powers.SlowPower
import com.megacrit.cardcrawl.powers.StrengthPower
import marisa.MarisaContinued
import marisa.monsters.ZombieFairy
import marisa.powers.monsters.LimboContactPower

class SummonFairyAction(monster: AbstractMonster?) : AbstractGameAction() {
    init {
        source = monster
        duration = Settings.ACTION_DUR_XFAST
    }

    override fun update() {
        var count = 0
        for (m in AbstractDungeon.getMonsters().monsters) {
            if (m !== source && m is ZombieFairy) {
                if (m.isDying) {
                    MarisaContinued.logger.info("SummonFairyAction : reviving Fairy;")
                    m.turnNum = 0
                    AbstractDungeon.actionManager.addToTop(
                        ReviveFairyAction(m, source)
                    )
                    AbstractDungeon.actionManager.addToTop(
                        ApplyPowerAction(m, m, MinionPower(m))
                    )
                    AbstractDungeon.actionManager.addToTop(
                        ApplyPowerAction(m, m, LimboContactPower(m))
                    )
                    AbstractDungeon.actionManager.addToTop(
                        ApplyPowerAction(m, m, FlightPower(m, 99))
                    )
                    if (AbstractDungeon.player.hasRelic("Philosopher's Stone")) {
                        m.addPower(StrengthPower(m, 1))
                    }
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
                    MarisaContinued.logger.info("SummonFairyAction : done reviving Fairy;")
                    isDone = true
                    return
                }
                MarisaContinued.logger.info("SummonFairyAction : Alive Fairy found,increasing counter;")
                count++
                MarisaContinued.logger.info("SummonFairyAction : counter increased: $count")
            }
        }
        if (count < 4) {
            MarisaContinued.logger.info("SummonFairyAction : spawning Fairy;")
            AbstractDungeon.actionManager.addToTop(
                SpawnFairyAction(COORDINATE[count][0], COORDINATE[count][1])
            )
        }
        //AbstractDungeon.actionManager.addToTop(new FairyWraithAction());
        isDone = true
    }

    companion object {
        private const val pos0X = 210.0f
        private const val pos0Y = 10.0f
        private const val pos1X = -220.0f
        private const val pos1Y = 50.0f
        private const val pos2X = 180.0f
        private const val pos2Y = 320.0f
        private const val pos3X = -250.0f
        private const val pos3Y = 310.0f
        private val COORDINATE = arrayOf(
            floatArrayOf(pos0X, pos0Y), floatArrayOf(pos1X, pos1Y), floatArrayOf(pos2X, pos2Y), floatArrayOf(
                pos3X, pos3Y
            )
        )
    }
}
