package marisa.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.cards.AbstractCard.CardType
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.potions.*

class FungusSplashAction(Target: AbstractCreature?) : AbstractGameAction() {
    private val p: AbstractPlayer

    init {
        amount = 1
        p = AbstractDungeon.player
        setValues(Target, p, amount)
        duration = Settings.ACTION_DUR_FAST
        actionType = ActionType.EXHAUST
    }

    override fun update() {
        if (duration == Settings.ACTION_DUR_FAST) {
            if (p.hand.size() == 0) {
                isDone = true
                return
            }
            if (p.hand.size() == 1) {
                val c = p.hand.topCard
                usePotion(c.type)
                p.hand.moveToExhaustPile(c)
                return
            }
            AbstractDungeon.handCardSelectScreen.open(TEXT[0], amount, false, false)
            tickDuration()
            return
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (c in AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                usePotion(c.type)
                p.hand.moveToExhaustPile(c)
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true
        }
        tickDuration()
    }

    fun usePotion(type: CardType?) {
        val p: AbstractPotion
        when (type) {
            CardType.ATTACK -> {
                p = FearPotion()
                p.use(target)
            }

            CardType.SKILL -> {
                p = WeakenPotion()
                p.use(target)
            }

            CardType.POWER -> {
                p = PoisonPotion()
                p.use(target)
            }

            CardType.STATUS -> {
                p = FirePotion()
                p.use(target)
            }

            CardType.CURSE -> {
                p = SmokeBomb()
                if (p.canUse()) p.use(this.p)
            }

            else -> {}
        }
    }

    companion object {
        private val uiStrings = CardCrawlGame.languagePack.getUIString("ExhaustAction")
        val TEXT = uiStrings.TEXT
        var numExhausted = 0
    }
}
