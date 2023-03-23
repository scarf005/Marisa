package marisa.abstracts

import basemod.abstracts.CustomCard
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride
import com.evacipated.cardcrawl.modthespire.lib.SpireSuper
import com.megacrit.cardcrawl.ui.panels.EnergyPanel
import marisa.ApplyPowerToPlayerAction
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
    id: String, name: String, img: String, cost: Int, rawDescription: String,
    type: CardType, color: CardColor, rarity: CardRarity, target: CardTarget,
) : CustomCard(id, name, img, cost, rawDescription, type, color, rarity, target) {
    var amplifyCost = 1
    private val actualCost get() = costForTurn + additionalCostToPay
    private val canPayAmplify get() = EnergyPanel.totalCount >= costForTurn + amplifyCost
    private val isFreeAmplify
        get() = when {
            p.hasPower(MillisecondPulsarsPower.POWER_ID) -> true
            p.hasPower(PulseMagicPower.POWER_ID) -> true
            freeToPlayOnce -> true
            else -> false
//        purgeOnUse -> true
        }
    private val canAmplify: Boolean
        get() = when {
            isAmplifyDisabled() -> false
            isFreeAmplify -> true
            canPayAmplify -> true
            else -> false
        }

    private val additionalCostToPay
        get() = when {
            isFreeAmplify -> 0
            canPayAmplify -> amplifyCost
            else -> 0
        }

    @SpireOverride
    protected fun renderEnergy(sb: SpriteBatch) {
        val tmpCostModified = isCostModifiedForTurn
        isCostModifiedForTurn = isCostModifiedForTurn || canAmplify
        SpireSuper.call<SpriteBatch>(sb)
        isCostModifiedForTurn = tmpCostModified
    }

    @SpireOverride
    protected fun getCost(): String = when (cost) {
        -1 -> "X"
        else -> actualCost.toString()
    }


    override fun triggerOnGlowCheck() {
        val color = if (canAmplify) GOLD_BORDER_GLOW_COLOR else BLUE_BORDER_GLOW_COLOR
        glowColor = color.cpy()
    }

    fun tryAmplify(): Boolean {
        costForTurn += additionalCostToPay

        if (canAmplify) {
            applyAmplify()
        }
        return canAmplify
    }
}
