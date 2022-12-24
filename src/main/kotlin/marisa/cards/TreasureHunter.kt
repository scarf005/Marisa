package marisa.cards

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.action.TreasureHunterDamageAction
import marisa.patches.AbstractCardEnum

class TreasureHunter : CustomCard(
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
        baseDamage = ATTACK_DMG
        exhaust = true
        tags.add(CardTags.HEALING)
    }

    override fun use(p: AbstractPlayer, m: AbstractMonster?) {
        addToBot(
            TreasureHunterDamageAction(
                m,
                DamageInfo(p, damage, damageTypeForTurn)
            )
        )
    }

    override fun makeCopy(): AbstractCard = TreasureHunter()

    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            upgradeDamage(UPGRADE_PLUS_DMG)
        }
    }

    companion object {
        const val ID = "TreasureHunter"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        const val IMG_PATH = "img/cards/TreasureHunter.png"
        private const val COST = 2
        private const val ATTACK_DMG = 12
        private const val UPGRADE_PLUS_DMG = 5
    }
}
