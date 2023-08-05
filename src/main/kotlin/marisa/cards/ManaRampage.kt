package marisa.cards

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.ui.panels.EnergyPanel
import marisa.action.ManaRampageAction
import marisa.patches.AbstractCardEnum

class ManaRampage : CustomCard(
    ID,
    NAME,
    IMG_PATH,
    COST,
    DESCRIPTION,
    CardType.SKILL,
    AbstractCardEnum.MARISA_COLOR,
    CardRarity.RARE,
    CardTarget.ALL_ENEMY
) {
    init {
        baseMagicNumber = DMG_UP
        magicNumber = baseMagicNumber
    }

    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        var cnt = EnergyPanel.totalCount
        if (p.hasRelic("Chemical X")) {
            cnt += 2
        }
        if (cnt > 0) {
            addToBot(
                ManaRampageAction(cnt, upgraded, freeToPlayOnce)
            )
        }
        /*
    if (!this.freeToPlayOnce) {
      p.energy.use(EnergyPanel.totalCount);
    }
    */
    }

    override fun makeCopy(): AbstractCard = ManaRampage()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        upgradeMagicNumber(DMG_UP_PLUS)
        rawDescription = cardStrings.UPGRADE_DESCRIPTION
        initializeDescription()
    }

    companion object {
        const val ID = "ManaRampage"
        const val IMG_PATH = "marisa/img/cards/ManaRampage.png"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        val DESCRIPTION_UPG = cardStrings.UPGRADE_DESCRIPTION
        private const val COST = -1
        private const val DMG_UP = 2
        private const val DMG_UP_PLUS = 1
    }
}
