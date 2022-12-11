package marisa.patches

import com.evacipated.cardcrawl.modthespire.lib.SpireEnum
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor

object AbstractCardEnum {
    @SpireEnum
    lateinit var MARISA_COLOR: CardColor

    @SpireEnum
    lateinit var MARISA_DERIVATIONS: CardColor
}
