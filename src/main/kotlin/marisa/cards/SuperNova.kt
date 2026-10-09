package marisa.cards

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.status.Burn
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.abstracts.MarisaCard
import marisa.powers.Marisa.SuperNovaPower

class SuperNova : MarisaCard(ID, "SuperNova", COST, CardType.POWER, CardRarity.RARE, CardTarget.SELF) {
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
        private const val COST = 2
        private const val STACK = 1
        private const val STACK_UPG = 1
    }
}
