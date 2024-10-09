package marisa.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.cards.status.Burn
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.relics.ChemicalX
import com.megacrit.cardcrawl.ui.panels.EnergyPanel
import marisa.countRelic
import marisa.exhaust
import marisa.fx.MeteoricShowerEffect
import marisa.p
import marisa.partitionByType

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
            val cards = AbstractDungeon.handCardSelectScreen.selectedCards.group
            val (regular, burns) = cards.partitionByType<Burn>()
            val cnt = regular.size * 2 + burns.size * 3

            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true
            cards.forEach { it.exhaust() }
            cards.clear()

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
        private val uiStrings = CardCrawlGame.languagePack.getUIString("ExhaustAction")
        val TEXT = uiStrings.TEXT
    }
}
