package marisa.relics

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.powers.FrailPower
import com.megacrit.cardcrawl.powers.PoisonPower
import com.megacrit.cardcrawl.powers.VulnerablePower
import com.megacrit.cardcrawl.powers.WeakPower
import com.megacrit.cardcrawl.relics.AbstractRelic
import marisa.abstracts.MarisaRelic
import marisa.p
import marisa.powers.Marisa.ChargeUpPower
import marisa.random

class RampagingMagicTools : MarisaRelic(ID, "RamTool", RelicTier.BOSS, LandingSound.FLAT) {
    override fun getUpdatedDescription(): String = DESCRIPTIONS[0]

    override fun makeCopy(): AbstractRelic = RampagingMagicTools()

    override fun onEquip() {
        AbstractDungeon.player.energy.energyMaster += 1
    }

    override fun onUnequip() {
        AbstractDungeon.player.energy.energyMaster -= 1
    }

    private fun effects() = listOf(
        FrailPower(p, STACK_DEBUFF, false),
        WeakPower(p, STACK_DEBUFF, false),
        VulnerablePower(p, STACK_DEBUFF, false),
        PoisonPower(p, p, STACK_POISON),
        ChargeUpPower(p, STACK_CHARGE),
    )

    override fun atBattleStart() = marisa.addToBot(
        RelicAboveCreatureAction(p, this),
        ApplyPowerAction(p, p, effects().random()),
    )

    companion object {
        const val ID = "marisa:RampagingMagicTools"
        private const val STACK_DEBUFF = 1
        private const val STACK_POISON = 2
        private const val STACK_CHARGE = 8
    }
}
