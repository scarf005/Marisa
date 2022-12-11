package marisa.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.utility.WaitAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.AbstractCard.CardType
import com.megacrit.cardcrawl.cards.CardQueueItem
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import marisa.MarisaMod

//public class BlazeAwayAction {
class BlazeAwayAction(card: AbstractCard) : AbstractGameAction() {
    private lateinit var card: AbstractCard
    var p: AbstractPlayer

    init {
        duration = Settings.ACTION_DUR_FAST
        p = AbstractDungeon.player
        if (card.type == CardType.ATTACK) {
            this.card = card
        } else {
            isDone = true
        }
        MarisaMod.logger.info(
            "BlazeAwayAction : Initialize complete ; card : " +
                    card.name
        )
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

        MarisaMod.logger.info(
            """BlazeAwayAction : card : ${card.cardID} ; target : ${target.id}"""
        )
        card.applyPowers()
        AbstractDungeon.actionManager.currentAction = null
        AbstractDungeon.actionManager.addToTop(this)
        AbstractDungeon.actionManager.cardQueue.add(
            CardQueueItem(card, target)
        )
        if (!Settings.FAST_MODE) {
            AbstractDungeon.actionManager.addToTop(WaitAction(Settings.ACTION_DUR_MED))
        } else {
            AbstractDungeon.actionManager.addToTop(WaitAction(Settings.ACTION_DUR_FASTER))
        }
        isDone = true
    }
}
