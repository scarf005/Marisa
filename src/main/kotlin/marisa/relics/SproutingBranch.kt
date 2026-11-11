package marisa.relics

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.powers.RegenPower
import com.megacrit.cardcrawl.relics.AbstractRelic
import com.megacrit.cardcrawl.relics.DeadBranch
import marisa.MarisaContinued.Companion.isClassic
import marisa.abstracts.MarisaRelic
import marisa.p

class SproutingBranch : MarisaRelic(ID, "sproutingBranch", RelicTier.SPECIAL, LandingSound.FLAT) {
    override fun getUpdatedDescription(): String = DESCRIPTIONS[0]

    override fun makeCopy(): AbstractRelic = SproutingBranch()

    override fun onEquip() {
        AbstractDungeon.rareRelicPool.remove(DeadBranch.ID)
    }

    override fun atBattleStart() {
        marisa.addToBot(
            RelicAboveCreatureAction(p, this),
            ApplyPowerAction(p, p, RegenPower(p, if (isClassic) CLASSIC_REGEN else REGEN)),
        )
    }

    companion object {
        const val ID = "marisa:SproutingBranch"
        private const val REGEN = 4
        private const val CLASSIC_REGEN = 5
    }
}
