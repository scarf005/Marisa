package marisa.cards.derivations

import com.megacrit.cardcrawl.actions.GameActionManager
import com.megacrit.cardcrawl.actions.common.GainBlockAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.abstracts.MarisaCard
import marisa.action.HandSelectAction
import marisa.patches.AbstractCardEnum

class BlackFlareStar : MarisaCard(
    ID, "Marisa/BlackFlareStar", COST, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF,
    color = AbstractCardEnum.MARISA_DERIVATIONS,
) {
    init {
        baseBlock = BLC_AMT
        exhaust = true
    }

    override fun canUse(p: AbstractPlayer, unused: AbstractMonster?): Boolean {
        return if (p.hand.size() >= HAND_REQ) {
            true
        } else {
            cantUseMessage = strings.EXTENDED_DESCRIPTION[0]
            false
        }
    }

    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        val blockPerCard = block
        addToBot(HandSelectAction("Discard", 99, anyNumber = true, canPickZero = true) { cards ->
            if (cards.isNotEmpty()) addToTop(GainBlockAction(p, p, blockPerCard * cards.size))
            cards.forEach {
                p.hand.moveToDiscardPile(it)
                GameActionManager.incrementDiscard(false)
                it.triggerOnManualDiscard()
            }
        })
    }

    override fun makeCopy(): AbstractCard = BlackFlareStar()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        upgradeBlock(UPG_BLC)
    }

    companion object {
        const val ID = "marisa:BlackFlareStar"
        private const val COST = 0
        private const val BLC_AMT = 4
        private const val UPG_BLC = 2
        private const val HAND_REQ = 4
    }
}
