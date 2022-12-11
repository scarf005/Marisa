package marisa.cards.Marisa

import marisa.Marisa
import marisa.patches.AbstractCardEnum
import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.GainBlockAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.powers.NextTurnBlockPower

class AsteroidBelt : CustomCard(
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
    }

    override fun use(p: AbstractPlayer, m: AbstractMonster) {
        AbstractDungeon.actionManager.addToBottom(
            GainBlockAction(p, p, block)
        )
        if (Marisa.Amplified(this, AMP)) {
            AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(
                    p,
                    p,
                    NextTurnBlockPower(p, block),
                    block
                )
            )
        }
    }

    override fun makeCopy(): AbstractCard {
        return AsteroidBelt()
    }

    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            upgradeBlock(UPGRADE_PLUS_BLOCK)
        }
    }

    companion object {
        const val ID = "AsteroidBelt"
        const val IMG_PATH = "img/cards/Asteroid.png"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        private const val COST = 1
        private const val BLOCK_AMT = 8
        private const val UPGRADE_PLUS_BLOCK = 3
        private const val AMP = 1
    }
}
