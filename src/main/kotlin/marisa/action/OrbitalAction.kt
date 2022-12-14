package marisa.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.AbstractCard.CardType
import com.megacrit.cardcrawl.cards.CardGroup
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.powers.CorruptionPower

class OrbitalAction : AbstractGameAction() {
    private val p: AbstractPlayer = AbstractDungeon.player
    private val orbitals = ArrayList<AbstractCard>()

    init {
        setValues(p, AbstractDungeon.player, amount)
        actionType = ActionType.CARD_MANIPULATION
        duration = Settings.ACTION_DUR_FAST
    }

    override fun update() {
        val c: MutableIterator<AbstractCard>
        if (duration == Settings.ACTION_DUR_FAST) {
            if (AbstractDungeon.player.hand.size() == 10) {
                AbstractDungeon.player.createHandIsFullDialog()
                isDone = true
                return
            }
            if (p.exhaustPile.isEmpty) {
                isDone = true
                return
            }
            val car: AbstractCard
            if (p.exhaustPile.size() == 1) {
                if (p.exhaustPile.group[0].cardID == "Orbital") {
                    isDone = true
                    return
                }
                car = p.exhaustPile.topCard
                car.unfadeOut()
                p.hand.addToHand(car)
                if (AbstractDungeon.player.hasPower(CorruptionPower.POWER_ID) && (car.type
                            == CardType.SKILL)
                ) {
                    car.setCostForTurn(-9)
                }
                p.exhaustPile.removeCard(car)
                car.unhover()
                car.fadingOut = false
                isDone = true
                return
            }
            for (ca in p.exhaustPile.group) {
                ca.stopGlowing()
                ca.unhover()
                ca.unfadeOut()
            }
            c = p.exhaustPile.group.iterator()
            while (c.hasNext()) {
                val derp = c.next()
                if (derp.cardID == "Orbital") {
                    c.remove()
                    orbitals.add(derp)
                }
            }
            if (p.exhaustPile.isEmpty) {
                p.exhaustPile.group.addAll(orbitals)
                orbitals.clear()
                isDone = true
                return
            }
            AbstractDungeon.gridSelectScreen.open(p.exhaustPile, 1, TEXT[0], false)
            tickDuration()
            return
        }
        if (AbstractDungeon.gridSelectScreen.selectedCards.isNotEmpty()) {
            for (ca in AbstractDungeon.gridSelectScreen.selectedCards) {
                p.hand.addToHand(ca)
                if (AbstractDungeon.player.hasPower(CorruptionPower.POWER_ID) && (ca.type
                            == CardType.SKILL)
                ) {
                    ca.setCostForTurn(-9)
                }
                p.exhaustPile.removeCard(ca)
                ca.unhover()
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear()
            p.hand.refreshHandLayout()
            p.exhaustPile.group.addAll(orbitals)
            orbitals.clear()
            for (car in p.exhaustPile.group) {
                car.unhover()
                car.target_x = CardGroup.DISCARD_PILE_X.toFloat()
                car.target_y = 0.0f
            }
        }
        tickDuration()
    }

    companion object {
        private val uiStrings = CardCrawlGame.languagePack.getUIString("ExhumeAction")
        private val TEXT = uiStrings.TEXT
    }
}
