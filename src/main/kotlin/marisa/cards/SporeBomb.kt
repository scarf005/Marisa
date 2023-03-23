package marisa.cards

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.powers.VulnerablePower
import marisa.abstracts.AmplifiableCard
import marisa.monsters
import marisa.patches.AbstractCardEnum

class SporeBomb : AmplifiableCard(
    ID,
    NAME,
    IMG_PATH,
    COST,
    DESCRIPTION,
    CardType.SKILL,
    AbstractCardEnum.MARISA_COLOR,
    CardRarity.COMMON,
    CardTarget.ENEMY
) {
    init {
        baseMagicNumber = STC
        magicNumber = baseMagicNumber
    }

    private fun vulnerableAction(p: AbstractPlayer, m: AbstractMonster) =
        ApplyPowerAction(m, p, VulnerablePower(m, magicNumber, false), magicNumber)

    override fun use(p: AbstractPlayer, m: AbstractMonster?) {
        val monsters = if (tryAmplify()) monsters else {
            m ?: return
            listOf(m)
        }
        val actions = monsters.map { vulnerableAction(p, it) }

        marisa.addToBot(actions)
    }

    override fun makeCopy(): AbstractCard = SporeBomb()

    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            upgradeMagicNumber(UPG_STC)
        }
    }

    companion object {
        const val ID = "SporeBomb"
        const val IMG_PATH = "img/cards/SporeCrump.png"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        private const val COST = 0
        private const val STC = 2
        private const val UPG_STC = 1
    }
}
