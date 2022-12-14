package marisa.cards.Marisa

import marisa.MarisaMod
import marisa.action.WasteBombAction
import marisa.patches.AbstractCardEnum
import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster

class DeepEcologicalBomb : CustomCard(
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
        baseMagicNumber = STC
        magicNumber = baseMagicNumber
    }

    override fun calculateCardDamage(unused: AbstractMonster?) {}
    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        var num = 1
        if (MarisaMod.isAmplified(this, AMP)) {
            num++
        }
        AbstractDungeon.actionManager.addToBottom(
            WasteBombAction(
                AbstractDungeon.getMonsters().getRandomMonster(true),
                damage,
                num,
                magicNumber
            )
        )
    }

    override fun makeCopy(): AbstractCard = DeepEcologicalBomb()

    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            upgradeDamage(UPG_DMG)
            upgradeMagicNumber(UPG_STC)
        }
    }

    companion object {
        const val ID = "DeepEcoloBomb"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        const val IMG_PATH = "img/cards/DeepEcoBomb.png"
        private const val STC = 2
        private const val UPG_STC = 1
        private const val COST = 1
        private const val ATK_DMG = 7
        private const val UPG_DMG = 2
        private const val AMP = 1
    }
}
