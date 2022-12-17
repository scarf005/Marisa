package marisa.relics

import basemod.abstracts.CustomRelic
import com.megacrit.cardcrawl.actions.common.HealAction
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.AbstractCard.CardType
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.ImageMaster
import com.megacrit.cardcrawl.relics.AbstractRelic
import marisa.MarisaContinued

class BreadOfAWashokuLover : CustomRelic(
    ID,
    ImageMaster.loadImage(IMG),
    ImageMaster.loadImage(IMG_OTL),
    RelicTier.UNCOMMON,
    LandingSound.FLAT
) {
    init {
        usedUp = false
    }

    override fun getUpdatedDescription(): String = DESCRIPTIONS[0]

    override fun makeCopy(): AbstractRelic = BreadOfAWashokuLover()

    override fun onEquip() {
        counter = 0
    }

    override fun onExhaust(card: AbstractCard) {
        MarisaContinued.logger.info(
            "BreadOfAWashokuLover : onExhaust : this.usedUp :" + usedUp +
                    " ; this.counter : " + counter
        )
        if (usedUp || counter < 0) {
            return
        }
        if (card.type == CardType.CURSE || card.type == CardType.STATUS) {
            counter++
            flash()
            AbstractDungeon.actionManager.addToBottom(
                RelicAboveCreatureAction(AbstractDungeon.player, this)
            )
            AbstractDungeon.actionManager.addToBottom(
                HealAction(AbstractDungeon.player, AbstractDungeon.player, 1)
            )
        }
        if (counter >= 13) {
            MarisaContinued.logger.info("BreadOfAWashokuLover : onExhaust : Using Up")
            flash()
            AbstractDungeon.actionManager.addToBottom(
                RelicAboveCreatureAction(AbstractDungeon.player, this)
            )
            img = ImageMaster.loadImage(USED_IMG)
            AbstractDungeon.player.increaseMaxHp(13, true)
            usedUp()
            counter = -2
        }
    }

    companion object {
        const val ID = "BreadOfAWashokuLover"
        private const val IMG = "img/relics/bread_s.png"
        private const val IMG_OTL = "img/relics/outline/bread_s.png"
        private const val USED_IMG = "img/relics/usedBread_s.png"
    }
}
