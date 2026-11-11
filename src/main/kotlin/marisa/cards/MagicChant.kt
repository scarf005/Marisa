package marisa.cards

import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.abstracts.MarisaCard
import marisa.action.GridSelectAction
import marisa.action.cardGroupOf

class MagicChant : MarisaCard(ID, "Chant", COST, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF) {
    init {
        baseMagicNumber = RTN
        magicNumber = baseMagicNumber
        exhaust = true
    }

    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        val text = CardCrawlGame.languagePack.getUIString("BetterToHandAction").TEXT[0]
        val drawPile = {
            cardGroupOf(p.drawPile.group).apply {
                sortAlphabetically(true)
                sortByRarityPlusStatusCardType(false)
            }
        }
        addToBot(GridSelectAction(text, forUpgrade = true, cards = drawPile) { cards ->
            cards.forEach {
                if (it.canUpgrade()) it.upgrade()
                if (p.hand.size() == 10) {
                    p.drawPile.moveToDiscardPile(it)
                    p.createHandIsFullDialog()
                } else {
                    p.drawPile.moveToHand(it, p.drawPile)
                }
            }
        })
    }

    override fun makeCopy(): AbstractCard = MagicChant()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        upgradeBaseCost(COST_UPG)
    }

    companion object {
        const val ID = "marisa:MagicChant"

        private const val COST = 1
        private const val COST_UPG = 0
        private const val RTN = 2
    }
}
