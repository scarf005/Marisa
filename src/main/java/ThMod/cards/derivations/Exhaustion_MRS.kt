package ThMod.cards.derivations

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.utility.UseCardAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster

class Exhaustion_MRS : CustomCard(
    ID,
    NAME,
    "img/cards/exhaustion.png",
    COST,
    DESCRIPTION,
    CardType.STATUS,
    CardColor.COLORLESS,
    CardRarity.SPECIAL,
    CardTarget.NONE
) {
    init {
        exhaust = true
    }

    override fun use(p: AbstractPlayer, m: AbstractMonster) {
        if (p.hasRelic("Medical Kit")) {
            useMedicalKit(p)
        } else {
            AbstractDungeon.actionManager.addToBottom(
                UseCardAction(this)
            )
        }
    }

    override fun makeCopy(): AbstractCard {
        return Exhaustion_MRS()
    }

    override fun upgrade() {}

    companion object {
        const val ID = "Exhaustion_MRS"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings("Exhaustion_MRS")
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        private const val COST = -2
    }
}