package marisa.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.GameActionManager
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import marisa.MarisaMod

class OccultationAction : AbstractGameAction() {
    private val p: AbstractPlayer

    init {
        actionType = ActionType.CARD_MANIPULATION
        duration = Settings.ACTION_DUR_FAST
        p = AbstractDungeon.player
    }

    override fun update() {
        if (AbstractDungeon.player.drawPile.group.isEmpty()) {
            return
        }
        val cards = AbstractDungeon.player.drawPile.group
        //int cnt = 0;
        MarisaMod.logger.info("Draw pile:" + cards.size)
        while (!p.drawPile.isEmpty) {
            val c = p.drawPile.topCard
            p.drawPile.moveToDiscardPile(c)
            GameActionManager.incrementDiscard(false)
            c.triggerOnManualDiscard()
            p.drawPile.removeCard(c)

            //cnt++;
        }
        //AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, cnt));
        isDone = true
    }
}
