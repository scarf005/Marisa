package marisa.cards

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase
import marisa.MarisaContinued
import marisa.abstracts.AmplifiableCard
import marisa.powers.Marisa.WitchOfGreedGold
import marisa.powers.Marisa.WitchOfGreedPotion

class WitchOfGreed : AmplifiableCard(ID, "Greed", COST, CardType.POWER, CardRarity.COMMON, CardTarget.SELF) {
    init {
        magicNumber = STC
        baseMagicNumber = magicNumber
        tags.add(CardTags.HEALING)
    }

    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        if (AbstractDungeon.getCurrRoom().phase == RoomPhase.COMBAT) {
            AbstractDungeon.getCurrRoom().addGoldToRewards(magicNumber)
            addToBot(
                ApplyPowerAction(p, p, WitchOfGreedGold(p, magicNumber), magicNumber)
            )

            if (!tryAmplify()) return

            val po = AbstractDungeon.returnRandomPotion()
            AbstractDungeon.getCurrRoom().addPotionToRewards(po)
            addToBot(
                ApplyPowerAction(p, p, WitchOfGreedPotion(p, 1), 1)
            )
            MarisaContinued.logger.info("WitchOfGreed : use : Amplified : adding :" + po.ID)
        }
    }

    override fun makeCopy(): AbstractCard = WitchOfGreed()

    override fun upgrade() {
        if (upgraded) return

        upgradeName()
        upgradeMagicNumber(UPG_STC)
    }

    companion object {
        const val ID = "marisa:WitchOfGreed"
        private const val COST = 1
        private const val STC = 15
        private const val UPG_STC = 10
    }
}
