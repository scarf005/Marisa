package marisa.patches

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import marisa.powers.Marisa.PropBagPower

class SmokeBombNPropBagPatch {
    @SpirePatch(cls = "com.megacrit.cardcrawl.potions.SmokeBomb", method = "use")
    object SmokePatch {
        @JvmStatic
        fun Prefix() {
            for (p in AbstractDungeon.player.powers) {
                (p as? PropBagPower)?.onVictory()
            }
        }
    }
}
