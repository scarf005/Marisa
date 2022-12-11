package marisa.powers.Marisa

import com.badlogic.gdx.graphics.Texture
import com.megacrit.cardcrawl.actions.common.GainEnergyAction
import com.megacrit.cardcrawl.actions.utility.UseCardAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.powers.AbstractPower
import marisa.MarisaMod

class SatelIllusPower(owner: AbstractCreature?, amount: Int) : AbstractPower() {
    private var counter: Int

    fun checkDrawPile() {
        val temp = AbstractDungeon.player.drawPile.size()
        MarisaMod.logger.info(
            """SatelIllusPower : checkDrawPile : counter : $counter ; 
                |drawPile size $temp ; grant energy :${temp > counter}""".trimMargin()
        )
        if (temp > counter) {
            flash()
            AbstractDungeon.actionManager.addToBottom(
                GainEnergyAction(amount)
            )
        }
        if (temp != counter) {
            counter = temp
        }
    }

    init {
        name = NAME
        ID = POWER_ID
        this.owner = owner
        this.amount = amount
        type = PowerType.BUFF
        updateDescription()
        img = Texture("img/powers/satelIllu.png")
        counter = AbstractDungeon.player.drawPile.size()
    }

    override fun onDrawOrDiscard() {
        MarisaMod.logger.info("SatelIllusPower : onDrawOrDiscard : checkDrawPile")
        checkDrawPile()
    }

    override fun onApplyPower(power: AbstractPower, target: AbstractCreature, source: AbstractCreature) {
        MarisaMod.logger.info("SatelIllusPower : onApplyPower : checkDrawPile")
        checkDrawPile()
    }

    override fun onInitialApplication() {
        MarisaMod.logger.info("SatelIllusPower : onInitialApplication : checkDrawPile")
        checkDrawPile()
    }

    override fun atEndOfRound() {
        MarisaMod.logger.info("SatelIllusPower : checkDrawPile : atEndOfRound ")
        checkDrawPile()
    }

    override fun onAfterUseCard(card: AbstractCard, action: UseCardAction) {
        MarisaMod.logger.info("""SatelIllusPower : checkDrawPile : onAfterUseCard : ${card.cardID}""")
        checkDrawPile()
    }

    override fun atStartOfTurnPostDraw() {
        MarisaMod.logger.info("SatelIllusPower : checkDrawPile : atStartOfTurnPostDraw ")
        checkDrawPile()
    }

    override fun updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1]
    }

    companion object {
        const val POWER_ID = "SatelIllusPower"
        private val powerStrings = CardCrawlGame.languagePack
            .getPowerStrings(POWER_ID)
        val NAME = powerStrings.NAME
        val DESCRIPTIONS = powerStrings.DESCRIPTIONS
    }
}
