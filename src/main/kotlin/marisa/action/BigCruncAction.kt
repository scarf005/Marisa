package marisa.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.common.DrawCardAction
import com.megacrit.cardcrawl.actions.common.GainEnergyAction
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import marisa.MarisaContinued

class BigCruncAction(upgraded: Boolean) : AbstractGameAction() {
    private var upg = false

    init {
        actionType = ActionType.CARD_MANIPULATION
        duration = Settings.ACTION_DUR_FAST
        upg = upgraded
    }

    override fun update() {
        isDone = false
        val p = AbstractDungeon.player
        var div = 5
        if (upg) {
            div--
        }
        var cnt = p.discardPile.size() / 2
        var total = cnt
        p.discardPile.shuffle()
        MarisaContinued.logger.info(
            "BigCruncAction : Discard size : " + p.discardPile.size()
                    + " ; counter : " + cnt
        )
        if (cnt > 0) {
            for (i in 0 until cnt) {
                p.discardPile.moveToExhaustPile(p.discardPile.topCard)
            }
        }
        p.drawPile.shuffle()
        cnt = p.drawPile.size() / 2
        MarisaContinued.logger.info(
            "BigCruncAction : Draw size : " + p.drawPile.size()
                    + " ; counter : " + cnt
        )
        total += cnt
        if (cnt > 0) {
            for (i in 0 until cnt) {
                p.drawPile.moveToExhaustPile(p.drawPile.topCard)
            }
        }
        val res = total / div
        MarisaContinued.logger.info(
            "BigCruncAction : total :" + total
                    + " ; res : " + res
        )
        if (res > 0) {
            AbstractDungeon.actionManager.addToBottom(
                GainEnergyAction(res)
            )
            AbstractDungeon.actionManager.addToBottom(
                DrawCardAction(source, res)
            )
        }
        isDone = true
    }
}
