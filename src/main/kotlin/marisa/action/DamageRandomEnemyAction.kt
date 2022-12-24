package marisa.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.dungeons.AbstractDungeon

class DamageRandomEnemyAction(private val info: DamageInfo, effect: AttackEffect?) :
    AbstractGameAction() {
    init {
        setValues(AbstractDungeon.getMonsters().getRandomMonster(true), info)
        actionType = ActionType.DAMAGE
        attackEffect = effect
        duration = 0.1f
    }

    override fun update() {
        if (target == null) {
            isDone = true
            return
        }
        addToBot(
            DamageAction(target, info, attackEffect)
        )
        isDone = true
    }
}
