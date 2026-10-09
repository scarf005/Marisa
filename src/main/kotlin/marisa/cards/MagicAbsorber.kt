package marisa.cards

import com.megacrit.cardcrawl.actions.common.GainBlockAction
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.powers.AbstractPower
import marisa.abstracts.MarisaCard

class MagicAbsorber : MarisaCard(ID, "MagicAbsorber", COST, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF) {
    init {
        baseBlock = BLOCK_AMT
        baseMagicNumber = 1
        magicNumber = baseMagicNumber
        exhaust = true
    }

    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        addToBot(
            GainBlockAction(p, p, block)
        )
        if (p.powers.isNotEmpty()) {
            val pows = ArrayList<AbstractPower>()
            for (pow in p.powers) {
                if (pow.type == AbstractPower.PowerType.DEBUFF) {
                    pows.add(pow)
                }
            }
            if (pows.isNotEmpty()) {
                val po = pows[AbstractDungeon.miscRng.random(0, pows.size - 1)]
                addToBot(
                    RemoveSpecificPowerAction(p, p, po)
                )
            }
        }
    }

    override fun makeCopy(): AbstractCard = MagicAbsorber()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        upgradeBlock(UPGRADE_PLUS_BLOCK)
    }

    companion object {
        const val ID = "marisa:MagicAbsorber"
        private const val COST = 1
        private const val BLOCK_AMT = 8
        private const val UPGRADE_PLUS_BLOCK = 3
    }
}
