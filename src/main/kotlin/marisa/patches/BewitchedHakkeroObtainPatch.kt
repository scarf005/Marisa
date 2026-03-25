package marisa.patches

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier
import marisa.relics.BewitchedHakkero
import marisa.relics.MiniHakkero

class BewitchedHakkeroObtainPatch {
    @SpirePatch(cls = "com.megacrit.cardcrawl.dungeons.AbstractDungeon", method = "returnRandomRelicKey")
    object ReturnRelicPatch {
        @SpirePostfixPatch
        @JvmStatic
        fun Postfix(retVal: String, tier: RelicTier?): String {
            return if (retVal == BewitchedHakkero.ID && !AbstractDungeon.player.hasRelic(MiniHakkero.ID)) {
                AbstractDungeon.returnRandomRelicKey(tier)
            } else retVal
        }
    }

    @SpirePatch(cls = "com.megacrit.cardcrawl.dungeons.AbstractDungeon", method = "returnEndRandomRelicKey")
    object ReturnEndRelicPatch {
        @SpirePostfixPatch
        @JvmStatic
        fun Postfix(retVal: String, tier: RelicTier?): String {
            return if (retVal == BewitchedHakkero.ID && !AbstractDungeon.player.hasRelic(MiniHakkero.ID)) {
                AbstractDungeon.returnEndRandomRelicKey(tier)
            } else retVal
        }
    }
}
