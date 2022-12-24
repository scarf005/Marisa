package marisa

import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.potions.AbstractPotion
import com.megacrit.cardcrawl.powers.AbstractPower
import com.megacrit.cardcrawl.relics.AbstractRelic
import com.megacrit.cardcrawl.random.Random as CardCrawlRandom

/**
 * -1 to size because [random] uses nextInt(range + 1)
 */
fun <T> List<T>.random(random: CardCrawlRandom) = this[random.random(size - 1)]

/** picks a random relic using Slay the Spire's random */
fun List<AbstractRelic>.random() = random(AbstractDungeon.relicRng)

/** picks a random card using Slay the Spire's random */
fun List<AbstractCard>.random() = random(AbstractDungeon.cardRng)

/** picks a random potion using Slay the Spire's random */
fun List<AbstractPotion>.random() = random(AbstractDungeon.potionRng)

/** picks a random power using Slay the Spire's random */
fun List<AbstractPower>.random() = random(AbstractDungeon.miscRng)
