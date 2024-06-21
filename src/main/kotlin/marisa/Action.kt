@file:Suppress("FunctionName")

package marisa

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.powers.AbstractPower
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

/** mustn't be used in compendium, since player is null there */
val p: AbstractPlayer get() = AbstractDungeon.player
val monsters: ArrayList<AbstractMonster> get() = AbstractDungeon.getCurrRoom().monsters.monsters

/** Add actions to bottom of the action queue */
fun addToBot(vararg actions: AbstractGameAction) =
    actions.forEach { AbstractDungeon.actionManager.addToBottom(it) }

/** Add actions to bottom of the action queue */
fun addToBot(actions: List<AbstractGameAction>) =
    actions.forEach { AbstractDungeon.actionManager.addToBottom(it) }

/** Add actions to top of the action queue */
fun addToTop(vararg actions: AbstractGameAction) =
    actions.forEach { AbstractDungeon.actionManager.addToTop(it) }

/** Add actions to top of the action queue */
fun addToTop(actions: List<AbstractGameAction>) =
    actions.forEach { AbstractDungeon.actionManager.addToTop(it) }

fun ApplyPowerToPlayerAction(powerClass: KClass<out AbstractPower>) =
    powerClass.primaryConstructor?.call(p).let { power -> ApplyPowerAction(p, p, power) }

fun AbstractPower.RemoveSelfAction(): RemoveSpecificPowerAction =
    RemoveSpecificPowerAction(owner, owner, this)

fun AbstractGameAction.updateContext(block: () -> Unit) {
    block()
    isDone = true
}

fun AbstractGameAction.runThenDone(fn: (AbstractGameAction) -> Unit) {
    fn(this)
    isDone = true
}
