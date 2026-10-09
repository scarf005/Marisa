package marisa.powers.Marisa

import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.relics.AbstractRelic
import marisa.MarisaContinued
import marisa.abstracts.MarisaPower

class PropBagPower(owner: AbstractCreature?, r: AbstractRelic) : MarisaPower(POWER_ID, owner, -1, "diminish") {
    private val r: AbstractRelic
    private val p: AbstractPlayer
    private val rName: String

    init {
        ID = POWER_ID + IdOffset
        IdOffset++
        this.r = r
        p = AbstractDungeon.player
        rName = r.name
        MarisaContinued.logger.info("PropBagPower : Granting relic : $rName")
        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(
            Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f, r
        )
        r.atBattleStart()
        updateDescription()
    }

    override fun stackPower(stackAmount: Int) {}
    override fun onVictory() {
        p.loseRelic(r.relicId)
    }

    override fun updateDescription() {
        description = descriptions[0] + rName + descriptions[1]
    }

    companion object {
        const val POWER_ID = "marisa:PropBagPower"
        private var IdOffset = 0
    }
}
