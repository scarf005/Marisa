package marisa.cards.Marisa

import marisa.marisa
import marisa.abstracts.AmplifiedAttack
import marisa.patches.AbstractCardEnum
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.powers.AbstractPower

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

    override fun use(p: AbstractPlayer, m: AbstractMonster) {
        val po: AbstractPower
        val fightingBoss = m.type == AbstractMonster.EnemyType.BOSS
        if (marisa.Amplified(this, AMP)) {
            if (!fightingBoss) {
                for (pow in m.powers) {
                    if (pow.type == AbstractPower.PowerType.BUFF) {
                        AbstractDungeon.actionManager.addToBottom(
                            RemoveSpecificPowerAction(m, p, pow)
                        )
                    }
                }
            }
            AbstractDungeon.actionManager.addToBottom(
                DamageAction(
                    m,
                    DamageInfo(p, block, damageTypeForTurn),
                    AttackEffect.SLASH_DIAGONAL
                )
            )
        } else {
            if (!m.powers.isEmpty() && !fightingBoss) {
                val pows = ArrayList<AbstractPower>()
                for (pow in m.powers) {
                    if (pow.type == AbstractPower.PowerType.BUFF) {
                        pows.add(pow)
                    }
                }
                if (!pows.isEmpty()) {
                    po = pows[(Math.random() * pows.size).toInt()]
                    AbstractDungeon.actionManager.addToBottom(
                        RemoveSpecificPowerAction(m, p, po)
                    )
                }
            }
            AbstractDungeon.actionManager.addToBottom(
                DamageAction(
                    m,
                    DamageInfo(p, damage, damageTypeForTurn),
                    AttackEffect.SLASH_DIAGONAL
                )
            )
        }
    }

    override fun makeCopy(): AbstractCard {
        return ShootTheMoon()
    }

    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            upgradeDamage(UPG_DMG)
            ampNumber += UPG_AMP
            baseBlock = baseDamage + ampNumber
            block = baseBlock
            isBlockModified = true
        }
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
