package marisa.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.utility.WaitAction
import com.megacrit.cardcrawl.cards.AbstractCard.CardType
import com.megacrit.cardcrawl.cards.CardQueueItem
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.MarisaMod

class PlayManaRampageCardAction internal constructor(upgraded: Boolean) : AbstractGameAction() {
    private val upgraded: Boolean

    init {
        duration = Settings.ACTION_DUR_FAST
        this.upgraded = upgraded
    }

    override fun update() {
        target = AbstractDungeon.getMonsters().getRandomMonster(true)
        val card = AbstractDungeon.returnTrulyRandomCardInCombat(CardType.ATTACK).makeCopy()
        if (upgraded) {
            card.upgrade()
        }
        AbstractDungeon.player.limbo.group.add(card)
        card.current_x = Settings.WIDTH / 2.0f
        card.current_y = Settings.HEIGHT / 2.0f
        card.target_x = Settings.WIDTH / 2.0f - 300.0f * Settings.scale
        card.target_y = Settings.HEIGHT / 2.0f
        card.freeToPlayOnce = true
        card.purgeOnUse = true
        card.targetAngle = 0.0f
        card.drawScale = 0.12f
        card.lighten(false)
        MarisaMod.logger.info(
            "PlayManaRampageCardAction : card : " +
                    card.cardID +
                    " ; target : " +
                    target.id
        )
        card.applyPowers()
        AbstractDungeon.actionManager.currentAction = null
        AbstractDungeon.actionManager.addToTop(this)
        AbstractDungeon.actionManager.cardQueue.add(
            CardQueueItem(card, target as AbstractMonster)
        )
        if (!Settings.FAST_MODE) {
            AbstractDungeon.actionManager.addToTop(WaitAction(Settings.ACTION_DUR_MED))
        } else {
            AbstractDungeon.actionManager.addToTop(WaitAction(Settings.ACTION_DUR_FASTER))
        }
        isDone = true
    }
}
