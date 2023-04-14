package marisa.cards.derivations

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.cards.status.Burn
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.p
import marisa.patches.AbstractCardEnum

class WhiteDwarf : CustomCard(
    ID,
    NAME,
    IMG_PATH,
    COST,
    DESCRIPTION,
    CardType.ATTACK,
    AbstractCardEnum.MARISA_DERIVATIONS,
    CardRarity.SPECIAL,
    CardTarget.ENEMY
) {
    private var multiplier = MULTIPLIER

    init {
        baseDamage = 0
        exhaust = true
    }

    private fun damageImpl() = p.discardPile.size() * multiplier

    override fun applyPowers() {
        baseDamage = damageImpl()
        super.applyPowers()
    }

    override fun calculateDamageDisplay(mo: AbstractMonster?) {
        baseDamage = damageImpl()
        calculateCardDamage(mo)
    }

    override fun canUse(p: AbstractPlayer, unused: AbstractMonster?): Boolean {
        return if (p.hand.size() <= HAND_REQ) {
            true
        } else {
            cantUseMessage = EXTENDED_DESCRIPTION[0]
            false
        }
    }

    override fun use(p: AbstractPlayer, m: AbstractMonster?) {
        marisa.addToBot(
            DamageAction(m, DamageInfo(p, damage, damageTypeForTurn), AttackEffect.SLASH_DIAGONAL),
            MakeTempCardInHandAction(Burn(), 2),
        )
    }

    override fun makeCopy(): AbstractCard = WhiteDwarf()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        multiplier = MULTIPLIER_UPG
        rawDescription = DESCRIPTION_UPG
        initializeDescription()
    }

    companion object {
        const val ID = "WhiteDwarf"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        val DESCRIPTION_UPG = cardStrings.UPGRADE_DESCRIPTION
        private val EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION
        const val IMG_PATH = "img/cards/Marisa/WhiteDwarf.png"
        private const val COST = 0
        private const val HAND_REQ = 4
        private const val MULTIPLIER = 2
        private const val MULTIPLIER_UPG = 3
    }
}
