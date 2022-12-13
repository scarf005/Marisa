package marisa.powers.Marisa

import com.badlogic.gdx.graphics.Texture
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.AbstractCard.CardType
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.powers.AbstractPower
import marisa.MarisaMod
import marisa.action.ConsumeChargeUpAction
import marisa.cards.derivations.Exhaustion_MRS
import marisa.relics.SimpleLauncher
import kotlin.math.pow

class ChargeUpPower(owner: AbstractCreature?, amount: Int) : AbstractPower() {
    private var cnt: Int
    private var stc = 0

    init {
        name = NAME
        ID = POWER_ID
        this.owner = owner
        if (ExhaustionCheck()) {
            this.amount = 0
        } else {
            this.amount = amount
        }
        type = PowerType.BUFF
        updateDescription()
        img = Texture("img/powers/generator.png")
        divider
        cnt = this.amount / stc
    }

    override fun stackPower(stackAmount: Int) {
        if (stackAmount > 0) {
            if (ExhaustionCheck()) {
                return
            }
        }
        fontScale = 8.0f
        amount += stackAmount
        if (amount <= 0) {
            amount = 0
        }
        divider
        /*
    ThMod.logger.info(
        "ChargeUpPower : Checking stack divider :"
            + this.stc
            + " ; Checking stack number :"
            + this.amount
    );
*/cnt = amount / stc
    }

    override fun updateDescription() {
        description = if (cnt > 0) {
            """${DESCRIPTIONS[0]}$amount${DESCRIPTIONS[1]},${DESCRIPTIONS[2]}${
                Math.pow(2.0, cnt.toDouble()).toInt()
            }${DESCRIPTIONS[3]}"""
        } else {
            """${DESCRIPTIONS[0]}$amount${DESCRIPTIONS[1]}."""
        }
    }

    override fun onAfterCardPlayed(card: AbstractCard) {
        if (owner.hasPower(OneTimeOffPlusPower.POWER_ID) || ExhaustionCheck()) {
            return
        }
        if (cnt > 0 && card.type == CardType.ATTACK) {
            MarisaMod.logger.info("ChargeUpPower : onPlayCard : consuming stacks for :" + card.cardID)
            flash()
            divider
            /*
      ThMod.logger.info("ChargeUpPower : onPlayCard :"
          + " Checking stack number : "
          + this.stc
          + " ; Checking square counter : "
          + this.cnt
      );
      */AbstractDungeon.actionManager.addToTop(
                ConsumeChargeUpAction(cnt * stc)
            )
        }
    }

    override fun atDamageFinalGive(damage: Float, type: DamageType): Float {
        if (owner.hasPower(OneTimeOffPlusPower.POWER_ID) || ExhaustionCheck()) {
            return damage
        }
        if (cnt > 0) {
            if (type == DamageType.NORMAL && amount >= 1) {
                return (damage * 2.0.pow(cnt.toDouble())).toFloat()
            }
        }
        return damage
    }

    private val divider: Unit
        get() {
            stc = if (AbstractDungeon.player.hasRelic(SimpleLauncher.ID)) {
                IMPR_STACK
            } else {
                ACT_STACK
            }
        }

    private fun ExhaustionCheck(): Boolean {
        var res = false
        for (c in AbstractDungeon.player.hand.group) {
            if (c is Exhaustion_MRS) {
                res = true
            }
        }
        return res
    }

    companion object {
        const val POWER_ID = "ChargeUpPower"
        private val powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID)
        val NAME = powerStrings.NAME
        val DESCRIPTIONS = powerStrings.DESCRIPTIONS
        private const val ACT_STACK = 8
        private const val IMPR_STACK = 6
    }
}
