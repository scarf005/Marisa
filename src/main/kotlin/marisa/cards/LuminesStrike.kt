package marisa.cards

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.ui.panels.EnergyPanel
import marisa.abstracts.AmplifiedAttack
import marisa.p
import marisa.patches.AbstractCardEnum

class LuminesStrike : AmplifiedAttack(
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
        baseMagicNumber = D0
        baseBlock = A0
        baseDamage = 0
        isException = true
        tags.add(CardTags.STRIKE)
    }

    override fun applyPowers() {
        damage = p.hand.size() * baseMagicNumber + baseDamage
        block = EnergyPanel.totalCount * baseBlock + baseDamage
        super.applyPowers()
    }

    override fun calculateDamageDisplay(mo: AbstractMonster?) {
        calculateCardDamage(mo)
    }

    override fun calculateCardDamage(mo: AbstractMonster?) {
        val player = AbstractDungeon.player
        damage = player.hand.size() * baseMagicNumber + baseDamage
        block = EnergyPanel.totalCount * baseBlock + baseDamage
        super.calculateCardDamage(mo)
    }

    override fun use(p: AbstractPlayer, m: AbstractMonster?) {
        val damageNumber = if (tryAmplify()) block else damage
        val action = DamageAction(
            m, DamageInfo(p, damageNumber, damageTypeForTurn), AttackEffect.SLASH_DIAGONAL
        )
        addToBot(action)
    }

    override fun makeCopy(): AbstractCard = LuminesStrike()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        baseBlock = A1
        baseMagicNumber = D1
        rawDescription = DESCRIPTION_UPG
        initializeDescription()
    }

    companion object {
        const val ID = "LuminesStrike"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        val DESCRIPTION_UPG = cardStrings.UPGRADE_DESCRIPTION
        const val IMG_PATH = "marisa/img/cards/LumiStrike.png"
        private const val COST = 0
        private const val D0 = 2
        private const val D1 = 3
        private const val A0 = 4
        private const val A1 = 5
    }
}
