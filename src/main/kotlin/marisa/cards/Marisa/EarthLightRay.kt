package marisa.cards.Marisa

import marisa.MarisaMod
import marisa.action.DiscToHandRandAction
import marisa.action.DiscardPileToHandAction
import marisa.patches.AbstractCardEnum
import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.HealAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster

class EarthLightRay : CustomCard(
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
    private val AMP = 1

    init {
        magicNumber = HEAL_AMT
        baseMagicNumber = magicNumber
        exhaust = true
        tags.add(CardTags.HEALING)
    }

    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        if (!p.discardPile.isEmpty) {
            if (MarisaMod.isAmplified(this, AMP)) {
                if (upgraded && !p.discardPile.isEmpty) {
                    AbstractDungeon.actionManager.addToBottom(
                        DiscardPileToHandAction(1)
                    )
                } else {
                    AbstractDungeon.actionManager.addToBottom(
                        DiscToHandRandAction()
                    )
                }
            }
        }
        AbstractDungeon.actionManager.addToBottom(
            HealAction(p, p, magicNumber)
        )
    }

    override fun makeCopy(): AbstractCard {
        return EarthLightRay()
    }

    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            upgradeMagicNumber(UPG_HEAL)
            rawDescription = DESCRIPTION_UPG
            initializeDescription()
        }
    }

    companion object {
        const val ID = "EarthLightRay"
        const val IMG_PATH = "img/cards/EarthLightRay.png"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        val DESCRIPTION_UPG = cardStrings.UPGRADE_DESCRIPTION
        private const val COST = 0
        private const val HEAL_AMT = 4
        private const val UPG_HEAL = 2
    }
}
