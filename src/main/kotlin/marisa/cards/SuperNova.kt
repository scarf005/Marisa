package marisa.cards

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.status.Burn
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.patches.AbstractCardEnum
import marisa.powers.Marisa.SuperNovaPower

class SuperNova : CustomCard(
    ID,
    NAME,
    IMG_PATH,
    COST,
    DESCRIPTION,
    CardType.POWER,
    AbstractCardEnum.MARISA_COLOR,
    CardRarity.RARE,
    CardTarget.SELF
) {
    init {
        //this.tags.add(BaseModCardTags.FORM);
        baseMagicNumber = STACK
        magicNumber = baseMagicNumber
        cardsToPreview = Burn()
    }

    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        /*
    if ((this.upgraded) && (p.hasPower(SuperNovaPower.POWER_ID))) {
      SuperNovaPower po = (SuperNovaPower) p.getPower("SuperNovaPower");
      po.upgraded = true;
    }
    */
        addToBot(
            ApplyPowerAction(
                p,
                p,  //new SuperNovaPower(p, 1, this.upgraded),
                SuperNovaPower(p, magicNumber),
                magicNumber
            )
        )
    }

    override fun makeCopy(): AbstractCard = SuperNova()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        upgradeMagicNumber(STACK_UPG)
        initializeDescription()
    }

    companion object {
        const val ID = "marisa:SuperNova"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        val DESCRIPTION_UPG = cardStrings.UPGRADE_DESCRIPTION
        const val IMG_PATH = "marisa/img/cards/SuperNova.png"
        private const val COST = 2
        private const val STACK = 1
        private const val STACK_UPG = 1
    }
}
