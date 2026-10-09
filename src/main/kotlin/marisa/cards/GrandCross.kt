package marisa.cards

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.abstracts.MarisaCard
import marisa.powers.Marisa.GrandCrossPower

class GrandCross : MarisaCard(ID, "GrandCross", COST, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY) {
    init {
        damage = ATTACK_DMG
        baseDamage = damage
    }

    override fun applyPowers() {
        super.applyPowers()
        if (AbstractDungeon.player.hasPower(GrandCrossPower.POWER_ID)) {
            if (costForTurn != 0) {
                this.flash()
                costForTurn = 0
            }
        }
    }

    override fun use(p: AbstractPlayer, m: AbstractMonster?) {
        addToBot(
            DamageAction(
                m,
                DamageInfo(
                    p,
                    damage,
                    damageTypeForTurn
                ),
                AttackEffect.SLASH_DIAGONAL
            )
        )
    }

    override fun makeCopy(): AbstractCard = GrandCross()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        upgradeDamage(UPGRADE_PLUS_DMG)
    }

    companion object {
        const val ID = "marisa:GrandCross"
        private const val COST = 2
        private const val ATTACK_DMG = 13
        private const val UPGRADE_PLUS_DMG = 5
    }
}
