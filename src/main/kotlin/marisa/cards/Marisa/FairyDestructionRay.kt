package marisa.cards.Marisa

import marisa.MarisaMod
import marisa.action.FairyDestrucCullingAction
import marisa.patches.AbstractCardEnum
import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster

class FairyDestructionRay : CustomCard(
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
        isMultiDamage = true
        damage = ATTACK_DMG
        baseDamage = damage
        baseMagicNumber = DIASPORA
        magicNumber = baseMagicNumber
    }

    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        AbstractDungeon.actionManager.addToBottom(
            DamageAllEnemiesAction(
                p,
                multiDamage,
                damageTypeForTurn,
                AttackEffect.SLASH_DIAGONAL
            )
        )
        if (!AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
            if (MarisaMod.isAmplified(this, AMP)) {
                AbstractDungeon.actionManager.addToBottom(
                    FairyDestrucCullingAction(magicNumber)
                )
            }
        }
    }

    override fun makeCopy(): AbstractCard = FairyDestructionRay()

    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            upgradeDamage(UPGRADE_PLUS_DMG)
            upgradeMagicNumber(UPG_DIASPORA)
        }
    }

    companion object {
        const val ID = "FairyDestructionRay"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        const val IMG_PATH = "img/cards/FairysBane.png"
        private const val COST = 0
        private const val AMP = 2
        private const val ATTACK_DMG = 5
        private const val UPGRADE_PLUS_DMG = 3
        private const val DIASPORA = 17
        private const val UPG_DIASPORA = 5
    }
}
