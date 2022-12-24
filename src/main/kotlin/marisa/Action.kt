package marisa

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon

fun addToBot(action: List<AbstractGameAction>) =
    action.forEach { AbstractDungeon.actionManager.addToBottom(it) }

fun addToTop(action: List<AbstractGameAction>) =
    action.forEach { AbstractDungeon.actionManager.addToTop(it) }
