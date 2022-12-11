package marisa.relics

import basemod.abstracts.CustomRelic
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.helpers.ImageMaster
import com.megacrit.cardcrawl.powers.*
import com.megacrit.cardcrawl.relics.AbstractRelic
import marisa.powers.Marisa.ChargeUpPower

class RampagingMagicTools : CustomRelic(
    ID,
    ImageMaster.loadImage(IMG),
    ImageMaster.loadImage(IMG_OTL),
    RelicTier.BOSS,
    LandingSound.FLAT
) {
    override fun getUpdatedDescription(): String = DESCRIPTIONS[0]

    override fun makeCopy(): AbstractRelic = RampagingMagicTools()

    override fun onEquip() {
        AbstractDungeon.player.energy.energyMaster += 1
    }

    override fun onUnequip() {
        AbstractDungeon.player.energy.energyMaster -= 1
    }

    override fun atBattleStart() {
        val rng = AbstractDungeon.miscRng.random(0, 4)
        var pow: AbstractPower? = null
        var stc = STACK
        val p = AbstractDungeon.player
        when (rng) {
            0 -> pow = FrailPower(p, stc, false)
            1 -> pow = WeakPower(p, stc, false)
            2 -> pow = VulnerablePower(p, stc, false)
            3 -> {
                stc = STACK_POISON
                pow = PoisonPower(p, p, stc)
            }

            4 -> {
                stc = STCS_H
                pow = ChargeUpPower(p, stc)
            }

            else -> {}
        }
        if (pow != null) {
            AbstractDungeon.actionManager.addToBottom(
                RelicAboveCreatureAction(AbstractDungeon.player, this)
            )
            AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(p, p, pow, stc)
            )
        }
    }

    companion object {
        const val ID = "RampagingMagicTools"
        private const val IMG = "img/relics/RamTool.png"
        private const val IMG_OTL = "img/relics/outline/RamTool.png"
        private const val STACK = 2
        private const val STACK_POISON = 3
        private const val STCS_H = 8
    }
}
