package marisa.testing

import basemod.ReflectionHacks
import com.badlogic.gdx.math.MathUtils
import com.megacrit.cardcrawl.actions.GameActionManager
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.CardGroup
import com.megacrit.cardcrawl.cards.CardQueueItem
import com.megacrit.cardcrawl.cards.SoulGroup
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.core.OverlayMenu
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.dungeons.Exordium
import com.megacrit.cardcrawl.map.MapRoomNode
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.monsters.MonsterGroup
import com.megacrit.cardcrawl.monsters.exordium.Cultist
import com.megacrit.cardcrawl.powers.AbstractPower
import com.megacrit.cardcrawl.rooms.AbstractRoom
import com.megacrit.cardcrawl.rooms.MonsterRoom
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen
import com.megacrit.cardcrawl.ui.panels.EnergyPanel
import marisa.characters.Marisa

/** A fight in the first act against [spawn]ed monsters with Marisa, no relics, empty piles and 3 energy. */
class Combat(spawn: () -> List<AbstractMonster> = { listOf(Cultist(0f, 0f)) }) {
    val player: AbstractPlayer
    val monsters: List<AbstractMonster>
    val monster get() = monsters.first()

    /** Picks [count] of the offered cards whenever a card selection screen opens; the first ones by default. */
    var choose: (offered: List<AbstractCard>, count: Int) -> List<AbstractCard> = { offered, count -> offered.take(count) }

    init {
        Game.init()
        Settings.seed = 0L
        AbstractDungeon.generateSeeds()
        // Vanilla visuals and some targeting draw from this unseeded generator.
        MathUtils.random.setSeed(0L)
        monsters = spawn()
        AbstractDungeon.actionManager = GameActionManager()
        AbstractDungeon.effectList.clear()
        player = Marisa("Marisa")
        AbstractDungeon.player = player
        AbstractDungeon.overlayMenu = OverlayMenu(player)
        CardCrawlGame.dungeon = Exordium(player, ArrayList())
        listOf(player.hand, player.drawPile, player.discardPile, player.exhaustPile).forEach { it.clear() }
        player.relics.clear()
        AbstractDungeon.currMapNode = MapRoomNode(0, 0).apply {
            room = MonsterRoom().apply {
                monsters = MonsterGroup(this@Combat.monsters.toTypedArray()).apply { init() }
                phase = AbstractRoom.RoomPhase.COMBAT
            }
        }
        AbstractDungeon.lastCombatMetricKey = monsters.joinToString(" and ") { it.id }
        EnergyPanel.totalCount = 3
    }

    /** Plays [card] from hand the way releasing it on [target] does, then runs the game until it settles. */
    fun play(card: AbstractCard, target: AbstractMonster? = monster) {
        if (card !in player.hand.group) player.hand.addToTop(card)
        if (card.cost == -1) card.energyOnUse = EnergyPanel.totalCount
        card.applyPowers()
        AbstractDungeon.actionManager.cardQueue.add(CardQueueItem(card, target))
        resolve()
    }

    /** Applies [power] to the player through the action queue, as cards do. */
    fun applyToPlayer(power: AbstractPower) {
        AbstractDungeon.actionManager.addToBottom(ApplyPowerAction(player, player, power, power.amount))
        resolve()
    }

    /** Runs the action manager and the effects it starts, frame by frame, until it waits for the player again. */
    fun resolve() {
        val manager = AbstractDungeon.actionManager
        repeat(60 * 60) {
            if (AbstractDungeon.isScreenUp) select() else manager.update()
            updateEffects()
            if (manager.phase == GameActionManager.Phase.WAITING_ON_USER && manager.actions.isEmpty() &&
                manager.cardQueue.isEmpty() && isIdle()
            ) return
        }
        val pending = listOfNotNull(manager.currentAction) + AbstractDungeon.effectList + AbstractDungeon.topLevelEffects
        error("${pending.map { it.javaClass.simpleName }} still running after a minute, souls active: ${SoulGroup.isActive()}")
    }

    /** Makes the choice the open selection screen waits for, like a player confirming it. */
    private fun select() {
        when (AbstractDungeon.screen) {
            AbstractDungeon.CurrentScreen.HAND_SELECT -> AbstractDungeon.handCardSelectScreen.run {
                choose(player.hand.group.toList(), numCardsToSelect).forEach {
                    player.hand.removeCard(it)
                    selectedCards.addToTop(it)
                }
                wereCardsRetrieved = false
            }

            AbstractDungeon.CurrentScreen.GRID -> AbstractDungeon.gridSelectScreen.run {
                val count = ReflectionHacks.getPrivate<Int>(this, GridCardSelectScreen::class.java, "numCards")
                selectedCards.addAll(choose(targetGroup.group.toList(), count))
            }

            AbstractDungeon.CurrentScreen.CARD_REWARD -> AbstractDungeon.cardRewardScreen.run {
                discoveryCard = choose(rewardGroup.toList(), 1).single()
            }

            else -> error("no choice for ${AbstractDungeon.screen}")
        }
        AbstractDungeon.closeCurrentScreen()
    }

    private fun isIdle() = AbstractDungeon.effectList.isEmpty() && AbstractDungeon.effectsQueue.isEmpty() &&
        AbstractDungeon.topLevelEffects.isEmpty() && AbstractDungeon.topLevelEffectsQueue.isEmpty() &&
        !SoulGroup.isActive()

    /** The effect and soul updates of `AbstractDungeon.update` and `AbstractRoom.update`. */
    private fun updateEffects() {
        AbstractDungeon.getCurrRoom().souls.update()
        listOf(
            AbstractDungeon.effectList to AbstractDungeon.effectsQueue,
            AbstractDungeon.topLevelEffects to AbstractDungeon.topLevelEffectsQueue,
        ).forEach { (effects, queue) ->
            effects.toList().forEach { it.update() }
            effects.removeAll { it.isDone }
            effects.addAll(queue)
            queue.clear()
        }
    }

    /** Everything a card can change, one line per creature and one for the piles. */
    fun state(): String {
        // Prop Bag numbers its power ids per game session.
        fun AbstractCreature.describe() = "hp=$currentHealth/$maxHealth block=$currentBlock" +
            " powers=${powers.map { "${it.ID.trimEnd(Char::isDigit)}(${it.amount})" }}"

        fun CardGroup.ids() = group.map { it.cardID + if (it.upgraded) "+" else "" }
        return listOf(
            "player ${player.describe()} energy=${EnergyPanel.totalCount} gold=${player.gold}" +
                " potions=${player.potions.map { it.ID }.filter { it != "Potion Slot" }}",
            *monsters.map { "${it.id} ${it.describe()}" }.toTypedArray(),
            "hand=${player.hand.ids()} draw=${player.drawPile.ids()} discard=${player.discardPile.ids()}" +
                " exhaust=${player.exhaustPile.ids()}",
        ).joinToString("\n")
    }
}
