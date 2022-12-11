package marisa.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.GameActionManager
import com.megacrit.cardcrawl.actions.common.GainBlockAction
import com.megacrit.cardcrawl.actions.utility.WaitAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon

class BlackFlareStarAction(block: Int) : AbstractGameAction() {
    private val blc: Int

    init {
        setValues(AbstractDungeon.player, AbstractDungeon.player, -1)
        actionType = ActionType.CARD_MANIPULATION
        blc = block
    }

    override fun update() {
        if (duration == 0.5f) {
            AbstractDungeon.handCardSelectScreen.open("Discard", 99, true, true)
            AbstractDungeon.actionManager.addToBottom(WaitAction(0.25f))
            tickDuration()
            return
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            if (!AbstractDungeon.handCardSelectScreen.selectedCards.group.isEmpty()) {
                val cnt = AbstractDungeon.handCardSelectScreen.selectedCards.group.size
                AbstractDungeon.actionManager.addToTop(
                    GainBlockAction(source, source, blc * cnt)
                )
                for (c in AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                    AbstractDungeon.player.hand.moveToDiscardPile(c)
                    GameActionManager.incrementDiscard(false)
                    c.triggerOnManualDiscard()
                }
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true
        }
        tickDuration()
    }
}
