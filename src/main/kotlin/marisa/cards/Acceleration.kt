package marisa.cards

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.common.DrawCardAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.MarisaContinued
import marisa.abstracts.AmplifiableCard
import marisa.runThenDone

class Acceleration : AmplifiableCard(ID, "GuidingStar", COST, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF) {
    init {
        baseMagicNumber = AMP
        magicNumber = baseMagicNumber
        baseBlock = DRAW
        block = baseBlock
    }

    /** Classic draws only what the draw pile holds, without shuffling the discard pile in. */
    private fun draw(amount: Int) = addToBot(
        if (!MarisaContinued.isClassic) DrawCardAction(amount)
        else object : AbstractGameAction() {
            override fun update() = runThenDone {
                addToTop(DrawCardAction(minOf(amount, AbstractDungeon.player.drawPile.size())))
            }
        }
    )

    override fun applyPowersToBlock() {}
    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        draw(block)

        if (tryAmplify()) {
            draw(magicNumber)
        }
    }

    override fun makeCopy(): AbstractCard = Acceleration()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        upgradeMagicNumber(AMP_UPG)
    }

    companion object {
        const val ID = "marisa:Acceleration"
        private const val COST = 0
        private const val DRAW = 2

        private const val AMP = 1
        private const val AMP_UPG = 1
    }
}
