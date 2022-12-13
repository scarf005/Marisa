package marisa.relics

import basemod.abstracts.CustomRelic
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction
import com.megacrit.cardcrawl.actions.utility.UseCardAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.ImageMaster
import com.megacrit.cardcrawl.relics.AbstractRelic
import marisa.MarisaMod
import marisa.powers.Marisa.ChargeUpPower

class MiniHakkero : CustomRelic(
    ID,
    ImageMaster.loadImage(IMG),
    ImageMaster.loadImage(IMG_OTL),
    RelicTier.STARTER,
    LandingSound.MAGICAL
) {
    override fun getUpdatedDescription(): String = DESCRIPTIONS[0]

    override fun makeCopy(): AbstractRelic = MiniHakkero()

    override fun onUseCard(card: AbstractCard, action: UseCardAction) {
        val p = AbstractDungeon.player
        val available = true
        val div = if (p.hasRelic("SimpleLauncher")) 6 else 8

        if (!available) {
            return
        }

        flash()
        MarisaMod.logger.info("""MiniHakkero : Applying ChargeUpPower for using card : ${card.cardID}""")
        AbstractDungeon.actionManager.addToTop(
            ApplyPowerAction(
                AbstractDungeon.player,
                AbstractDungeon.player,
                ChargeUpPower(AbstractDungeon.player, 1),
                1
            )
        )
        AbstractDungeon.actionManager.addToBottom(
            RelicAboveCreatureAction(
                AbstractDungeon.player, this
            )
        )
    }

    companion object {
        const val ID = "MiniHakkero"
        private const val IMG = "img/relics/Hakkero_s.png"
        private const val IMG_OTL = "img/relics/outline/Hakkero_s.png"
    }
}
