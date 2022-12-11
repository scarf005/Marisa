package marisa.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.powers.FlightPower
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase
import marisa.monsters.ZombieFairy
import marisa.powers.monsters.LimboContactPower

class FairyWraithAction : AbstractGameAction() {
    init {
        duration = Settings.ACTION_DUR_XFAST
    }

    override fun update() {
        if (AbstractDungeon.getCurrRoom().phase != RoomPhase.COMBAT) {
            isDone = true
            return
        }
        isDone = false
        for (m in AbstractDungeon.getMonsters().monsters) {
            if (m is ZombieFairy) {
                AbstractDungeon.actionManager.addToTop(
                    ApplyPowerAction(m, m, LimboContactPower(m))
                )
                AbstractDungeon.actionManager.addToTop(
                    ApplyPowerAction(m, m, FlightPower(m, 99))
                )
            }
        }
        isDone = true
    }
}
