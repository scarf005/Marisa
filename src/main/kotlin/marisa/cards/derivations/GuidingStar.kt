package marisa.cards.derivations

import marisa.patches.AbstractCardEnum
import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction
import com.megacrit.cardcrawl.actions.common.GainEnergyAction
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction
import com.megacrit.cardcrawl.actions.common.ShuffleAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster

class GuidingStar : CustomCard(
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
        exhaust = true
        retain = true
    }

    override fun applyPowers() {
        super.applyPowers()
        retain = true
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
        if (AbstractDungeon.player.discardPile.size() > 0) {
            AbstractDungeon.actionManager.addToBottom(
                EmptyDeckShuffleAction()
            )
            AbstractDungeon.actionManager.addToBottom(
                ShuffleAction(AbstractDungeon.player.drawPile, false)
            )
        }
        /*
		p.drawPile.shuffle();

		for (AbstractRelic r : p.relics) {
			r.onShuffle();
	    }
	    */
    }

    override fun triggerAtStartOfTurn() {
        AbstractDungeon.actionManager.addToBottom(
            GainEnergyAction(1)
        )
    }

    override fun makeCopy(): AbstractCard = GuidingStar()

    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            upgradeBaseCost(UPG_COST)
        }
    }

    companion object {
        const val ID = "GuidingStar"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        const val IMG_PATH = "img/cards/GuidingStar.png"
        private const val COST = 1
        private const val UPG_COST = 0
    }
}
