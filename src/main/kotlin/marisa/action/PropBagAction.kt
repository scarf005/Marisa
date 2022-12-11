package marisa.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.relics.*
import marisa.MarisaMod
import marisa.powers.Marisa.PropBagPower
import marisa.relics.AmplifyWand

class PropBagAction : AbstractGameAction() {
    init {
        actionType = ActionType.CARD_MANIPULATION
        duration = Settings.ACTION_DUR_FAST
    }

    override fun update() {
        val p = AbstractDungeon.player
        MarisaMod.logger.info("PropBagAction : Checking for relics")
        val rs = ArrayList<AbstractRelic>()
        var r: AbstractRelic
        if (!p.hasRelic("Meat on the Bone")) {
            r = MeatOnTheBone()
            rs.add(r)
        }
        if (!p.hasRelic("Mummified Hand")) {
            r = MummifiedHand()
            rs.add(r)
        }
        if (!p.hasRelic("Letter Opener")) {
            r = LetterOpener()
            rs.add(r)
        }
        if (!p.hasRelic("Shuriken")) {
            r = Shuriken()
            rs.add(r)
        }
        if (!p.hasRelic("Gremlin Horn")) {
            r = GremlinHorn()
            rs.add(r)
        }
        if (!p.hasRelic("Sundial")) {
            r = Sundial()
            rs.add(r)
        }
        if (!p.hasRelic("Mercury Hourglass")) {
            r = MercuryHourglass()
            rs.add(r)
        }
        if (!p.hasRelic("Ornamental Fan")) {
            r = OrnamentalFan()
            rs.add(r)
        }
        if (!p.hasRelic("Kunai")) {
            r = Kunai()
            rs.add(r)
        }
        if (!p.hasRelic("Blue Candle")) {
            r = BlueCandle()
            rs.add(r)
        }
        if (!p.hasRelic("AmplifyWand")) {
            r = AmplifyWand()
            rs.add(r)
        }
        if (rs.size <= 0) {
            MarisaMod.logger.info("PropBagAction : No relic to give,returning")
            isDone = true
            return
        }
        if (rs.size == 1) {
            r = rs[0]
            MarisaMod.logger.info("PropBagAction : Only one relic to give : " + r.relicId)
            AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(
                    p, p,
                    PropBagPower(p, r)
                )
            )
            isDone = true
            return
        }
        rs.size
        val index = AbstractDungeon.miscRng.random(0, rs.size - 1)
        r = rs[index]
        MarisaMod.logger.info(
            "PropBagAction : random relic : " + r.relicId
                    + " ; random index : " + index
        )
        AbstractDungeon.actionManager.addToBottom(
            ApplyPowerAction(
                p, p,
                PropBagPower(p, r)
            )
        )
        isDone = true
    }
}
