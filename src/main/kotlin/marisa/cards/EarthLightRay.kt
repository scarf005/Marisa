package marisa.cards

import com.megacrit.cardcrawl.actions.common.HealAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.CardGroup
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.abstracts.AmplifiableCard
import marisa.action.DiscToHandRandAction
import marisa.action.GridSelectAction
import marisa.action.cardGroupOf
import marisa.p

class EarthLightRay : AmplifiableCard(ID, "EarthLightRay", COST, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF) {

    init {
        magicNumber = HEAL_AMT
        baseMagicNumber = magicNumber
        exhaust = true
        tags.add(CardTags.HEALING)
    }

    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        if (!p.discardPile.isEmpty) {
            if (tryAmplify()) {
                if (upgraded && !p.discardPile.isEmpty) {
                    addToBot(returnFromDiscardPile())
                } else {
                    addToBot(
                        DiscToHandRandAction()
                    )
                }
            }
        }
        addToBot(HealAction(p, p, magicNumber))
    }

    private fun returnFromDiscardPile() = GridSelectAction(
        CardCrawlGame.languagePack.getUIString("DiscardPileToHandAction").TEXT[0],
        cards = { if (p.hand.size() >= 10) cardGroupOf() else p.discardPile },
    ) { cards ->
        cards.forEach {
            p.hand.addToHand(it)
            p.discardPile.removeCard(it)
            it.lighten(false)
            it.unhover()
        }
        p.discardPile.group.forEach {
            it.unhover()
            it.target_x = CardGroup.DISCARD_PILE_X.toFloat()
            it.target_y = 0.0f
        }
    }

    override fun makeCopy(): AbstractCard = EarthLightRay()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        upgradeMagicNumber(UPG_HEAL)
        rawDescription = strings.UPGRADE_DESCRIPTION
        initializeDescription()
    }

    companion object {
        const val ID = "marisa:EarthLightRay"
        private const val COST = 0
        private const val HEAL_AMT = 4
        private const val UPG_HEAL = 2
    }
}
