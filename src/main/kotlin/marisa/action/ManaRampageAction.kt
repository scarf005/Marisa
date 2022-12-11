package marisa.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.ui.panels.EnergyPanel
import marisa.MarisaMod

class ManaRampageAction(amount: Int, upgraded: Boolean, freeToPlay: Boolean) : AbstractGameAction() {
    private val f2p: Boolean
    var p: AbstractPlayer
    var upgraded: Boolean

    init {
        duration = Settings.ACTION_DUR_FAST
        this.amount = amount
        f2p = freeToPlay
        p = AbstractDungeon.player
        this.upgraded = upgraded
        MarisaMod.logger.info(
            "ManaRampageAction : Initialize complete ; card number :" +
                    amount +
                    " ; upgraded : " +
                    upgraded
        )
    }

    override fun update() {
        for (i in 0 until amount) {
            AbstractDungeon.actionManager.addToBottom(
                PlayManaRampageCardAction(upgraded)
            )
        }
        if (!f2p) {
            p.energy.use(EnergyPanel.totalCount)
        }
        isDone = true
    }
}
