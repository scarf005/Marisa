package marisa.powers.Marisa

import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.AbstractCard.CardType
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.powers.AbstractPower
import marisa.MarisaContinued
import marisa.action.ConsumeChargeUpAction
import marisa.cards.derivations.Exhaustion_MRS
import marisa.relics.SimpleLauncher
import marisa.texture
import kotlin.math.pow

class ChargeUpPower(
    owner: AbstractCreature?, amount: Int
) : AbstractPower() {
    /** Stacks per doubling of attack damage. */
    private val threshold get() = if (AbstractDungeon.player.hasRelic(SimpleLauncher.ID)) IMPR_STACK else ACT_STACK
    private val doublings get() = amount / threshold
    private val multiplier get() = 2.0.pow(doublings)

    init {
        name = NAME
        ID = POWER_ID
        this.owner = owner
        this.amount = if (isExhausted()) 0 else amount
        type = PowerType.BUFF
        updateDescription()
        img = texture("marisa/img/powers/generator.png")
    }

    override fun stackPower(stackAmount: Int) {
        if (stackAmount > 0 && isExhausted()) return
        fontScale = 8.0f
        amount = (amount + stackAmount).coerceAtLeast(0)
    }

    override fun updateDescription() {
        description = if (doublings > 0) {
            "${DESCRIPTIONS[0]}$amount${DESCRIPTIONS[1]},${DESCRIPTIONS[2]}${multiplier.toInt()}${DESCRIPTIONS[3]}"
        } else {
            "${DESCRIPTIONS[0]}$amount${DESCRIPTIONS[1]}."
        }
    }

    /** Whether the next attack is multiplied and spends the stacks. */
    private val isCharged get() = doublings > 0 && !owner.hasPower(OneTimeOffPlusPower.POWER_ID) && !isExhausted()

    override fun onAfterCardPlayed(card: AbstractCard) {
        if (isCharged && card.type == CardType.ATTACK) {
            MarisaContinued.logger.info("ChargeUpPower : onPlayCard : consuming stacks for :" + card.cardID)
            flash()
            AbstractDungeon.actionManager.addToTop(ConsumeChargeUpAction(doublings * threshold))
        }
    }

    override fun atDamageFinalGive(damage: Float, type: DamageType): Float =
        if (isCharged && type == DamageType.NORMAL) (damage * multiplier).toFloat() else damage

    private fun isExhausted() = AbstractDungeon.player.hand.group.any { it is Exhaustion_MRS }

    companion object {
        const val POWER_ID = "marisa:ChargeUpPower"
        private val powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID)
        val NAME = powerStrings.NAME
        val DESCRIPTIONS = powerStrings.DESCRIPTIONS
        private const val ACT_STACK = 8
        private const val IMPR_STACK = 6
    }
}
