package marisa.abstracts

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.ui.panels.EnergyPanel
import marisa.ApplyPowerToPlayerAction
import marisa.MarisaContinued
import marisa.addToTop
import marisa.p
import marisa.powers.Marisa.*
import marisa.relics.AmplifyWand

fun applyAmplify() {
    addToTop(ApplyPowerToPlayerAction(GrandCrossPower::class))
    p.getPower(EventHorizonPower.POWER_ID)?.onSpecificTrigger()
    p.getRelic(AmplifyWand.ID)?.onTrigger()
}

fun isAmplifyDisabled() = when {
    p.hasPower(OneTimeOffPower.POWER_ID) -> true
    p.hasPower(OneTimeOffPlusPower.POWER_ID) -> true
    else -> false
}

abstract class AmplifiableCard(
    id: String,
    name: String,
    img: String,
    cost: Int,
    rawDescription: String,
    type: CardType,
    color: CardColor,
    rarity: CardRarity,
    target: CardTarget,
) : CustomCard(
    id,
    name,
    img,
    cost,
    rawDescription,
    type,
    color,
    rarity,
    target
) {
    var amplifyCost = 1

    fun isFreeAmplify(): Boolean = when {
        p.hasPower(MillisecondPulsarsPower.POWER_ID) -> true
        p.hasPower(PulseMagicPower.POWER_ID) -> true
        freeToPlayOnce -> true
        else -> false
//        purgeOnUse -> true
    }

    override fun triggerOnGlowCheck() {
        val color = if (canAmplify()) GOLD_BORDER_GLOW_COLOR else BLUE_BORDER_GLOW_COLOR
        glowColor = color.cpy()
    }

    fun canPayAmplify() = EnergyPanel.totalCount >= costForTurn + amplifyCost

    fun canAmplify(): Boolean = when {
        isAmplifyDisabled() -> false
        isFreeAmplify() -> true
        canPayAmplify() -> true
        else -> false
    }

    fun additionalCostToPay() = when {
        isFreeAmplify() -> 0
        canPayAmplify() -> amplifyCost
        else -> 0
    }

    fun tryAmplify(): Boolean {
        MarisaContinued.logger.info("""Card to be Amplified: ${cardID} with costForTurn : ${costForTurn}""")

        costForTurn += additionalCostToPay()

        val res = canAmplify()
        if (res) {
            applyAmplify()
        }
        MarisaContinued.logger.info("""Amplified : card : $cardID; Amplify : $res ; costForTurn : $costForTurn""")
        return res
    }
}
