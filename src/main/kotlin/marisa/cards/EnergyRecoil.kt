package marisa.cards

import com.megacrit.cardcrawl.actions.common.GainBlockAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.abstracts.MarisaCard
import marisa.powers.Marisa.ChargeUpPower

class EnergyRecoil : MarisaCard(ID, "recoil", COST, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF) {
    init {
        block = 0
        baseBlock = block
    }

    override fun applyPowers() {
        val p = AbstractDungeon.player
        baseBlock = (if (upgraded) 3 else 0)
        if (p.hasPower(ChargeUpPower.POWER_ID)) {
            baseBlock += p.getPower(ChargeUpPower.POWER_ID).amount
            super.applyPowers()
        }
        if (block > 0) {
            val extendString = strings.EXTENDED_DESCRIPTION[0] + block + strings.EXTENDED_DESCRIPTION[1]
            rawDescription = if (upgraded) {
                strings.UPGRADE_DESCRIPTION + extendString
            } else {
                strings.DESCRIPTION + extendString
            }
            initializeDescription()
        }
    }

    override fun onMoveToDiscard() {
        rawDescription = if (upgraded) {
            strings.UPGRADE_DESCRIPTION
        } else {
            strings.DESCRIPTION
        }
        initializeDescription()
    }

    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        if (block > 0) {
            addToBot(
                GainBlockAction(p, p, block)
            )
        }
    }

    override fun makeCopy(): AbstractCard = EnergyRecoil()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        rawDescription = strings.UPGRADE_DESCRIPTION
        initializeDescription()
    }

    companion object {
        const val ID = "marisa:EnergyRecoil"
        private const val COST = 1
    }
}
