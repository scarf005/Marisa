package marisa.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.actions.utility.WaitAction
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import marisa.p
import marisa.runThenDone


class RandomDamageAction(
    private val numTimes: Int,
    private val getDamage: () -> Int,
) : AbstractGameAction() {

    private fun doAttack() {
        marisa.addToTop(
            DamageAction(
                AbstractDungeon.getMonsters().getRandomMonster(true),
                DamageInfo(p, getDamage()),
                AttackEffect.FIRE
            ),
            WaitAction(POST_ATTACK_WAIT_DUR),
        )
    }

    override fun update() = runThenDone {
        if (numTimes > 0) doAttack()
        if (numTimes > 1) {
            addToTop(RandomDamageAction(numTimes - 1, getDamage))
        }
    }

    companion object {
        private const val POST_ATTACK_WAIT_DUR = 0.2f
    }
}
