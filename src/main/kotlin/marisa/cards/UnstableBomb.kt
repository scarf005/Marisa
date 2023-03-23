package marisa.cards

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.action.UnstableBombAction
import marisa.patches.AbstractCardEnum

class UnstableBomb : CustomCard(
    ID,
    NAME,
    IMG_PATH,
    COST,
    DESCRIPTION,
    CardType.ATTACK,
    AbstractCardEnum.MARISA_COLOR,
    CardRarity.COMMON,
    CardTarget.ALL_ENEMY
) {
    private var num = DAMAGE_RANGE

    init {
        baseDamage = DAMAGE
        num = DAMAGE_RANGE
        baseBlock = baseDamage + num
    }

    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        addToBot(
            UnstableBombAction(
                AbstractDungeon.getMonsters().getRandomMonster(true),
                damage,
                block,
                4
            )
        )
    }

    override fun makeCopy(): AbstractCard = UnstableBomb()

    override fun upgrade() {
        if (upgraded) return

        upgradeDamage(UPG_DAMAGE)
        upgradeName()
        num += UPG_DAMAGE_RANGE
        baseBlock = baseDamage + num
        block = baseBlock
        isBlockModified = true
    }

    companion object {
        const val ID = "UnstableBomb"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        const val IMG_PATH = "img/cards/UnstableBomb.png"
        private const val COST = 1
        private const val DAMAGE = 1
        private const val DAMAGE_RANGE = 3
        private const val UPG_DAMAGE = 1
        private const val UPG_DAMAGE_RANGE = 0
    }
}
