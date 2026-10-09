package marisa.cards

import com.megacrit.cardcrawl.actions.common.GainBlockAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.abstracts.MarisaCard

class Defend_MRS : MarisaCard(ID, "Defend_MRS", COST, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF) {
    init {
        tags.add(CardTags.STARTER_DEFEND)
        baseBlock = BLOCK_AMT
    }

    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        addToBot(
            GainBlockAction(p, p, block)
        )
    }

    override fun makeCopy(): AbstractCard = Defend_MRS()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        upgradeBlock(UPGRADE_PLUS_BLOCK)
    }

    companion object {
        const val ID = "marisa:Defend_MRS"
        private const val COST = 1
        private const val BLOCK_AMT = 5
        private const val UPGRADE_PLUS_BLOCK = 3
    }
}
