package marisa.cards

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.abstracts.AmplifiableCard
import marisa.action.FairyDestrucCullingAction

class FairyDestructionRay : AmplifiableCard(
    ID, "FairysBane", COST, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY,
) {
    init {
        isMultiDamage = true
        damage = ATTACK_DMG
        baseDamage = damage
        baseMagicNumber = DIASPORA
        magicNumber = baseMagicNumber
        amplifyCost = AMP
    }

    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        addToBot(
            DamageAllEnemiesAction(
                p, multiDamage, damageTypeForTurn, AttackEffect.SLASH_DIAGONAL
            )
        )
        if (!AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
            if (tryAmplify()) {
                addToBot(FairyDestrucCullingAction(magicNumber))
            }
        }
    }

    override fun makeCopy(): AbstractCard = FairyDestructionRay()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        upgradeDamage(UPGRADE_PLUS_DMG)
        upgradeMagicNumber(UPG_DIASPORA)
    }

    companion object {
        const val ID = "marisa:FairyDestructionRay"
        private const val COST = 0
        private const val AMP = 2
        private const val ATTACK_DMG = 5
        private const val UPGRADE_PLUS_DMG = 3
        private const val DIASPORA = 17
        private const val UPG_DIASPORA = 5
    }
}
