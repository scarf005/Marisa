package marisa.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import marisa.MarisaContinued
import marisa.MarisaContinued.Companion.randomMarisaCard

class StarDustReverieAction(upgraded: Boolean) : AbstractGameAction() {
    private val p: AbstractPlayer
    private val upgraded: Boolean

    init {
        duration = Settings.ACTION_DUR_FAST
        this.upgraded = upgraded
        p = AbstractDungeon.player
    }

    override fun update() {
        isDone = false
        var cnt = 0
        MarisaContinued.logger.info("StarDustReverieAction : player hand size : " + p.hand.size())
        if (!p.hand.isEmpty) {
            while (!p.hand.isEmpty) {
                val c = p.hand.topCard
                MarisaContinued.logger.info("StarDustReverieAction : moving " + c.cardID)
                p.hand.moveToDeck(c, true)
                cnt++
                MarisaContinued.logger.info("StarDustReverieAction : Counter : $cnt")
            }
        } else {
            isDone = true
            return
        }
        p.drawPile.shuffle()
        for (r in p.relics) {
            r.onShuffle()
        }
        for (i in 0 until cnt) {
            val c = randomMarisaCard
            MarisaContinued.logger.info("""StarDustReverieAction : adding ${c.cardID}""")
            if (upgraded) {
                c.upgrade()
            }
            MarisaContinued.logger.info(
                """StarDustReverieAction : checking : Exhaust : ${c.exhaust} ; Ethereal : ${c.isEthereal} ; Upgraded : ${c.upgraded}"""
            )
            AbstractDungeon.actionManager.addToBottom(
                MakeTempCardInHandAction(c, 1)
            )
        }
        isDone = true
    }
}
