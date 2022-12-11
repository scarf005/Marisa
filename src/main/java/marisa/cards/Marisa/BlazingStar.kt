package marisa.cards.Marisa

import marisa.Marisa
import marisa.abstracts.AmplifiedAttack
import marisa.patches.AbstractCardEnum
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.cards.status.Burn
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster

class BlazingStar : AmplifiedAttack(
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
    init {
        baseDamage = ATK_DMG
        damage = baseDamage
        baseBlock = damage
        block = baseBlock
        baseMagicNumber = AMP_DMG
        magicNumber = baseMagicNumber
        isException = true
    }

    private fun burns() = AbstractDungeon.player.hand.group.filterIsInstance<Burn>().size
    private fun burnDamage() = baseDamage + burns() * magicNumber
    override fun applyPowers() {
        block = burnDamage()
        super.applyPowers()
    }

    override fun calculateDamageDisplay(mo: AbstractMonster) {
        calculateCardDamage(mo)
    }

    override fun calculateCardDamage(mo: AbstractMonster) {
        block = burnDamage()
        super.calculateCardDamage(mo)
    }

    override fun use(p: AbstractPlayer, m: AbstractMonster) {
        if (Marisa.Amplified(this, AMP)) {
            block *= 2
        }
        AbstractDungeon.actionManager.addToBottom(
            DamageAction(
                m,
                DamageInfo(p, block, damageTypeForTurn),
                AttackEffect.FIRE
            )
        )
    }

    override fun makeCopy(): AbstractCard = BlazingStar()

    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            upgradeDamage(UPG_DMG)
            upgradeMagicNumber(UPG_AMP)
            baseBlock = baseDamage
            block = baseBlock
            isBlockModified = true
        }
    }

    companion object {
        const val ID = "BlazingStar"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        const val IMG_PATH = "img/cards/BlazingStar.png"
        private const val COST = 2
        private const val ATK_DMG = 16
        private const val UPG_DMG = 4
        private const val AMP_DMG = 8
        private const val UPG_AMP = 2
        private const val AMP = 1
    }
}
