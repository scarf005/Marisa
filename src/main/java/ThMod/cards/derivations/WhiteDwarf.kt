package ThMod.cards.derivations

import ThMod.abstracts.AmplifiedAttack
import ThMod.patches.AbstractCardEnum
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.cards.status.Burn
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster

class WhiteDwarf : AmplifiedAttack(
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
    private var magn = MULTIPLIER

    init {
        damage = ATTACK_DMG
        baseDamage = damage
        exhaust = true
    }

    override fun applyPowers() {
        val player = AbstractDungeon.player
        ampNumber = Math.floor((player.discardPile.size() * magn).toDouble()).toInt()
        super.applyPowers()
    }

    override fun calculateDamageDisplay(mo: AbstractMonster) {
        calculateCardDamage(mo)
    }

    override fun calculateCardDamage(mo: AbstractMonster) {
        val player = AbstractDungeon.player
        ampNumber = Math.floor((player.discardPile.size() * magn).toDouble()).toInt()
        super.calculateCardDamage(mo)
    }

    override fun canUse(p: AbstractPlayer, m: AbstractMonster): Boolean {
        return if (p.hand.size() <= HAND_REQ) {
            true
        } else {
            cantUseMessage = EXTENDED_DESCRIPTION[0]
            false
        }
    }

    override fun use(p: AbstractPlayer, m: AbstractMonster) {
        AbstractDungeon.actionManager.addToBottom(
            DamageAction(
                m,
                DamageInfo(p, block, damageTypeForTurn),
                AttackEffect.SLASH_DIAGONAL
            )
        )
        AbstractDungeon.actionManager.addToBottom(
            MakeTempCardInHandAction(Burn(), 2)
        )
    }

    override fun makeCopy(): AbstractCard {
        return WhiteDwarf()
    }

    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            magn = MULTIPLIER_UPG
            rawDescription = DESCRIPTION_UPG
            initializeDescription()
        }
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
        private const val ATTACK_DMG = 0
        private const val HAND_REQ = 4
        private const val MULTIPLIER = 2f
        private const val MULTIPLIER_UPG = 3f
    }
}