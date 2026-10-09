package marisa.relics

import com.megacrit.cardcrawl.actions.common.HealAction
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.AbstractCard.CardType
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.relics.AbstractRelic
import marisa.MarisaContinued
import marisa.abstracts.MarisaRelic
import marisa.abstracts.relicTexture

class BreadOfAWashokuLover : MarisaRelic(ID, "bread_s", RelicTier.UNCOMMON, LandingSound.FLAT) {
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
            addToBot(
                RelicAboveCreatureAction(AbstractDungeon.player, this)
            )
            addToBot(
                HealAction(AbstractDungeon.player, AbstractDungeon.player, 1)
            )
        }
        if (counter >= 13) {
            MarisaContinued.logger.info("BreadOfAWashokuLover : onExhaust : Using Up")
            flash()
            addToBot(
                RelicAboveCreatureAction(AbstractDungeon.player, this)
            )
            img = relicTexture(USED_IMG)
            AbstractDungeon.player.increaseMaxHp(13, true)
            usedUp()
            counter = -2
        }
    }

    companion object {
        const val ID = "marisa:BreadOfAWashokuLover"
        private const val USED_IMG = "usedBread_s"
    }
}
