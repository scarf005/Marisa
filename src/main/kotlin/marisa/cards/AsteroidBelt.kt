package marisa.cards

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.GainBlockAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.powers.NextTurnBlockPower
import marisa.abstracts.AmplifiableCard
import marisa.patches.AbstractCardEnum

class AsteroidBelt : AmplifiableCard(
    ID,
    NAME,
    IMG_PATH,
    COST,
    DESCRIPTION,
    CardType.SKILL,
    AbstractCardEnum.MARISA_COLOR,
    CardRarity.COMMON,
    CardTarget.SELF
) {
    init {
        baseBlock = BLOCK_AMT
        amplifyCost = AMP
    }

    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        addToBot(
            GainBlockAction(p, p, block)
        )
        if (tryAmplify()) {
            addToBot(ApplyPowerAction(p, p, NextTurnBlockPower(p, block), block))
        }
    }

    override fun makeCopy(): AbstractCard = AsteroidBelt()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        upgradeBlock(UPGRADE_PLUS_BLOCK)
    }

    companion object {
        const val ID = "marisa:AsteroidBelt"
        const val IMG_PATH = "marisa/img/cards/Asteroid.png"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        private const val COST = 1
        private const val BLOCK_AMT = 8
        private const val UPGRADE_PLUS_BLOCK = 3
        private const val AMP = 1
    }
}
