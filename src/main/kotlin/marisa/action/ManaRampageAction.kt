package marisa.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.relics.ChemicalX
import com.megacrit.cardcrawl.ui.panels.EnergyPanel
import marisa.countRelic
import marisa.p

class ManaRampageAction(
    val energyOnUse: Int, val upgraded: Boolean, val freeToPlay: Boolean
) : AbstractGameAction() {

    init {
        duration = Settings.ACTION_DUR_FAST
    }

    override fun update() {
        val cnt = energyOnUse + countRelic(ChemicalX.ID) * 2
        for (i in 0..<cnt) {
            addToBot(PlayManaRampageCardAction(upgraded))
        }
        if (!freeToPlay) {
            p.energy.use(EnergyPanel.totalCount)
        }
        isDone = true
    }
}
