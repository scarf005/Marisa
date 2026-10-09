package marisa.cards

import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.MarisaContinued
import marisa.abstracts.MarisaCard
import marisa.action.BlazeAwayAction

class BlazeAway : MarisaCard(ID, "blazeAway", COST, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF) {
    init {
        magicNumber = USE_TIMES
        baseMagicNumber = magicNumber
        exhaust = true
    }

    private val desc get() = strings.EXTENDED_DESCRIPTION.let { Description(it[0], it[1], it[2]) }

    private fun lastAttack() = AbstractDungeon.actionManager.cardsPlayedThisTurn
        .reversed()
        .find { it.type == CardType.ATTACK }

    override fun applyPowers() {
        rawDescription = "${strings.DESCRIPTION}${desc.render(lastAttack())}"
        initializeDescription()
    }

    override fun onMoveToDiscard() {
        rawDescription = strings.DESCRIPTION
        initializeDescription()
    }

    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        val last = lastAttack() ?: return

        MarisaContinued.logger.info("""BlazeAway: last attack :${last.cardID}""")
        val lastCard = last.makeStatEquivalentCopy()

        with(lastCard) {
            MarisaContinued.logger.info(
                """BlazeAway: card :$cardID;
                        |baseD: $baseDamage; Damage: $damage; baseB :$baseBlock ; B: $block ;
                        |baseM: $baseMagicNumber ; M : $magicNumber ; C : $cost ; CFT: $costForTurn""".trimMargin()
            )
        }
        repeat(magicNumber) { addToBot(BlazeAwayAction(lastCard)) }
    }

    override fun makeCopy(): AbstractCard = BlazeAway()

    override fun upgrade() {
        if (upgraded) return
        upgradeMagicNumber(UPGRADE_USE_TIMES)
        upgradeName()
    }

    companion object {
        const val ID = "marisa:BlazeAway"

        private data class Description(val opening: String, val closing: String, val none: String) {
            fun render(last: AbstractCard?) = last?.let { "$opening${it.name}$closing" } ?: none
        }

        private const val COST = 1
        private const val USE_TIMES = 1
        private const val UPGRADE_USE_TIMES = 1
    }
}
