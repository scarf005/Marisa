package marisa.cards

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.powers.EnergizedBluePower
import marisa.ApplyPowerToPlayerAction
import marisa.abstracts.AmplifiableCard
import marisa.patches.AbstractCardEnum
import marisa.powers.Marisa.PulseMagicPower

class PulseMagic : AmplifiableCard(
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
        baseMagicNumber = ENE
        magicNumber = baseMagicNumber
    }

    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        if (tryAmplify()) {
            addToBot(ApplyPowerToPlayerAction(PulseMagicPower::class))
        }
        addToBot(ApplyPowerAction(p, p, EnergizedBluePower(p, magicNumber), magicNumber))
    }

    override fun makeCopy(): AbstractCard = PulseMagic()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        upgradeMagicNumber(UPG_ENE)
        rawDescription = DESCRIPTION_UPG
        initializeDescription()
    }

    companion object {
        const val ID = "PulseMagic"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        val DESCRIPTION_UPG = cardStrings.UPGRADE_DESCRIPTION
        const val IMG_PATH = "img/cards/pulseMagic.png"
        private const val COST = 0
        private const val ENE = 1
        private const val UPG_ENE = 1
    }
}
