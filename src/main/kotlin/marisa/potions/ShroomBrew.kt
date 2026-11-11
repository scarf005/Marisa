package marisa.potions

import com.megacrit.cardcrawl.cards.AbstractCard.CardType
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.PowerTip
import com.megacrit.cardcrawl.potions.*
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase
import marisa.action.HandSelectAction
import marisa.exhaust
import marisa.p

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
            val text = CardCrawlGame.languagePack.getUIString("ExhaustAction").TEXT[0]
            addToBot(HandSelectAction(text, 1) { cards ->
                cards.forEach {
                    splash(it.type, target)
                    it.exhaust()
                }
            })
        }
    }

    private fun splash(type: CardType, target: AbstractCreature) {
        when (type) {
            CardType.ATTACK -> FearPotion().use(target)
            CardType.SKILL -> WeakenPotion().use(target)
            CardType.POWER -> PoisonPotion().use(target)
            CardType.STATUS -> FirePotion().use(target)
            CardType.CURSE -> SmokeBomb().let { if (it.canUse()) it.use(p) }
            else -> {}
        }
    }

    override fun makeCopy(): AbstractPotion = ShroomBrew()

    override fun getPotency(ascensionLevel: Int): Int = 1

    companion object {
        const val POTION_ID = "marisa:ShroomBrew"
        private val potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID)
        val NAME = potionStrings.NAME
        val DESCRIPTIONS = potionStrings.DESCRIPTIONS
    }
}
