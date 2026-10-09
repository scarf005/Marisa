package marisa.cards

import com.megacrit.cardcrawl.actions.common.DrawCardAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.abstracts.AmplifiableCard

class Acceleration : AmplifiableCard(ID, "GuidingStar", COST, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF) {
    init {
        baseMagicNumber = AMP
        magicNumber = baseMagicNumber
        baseBlock = DRAW
        block = baseBlock
    }

    override fun applyPowersToBlock() {}
    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        addToBot(DrawCardAction(block))

        if (tryAmplify()) {
            addToBot(DrawCardAction(magicNumber))
        }
    }

    override fun makeCopy(): AbstractCard = Acceleration()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        upgradeMagicNumber(AMP_UPG)
    }

    companion object {
        const val ID = "marisa:Acceleration"
        private const val COST = 0
        private const val DRAW = 2

        private const val AMP = 1
        private const val AMP_UPG = 1
    }
}
