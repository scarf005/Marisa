package marisa.cards

import com.megacrit.cardcrawl.actions.common.HealAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.abstracts.AmplifiableCard
import marisa.action.DiscToHandRandAction
import marisa.action.DiscardPileToHandAction

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
                    addToBot(
                        DiscardPileToHandAction(1)
                    )
                } else {
                    addToBot(
                        DiscToHandRandAction()
                    )
                }
            }
        }
        addToBot(HealAction(p, p, magicNumber))
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
