package marisa.cards

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.action._6AAction
import marisa.patches.AbstractCardEnum

class _6A : CustomCard(
    ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.ATTACK,
    AbstractCardEnum.MARISA_COLOR, CardRarity.COMMON,
    CardTarget.ENEMY
) {
    //private static final int UPG_COST = 0;
    init {
        baseDamage = ATTACK_DMG
    }

    override fun use(p: AbstractPlayer, m: AbstractMonster?) {
        addToBot(
            _6AAction(
                m,
                DamageInfo(p, damage, damageTypeForTurn)
            )
        )
    }

    override fun makeCopy(): AbstractCard = _6A()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        upgradeDamage(UPGRADE_PLUS_DMG)
    }

    companion object {
        const val ID = "marisa:6A"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        const val IMG_PATH = "marisa/img/cards/6A.png"
        private const val COST = 1
        private const val ATTACK_DMG = 5
        private const val UPGRADE_PLUS_DMG = 2
    }
}
