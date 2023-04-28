package marisa.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.cards.status.Burn
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.ui.panels.EnergyPanel

class MeteoricShowerAction(number: Int, damage: Int, freeToPlay: Boolean) : AbstractGameAction() {
    private val p: AbstractPlayer
    private val num: Int
    private val dmg: Int
    private val f2p: Boolean

    init {
        actionType = ActionType.CARD_MANIPULATION
        p = AbstractDungeon.player
        duration = Settings.ACTION_DUR_FAST
        num = number
        dmg = damage
        f2p = freeToPlay
    }

    override fun update() {
        if (duration == Settings.ACTION_DUR_FAST) {
            if (p.hand.isEmpty) {
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
                cnt += 2
                if (c is Burn) {
                    cnt++
                }
                p.hand.moveToExhaustPile(c)
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear()
            addToTop(RandomDamageAction(cnt) { dmg })
            AbstractDungeon.gridSelectScreen.selectedCards.clear()
            AbstractDungeon.player.hand.refreshHandLayout()
        }
        if (!f2p) {
            p.energy.use(EnergyPanel.totalCount)
        }
        tickDuration()
    }

    companion object {
        private val uiStrings = CardCrawlGame.languagePack
            .getUIString("ExhaustAction")
        val TEXT = uiStrings.TEXT
    }
}
