package marisa.cards.Marisa

import marisa.action.OrbitalAction
import marisa.patches.AbstractCardEnum
import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.DrawCardAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster

class Orbital : CustomCard(
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
        baseMagicNumber = DRAW
        magicNumber = baseMagicNumber
    }

    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            upgradeMagicNumber(UPG_DRAW)
        }
    }

    override fun canUse(p: AbstractPlayer, unused: AbstractMonster?): Boolean = false

    override fun triggerOnExhaust() {
        AbstractDungeon.actionManager.addToBottom(
            OrbitalAction()
        )
        if (upgraded) {
            AbstractDungeon.actionManager.addToBottom(
                OrbitalAction()
            )
        }
    }

    override fun triggerWhenDrawn() {
        AbstractDungeon.actionManager.addToBottom(
            DrawCardAction(AbstractDungeon.player, DRAW)
        )
    }

    override fun use(arg0: AbstractPlayer, arg1: AbstractMonster?) {}

    companion object {
        const val ID = "Orbital"
        const val IMG_PATH = "img/cards/Marisa/orbit.png"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        private const val COST = -2
        private const val UPG_DRAW = 1
        private const val DRAW = 1
    }
}
