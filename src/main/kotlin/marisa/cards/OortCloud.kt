package marisa.cards

import marisa.MarisaMod
import marisa.patches.AbstractCardEnum
import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.powers.PlatedArmorPower

class OortCloud : CustomCard(
    ID,
    NAME,
    IMG_PATH,
    COST,
    DESCRIPTION,
    CardType.POWER,
    AbstractCardEnum.MARISA_COLOR,
    CardRarity.UNCOMMON,
    CardTarget.SELF
) {
    init {
        baseMagicNumber = ARMOR_GAIN
        magicNumber = baseMagicNumber
        baseBlock = AMP_ARMOR
        block = baseBlock
    }

    override fun applyPowers() {
        block = baseBlock
        magicNumber = baseMagicNumber
    }

    override fun calculateCardDamage(unused: AbstractMonster?) {
        block = baseBlock
        magicNumber = baseMagicNumber
    }

    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        if (MarisaMod.isAmplified(this, AMP)) {
            AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(
                    p,
                    p,
                    PlatedArmorPower(p, block),
                    block
                )
            )
        }
        AbstractDungeon.actionManager.addToBottom(
            ApplyPowerAction(
                p,
                p,
                PlatedArmorPower(p, magicNumber),
                magicNumber
            )
        )
    }

    override fun makeCopy(): AbstractCard = OortCloud()

    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            upgradeMagicNumber(UPG_ARMOR)
            upgradeBlock(UPG_AMP)
            //this.rawDescription = DESCRIPTION_UPG;
            //initializeDescription();
        }
    }

    companion object {
        const val ID = "OortCloud"
        const val IMG_PATH = "img/cards/oort.png"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        val DESCRIPTION_UPG = cardStrings.UPGRADE_DESCRIPTION
        private const val COST = 1
        private const val ARMOR_GAIN = 4
        private const val UPG_ARMOR = 1
        private const val AMP_ARMOR = 2
        private const val UPG_AMP = 1
        private const val AMP = 1
    }
}
