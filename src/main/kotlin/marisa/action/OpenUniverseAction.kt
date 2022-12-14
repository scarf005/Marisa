package marisa.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.common.DrawCardAction
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import marisa.MarisaContinued
import marisa.MarisaContinued.Companion.randomMarisaCard

class OpenUniverseAction(draw: Int, private val upgraded: Boolean) : AbstractGameAction() {
    private val p: AbstractPlayer
    private val draw: Int

    init {
        duration = Settings.ACTION_DUR_FAST
        p = AbstractDungeon.player
        this.draw = draw
    }

    override fun update() {
        isDone = false
        MarisaContinued.logger.info("OpenUniverseAction : generating cards")
        for (i in 0..4) {
            val card = randomMarisaCard
            val rand = if (upgraded) 30 else 20
            val res = AbstractDungeon.miscRng.random(0, 99)
            MarisaContinued.logger.info("OpenUniverseAction : random res : $res")
            if (res < rand) {
                MarisaContinued.logger.info("OpenUniverseAction : Upgrading card ")
                card.upgrade()
            }
            MarisaContinued.logger.info("OpenUniverseAction : adding : " + card.cardID)
            addToBot(
                MakeTempCardInDrawPileAction(card, 1, true, true)
            )
        }
        MarisaContinued.logger.info("OpenUniverseAction : shuffling")
        p.drawPile.shuffle()
        for (r in p.relics) {
            r.onShuffle()
        }
        MarisaContinued.logger.info("OpenUniverseAction : drawing")
        addToBot(
            DrawCardAction(p, draw)
        )
        MarisaContinued.logger.info("OpenUniverseAction : done")
        isDone = true
    }
}
