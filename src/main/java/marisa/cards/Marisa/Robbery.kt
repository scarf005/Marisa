package marisa.cards.Marisa

import marisa.MarisaMod
import marisa.action.RobberyDamageAction
import marisa.patches.AbstractCardEnum
import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster

class Robbery : CustomCard(
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
        baseDamage = ATTACK_DMG
        exhaust = true
        tags.add(CardTags.HEALING)
    }

    override fun use(p: AbstractPlayer, m: AbstractMonster) {
        AbstractDungeon.actionManager.addToBottom(
            RobberyDamageAction(
                m,
                DamageInfo(p, damage, damageTypeForTurn),
                MarisaMod.isAmplified(this, AMP)
            )
        )
    }

    override fun makeCopy(): AbstractCard {
        return Robbery()
    }

    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            upgradeDamage(UPGRADE_PLUS_DMG)
        }
    }

    companion object {
        const val ID = "Robbery"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        const val IMG_PATH = "img/cards/rob.png"
        private const val COST = 1
        private const val ATTACK_DMG = 7
        private const val UPGRADE_PLUS_DMG = 3
        private const val AMP = 1
    }
}
