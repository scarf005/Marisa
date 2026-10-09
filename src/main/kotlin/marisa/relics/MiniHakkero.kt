package marisa.relics

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.utility.UseCardAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.relics.AbstractRelic
import marisa.MarisaContinued
import marisa.abstracts.MarisaRelic
import marisa.powers.Marisa.ChargeUpPower

class MiniHakkero : MarisaRelic(ID, "Hakkero_s", RelicTier.STARTER, LandingSound.MAGICAL) {
    override fun getUpdatedDescription(): String = DESCRIPTIONS[0]

    override fun makeCopy(): AbstractRelic = MiniHakkero()

    override fun onUseCard(card: AbstractCard, action: UseCardAction) {
        val p = AbstractDungeon.player

        flash()
        MarisaContinued.logger.info("""MiniHakkero : Applying ChargeUpPower for using card : ${card.cardID}""")
        addToTop(ApplyPowerAction(p, p, ChargeUpPower(p, 1), 1))
    }

    companion object {
        const val ID = "marisa:MiniHakkero"
    }
}
