package marisa.event

import basemod.ReflectionHacks
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.events.AbstractEvent
import com.megacrit.cardcrawl.events.RoomEventDialog
import com.megacrit.cardcrawl.rooms.EventRoom
import marisa.testing.Combat
import marisa.testing.assertSnapshot
import kotlin.test.Test

/** Picks options of the mod's events and records what each choice changed. */
class EventTest {
    /**
     * Enters an event room with the [event] it makes, as EventRoom.onPlayerEntry does,
     * with Marisa at half health, then picks [options] in order.
     */
    private fun choose(title: String, event: () -> AbstractEvent, vararg options: Int): String {
        val combat = Combat()
        val player = combat.player
        player.currentHealth = player.maxHealth / 2
        val room = EventRoom()
        AbstractDungeon.currMapNode.room = room
        room.event = event()
        room.event.onEnterRoom()
        return "$title\n" + options.joinToString("") { option ->
            ReflectionHacks.privateMethod(AbstractEvent::class.java, "buttonEffect", Int::class.java)
                .invoke<Unit>(room.event, option)
            combat.resolve()
            "option $option\n" + listOf(
                "phase=${room.phase} monsters=${room.monsters?.monsters?.map { it.id }}",
                "hp=${player.currentHealth}/${player.maxHealth} relics=${player.relics.map { it.relicId }}" +
                    " deck=${player.masterDeck.group.map { it.cardID }}",
                "rewards=${room.rewards.map { it.type.name + (it.relic?.relicId?.let { id -> ":$id" } ?: "") }}",
                "options=${RoomEventDialog.optionList.map { it.msg }}",
            ).joinToString("\n").prependIndent("  ") + "\n"
        }.prependIndent("  ").trimEnd() + "\n"
    }

    @Test
    fun `Mushrooms heal for a Parasite or lead to a fight`() = assertSnapshot(
        "events-mushrooms",
        choose("eat", ::Mushrooms_MRS, 1) + choose("fight", ::Mushrooms_MRS, 0, 0),
    )

    @Test
    fun `Orin the Cat gives Wraith or a fight`() = assertSnapshot(
        "events-orin",
        choose("skip", ::OrinTheCat, 0) + choose("fight", ::OrinTheCat, 1, 0),
    )
}
