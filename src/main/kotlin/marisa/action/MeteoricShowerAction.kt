package marisa.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.relics.ChemicalX
import com.megacrit.cardcrawl.ui.panels.EnergyPanel
import marisa.countRelic
import marisa.exhaustBurns
import marisa.fx.MeteoricShowerEffect
import marisa.p


class MeteoricShowerAction(val energyOnUse: Int, val dmg: Int, val freeToPlay: Boolean) :
    AbstractGameAction() {
    init {
        actionType = ActionType.CARD_MANIPULATION
        duration = Settings.ACTION_DUR_FAST
    }

    override fun update() {
        val num = energyOnUse + 1 + countRelic(ChemicalX.ID) * 2

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
            val cnt =
                2 * AbstractDungeon.handCardSelectScreen.selectedCards.group.exhaustBurns()
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear()
            addToBot(MeteoricShowerEffect.toVfx(cnt))
            addToBot(RandomDamageAction(cnt) { dmg })
            AbstractDungeon.gridSelectScreen.selectedCards.clear()
            AbstractDungeon.player.hand.refreshHandLayout()
        }
        if (!freeToPlay) {
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
