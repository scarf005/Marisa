package marisa

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon

fun addToBot(vararg actions: AbstractGameAction) =
    actions.forEach { AbstractDungeon.actionManager.addToBottom(it) }

fun addToTop(vararg actions: AbstractGameAction) =
    actions.forEach { AbstractDungeon.actionManager.addToTop(it) }
