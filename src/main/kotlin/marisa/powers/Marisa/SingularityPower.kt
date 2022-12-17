package marisa.powers.Marisa

import com.badlogic.gdx.graphics.Texture
import com.megacrit.cardcrawl.actions.utility.UseCardAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.AbstractCard.CardType
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.powers.AbstractPower
import marisa.MarisaContinued

class SingularityPower(owner: AbstractCreature?, amount: Int) : AbstractPower() {
    init {
        name = NAME
        ID = POWER_ID
        this.owner = owner
        this.amount = amount
        type = PowerType.BUFF
        updateDescription()
        img = Texture("img/powers/singularity.png")
    }

    override fun updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1]
    }

    override fun onAfterUseCard(card: AbstractCard, action: UseCardAction) {
        if (card.costForTurn == 0 || card.costForTurn <= -2 || card.costForTurn == -1 && AbstractDungeon.player.energy.energy <= 0) {
            MarisaContinued.logger.info("SingularityPower : applying upgrade :")
            flash()
            val pool = ArrayList<AbstractCard>()
            for (c in AbstractDungeon.player.hand.group) {
                if (c.type == CardType.ATTACK) {
                    pool.add(c)
                }
            }
            if (pool.isNotEmpty()) {
                val rand = AbstractDungeon.miscRng.random(0, pool.size - 1)
                val c = pool[rand]
                MarisaContinued.logger.info(
                    "SingularityPower : adding "
                            + amount
                            + " base damage to "
                            + c.cardID
                            + " random res :"
                            + rand
                )
                c.baseDamage += amount
                c.applyPowers()
                c.flash()
            }
        }
    }

    companion object {
        const val POWER_ID = "SingularityPower"
        private val powerStrings = CardCrawlGame.languagePack
            .getPowerStrings(POWER_ID)
        val NAME = powerStrings.NAME
        val DESCRIPTIONS = powerStrings.DESCRIPTIONS
    }
}
