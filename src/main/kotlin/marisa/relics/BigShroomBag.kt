package marisa.relics

import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.relics.AbstractRelic
import marisa.abstracts.MarisaRelic

class BigShroomBag : MarisaRelic(ID, "BigShroomBag", RelicTier.SPECIAL, LandingSound.FLAT) {
    override fun getUpdatedDescription(): String = DESCRIPTIONS[0]

    override fun makeCopy(): AbstractRelic = BigShroomBag()

    override fun onEquip() {
        AbstractDungeon.player.loseRelic(ShroomBag.ID)
    }

    companion object {
        const val ID = "marisa:BigShroomBag"
    }
}
