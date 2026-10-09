package marisa.powers.Marisa

import com.megacrit.cardcrawl.core.AbstractCreature
import marisa.abstracts.MarisaPower

class WitchOfGreedGold(owner: AbstractCreature?, amount: Int) : MarisaPower(POWER_ID, owner, amount, "coin") {
    init {
        updateDescription()
    }

    /*
  public void onVictory() {
    AbstractPlayer p = AbstractDungeon.player;
    for (int i = 0; i < this.amount; i++) {
      AbstractDungeon.effectList.add(
          new GainPennyEffect(p, p.hb.cX, p.hb.cY, p.hb.cX, p.hb.cY, true)
      );
    }
  }
*/
    override fun updateDescription() {
        description = descriptions[0] + amount + descriptions[1]
    }

    companion object {
        const val POWER_ID = "marisa:WitchOfGreedGold"
    }
}
