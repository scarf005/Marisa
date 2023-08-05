package marisa.cards

import com.megacrit.cardcrawl.actions.common.HealAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.abstracts.AmplifiableCard
import marisa.action.DiscToHandRandAction
import marisa.action.DiscardPileToHandAction
import marisa.patches.AbstractCardEnum

class EarthLightRay : AmplifiableCard(
    ID,
    NAME,
    IMG_PATH,
    COST,
    DESCRIPTION,
    CardType.SKILL,
    AbstractCardEnum.MARISA_COLOR,
    CardRarity.UNCOMMON,
    CardTarget.SELF
) {

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
        rawDescription = DESCRIPTION_UPG
        initializeDescription()
    }

    companion object {
        const val ID = "EarthLightRay"
        const val IMG_PATH = "marisa/img/cards/EarthLightRay.png"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        val DESCRIPTION_UPG = cardStrings.UPGRADE_DESCRIPTION
        private const val COST = 0
        private const val HEAL_AMT = 4
        private const val UPG_HEAL = 2
    }
}
