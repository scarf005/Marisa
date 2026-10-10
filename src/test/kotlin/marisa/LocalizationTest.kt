package marisa

import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.core.Settings.GameLanguage
import com.megacrit.cardcrawl.localization.LocalizedStrings
import com.megacrit.cardcrawl.relics.AbstractRelic
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
}
