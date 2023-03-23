package marisa.cards

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.action.OpenUniverseAction
import marisa.patches.AbstractCardEnum

//import com.megacrit.cardcrawl.actions.common.DrawCardAction;
class OpenUniverse : CustomCard(
    ID, NAME, IMG_PATH,
    COST, DESCRIPTION,
    CardType.SKILL,
    AbstractCardEnum.MARISA_COLOR,
    CardRarity.UNCOMMON,
    CardTarget.SELF
) {
    init {
        baseMagicNumber = DRAW
        magicNumber = baseMagicNumber
        baseDamage = CHANCE
        damage = baseDamage
    }

    override fun applyPowers() {}
    override fun calculateCardDamage(unused: AbstractMonster?) {}
    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        addToBot(
            OpenUniverseAction(magicNumber, upgraded)
        )
    }

    override fun makeCopy(): AbstractCard = OpenUniverse()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        upgradeMagicNumber(UPG_DRAW)
        upgradeDamage(UPG_CHANCE)
    }

    companion object {
        const val ID = "OpenUniverse"
        const val IMG_PATH = "img/cards/openUni.png"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        val DESCRIPTION_UPG = cardStrings.UPGRADE_DESCRIPTION
        private const val COST = 1
        private const val DRAW = 2
        private const val UPG_DRAW = 1
        private const val CHANCE = 20
        private const val UPG_CHANCE = 10
    }
}
