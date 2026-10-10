package marisa

import basemod.ReflectionHacks
import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.potions.AbstractPotion
import com.megacrit.cardcrawl.relics.AbstractRelic
import marisa.testing.Combat
import marisa.testing.assertSnapshot
import marisa.testing.concreteClasses
import marisa.testing.newPower
import marisa.testing.powerClasses
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CatalogTest {
    private val combat = Combat()
    private val cardClasses = concreteClasses<AbstractCard>("marisa.cards")

    private fun AbstractCard.describe() = listOf(
        "name=$name", "cost=$cost", "type=$type", "rarity=$rarity", "target=$target", "color=$color",
        "damage=$baseDamage", "block=$baseBlock", "magic=$baseMagicNumber", "multiDamage=${ReflectionHacks.getPrivate<Boolean>(this, AbstractCard::class.java, "isMultiDamage")}",
        "exhaust=$exhaust", "ethereal=$isEthereal", "innate=$isInnate", "retain=$selfRetain",
        "tags=${tags.map { it.name }.sorted()}", "preview=${cardsToPreview?.cardID}",
        "img=${(this as CustomCard).textureImg}", "description=$rawDescription",
    ).joinToString(" | ")

    @Test
    fun `cards keep their stats and text before and after upgrade`() {
        val snapshot = cardClasses.joinToString("") { cls ->
            val card = cls.getDeclaredConstructor().newInstance()
            val base = card.describe()
            card.upgrade()
            "${card.cardID}\n  base: $base\n  upgraded: ${card.describe()}\n"
        }
        assertSnapshot("cards", snapshot)
    }

    @Test
    fun `upgrading twice is the same as upgrading once`() {
        val changed = cardClasses.filter { cls ->
            val once = cls.getDeclaredConstructor().newInstance().apply { upgrade() }
            val twice = cls.getDeclaredConstructor().newInstance().apply { upgrade(); upgrade() }
            once.describe() != twice.describe()
        }
        assertEquals(emptyList(), changed.map { it.simpleName })
    }

    @Test
    fun `copies keep the class, id and upgrade`() {
        cardClasses.forEach { cls ->
            val card = cls.getDeclaredConstructor().newInstance()
            assertEquals(cls, card.makeCopy().javaClass, cls.simpleName)
            card.upgrade()
            val copy = card.makeStatEquivalentCopy()
            assertEquals(card.cardID, copy.cardID, cls.simpleName)
            assertEquals(card.describe(), copy.describe(), cls.simpleName)
        }
    }

    @Test
    fun `every card is registered exactly once`() {
        val registered = MarisaContinued.cards().map { it.javaClass }
        assertEquals(registered.distinct(), registered)
        assertEquals(cardClasses.toSet(), registered.toSet())
    }

    @Test
    fun `card ids are unique and namespaced`() {
        val ids = MarisaContinued.cards().map { it.cardID }
        assertEquals(ids.distinct(), ids)
        assertTrue(ids.all { it.startsWith("$modName:") }, ids.filterNot { it.startsWith("$modName:") }.toString())
    }

    @Test
    fun `relics keep their tier and text`() {
        val relics = concreteClasses<AbstractRelic>("marisa.relics")
        val snapshot = relics.joinToString("") { cls ->
            val relic = cls.getDeclaredConstructor().newInstance()
            "${relic.relicId}\n  name=${relic.name} | tier=${relic.tier} | counter=${relic.counter}" +
                " | copy=${relic.makeCopy().javaClass.simpleName} | description=${relic.description}\n"
        }
        assertSnapshot("relics", snapshot)
    }

    @Test
    fun `every relic except the shared Cat Cart and the unfinished Cape is in Marisa's pool`() {
        val registered = MarisaContinued.relics().map { it.javaClass.simpleName }
        val relics = concreteClasses<AbstractRelic>("marisa.relics").map { it.simpleName }
        assertEquals(registered.distinct(), registered)
        assertEquals(relics.toSet() - "CatCart" - "Cape", registered.toSet())
    }

    @Test
    fun `potions keep their potency and text`() {
        val snapshot = concreteClasses<AbstractPotion>("marisa.potions").joinToString("") { cls ->
            val potion = cls.getDeclaredConstructor().newInstance()
            "${potion.ID}\n  name=${potion.name} | rarity=${potion.rarity} | potency=${potion.potency}" +
                " | description=${potion.description}\n"
        }
        assertSnapshot("potions", snapshot)
    }

    @Test
    fun `powers keep their type and text`() {
        val snapshot = powerClasses.joinToString("") { cls ->
            val power = newPower(cls, combat)
            "${power.ID}\n  name=${power.name} | type=${power.type} | amount=${power.amount}" +
                " | description=${power.description}\n"
        }
        assertSnapshot("powers", snapshot)
    }
}
