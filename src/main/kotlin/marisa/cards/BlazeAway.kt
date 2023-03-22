package marisa.cards

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.MarisaContinued
import marisa.action.BlazeAwayAction
import marisa.patches.AbstractCardEnum

class BlazeAway : CustomCard(
    ID,
    NAME,
    IMG_PATH,
    COST,
    DESCRIPTION,
    CardType.SKILL,
    AbstractCardEnum.MARISA_COLOR,
    CardRarity.UNCOMMON,
    CardTarget.SELF
) {
    init {
        magicNumber = USE_TIMES
        baseMagicNumber = magicNumber
    }

    private fun lastAttack() = AbstractDungeon.actionManager.cardsPlayedThisTurn
        .reversed()
        .find { it.type == CardType.ATTACK }

    override fun applyPowers() {
        rawDescription = "$DESCRIPTION${desc.render(lastAttack())}"
        initializeDescription()
    }

    override fun onMoveToDiscard() {
        rawDescription = DESCRIPTION
        initializeDescription()
    }

    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        val last = lastAttack() ?: return

        MarisaContinued.logger.info("""BlazeAway : last attack :${last.cardID}""")
        val card = last.makeStatEquivalentCopy()
            .also {
                MarisaContinued.logger.info(
                    """BlazeAway: card :$cardID; 
                            |baseD :$baseDamage; Damage: $damage; baseB :$baseBlock ; B : $block ; 
                            |baseM :$baseMagicNumber ; M : $magicNumber ; C : $cost ; CFT : $costForTurn""".trimMargin()
                )
            }
        repeat(magicNumber) { addToBot(BlazeAwayAction(card)) }
    }

    override fun makeCopy(): AbstractCard = BlazeAway()

    override fun upgrade() {
        if (!upgraded) {
            upgradeMagicNumber(UPGRADE_USE_TIMES)
            upgradeName()
        }
    }

    companion object {
        const val ID = "BlazeAway"
        const val IMG_PATH = "img/cards/blazeAway.png"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        val DESCRIPTION_UPG = cardStrings.UPGRADE_DESCRIPTION

        private data class Description(val opening: String, val closing: String, val none: String) {
            fun render(last: AbstractCard?) = last?.let { "$opening${it.name}$closing" } ?: none
        }

        private val desc = cardStrings.EXTENDED_DESCRIPTION.let {
            Description(it[0], it[1], it[2])
        }
        private const val COST = 1
        private const val USE_TIMES = 1
        private const val UPGRADE_USE_TIMES = 1
    }
}
