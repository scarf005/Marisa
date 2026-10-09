package marisa.cards

import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.abstracts.MarisaCard
import marisa.action.DarkSparkAction
import marisa.patches.CardTagEnum

class DarkSpark : MarisaCard(ID, "darkSpark", COST, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY) {
    init {
        tags.add(CardTagEnum.SPARK)
        baseDamage = ATK_DMG
        isMultiDamage = true
        baseMagicNumber = EXHAUST_COUNT
        magicNumber = baseMagicNumber
    }

    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        for (i in 0 until magicNumber) {
            addToBot(DarkSparkAction(multiDamage, damageType))
        }
    }

    override fun makeCopy(): AbstractCard = DarkSpark()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        upgradeMagicNumber(COUNT_UPG)
        initializeDescription()
    }

    companion object {
        const val ID = "marisa:DarkSpark"
        private const val COST = 2
        private const val ATK_DMG = 7

        //private static final int UPG_DMG = 3;
        private const val EXHAUST_COUNT = 5
        private const val COUNT_UPG = 3
    }
}
