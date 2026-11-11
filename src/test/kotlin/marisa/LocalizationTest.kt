package marisa

import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.core.Settings.GameLanguage
import com.megacrit.cardcrawl.localization.LocalizedStrings
import com.megacrit.cardcrawl.relics.AbstractRelic
import marisa.cards.Acceleration
import marisa.cards.BlazeAway
import marisa.cards.OneTimeOff
import marisa.cards.UnstableBomb
import marisa.relics.RampagingMagicTools
import marisa.relics.SproutingBranch
import marisa.testing.Combat
import marisa.testing.Game
import marisa.testing.concreteClasses
import marisa.testing.newPower
import marisa.testing.powerClasses
import kotlin.test.Test
import kotlin.test.assertEquals

/** Builds every card, upgraded card, power and relic in each language the mod is translated to. */
class LocalizationTest {
    private val languages = listOf(
        GameLanguage.ENG, GameLanguage.FRA, GameLanguage.JPN, GameLanguage.KOR,
        GameLanguage.ZHS, GameLanguage.ZHT, GameLanguage.SPA,
    )

    /** What fails to build or describe itself in [language], as `class: problem`. */
    private fun problems(language: GameLanguage): List<String> {
        Settings.language = language
        CardCrawlGame.languagePack = LocalizedStrings()
        val combat = Combat()
        fun check(name: String, text: () -> String?) = runCatching { text() }.fold(
            { if (it.isNullOrBlank() || "null" in it) listOf("$name: $it") else emptyList() },
            { listOf("$name: ${it.javaClass.simpleName} ${it.message}") },
        )
        val cards = concreteClasses<AbstractCard>("marisa.cards").flatMap { cls ->
            val card = cls.getDeclaredConstructor().newInstance()
            check(cls.simpleName) { card.rawDescription } +
                check("${cls.simpleName}+") { card.upgrade(); card.rawDescription }
        }
        val powers = powerClasses.flatMap { cls -> check(cls.simpleName) { newPower(cls, combat).description } }
        val relics = concreteClasses<AbstractRelic>("marisa.relics").flatMap { cls ->
            check(cls.simpleName) { cls.getDeclaredConstructor().newInstance().description }
        }
        return (cards + powers + relics).map { "$language $it" }
    }

    @Test
    fun `every language describes every card, upgrade, power and relic`() {
        Game.init()
        try {
            assertEquals(emptyList(), languages.flatMap(::problems))
        } finally {
            Settings.language = GameLanguage.ENG
            CardCrawlGame.languagePack = LocalizedStrings()
        }
    }

    /** Words of the classic texts in each language: Exhaust, the draw pile and Charge-up. */
    private val classicWords = mapOf(
        GameLanguage.ENG to listOf("Exhaust", "draw pile", "Charge-up"),
        GameLanguage.FRA to listOf("Épuisement", "pioche", "charge"),
        GameLanguage.JPN to listOf("廃棄", "山札", "チャージ"),
        GameLanguage.KOR to listOf("소멸", "뽑을 카드 더미", "충전"),
        GameLanguage.ZHS to listOf("消耗", "抽牌堆", "蓄力"),
        GameLanguage.ZHT to listOf("消耗", "抽卡堆", "蓄力"),
        GameLanguage.SPA to listOf("Agota", "pila de robo", "Carga"),
        // Without its own files, Russian falls back to English.
        GameLanguage.RUS to listOf("Exhaust", "draw pile", "Charge-up"),
    )

    /** What the classic texts in the current language say against the classic balance. */
    private fun classicProblems(exhaust: String, drawPile: String, charge: String): List<String> {
        fun numbers(text: String) = Regex("#b(\\d+)").findAll(text).map { it.groupValues[1].toInt() }.sorted().toList()
        val sproutingBranch = numbers(SproutingBranch().description)
        val rampagingMagicTools = numbers(RampagingMagicTools().description)
        return listOfNotNull(
            "Blaze Away says $exhaust".takeIf { exhaust in BlazeAway().rawDescription },
            "Acceleration lacks $drawPile".takeIf { drawPile !in Acceleration().rawDescription },
            "One Time Off lacks $charge".takeIf { charge !in OneTimeOff().rawDescription },
            "Unstable Bomb deals up to ${UnstableBomb().baseBlock}".takeIf { UnstableBomb().baseBlock != 4 },
            "Sprouting Branch shows $sproutingBranch".takeIf { sproutingBranch != listOf(5) },
            "Rampaging Magic Tool shows $rampagingMagicTools".takeIf { rampagingMagicTools != listOf(2, 2, 2, 3, 8) },
        )
    }

    @Test
    fun `every language describes the classic balance`() {
        Game.init()
        MarisaContinued.isClassic = true
        try {
            val found = classicWords.flatMap { (language, words) ->
                val (exhaust, drawPile, charge) = words
                problems(language) + classicProblems(exhaust, drawPile, charge).map { "$language $it" }
            }
            assertEquals(emptyList(), found)
        } finally {
            MarisaContinued.isClassic = false
            Settings.language = GameLanguage.ENG
            CardCrawlGame.languagePack = LocalizedStrings()
        }
    }
}
