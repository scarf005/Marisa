package marisa.potions

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.PowerTip
import com.megacrit.cardcrawl.potions.AbstractPotion
import marisa.powers.Marisa.ChargeUpPower
import marisa.powers.Marisa.PulseMagicPower

class StarNLove : AbstractPotion(
    NAME,
    POTION_ID,
    PotionRarity.RARE,
    PotionSize.HEART,
    PotionColor.SWIFT
) {
    init {
        potency = getPotency()
        description = DESCRIPTIONS[0] + potency + DESCRIPTIONS[1]
        tips.clear()
        tips.add(PowerTip(name, description))
    }

    override fun use(abstractCreature: AbstractCreature) {
        addToBot(
            ApplyPowerAction(
                AbstractDungeon.player, AbstractDungeon.player,
                PulseMagicPower(AbstractDungeon.player), 1
            )
        )
        addToBot(
            ApplyPowerAction(
                AbstractDungeon.player, AbstractDungeon.player,
                ChargeUpPower(AbstractDungeon.player, this.getPotency()), this.getPotency()
            )
        )
    }

    override fun getPotency(i: Int): Int = 8

    override fun makeCopy(): AbstractPotion = StarNLove()

    companion object {
        const val POTION_ID = "StarNLove"
        private val potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID)
        val NAME = potionStrings.NAME
        val DESCRIPTIONS = potionStrings.DESCRIPTIONS
    }
}
