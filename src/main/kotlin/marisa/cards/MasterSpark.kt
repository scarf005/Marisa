package marisa.cards

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.actions.animations.VFXAction
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.vfx.combat.MindblastEffect
import marisa.abstracts.AmplifiedAttack
import marisa.patches.AbstractCardEnum
import marisa.patches.CardTagEnum

class MasterSpark : AmplifiedAttack(
    ID,
    NAME,
    IMG_PATH,
    COST,
    DESCRIPTION,
    CardType.ATTACK,
    AbstractCardEnum.MARISA_COLOR,
    CardRarity.BASIC,
    CardTarget.ENEMY
) {
    init {
        baseDamage = ATK_DMG
        ampNumber = AMP_DMG
        baseBlock = baseDamage + ampNumber
        tags.add(CardTagEnum.SPARK)
    }

    override fun use(p: AbstractPlayer, m: AbstractMonster?) {
        val damageNumber = if (tryAmplify()) block else damage
        val blast = VFXAction(MindblastEffect(p.dialogX, p.dialogY, false))
        val action = DamageAction(
            m, DamageInfo(p, damageNumber, damageTypeForTurn), AttackEffect.SLASH_DIAGONAL
        )

        marisa.addToBot(blast, action)
    }

    override fun makeCopy(): AbstractCard = MasterSpark()

    override fun upgrade() {
        if (upgraded) return

        upgradeName()
        upgradeDamage(UPG_DMG)
        ampNumber += UPG_AMP
        block = baseDamage + ampNumber
        isBlockModified = true
    }

    companion object {
        const val ID = "marisa:MasterSpark"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        const val IMG_PATH = "marisa/img/cards/MasterSpark.png"
        private const val COST = 1
        private const val ATK_DMG = 8
        private const val UPG_DMG = 3
        private const val AMP_DMG = 7
        private const val UPG_AMP = 2
    }
}
