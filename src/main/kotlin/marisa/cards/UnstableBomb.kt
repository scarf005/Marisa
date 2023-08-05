package marisa.cards

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.action.RandomDamageAction
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
    private var damageMaxAdded = DAMAGE_MAX_ADDED
    private val maxDamage get() = baseDamage + damageMaxAdded
    private val damageRange get() = (baseDamage..maxDamage)

    private fun setMaxDamageDisplay() {
        if (baseBlock > maxDamage)
            isBlockModified = true
        baseBlock = maxDamage
    }

    init {
        damageMaxAdded = DAMAGE_MAX_ADDED
        baseDamage = DAMAGE_BASE
        setMaxDamageDisplay()
    }

    override fun applyPowers() {
        super.applyPowers()
        setMaxDamageDisplay()
    }

    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        addToBot(RandomDamageAction(4) { damageRange.random() })
    }

    override fun makeCopy(): AbstractCard = UnstableBomb()

    override fun upgrade() {
        if (upgraded) return

        upgradeDamage(UPG_DAMAGE)
        setMaxDamageDisplay()
        upgradeName()
    }

    companion object {
        const val ID = "UnstableBomb"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        const val IMG_PATH = "marisa/img/cards/UnstableBomb.png"
        private const val COST = 1
        private const val DAMAGE_BASE = 1
        private const val DAMAGE_MAX_ADDED = 2
        private const val UPG_DAMAGE = 1
    }
}
