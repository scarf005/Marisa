package ThMod.cards.Marisa

import ThMod.action.OccultationAction
import ThMod.patches.AbstractCardEnum
import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.GainBlockAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster

class Occultation : CustomCard(
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
    }

    override fun applyPowers() {
        if (AbstractDungeon.player.drawPile.size() >= 0) {
            baseBlock = AbstractDungeon.player.drawPile.size()
            rawDescription = DESCRIPTION + EXT_DESCRIPTION
            initializeDescription()
        }
        super.applyPowers()
    }

    override fun onMoveToDiscard() {
        rawDescription = DESCRIPTION
        baseBlock = 0
        block = baseBlock
        initializeDescription()
    }

    override fun use(p: AbstractPlayer, m: AbstractMonster) {
        if (!AbstractDungeon.player.drawPile.isEmpty) {
            AbstractDungeon.actionManager.addToBottom(
                OccultationAction()
            )
        }
        AbstractDungeon.actionManager.addToBottom(
            GainBlockAction(p, p, block)
        )
        /*
    if (this.upgraded) {
      AbstractDungeon.actionManager.addToBottom(
          new GainBlockAction(p, p, this.block)
      );
    }
    */
    }

    override fun makeCopy(): AbstractCard {
        return Occultation()
    }

    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            upgradeBaseCost(UPG_COST)
            //this.rawDescription = (this.rawDescription + " NL Gain !B! block.");
            //initializeDescription();
            //this.rawDescription = DESCRIPTION_UPG;
            //initializeDescription();
        }
    }

    companion object {
        const val ID = "Occultation"
        const val IMG_PATH = "img/cards/occultation.png"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        private val EXT_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION[0]
        val DESCRIPTION_UPG = cardStrings.UPGRADE_DESCRIPTION
        private const val COST = 2
        private const val UPG_COST = 1
        private const val BLOCK_AMT = 0
    }
}