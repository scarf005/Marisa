package marisa.patches;

import marisa.Marisa;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import marisa.relics.SproutingBranch;

public class DeadBranchObtainPatch {

  @SpirePatch(cls = "com.megacrit.cardcrawl.relics.DeadBranch", method = "makeCopy")
  public static class DeadBranchObtain {

    @SpirePrefixPatch
    public static SpireReturn<AbstractRelic> Prefix(AbstractRelic _inst) {
      if ((AbstractDungeon.player instanceof Marisa)&&(!Marisa.isDeadBranchEnabled)) {
        return SpireReturn.Return(new SproutingBranch());
      }
      return SpireReturn.Continue();
    }
  }
}
