package marisa.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.CardGroup
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import marisa.cards.derivations.BlackFlareStar
import marisa.cards.derivations.WhiteDwarf

class BinaryStarsAction(upgraded: Boolean) : AbstractGameAction() {
    private val p: AbstractPlayer = AbstractDungeon.player
    private val upg: Boolean

    init {
        setValues(p, AbstractDungeon.player, 1)
        actionType = ActionType.CARD_MANIPULATION
        duration = Settings.ACTION_DUR_MED
        upg = upgraded
    }

    override fun update() {
        val tmp: CardGroup
        if (duration == Settings.ACTION_DUR_MED) {
            tmp = CardGroup(CardGroup.CardGroupType.UNSPECIFIED)
            var c: AbstractCard = BlackFlareStar()
            if (upg) c.upgrade()
            tmp.addToTop(c)
            c = WhiteDwarf()
            if (upg) c.upgrade()
            tmp.addToTop(c)
            AbstractDungeon.gridSelectScreen.open(tmp, 1, "Choose", false)
            tickDuration()
            return
        }
        if (AbstractDungeon.gridSelectScreen.selectedCards.size != 0) {
            for (c in AbstractDungeon.gridSelectScreen.selectedCards) {
                c.unhover()
                if (p.hand.size() == 10) {
                    p.createHandIsFullDialog()
                    p.discardPile.addToTop(c)
                } else {
                    p.hand.addToTop(c)
                }
                p.hand.refreshHandLayout()
                p.hand.applyPowers()
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear()
            p.hand.refreshHandLayout()
        }
        tickDuration()
    }
}
