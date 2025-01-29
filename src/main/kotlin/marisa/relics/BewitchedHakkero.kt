package marisa.relics

import basemod.abstracts.CustomRelic
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction
import com.megacrit.cardcrawl.actions.utility.UseCardAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.ImageMaster
import com.megacrit.cardcrawl.relics.AbstractRelic
import marisa.MarisaContinued
import marisa.patches.CardTagEnum
import marisa.powers.Marisa.ChargeUpPower

class BewitchedHakkero : CustomRelic(
    ID,
    ImageMaster.loadImage(IMG),
    ImageMaster.loadImage(IMG_OTL),
    RelicTier.BOSS,
    LandingSound.MAGICAL
) {
    override fun getUpdatedDescription(): String = DESCRIPTIONS[0]

    override fun makeCopy(): AbstractRelic = BewitchedHakkero()

    override fun obtain() {
        if (AbstractDungeon.player.hasRelic("MiniHakkero")) {
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
        private const val IMG = "marisa/img/relics/Hakkero_1_s.png"
        private const val IMG_OTL = "marisa/img/relics/outline/Hakkero_1_s.png"
    }
}
