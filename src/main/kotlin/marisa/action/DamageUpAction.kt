package marisa.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.AbstractCard.CardType
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import marisa.runThenDone

class DamageUpAction(amount: Int) : AbstractGameAction() {
    init {
        actionType = ActionType.CARD_MANIPULATION
        duration = Settings.ACTION_DUR_FAST
        this.amount = amount
    }

    private fun upgradeCard(card: AbstractCard) = card.apply {
        baseDamage += amount
        isDamageModified = true
        applyPowers()
        flash()
    }

    override fun update() = runThenDone {
        AbstractDungeon.player.hand.group
            .filter { it.type == CardType.ATTACK }
            .forEach(::upgradeCard)
        tickDuration()
    }
}
