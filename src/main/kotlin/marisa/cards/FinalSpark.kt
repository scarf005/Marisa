package marisa.cards

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.actions.animations.VFXAction
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction
import com.megacrit.cardcrawl.actions.common.GainEnergyAction
import com.megacrit.cardcrawl.actions.utility.SFXAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.vfx.combat.MindblastEffect
import marisa.cards.derivations.Spark
import marisa.patches.AbstractCardEnum
import marisa.patches.CardTagEnum

class FinalSpark : CustomCard(
    ID,
    NAME,
    IMG_PATH,
    COST,
    DESCRIPTION,
    CardType.ATTACK,
    AbstractCardEnum.MARISA_COLOR,
    CardRarity.RARE,
    CardTarget.ALL_ENEMY
) {
    init {
        isMultiDamage = true
        baseDamage = ATK_DMG
        tags.add(CardTagEnum.SPARK)
        cardsToPreview = Spark()
    }

    override fun applyPowers() {
        super.applyPowers()
        if (upgraded) {
            retain = true
        }
    }

    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        addToBot(
            SFXAction("ATTACK_HEAVY")
        )
        addToBot(
            VFXAction(
                MindblastEffect(p.dialogX, p.dialogY, false)
            )
        )
        addToBot(
            DamageAllEnemiesAction(
                p,
                multiDamage,
                damageTypeForTurn,
                AttackEffect.SLASH_DIAGONAL
            )
        )
        if (!freeToPlayOnce) {
            addToBot(
                GainEnergyAction(-costForTurn)
            )
            freeToPlayOnce = true
        }
        upgradeBaseCost(COST)
        setCostForTurn(COST)
        isCostModified = false
    }

    override fun makeCopy(): AbstractCard = FinalSpark()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        rawDescription = DESCRIPTION_UPG
        initializeDescription()
    }

    companion object {
        const val ID = "marisa:FinalSpark"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        val DESCRIPTION_UPG = cardStrings.UPGRADE_DESCRIPTION
        const val IMG_PATH = "marisa/img/cards/FinalSpark.png"
        private const val COST = 7
        private const val ATK_DMG = 40
    }
}
