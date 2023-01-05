package marisa.cards

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.actions.common.DrawCardAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.patches.AbstractCardEnum

class GravityBeat : CustomCard(
    ID,
    NAME,
    IMG_PATH,
    COST,
    DESCRIPTION,
    CardType.ATTACK,
    AbstractCardEnum.MARISA_COLOR,
    CardRarity.COMMON,
    CardTarget.ENEMY
) {
    init {

        //this.isMultiDamage = true;
        damage = ATTACK_DMG
        baseDamage = damage
        block = DIVIDER
        baseBlock = block
        //this.magicNumber = this.baseMagicNumber = WK;
    }

    override fun applyPowers() {
        super.applyPowers()
        if (AbstractDungeon.player != null) {
            block = baseBlock
            isBlockModified = false
            baseMagicNumber = AbstractDungeon.player.masterDeck.size() / block
            magicNumber = baseMagicNumber
            rawDescription = DESCRIPTION + EX_DESC[0] + magicNumber + EX_DESC[1]
            initializeDescription()
        }
    }

    override fun use(p: AbstractPlayer, m: AbstractMonster?) {
        fun damage() = DamageAction(m, DamageInfo(p, damage, damageTypeForTurn), AttackEffect.BLUNT_LIGHT)

        repeat(magicNumber) {
            m?.let { if (!it.isDeadOrEscaped) addToBot(damage()) }
            addToBot(DrawCardAction(1))
        }
    }

    override fun makeCopy(): AbstractCard = GravityBeat()

    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            upgradeMagicNumber(DIV_UPG)
            upgradeDamage(UPGRADE_PLUS_DMG)
        }
    }

    companion object {
        const val ID = "GravityBeat"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME: String = cardStrings.NAME
        val DESCRIPTION: String = cardStrings.DESCRIPTION
        val EX_DESC: Array<String> = cardStrings.EXTENDED_DESCRIPTION
        const val IMG_PATH = "img/cards/GravityBeat.png"
        private const val COST = 1
        private const val ATTACK_DMG = 6
        private const val UPGRADE_PLUS_DMG = 2
        private const val DIVIDER = 12
        private const val DIV_UPG = 10
    }
}
