package ThMod.cards.derivations

import ThMod.action.BlackFlareStarAction
import ThMod.patches.AbstractCardEnum
import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster

class BlackFlareStar : CustomCard(
    ID,
    NAME,
    IMG_PATH,
    COST,
    DESCRIPTION,
    CardType.SKILL,
    AbstractCardEnum.MARISA_DERIVATIONS,
    CardRarity.SPECIAL,
    CardTarget.SELF
) {
    init {
        baseBlock = BLC_AMT
        exhaust = true
    }

    override fun canUse(p: AbstractPlayer, m: AbstractMonster): Boolean {
        return if (p.hand.size() >= HAND_REQ) {
            true
        } else {
            cantUseMessage = EXTENDED_DESCRIPTION[0]
            false
        }
    }

    override fun use(p: AbstractPlayer, m: AbstractMonster) {
        AbstractDungeon.actionManager.addToBottom(
            BlackFlareStarAction(block)
        )
    }

    override fun makeCopy(): AbstractCard {
        return BlackFlareStar()
    }

    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            upgradeBlock(UPG_BLC)
        }
    }

    companion object {
        const val ID = "BlackFlareStar"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        val EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION
        const val IMG_PATH = "img/cards/Marisa/BlackFlareStar.png"
        private const val COST = 0
        private const val BLC_AMT = 4
        private const val UPG_BLC = 2
        private const val HAND_REQ = 4
    }
}