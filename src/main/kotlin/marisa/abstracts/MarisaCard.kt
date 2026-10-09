package marisa.abstracts

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.localization.CardStrings
import marisa.modPath
import marisa.patches.AbstractCardEnum

private fun cardStrings(id: String): CardStrings = CardCrawlGame.languagePack.getCardStrings(id)

/** A card named and described by the strings of [id], drawn with `img/cards/[image].png`. */
abstract class MarisaCard(
    id: String,
    image: String,
    cost: Int,
    type: CardType,
    rarity: CardRarity,
    target: CardTarget,
    color: CardColor = AbstractCardEnum.MARISA_COLOR,
) : CustomCard(
    id, cardStrings(id).NAME, "img/cards/$image.png".modPath(), cost, cardStrings(id).DESCRIPTION,
    type, color, rarity, target,
) {
    protected val strings = cardStrings(id)
}
