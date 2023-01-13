package marisa.cards

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.abstracts.AmplifiedAttack
import marisa.patches.AbstractCardEnum
import marisa.powers.Marisa.ChargeUpPower

class AbsoluteMagnitude : AmplifiedAttack(
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
    private var multiplier: Float

    init {
        baseDamage = 0
        damage = baseDamage
        multiplier = ATK_MULT
        baseBlock = 0
        block = baseBlock
    }

    override fun applyPowers() {
        val p = AbstractDungeon.player
        if (p.hasPower(ChargeUpPower.POWER_ID)) {
            ampNumber = (p.getPower(ChargeUpPower.POWER_ID).amount * multiplier).toInt()
        }
        super.applyPowers()
        isBlockModified = true
    }

    override fun onMoveToDiscard() {
        ampNumber = 0
        super.applyPowers()
    }

    override fun use(p: AbstractPlayer, m: AbstractMonster?) {
        addToBot(
            DamageAction(
                m,
                DamageInfo(p, block, damageTypeForTurn),
                AttackEffect.SLASH_DIAGONAL
            )
        )
    }

    override fun makeCopy(): AbstractCard = AbsoluteMagnitude()

    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            multiplier = ATK_MULT_UPG
            rawDescription = DESCRIPTION_UPG
            initializeDescription()
        }
    }

    companion object {
        const val ID = "AbsoluteMagnitude"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        val DESCRIPTION_UPG = cardStrings.UPGRADE_DESCRIPTION
        const val IMG_PATH = "img/cards/absMagni.png"
        private const val COST = 2
        private const val ATK_MULT = 2f
        private const val ATK_MULT_UPG = 3f
    }
}
