package marisa.potions

import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.PowerTip
import com.megacrit.cardcrawl.potions.AbstractPotion
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase
import marisa.action.FungusSplashAction

class ShroomBrew : AbstractPotion(
    NAME,
    POTION_ID,
    PotionRarity.UNCOMMON,
    PotionSize.FAIRY,
    PotionColor.SMOKE
) {
    init {
        potency = getPotency()
        description = DESCRIPTIONS[0]
        isThrown = true
        targetRequired = true
        tips.add(
            PowerTip(name, description)
        )
        tips.add(
            PowerTip(DESCRIPTIONS[1], DESCRIPTIONS[2])
        )
    }

    override fun use(target: AbstractCreature) {
        if (AbstractDungeon.getCurrRoom().phase == RoomPhase.COMBAT) {
            addToBot(
                FungusSplashAction(target)
            )
        }
    }

    override fun makeCopy(): AbstractPotion = ShroomBrew()

    override fun getPotency(ascensionLevel: Int): Int = 1

    companion object {
        const val POTION_ID = "ShroomBrew"
        private val potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID)
        val NAME = potionStrings.NAME
        val DESCRIPTIONS = potionStrings.DESCRIPTIONS
    }
}
