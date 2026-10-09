package marisa.powers.Marisa

import com.megacrit.cardcrawl.core.AbstractCreature
import marisa.abstracts.MarisaPower

class WitchOfGreedPotion(owner: AbstractCreature?, amount: Int) : MarisaPower(POWER_ID, owner, amount, "potion") {
    init {
        updateDescription()
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
        description = descriptions[0] + amount + descriptions[1]
    }

    companion object {
        const val POWER_ID = "marisa:WitchOfGreedPotion"
    }
}
