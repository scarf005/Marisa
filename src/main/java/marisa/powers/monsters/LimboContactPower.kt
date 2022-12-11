package marisa.powers.monsters

import com.badlogic.gdx.graphics.Texture
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.powers.AbstractPower

class LimboContactPower(owner: AbstractCreature?) : AbstractPower() {
    init {
        name = NAME
        ID = POWER_ID
        this.owner = owner
        amount = -1
        updateDescription()
        img = Texture("img/powers/poison.png")
    }

    override fun updateDescription() {
        description = DESCRIPTIONS[0]
    }

    override fun stackPower(amount: Int) {}
    override fun onAttack(info: DamageInfo, damageAmount: Int, target: AbstractCreature) {
        run {
            val p = AbstractDungeon.player
            //if (damageAmount > 0)
            if (target === p) {
                AbstractDungeon.actionManager.addToBottom(
                    ApplyPowerAction(
                        p, this.owner, WraithPower(p, 1), 1
                    )
                )
            }
        }
    }

    override fun onDeath() {
        super.onDeath()
        val p = AbstractDungeon.player
        AbstractDungeon.actionManager.addToBottom(
            ApplyPowerAction(
                p, null, WraithPower(p, 1), 1
            )
        )
        /*
    for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
      if (!m.isDeadOrEscaped()) {
        AbstractDungeon.actionManager.addToBottom(
            new ApplyPowerAction(m, null, new StrengthPower(m, 1), 1)
        );
      }
    }
    */
    }

    companion object {
        const val POWER_ID = "LimboContact"
        private val powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID)
        val NAME = powerStrings.NAME
        val DESCRIPTIONS = powerStrings.DESCRIPTIONS
    }
}
