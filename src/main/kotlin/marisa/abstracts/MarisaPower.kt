package marisa.abstracts

import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.powers.AbstractPower
import marisa.modPath
import marisa.texture

/** A power named and described by the strings of [id], shown with `img/powers/[icon].png`. */
abstract class MarisaPower(
    id: String,
    owner: AbstractCreature?,
    amount: Int,
    icon: String,
    type: PowerType = PowerType.BUFF,
) : AbstractPower() {
    private val strings = CardCrawlGame.languagePack.getPowerStrings(id)
    protected val descriptions: Array<String> = strings.DESCRIPTIONS

    init {
        ID = id
        name = strings.NAME
        this.owner = owner
        this.amount = amount
        this.type = type
        img = texture("img/powers/$icon.png".modPath())
    }
}
