package marisa.relics

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction
import com.megacrit.cardcrawl.actions.utility.UseCardAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.relics.AbstractRelic
import marisa.MarisaContinued
import marisa.abstracts.MarisaRelic
import marisa.patches.CardTagEnum
import marisa.powers.Marisa.ChargeUpPower

class BewitchedHakkero : MarisaRelic(ID, "Hakkero_1_s", RelicTier.BOSS, LandingSound.MAGICAL) {
    override fun getUpdatedDescription(): String = DESCRIPTIONS[0]

    override fun makeCopy(): AbstractRelic = BewitchedHakkero()

    override fun obtain() {
        if (AbstractDungeon.player.hasRelic(MiniHakkero.ID)) {
            instantObtain(AbstractDungeon.player, 0, false)
        } else {
            super.obtain()
        }
    }

    override fun onUseCard(card: AbstractCard, action: UseCardAction) {
        flash()
        MarisaContinued.logger.info(
            "BewitchedHakkero : Applying ChargeUpPower for using card : " + card.cardID
        )
        var amt = 1
        if (card.hasTag(CardTagEnum.SPARK)) {
            amt++
        }
        AbstractDungeon.actionManager.addToTop(
            ApplyPowerAction(
                AbstractDungeon.player,
                AbstractDungeon.player,
                ChargeUpPower(AbstractDungeon.player, amt),
                amt
            )
        )
        addToBot(
            RelicAboveCreatureAction(AbstractDungeon.player, this)
        )
    }

    companion object {
        const val ID = "marisa:BewitchedHakkero"
    }
}
