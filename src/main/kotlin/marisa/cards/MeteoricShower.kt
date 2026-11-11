package marisa.cards

import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.status.Burn
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.relics.ChemicalX
import com.megacrit.cardcrawl.ui.panels.EnergyPanel
import marisa.abstracts.MarisaCard
import marisa.action.HandSelectAction
import marisa.action.RandomDamageAction
import marisa.countRelic
import marisa.exhaust
import marisa.fx.MeteoricShowerEffect
import marisa.partitionByType

class MeteoricShower : MarisaCard(ID, "meteoric", COST, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY) {
    init {
        baseDamage = ATK_DMG
    }

    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        val text = CardCrawlGame.languagePack.getUIString("ExhaustAction").TEXT[0]
        val amount = energyOnUse + 1 + countRelic(ChemicalX.ID) * 2
        val dmg = damage
        val free = freeToPlayOnce
        addToBot(HandSelectAction(text, amount, anyNumber = true, canPickZero = true) { cards ->
            val (burns, regular) = cards.partitionByType<Burn>()
            val cnt = regular.size * 2 + burns.size * 3
            cards.forEach { it.exhaust() }
            addToBot(MeteoricShowerEffect.toVfx(cnt))
            addToBot(RandomDamageAction(cnt) { dmg })
            if (!free) p.energy.use(EnergyPanel.totalCount)
        })
    }

    override fun makeCopy(): AbstractCard = MeteoricShower()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        upgradeDamage(UPG_DMG)
    }

    companion object {
        const val ID = "marisa:MeteoricShower"
        private const val COST = -1
        private const val ATK_DMG = 3
        private const val UPG_DMG = 1
    }
}
