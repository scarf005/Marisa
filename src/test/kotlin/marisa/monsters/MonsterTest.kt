package marisa.monsters

import basemod.ReflectionHacks
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.testing.Combat
import marisa.testing.assertSnapshot
import kotlin.test.Test
import kotlin.test.assertEquals

/** Fights a mod monster with a player who survives it, recording the fight after each step. */
private class Fight(monster: () -> AbstractMonster) {
    val combat = Combat { listOf(monster()) }
    val log = StringBuilder()

    init {
        combat.player.maxHealth = 999
        combat.player.currentHealth = 999
        combat.resolve()
    }

    private fun record(step: String) {
        val intents = AbstractDungeon.getMonsters().monsters.map { "${it.id}:${it.intent}" }
        log.append("$step\n").append("${combat.state()}\nintents=$intents".prependIndent("  ")).append("\n")
    }

    fun endTurns(count: Int) = repeat(count) {
        combat.endTurn()
        record("end turn")
    }

    fun kill(monster: AbstractMonster = combat.monster) {
        AbstractDungeon.actionManager.addToBottom(
            DamageAction(monster, DamageInfo(combat.player, 999, DamageInfo.DamageType.HP_LOSS)),
        )
        combat.resolve()
        record("kill ${monster.id}")
    }
}

class MonsterTest {
    @Test
    fun `Orin turns into her second form, summons fairies and dies with them`() {
        val fight = Fight(::Orin)
        fight.endTurns(2)
        fight.kill()
        fight.endTurns(5)
        fight.kill()
        assertSnapshot("monsters-orin", fight.log.toString())
    }

    @Test
    fun `Zombie Fairy fights and dies`() {
        val fight = Fight { ZombieFairy() }
        fight.endTurns(4)
        fight.kill()
        assertSnapshot("monsters-zombie-fairy", fight.log.toString())
    }

    @Test
    fun `Zombie Fairy hits as often as its intent shows from its second turn`() {
        val combat = Fight { ZombieFairy() }.combat
        val fairy = combat.monster as ZombieFairy
        fairy.turnNum = 1
        // Rolls an attack for the second turn.
        ReflectionHacks.privateMethod(AbstractMonster::class.java, "getMove", Int::class.java).invoke<Unit>(fairy, 0)
        fairy.createIntent()
        val hits = ReflectionHacks.getPrivate<Int>(fairy, AbstractMonster::class.java, "intentMultiAmt").coerceAtLeast(1)
        val shown = hits * fairy.intentDmg
        val hp = combat.player.currentHealth
        combat.endTurn()
        assertEquals(shown, hp - combat.player.currentHealth)
    }

    @Test
    fun `Zombie Fairy's defend intent shows only the block it gives`() {
        val combat = Fight { ZombieFairy() }.combat
        val fairy = combat.monster as ZombieFairy
        // Rolls a defense for the first turn.
        ReflectionHacks.privateMethod(AbstractMonster::class.java, "getMove", Int::class.java).invoke<Unit>(fairy, 99)
        fairy.createIntent()
        assertEquals(AbstractMonster.Intent.DEFEND, fairy.intent)
    }
}
