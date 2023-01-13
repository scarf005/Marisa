package marisa.cards

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.powers.AbstractPower
import com.megacrit.cardcrawl.powers.FadingPower
import com.megacrit.cardcrawl.powers.ShiftingPower
import marisa.abstracts.AmplifiedAttack
import marisa.patches.AbstractCardEnum
import marisa.random

class ShootTheMoon : AmplifiedAttack(
    ID,
    NAME,
    IMG_PATH,
    COST,
    DESCRIPTION, CardType.ATTACK,
    AbstractCardEnum.MARISA_COLOR,
    CardRarity.UNCOMMON,
    CardTarget.ENEMY
) {
    init {
        baseDamage = ATK_DMG
        ampNumber = AMP_DMG
        baseBlock = baseDamage + ampNumber
    }

    override fun use(p: AbstractPlayer, m: AbstractMonster?) {
        m ?: return
        val isAmplified = isAmplified(AMP)

        val buffs = m.powers
            .filter { it.type == AbstractPower.PowerType.BUFF }
            .filterNot { it.ID == FadingPower.POWER_ID }
            .filterNot { it.ID == ShiftingPower.POWER_ID }

        val toRemove: List<AbstractGameAction> = when {
            m.type == AbstractMonster.EnemyType.BOSS -> emptyList()
            buffs.isEmpty() -> emptyList()
            isAmplified -> buffs.map { RemoveSpecificPowerAction(m, p, it) }
            else -> listOf(RemoveSpecificPowerAction(m, p, buffs.random()))
        }

        val harm = if (isAmplified) block else damage
        addToBot(
            DamageAction(m, DamageInfo(p, harm, damageTypeForTurn), AttackEffect.SLASH_DIAGONAL)
        )
        toRemove.forEach { addToBot(it) }
    }

    override fun makeCopy(): AbstractCard = ShootTheMoon()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        upgradeDamage(UPG_DMG)
        ampNumber += UPG_AMP
        baseBlock = baseDamage + ampNumber
        block = baseBlock
        isBlockModified = true
    }

    companion object {
        const val ID = "ShootTheMoon"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        const val IMG_PATH = "img/cards/ShootTheMoon_v1.png"
        private const val COST = 1
        private const val ATK_DMG = 8
        private const val UPG_DMG = 3
        private const val AMP_DMG = 5
        private const val UPG_AMP = 2
        private const val AMP = 1
    }
}
