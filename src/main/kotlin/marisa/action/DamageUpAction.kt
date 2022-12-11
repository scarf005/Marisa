package marisa.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.cards.AbstractCard.CardType
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import marisa.MarisaMod

class DamageUpAction(amount: Int) : AbstractGameAction() {
    init {
        actionType = ActionType.CARD_MANIPULATION
        duration = Settings.ACTION_DUR_FAST
        this.amount = amount
    }

    override fun update() {
        if (duration == Settings.ACTION_DUR_FAST) {
            for (c in AbstractDungeon.player.hand.group) {
                if (c.type == CardType.ATTACK) {
                    MarisaMod.logger.info("Milky Way Action : add " + amount + " damage to " + c.cardID)
                    c.baseDamage += amount
                    c.applyPowers()
                    c.flash()
                }
            }
        }
        tickDuration()
    }
}
