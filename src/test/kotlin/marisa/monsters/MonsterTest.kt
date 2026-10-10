package marisa.monsters

import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.testing.Combat
import marisa.testing.assertSnapshot
import kotlin.test.Test

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
}
