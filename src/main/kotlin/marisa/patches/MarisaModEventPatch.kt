@file:Suppress("unused")

package marisa.patches

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.events.AbstractEvent
import com.megacrit.cardcrawl.events.exordium.Mushrooms
import com.megacrit.cardcrawl.random.Random
import marisa.MarisaMod
import marisa.characters.Marisa
import marisa.event.Mushrooms_MRS
import marisa.event.OrinTheCat
import marisa.relics.CatCart

@Suppress("FunctionName")
class MarisaModEventPatch {
    @SpirePatch(clz = AbstractDungeon::class, method = "initializeCardPools")
    object EventPatch {

        private fun currentEvents() = AbstractDungeon.eventList.joinToString { "$it ; " }
        private fun logEvents() = MarisaMod.logger.info("MarisaModEventPatch : current event list : ${currentEvents()}")

        @JvmStatic
        @SpirePostfixPatch
        fun EventListPatch(unused: AbstractDungeon?) {
            MarisaMod.logger.info(
                """MarisaModEventPatch : EventListPatch : PlayerCharacter : ${AbstractDungeon.player.title}"""
            )
            logEvents()
            when (AbstractDungeon.player) {
                is Marisa -> Mushrooms.ID
                else -> Mushrooms_MRS.ID
            }
                .also {
                    MarisaMod.logger.info("MarisaModEventPatch : EventListPatch : removing $it")
                    AbstractDungeon.eventList.remove(it)
                }
            logEvents()
        }
    }

    @SpirePatch(cls = "com.megacrit.cardcrawl.dungeons.AbstractDungeon", method = "getEvent")
    object GetEventPatch {
        @SpirePostfixPatch
        @JvmStatic
        fun GetEventPatch(event: AbstractEvent, unused: Random?): AbstractEvent {
            MarisaMod.logger.info(
                """MarisaModEventPatch : GetEventPatch : PlayerCharacter  : ${AbstractDungeon.player.title} ; retVal event : $event"""
            )
            val p = AbstractDungeon.player
            val floor = AbstractDungeon.floorNum
            val abort: Boolean = when (event) {
                is OrinTheCat -> p.hasRelic(CatCart.ID) || (p !is Marisa) && !MarisaMod.isCatEventEnabled
                is Mushrooms_MRS -> floor <= 6
                else -> false
            }
            return when (abort) {
                true -> AbstractDungeon.getEvent(AbstractDungeon.eventRng)
                else -> event
            }
        }


    }
}
