package marisa.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction
import com.megacrit.cardcrawl.cards.AbstractCard.CardType
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon

class DarkSparkAction(multiDamage: IntArray, damageType: DamageType?) : AbstractGameAction() {
    private val multiDamage: IntArray

    init {
        actionType = ActionType.EXHAUST
        duration = Settings.ACTION_DUR_FAST
        this.multiDamage = multiDamage
        this.damageType = damageType
    }

    override fun update() {
        isDone = true
        val p = AbstractDungeon.player
        if (p.drawPile.isEmpty) {
            return
        }
        val card = p.drawPile.topCard
        p.drawPile.moveToExhaustPile(card)
        if (card.type == CardType.ATTACK) {
            addToTop(DamageAllEnemiesAction(p, multiDamage, damageType, AttackEffect.FIRE))
        }
    }
}
