package marisa.cards

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.cards.derivations.Spark
import marisa.patches.AbstractCardEnum

class JA : CustomCard(
    ID,
    NAME,
    IMG_PATH,
    COST,
    DESCRIPTION,
    CardType.ATTACK,
    AbstractCardEnum.MARISA_COLOR,
    CardRarity.RARE,
    CardTarget.ENEMY
) {
    init {
        baseDamage = ATK_DMG
        multiplePreviews(cards)
    }

    override fun use(p: AbstractPlayer, m: AbstractMonster?) {
        addToBot(
            DamageAction(
                m,
                DamageInfo(p, damage, damageTypeForTurn),
                AttackEffect.SLASH_DIAGONAL
            )
        )
        cards.forEach { addToBot(MakeTempCardInHandAction(it, 1)) }
    }

    private val cards
        get() = listOf(UpSweep(), Spark(), WitchLeyline()).map { followUpgrade(it) }

    override fun makeCopy(): AbstractCard = JA()

    override fun upgrade() {
        if (upgraded) return

        upgradeName()
        rawDescription = DESCRIPTION_UPG
        initializeDescription()
        multiplePreviews(cards)
    }

    companion object {
        const val ID = "marisa:JA"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        val DESCRIPTION_UPG = cardStrings.UPGRADE_DESCRIPTION
        const val IMG_PATH = "marisa/img/cards/JA.png"
        private const val COST = 2
        private const val ATK_DMG = 1
    }
}
