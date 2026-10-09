package marisa.cards

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.abstracts.MarisaCard
import marisa.patches.CardTagEnum

class MachineGunSpark : MarisaCard(
    ID, "MachineGunSpark", COST, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY,
) {
    init {
        baseDamage = ATTACK_DMG
        baseMagicNumber = CNT
        magicNumber = baseMagicNumber
        exhaust = true
        tags.add(CardTagEnum.SPARK)
    }

    override fun use(p: AbstractPlayer, m: AbstractMonster?) {
        for (i in 0 until magicNumber) {
            addToBot(
                DamageAction(
                    m,
                    DamageInfo(p, damage, damageTypeForTurn),
                    AttackEffect.SLASH_DIAGONAL,
                    true
                )
            )
        }
    }

    override fun makeCopy(): AbstractCard = MachineGunSpark()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        upgradeMagicNumber(UPG_CNT)
    }

    companion object {
        const val ID = "marisa:MachineGunSpark"
        private const val COST = 1
        private const val ATTACK_DMG = 1
        private const val CNT = 6
        private const val UPG_CNT = 2
    }
}
