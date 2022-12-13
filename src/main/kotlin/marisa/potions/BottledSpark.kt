package marisa.potions

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.PowerTip
import com.megacrit.cardcrawl.potions.AbstractPotion
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase
import marisa.cards.derivations.Spark

class BottledSpark : AbstractPotion(
    NAME,
    POTION_ID,
    PotionRarity.COMMON,
    PotionSize.FAIRY,
    PotionColor.SWIFT
) {
    init {
        potency = getPotency()
        description = DESCRIPTIONS[0] + potency + DESCRIPTIONS[1]
        tips.clear()
        tips.add(PowerTip(name, description))
    }

    override fun use(unused: AbstractCreature?) {
        if (AbstractDungeon.getCurrRoom().phase == RoomPhase.COMBAT) {
            addToBot(MakeTempCardInHandAction(Spark().makeStatEquivalentCopy(), potency))
        }
    }

    override fun getPotency(i: Int): Int = 3

    override fun makeCopy(): AbstractPotion = BottledSpark()

    companion object {
        const val POTION_ID = "BottledSpark"
        private val potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID)
        val NAME = potionStrings.NAME
        val DESCRIPTIONS = potionStrings.DESCRIPTIONS
    }
}
