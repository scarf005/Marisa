@file:Suppress("FunctionName")

package marisa

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.powers.AbstractPower
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

val p get() = AbstractDungeon.player

fun addToBot(vararg actions: AbstractGameAction) =
    actions.forEach { AbstractDungeon.actionManager.addToBottom(it) }

fun addToTop(vararg actions: AbstractGameAction) =
    actions.forEach { AbstractDungeon.actionManager.addToTop(it) }

fun ApplyPowerToPlayerAction(powerClass: KClass<out AbstractPower>) =
    powerClass.primaryConstructor?.call(p).let { power -> ApplyPowerAction(p, p, power) }

fun AbstractPower.RemoveSelfAction(): RemoveSpecificPowerAction =
    RemoveSpecificPowerAction(owner, owner, this)

fun AbstractGameAction.updateContext(block: () -> Unit) {
    block()
    isDone = true
}
