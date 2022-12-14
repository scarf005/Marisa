package marisa.cards

import marisa.cards.derivations.Spark
import marisa.patches.AbstractCardEnum
import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster

class JA : CustomCard(
    ID,
    NAME,
    IMG_PATH,
    COST,
    DESCRIPTION,
    CardType.ATTACK,
    AbstractCardEnum.MARISA_COLOR,
    CardRarity.RARE,
    CardTarget.ENEMY
) {
    //private static final int UPG_DMG = 2;
    init {
        baseDamage = ATK_DMG
    }

    override fun use(p: AbstractPlayer, m: AbstractMonster?) {
        AbstractDungeon.actionManager.addToBottom(
            DamageAction(
                m,
                DamageInfo(p, damage, damageTypeForTurn),
                AttackEffect.SLASH_DIAGONAL
            )
        )
        var c: AbstractCard = UpSweep()
        if (upgraded) {
            c.upgrade()
        }
        AbstractDungeon.actionManager.addToBottom(
            MakeTempCardInHandAction(c, 1)
        )
        c = Spark()
        if (upgraded) {
            c.upgrade()
        }
        AbstractDungeon.actionManager.addToBottom(
            MakeTempCardInHandAction(c, 1)
        )
        c = WitchLeyline()
        if (upgraded) {
            c.upgrade()
        }
        AbstractDungeon.actionManager.addToBottom(
            MakeTempCardInHandAction(c, 1)
        )
    }

    override fun makeCopy(): AbstractCard = JA()

    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            //upgradeDamage(UPG_DMG);
            rawDescription = DESCRIPTION_UPG
            initializeDescription()
        }
    }

    companion object {
        const val ID = "JA"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        val DESCRIPTION_UPG = cardStrings.UPGRADE_DESCRIPTION
        const val IMG_PATH = "img/cards/JA.png"
        private const val COST = 2
        private const val ATK_DMG = 1
    }
}
