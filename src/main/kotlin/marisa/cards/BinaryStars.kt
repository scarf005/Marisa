package marisa.cards

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.abstracts.AmplifiableCard
import marisa.action.BinaryStarsAction
import marisa.cards.derivations.BlackFlareStar
import marisa.cards.derivations.WhiteDwarf

class BinaryStars : AmplifiableCard(ID, "binaryStar", COST, CardType.SKILL, CardRarity.RARE, CardTarget.SELF) {
    init {
        amplifyCost = AMP
        multiplePreviews(stars())
    }

    private fun stars() = listOf(WhiteDwarf(), BlackFlareStar()).map { followUpgrade(it) }
    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        if (tryAmplify()) {
            stars().forEach { addToBot(MakeTempCardInHandAction(it, 1)) }
        } else {
            addToBot(BinaryStarsAction(upgraded))
        }
    }

    override fun makeCopy(): AbstractCard = BinaryStars()

    override fun upgrade() {
        if (upgraded) return

        upgradeName()
        rawDescription = strings.UPGRADE_DESCRIPTION
        initializeDescription()
        multiplePreviews(stars())
    }

    companion object {
        const val ID = "marisa:BinaryStars"
        private const val COST = 1
        private const val AMP = 1
    }
}
