package marisa.cards

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.DrawCardAction
import com.megacrit.cardcrawl.actions.common.GainBlockAction
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.abstracts.MarisaCard
import marisa.powers.Marisa.DarkMatterPower

class DarkMatter : MarisaCard(ID, "DarkMatter", COST, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF) {
    init {
        isEthereal = true
        baseBlock = BLC_GAIN
        block = baseBlock
    }

    override fun triggerOnExhaust() {
        val p = AbstractDungeon.player
        addToBot(
            GainBlockAction(p, p, block)
        )
    }

    override fun canUse(p: AbstractPlayer, m: AbstractMonster?): Boolean {
        val canUse = super.canUse(p, m)
        if (!canUse) {
            return false
        }
        if (p.hasPower(DarkMatterPower.POWER_ID)) {
            cantUseMessage = strings.EXTENDED_DESCRIPTION[0]
            return false
        }
        return true
    }

    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        addToBot(
            MakeTempCardInDrawPileAction(
                makeStatEquivalentCopy(),
                1,
                true,
                true
            )
        )
        addToBot(
            MakeTempCardInDrawPileAction(
                makeStatEquivalentCopy(),
                1,
                true,
                true
            )
        )
        addToBot(
            DrawCardAction(p, 1)
        )
        addToBot(
            ApplyPowerAction(
                p,
                p,
                DarkMatterPower(p)
            )
        )
    }

    override fun makeCopy(): AbstractCard = DarkMatter()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        upgradeBlock(UPG_BLC)
        rawDescription = strings.UPGRADE_DESCRIPTION
        initializeDescription()
    }

    companion object {
        const val ID = "marisa:DarkMatter"
        private const val COST = 0
        private const val BLC_GAIN = 5
        private const val UPG_BLC = 2
    }
}
