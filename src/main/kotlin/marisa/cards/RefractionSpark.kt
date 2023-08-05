package marisa.cards

import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.abstracts.AmplifiedAttack
import marisa.action.RefractionSparkAction
import marisa.cards.derivations.Spark
import marisa.patches.AbstractCardEnum
import marisa.patches.CardTagEnum

class RefractionSpark : AmplifiedAttack(
    ID,
    NAME,
    IMG_PATH,
    COST,
    DESCRIPTION,
    CardType.ATTACK,
    AbstractCardEnum.MARISA_COLOR,
    CardRarity.UNCOMMON,
    CardTarget.ENEMY
) {
    init {
        baseDamage = ATK_DMG
        ampNumber = AMP_DMG
        baseBlock = baseDamage + ampNumber
        tags.add(CardTagEnum.SPARK)
        cardsToPreview = Spark()
    }

    override fun use(p: AbstractPlayer, m: AbstractMonster?) {
        val damageNumber = if (tryAmplify()) block else damage
        val action = RefractionSparkAction(m, DamageInfo(p, damageNumber, damageTypeForTurn))

        addToBot(action)
    }

    override fun makeCopy(): AbstractCard = RefractionSpark()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        upgradeDamage(UPG_DMG)
        ampNumber += UPG_AMP
        isBlockModified = true
        block = baseDamage + ampNumber
    }

    companion object {
        const val ID = "RefractionSpark"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        const val IMG_PATH = "marisa/img/cards/Refraction.png"
        private const val COST = 1
        private const val ATK_DMG = 4
        private const val UPG_DMG = 1
        private const val AMP_DMG = 3
        private const val UPG_AMP = 1
    }
}
