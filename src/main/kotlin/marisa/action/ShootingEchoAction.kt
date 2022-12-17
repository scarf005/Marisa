package marisa.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType
import com.megacrit.cardcrawl.cards.status.Burn
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import marisa.MarisaContinued

class ShootingEchoAction(private val card: AbstractCard) : AbstractGameAction() {
    var player: AbstractPlayer = AbstractDungeon.player
    private val damageTypeForTurn: DamageType

    init {
        damageTypeForTurn = DamageType.NORMAL
        duration = Settings.ACTION_DUR_FAST
        actionType = ActionType.EXHAUST
    }

    override fun update() {
        if (duration == Settings.ACTION_DUR_FAST) {
            if (player.hand.isEmpty) {
                isDone = true
                return
            }
            if (player.hand.size() == 1) {
                MarisaContinued.logger.info("ShootingEchoAction : player hand size is 1")
                val c = player.hand.topCard
                if (c is Burn) {
                    AbstractDungeon.actionManager.addToBottom(
                        DiscardToHandAction(card)
                    )
                }
                player.hand.moveToExhaustPile(c)
                isDone = true
                return
            } else {
                MarisaContinued.logger.info("ShootingEchoAction : opening hand card select")
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, false)
            }
            tickDuration()
            return
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            card.returnToHand = false
            for (c in AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                if (c is Burn) {
                    /*
            AbstractDungeon.actionManager.addToBottom(
                new DiscardToHandAction(card)
            );
            */
                    card.returnToHand = true
                    /*
            addToBot(
                new RefreshHandAction()
            );
            */
                }
                player.hand.moveToExhaustPile(c)
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear()
            AbstractDungeon.gridSelectScreen.selectedCards.clear()
            AbstractDungeon.player.hand.refreshHandLayout()
            isDone = true
            return
        }
        tickDuration()
    }

    companion object {
        private val uiStrings = CardCrawlGame.languagePack
            .getUIString("ExhaustAction")
        val TEXT = uiStrings.TEXT
    }
}
