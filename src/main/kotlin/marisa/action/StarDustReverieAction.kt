package marisa.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import marisa.MarisaMod
import marisa.MarisaMod.Companion.randomMarisaCard

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
        MarisaMod.logger.info("StarDustReverieAction : player hand size : " + p.hand.size())
        if (!p.hand.isEmpty) {
            while (!p.hand.isEmpty) {
                val c = p.hand.topCard
                MarisaMod.logger.info("StarDustReverieAction : moving " + c.cardID)
                p.hand.moveToDeck(c, true)
                cnt++
                MarisaMod.logger.info("StarDustReverieAction : Counter : $cnt")
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
            MarisaMod.logger.info("StarDustReverieAction : adding " + c.cardID)
            if (upgraded) {
                c.upgrade()
            }
            MarisaMod.logger.info(
                "StarDustReverieAction : checking : Exhaust : " + c.exhaust +
                        " ; Ethereal : " + c.isEthereal +
                        " ; Upgraded : " + c.upgraded
            )
            AbstractDungeon.actionManager.addToBottom(
                MakeTempCardInHandAction(c, 1)
            )
        }
        /*
	    AbstractDungeon.actionManager.addToBottom(
	    		new HandCheckAction(this.upgraded)
	    		);
	    */isDone = true
    }
}
