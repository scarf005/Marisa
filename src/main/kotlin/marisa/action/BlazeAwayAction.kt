package marisa.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.utility.WaitAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.AbstractCard.CardType
import com.megacrit.cardcrawl.cards.CardQueueItem
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import marisa.MarisaContinued

class BlazeAwayAction(val card: AbstractCard) : AbstractGameAction() {

    init {
        duration = if (Settings.FAST_MODE) Settings.ACTION_DUR_FASTER else Settings.ACTION_DUR_MED
        isDone = card.type != CardType.ATTACK
        MarisaContinued.logger.info("BlazeAwayAction : Initialize complete ; card : ${card.name}")
    }

    override fun update() {
        val target = AbstractDungeon.getMonsters().getRandomMonster(true)

        AbstractDungeon.player.limbo.group.add(card)
        card.apply {
            current_x = Settings.WIDTH / 2.0f
            current_y = Settings.HEIGHT / 2.0f
            target_x = Settings.WIDTH / 2.0f - 300.0f * Settings.scale
            target_y = Settings.HEIGHT / 2.0f
            freeToPlayOnce = true
            purgeOnUse = true
            targetAngle = 0.0f
            drawScale = 0.12f
        }

        MarisaContinued.logger.info(
            """BlazeAwayAction : card : ${card.cardID} ; target : ${target.id}"""
        )
        card.applyPowers()
        AbstractDungeon.actionManager.run {
            currentAction = null
            addToTop(this@BlazeAwayAction)
            cardQueue.add(CardQueueItem(card, target))
            addToTop(WaitAction(duration))
        }
        isDone = true
    }
}
