package marisa.cards

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.GainBlockAction
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.status.Burn
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.patches.AbstractCardEnum

class GasGiant : CustomCard(
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
        baseBlock = BLOCK_AMT
        baseMagicNumber = VUL_GAIN
        magicNumber = baseMagicNumber
        cardsToPreview = Burn()
    }

    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        addToBot(
            GainBlockAction(p, p, block)
        )
        addToBot(
            MakeTempCardInHandAction(
                Burn(),
                magicNumber
            )
        )
    }

    override fun makeCopy(): AbstractCard = GasGiant()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        upgradeBlock(UPGRADE_PLUS_BLOCK)
    }

    companion object {
        const val ID = "marisa:GasGiant"
        const val IMG_PATH = "marisa/img/cards/Jupiter.png"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        private const val COST = 1
        private const val BLOCK_AMT = 14
        private const val UPGRADE_PLUS_BLOCK = 4
        private const val VUL_GAIN = 1
    }
}
