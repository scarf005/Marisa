package marisa.relics

import basemod.abstracts.CustomRelic
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.ImageMaster
import com.megacrit.cardcrawl.relics.AbstractRelic
import com.megacrit.cardcrawl.rooms.AbstractRoom

class CatCart : CustomRelic(
    ID,
    ImageMaster.loadImage(IMG),
    ImageMaster.loadImage(IMG_OTL),
    RelicTier.SPECIAL,
    LandingSound.FLAT
) {
    init {
        counter = 0
    }

    override fun getUpdatedDescription(): String {
        return DESCRIPTIONS[0]
    }

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

    override fun makeCopy(): AbstractRelic {
        return CatCart()
    }

    companion object {
        const val ID = "CatCart"
        private const val IMG = "img/relics/CatCart.png"
        private const val IMG_OTL = "img/relics/outline/CatCart.png"
        private const val HEAL_PER_CHARGE = 4
    }
}
