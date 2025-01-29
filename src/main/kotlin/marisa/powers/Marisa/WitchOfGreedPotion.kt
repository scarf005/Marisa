package marisa.powers.Marisa

import com.badlogic.gdx.graphics.Texture
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.powers.AbstractPower

class WitchOfGreedPotion(owner: AbstractCreature?, amount: Int) : AbstractPower() {
    init {
        name = NAME
        ID = POWER_ID
        this.owner = owner
        this.amount = amount
        type = PowerType.BUFF
        updateDescription()
        img = Texture("marisa/img/powers/potion.png")
    }

    /*
  public void onVictory() {
    for (int i = 0; i < this.amount; i++) {
      if (AbstractDungeon.player.hasRelic("Sozu")) {
        AbstractDungeon.player.getRelic("Sozu").flash();
      } else {
        AbstractDungeon.player.obtainPotion(AbstractDungeon.returnRandomPotion(true));
      }
    }
  }
*/
    override fun updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1]
    }

    companion object {
        const val POWER_ID = "marisa:WitchOfGreedPotion"
        private val powerStrings = CardCrawlGame.languagePack
            .getPowerStrings(POWER_ID)
        val NAME = powerStrings.NAME
        val DESCRIPTIONS = powerStrings.DESCRIPTIONS
    }
}
