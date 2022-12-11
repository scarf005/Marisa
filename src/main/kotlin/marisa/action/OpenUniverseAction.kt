package marisa.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.common.DrawCardAction
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import marisa.MarisaMod
import marisa.MarisaMod.Companion.randomMarisaCard

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
        MarisaMod.logger.info("OpenUniverseAction : generating cards")
        for (i in 0..4) {
            val card = randomMarisaCard
            val rand = if (upgraded) 30 else 20
            val res = AbstractDungeon.miscRng.random(0, 99)
            MarisaMod.logger.info("OpenUniverseAction : random res : $res")
            if (res < rand) {
                MarisaMod.logger.info("OpenUniverseAction : Upgrading card ")
                card.upgrade()
            }
            MarisaMod.logger.info("OpenUniverseAction : adding : " + card.cardID)
            AbstractDungeon.actionManager.addToBottom(
                MakeTempCardInDrawPileAction(card, 1, true, true)
            )
        }
        MarisaMod.logger.info("OpenUniverseAction : shuffling")
        p.drawPile.shuffle()
        for (r in p.relics) {
            r.onShuffle()
        }
        MarisaMod.logger.info("OpenUniverseAction : drawing")
        AbstractDungeon.actionManager.addToBottom(
            DrawCardAction(p, draw)
        )
        MarisaMod.logger.info("OpenUniverseAction : done")
        isDone = true
    }
}
