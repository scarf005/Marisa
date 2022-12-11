package marisa.cards.Marisa

import marisa.patches.AbstractCardEnum
import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.GainBlockAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster

class Defend_MRS : CustomCard(
    ID,
    NAME,
    IMG_PATH,
    COST,
    DESCRIPTION,
    CardType.SKILL,
    AbstractCardEnum.MARISA_COLOR,
    CardRarity.BASIC,
    CardTarget.SELF
) {
    init {
        //this.tags.add(BaseModCardTags.BASIC_DEFEND);
        tags.add(CardTags.STARTER_DEFEND)
        baseBlock = BLOCK_AMT
    }

    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        AbstractDungeon.actionManager.addToBottom(
            GainBlockAction(p, p, block)
        )
    }

    override fun makeCopy(): AbstractCard {
        return Defend_MRS()
    }


    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            upgradeBlock(UPGRADE_PLUS_BLOCK)
        }
    }

    companion object {
        const val ID = "Defend_MRS"
        const val IMG_PATH = "img/cards/Defend_MRS.png"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        private const val COST = 1
        private const val BLOCK_AMT = 5
        private const val UPGRADE_PLUS_BLOCK = 3
    }
}
