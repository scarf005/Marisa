package marisa.cards.Marisa

import marisa.patches.AbstractCardEnum
import marisa.powers.Marisa.DarkMatterPower
import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.DrawCardAction
import com.megacrit.cardcrawl.actions.common.GainBlockAction
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster

//import com.megacrit.cardcrawl.core.Settings;
//import com.megacrit.cardcrawl.relics.AbstractRelic;
//import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;
class DarkMatter : CustomCard(
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
        isEthereal = true
        baseBlock = BLC_GAIN
        block = baseBlock
    }

    override fun triggerOnExhaust() {
        val p = AbstractDungeon.player
        AbstractDungeon.actionManager.addToBottom(
            GainBlockAction(p, p, block)
        )
    }

    override fun canUse(p: AbstractPlayer, m: AbstractMonster?): Boolean {
        val canUse = super.canUse(p, m)
        if (!canUse) {
            return false
        }
        if (p.hasPower(DarkMatterPower.POWER_ID)) {
            cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0]
            return false
        }
        return true
    }

    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        AbstractDungeon.actionManager.addToBottom(
            MakeTempCardInDrawPileAction(
                makeStatEquivalentCopy(),
                1,
                true,
                true
            )
        )
        AbstractDungeon.actionManager.addToBottom(
            MakeTempCardInDrawPileAction(
                makeStatEquivalentCopy(),
                1,
                true,
                true
            )
        )
        AbstractDungeon.actionManager.addToBottom(
            DrawCardAction(p, 1)
        )
        AbstractDungeon.actionManager.addToBottom(
            ApplyPowerAction(
                p,
                p,
                DarkMatterPower(p)
            )
        )
        /*
		p.drawPile.shuffle();
	    for (AbstractRelic r : p.relics)
	        r.onShuffle();
	  */
    }

    override fun makeCopy(): AbstractCard = DarkMatter()

    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            upgradeBlock(UPG_BLC)
            rawDescription = DESCRIPTION_UPG
            initializeDescription()
        }
    }

    companion object {
        const val ID = "DarkMatter"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        val DESCRIPTION_UPG = cardStrings.UPGRADE_DESCRIPTION
        const val IMG_PATH = "img/cards/DarkMatter.png"
        private const val COST = 0
        private const val BLC_GAIN = 5
        private const val UPG_BLC = 2
    }
}
