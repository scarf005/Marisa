package marisa.cards

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.actions.common.DrawCardAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.abstracts.MarisaCard

class GravityBeat : MarisaCard(ID, "GravityBeat", COST, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY) {
    init {

        damage = ATTACK_DMG
        baseDamage = damage
        block = DIVIDER
        baseBlock = block
    }

    override fun applyPowers() {
        super.applyPowers()
        if (AbstractDungeon.player != null) {
            block = baseBlock
            isBlockModified = false
            baseMagicNumber = AbstractDungeon.player.masterDeck.size() / block
            magicNumber = baseMagicNumber
            rawDescription = strings.DESCRIPTION +
                strings.EXTENDED_DESCRIPTION[0] + magicNumber + strings.EXTENDED_DESCRIPTION[1]
            initializeDescription()
        }
    }

    override fun use(p: AbstractPlayer, m: AbstractMonster?) {
        fun damage() =
            DamageAction(m, DamageInfo(p, damage, damageTypeForTurn), AttackEffect.BLUNT_LIGHT)

        repeat(magicNumber) {
            m?.let { if (!it.isDeadOrEscaped) addToBot(damage()) }
            addToBot(DrawCardAction(1))
        }
    }

    override fun makeCopy(): AbstractCard = GravityBeat()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        upgradeMagicNumber(DIV_UPG)
        upgradeDamage(UPGRADE_PLUS_DMG)
    }

    companion object {
        const val ID = "marisa:GravityBeat"
        private const val COST = 1
        private const val ATTACK_DMG = 6
        private const val UPGRADE_PLUS_DMG = 2
        private const val DIVIDER = 12
        private const val DIV_UPG = 10
    }
}
