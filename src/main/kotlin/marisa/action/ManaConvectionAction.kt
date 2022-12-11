package marisa.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.common.GainEnergyAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon

class ManaConvectionAction(number: Int) : AbstractGameAction() {
    private val p: AbstractPlayer
    private val num: Int

    init {
        actionType = ActionType.CARD_MANIPULATION
        p = AbstractDungeon.player
        duration = Settings.ACTION_DUR_FAST
        num = number
    }

    override fun update() {
        if (duration == Settings.ACTION_DUR_FAST) {
            if (p.hand.size() == 0) {
                isDone = true
                return
            }
            AbstractDungeon.handCardSelectScreen.open(TEXT[0], num, true, true)
            tickDuration()
            return
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            var cnt = 0
            for (c in AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                cnt += 1
                /*
        if ((c instanceof Burn)) {
          cnt++;
        }
        */p.hand.moveToExhaustPile(c)
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear()
            if (cnt > 0) {
                AbstractDungeon.actionManager.addToTop(
                    GainEnergyAction(cnt)
                )
            }
        }
        tickDuration()
    }

    companion object {
        private val uiStrings = CardCrawlGame.languagePack
            .getUIString("ExhaustAction")
        private val TEXT = uiStrings.TEXT
    }
}
