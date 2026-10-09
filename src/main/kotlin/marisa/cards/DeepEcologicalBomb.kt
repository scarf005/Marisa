package marisa.cards

import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.abstracts.AmplifiableCard
import marisa.action.WasteBombAction

class DeepEcologicalBomb : AmplifiableCard(
    ID, "DeepEcoBomb", COST, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY,
) {
    init {
        baseDamage = ATK_DMG
        baseMagicNumber = STC
        magicNumber = baseMagicNumber
        amplifyCost = AMP
    }

    override fun calculateCardDamage(unused: AbstractMonster?) {}
    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        val num = if (tryAmplify()) 2 else 1
        addToBot(
            WasteBombAction(
                AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng),
                damage,
                num,
                magicNumber
            )
        )
    }

    override fun makeCopy(): AbstractCard = DeepEcologicalBomb()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        upgradeDamage(UPG_DMG)
        upgradeMagicNumber(UPG_STC)
    }

    companion object {
        const val ID = "marisa:DeepEcoloBomb"
        private const val STC = 2
        private const val UPG_STC = 1
        private const val COST = 1
        private const val ATK_DMG = 7
        private const val UPG_DMG = 2
        private const val AMP = 1
    }
}
