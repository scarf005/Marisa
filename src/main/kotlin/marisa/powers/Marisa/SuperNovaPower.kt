package marisa.powers.Marisa

import com.badlogic.gdx.graphics.Texture
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.AbstractCard.CardType
import com.megacrit.cardcrawl.cards.status.Burn
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.powers.AbstractPower
import com.megacrit.cardcrawl.powers.StrengthPower
import marisa.MarisaContinued

class SuperNovaPower(owner: AbstractCreature?, amount: Int) : AbstractPower() {
    private val p: AbstractPlayer

    init {
        name = NAME
        ID = POWER_ID
        this.owner = owner
        this.amount = amount
        type = PowerType.BUFF
        updateDescription()
        img = Texture("img/powers/impulse.png")
        p = AbstractDungeon.player
    }

    override fun atEndOfTurn(isPlayer: Boolean) {
        if (!p.hand.isEmpty) {
            flash()
            for (c in p.hand.group) {
                addToBot(
                    ExhaustSpecificCardAction(c, p.hand)
                )
            }
        }
    }

    override fun onExhaust(card: AbstractCard) {
        val apply = card is Burn
        if (apply) {
            addToBot(
                ApplyPowerAction(p, p, StrengthPower(p, amount), amount)
            )
        }
    }

    override fun onDrawOrDiscard() {
        //ThMod.logger.info("SuperNovaPower : onDrawOrDiscard : ExhaustDiscard");
        ExhaustDiscard()
    }

    override fun onApplyPower(
        power: AbstractPower,
        target: AbstractCreature,
        source: AbstractCreature
    ) {
        //ThMod.logger.info("SuperNovaPower : onApplyPower : ExhaustDiscard");
        ExhaustDiscard()
    }

    override fun onInitialApplication() {
        //ThMod.logger.info("SuperNovaPower : onInitialApplication : ExhaustDiscard");
        ExhaustDiscard()
    }

    private fun ExhaustDiscard() {
        for (c in AbstractDungeon.player.hand.group) {
            if (discardCheck(c)) {
                c.exhaust = true
                c.isEthereal = true
            }
        }
    }

    private fun discardCheck(card: AbstractCard): Boolean {
        if (card.type == CardType.CURSE || card.type == CardType.STATUS) {
            MarisaContinued.logger.info("SuperNovaPower : discardCheck : " + card.cardID + " detected.")
            return true
        }
        return false
    }

    override fun updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2]
    }

    companion object {
        const val POWER_ID = "SuperNovaPower"
        private val powerStrings = CardCrawlGame.languagePack
            .getPowerStrings(POWER_ID)
        val NAME = powerStrings.NAME
        val DESCRIPTIONS = powerStrings.DESCRIPTIONS
    }
}
