package marisa.cards

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.GainBlockAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower
import marisa.ApplyPowerToPlayerAction
import marisa.patches.AbstractCardEnum
import marisa.powers.Marisa.OneTimeOffPlusPower
import marisa.powers.Marisa.OneTimeOffPower

class OneTimeOff : CustomCard(
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
        baseMagicNumber = DRAW
        magicNumber = baseMagicNumber
    }

    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        addToBot(
            GainBlockAction(p, p, block)
        )
        addToBot(
            ApplyPowerAction(p, p, DrawCardNextTurnPower(p, magicNumber), magicNumber)
        )
        val powerToAdd = if (upgraded) OneTimeOffPlusPower::class else OneTimeOffPower::class
        addToBot(ApplyPowerToPlayerAction(powerToAdd))
    }

    override fun makeCopy(): AbstractCard = OneTimeOff()

    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            upgradeBlock(UPGRADE_PLUS_BLOCK)
            upgradeMagicNumber(UPGRADE_PLUS_DRAW)
            rawDescription = DESCRIPTION_UPG
            initializeDescription()
        }
    }

    companion object {
        const val ID = "OneTimeOff"
        const val IMG_PATH = "img/cards/MoraleDelpletion.png"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        val DESCRIPTION_UPG = cardStrings.UPGRADE_DESCRIPTION
        private const val COST = 1
        private const val BLOCK_AMT = 5
        private const val UPGRADE_PLUS_BLOCK = 2
        private const val DRAW = 1
        private const val UPGRADE_PLUS_DRAW = 1
    }
}
