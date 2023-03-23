package marisa.cards

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.ui.panels.EnergyPanel
import marisa.action.MeteoricShowerAction
import marisa.patches.AbstractCardEnum

class MeteoricShower : CustomCard(
    ID,
    NAME,
    IMG_PATH,
    COST,
    DESCRIPTION,
    CardType.ATTACK,
    AbstractCardEnum.MARISA_COLOR,
    CardRarity.UNCOMMON,
    CardTarget.ALL_ENEMY
) {
    init {
        baseDamage = ATK_DMG
    }

    override fun calculateCardDamage(unused: AbstractMonster?) {}
    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        var cnt = EnergyPanel.totalCount + 1
        if (p.hasRelic("Chemical X")) {
            cnt += 2
        }
        addToBot(
            MeteoricShowerAction(cnt, damage, freeToPlayOnce)
        )
        /*
    if (!this.freeToPlayOnce) {
      p.energy.use(EnergyPanel.totalCount);
    }
    */
    }

    override fun makeCopy(): AbstractCard = MeteoricShower()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        upgradeDamage(UPG_DMG)
    }

    companion object {
        const val ID = "MeteoricShower"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        const val IMG_PATH = "img/cards/meteonic.png"
        private const val COST = -1
        private const val ATK_DMG = 3
        private const val UPG_DMG = 1
    }
}
