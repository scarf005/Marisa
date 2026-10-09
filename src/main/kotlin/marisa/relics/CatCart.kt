package marisa.relics

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.relics.AbstractRelic
import com.megacrit.cardcrawl.rooms.AbstractRoom
import marisa.abstracts.MarisaRelic

class CatCart : MarisaRelic(ID, "CatCart", RelicTier.SPECIAL, LandingSound.FLAT) {
    init {
        counter = 0
    }

    override fun getUpdatedDescription(): String = DESCRIPTIONS[0]

    override fun onEnterRoom(room: AbstractRoom) {
        flash()
        counter++
    }

    override fun onTrigger() {
        if (counter > 0) {
            flash()
            AbstractDungeon.actionManager.addToTop(
                RelicAboveCreatureAction(AbstractDungeon.player, this)
            )
            val healAmt = counter * HEAL_PER_CHARGE
            AbstractDungeon.player.heal(healAmt, true)
            counter = 0
        }
    }

    override fun makeCopy(): AbstractRelic = CatCart()

    companion object {
        const val ID = "marisa:CatCart"
        private const val HEAL_PER_CHARGE = 4
    }
}
