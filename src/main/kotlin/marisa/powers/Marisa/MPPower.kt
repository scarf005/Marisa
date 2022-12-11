package marisa.powers.Marisa

import com.badlogic.gdx.graphics.Texture
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.powers.AbstractPower

class MPPower(owner: AbstractCreature?, amount: Int) : AbstractPower() {
    var ene = 0

    init {
        name = NAME
        ID = POWER_ID
        this.owner = owner
        this.amount = amount
        type = PowerType.BUFF
        updateDescription()
        img = Texture("img/powers/doubleDamage.png")
    }

    override fun atDamageFinalGive(damage: Float, type: DamageType): Float {
        return if (type == DamageType.NORMAL) {
            (damage * Math.pow(2.0, amount.toDouble())).toFloat()
        } else damage
    }

    override fun updateDescription() {
        description = DESCRIPTIONS[0] + Math.pow(2.0, amount.toDouble()) + DESCRIPTIONS[1]
    }

    override fun atEndOfTurn(isPlayer: Boolean) {
        if (isPlayer) {
            AbstractDungeon.actionManager.addToBottom(RemoveSpecificPowerAction(owner, owner, this))
        }
    } /*
	@Override
	public void onDrawOrDiscard() {
		ThMod.logger.info("MPPower : onDrawOrDiscard : DiscardNoneAttack");
		DiscardNoneAttack();
	}

	@Override
	public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
		ThMod.logger.info("MPPower : onApplyPower : DiscardNoneAttack");
		DiscardNoneAttack();
	}
	@Override
	public void onInitialApplication() {
		ThMod.logger.info("MPPower : onInitialApplication : DiscardNoneAttack");
		DiscardNoneAttack();
	}

	private void DiscardNoneAttack() {
		for (AbstractCard c : AbstractDungeon.player.hand.group) {
			if (c.type != CardType.ATTACK) {
				AbstractDungeon.actionManager.addToBottom(
						new DiscardSpecificCardAction(c)
					);
			}
		}
	}


	*/

    companion object {
        const val POWER_ID = "MPPower"
        private val powerStrings = CardCrawlGame.languagePack
            .getPowerStrings(POWER_ID)
        val NAME = powerStrings.NAME
        val DESCRIPTIONS = powerStrings.DESCRIPTIONS
    }
}
