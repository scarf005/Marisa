package marisa.cards

import com.megacrit.cardcrawl.actions.common.GainBlockAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.abstracts.MarisaCard
import marisa.action.OccultationAction

class Occultation : MarisaCard(ID, "occultation", COST, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF) {
    init {
        baseBlock = BLOCK_AMT
    }

    override fun applyPowers() {
        if (AbstractDungeon.player.drawPile.size() >= 0) {
            baseBlock = AbstractDungeon.player.drawPile.size()
            rawDescription = strings.DESCRIPTION + strings.EXTENDED_DESCRIPTION[0]
            initializeDescription()
        }
        super.applyPowers()
    }

    override fun onMoveToDiscard() {
        rawDescription = strings.DESCRIPTION
        baseBlock = 0
        block = baseBlock
        initializeDescription()
    }

    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        if (!AbstractDungeon.player.drawPile.isEmpty) {
            addToBot(
                OccultationAction()
            )
        }
        addToBot(
            GainBlockAction(p, p, block)
        )
        /*
    if (this.upgraded) {
      addToBot(
          new GainBlockAction(p, p, this.block)
      );
    }
    */
    }

    override fun makeCopy(): AbstractCard = Occultation()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        upgradeBaseCost(UPG_COST)
    }

    companion object {
        const val ID = "marisa:Occultation"
        private const val COST = 2
        private const val UPG_COST = 1
        private const val BLOCK_AMT = 0
    }
}
