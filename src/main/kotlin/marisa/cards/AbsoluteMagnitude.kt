package marisa.cards

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.patches.AbstractCardEnum
import marisa.powers.Marisa.ChargeUpPower

class AbsoluteMagnitude : CustomCard(
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
    private var multiplier: Int

    init {
        baseDamage = 0
        multiplier = ATK_MULT
    }

    override fun applyPowers() {
        val p = AbstractDungeon.player
        if (p.hasPower(ChargeUpPower.POWER_ID)) {
            baseDamage = p.getPower(ChargeUpPower.POWER_ID).amount * multiplier
            isDamageModified = true
        }
        super.applyPowers()
    }

    override fun onMoveToDiscard() {
        baseDamage = 0
        super.applyPowers()
    }

    override fun use(p: AbstractPlayer, m: AbstractMonster?) {
        addToBot(
            DamageAction(m, DamageInfo(p, damage, damageTypeForTurn), AttackEffect.SLASH_DIAGONAL)
        )
    }

    override fun makeCopy(): AbstractCard = AbsoluteMagnitude()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        multiplier = ATK_MULT_UPG
        rawDescription = DESCRIPTION_UPG
        initializeDescription()
    }

    companion object {
        const val ID = "AbsoluteMagnitude"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        val DESCRIPTION_UPG = cardStrings.UPGRADE_DESCRIPTION
        const val IMG_PATH = "marisa/img/cards/absMagni.png"
        private const val COST = 2
        private const val ATK_MULT = 2
        private const val ATK_MULT_UPG = 3
    }
}
