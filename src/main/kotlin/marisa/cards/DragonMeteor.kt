package marisa.cards

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.actions.animations.VFXAction
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.actions.utility.WaitAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect
import marisa.abstracts.AmplifiedAttack
import marisa.patches.AbstractCardEnum

class DragonMeteor : AmplifiedAttack(
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
        damage = baseDamage
        baseBlock = damage
        block = baseBlock
        baseMagicNumber = DMG_GAIN
        magicNumber = baseMagicNumber
        isException = true
    }

    override fun applyPowers() {
        block = baseDamage + AbstractDungeon.player.exhaustPile.size() * magicNumber
        super.applyPowers()
        if (block == baseDamage) {
            isBlockModified = false
        }
    }

    override fun calculateDamageDisplay(mo: AbstractMonster?) {
        calculateCardDamage(mo)
    }

    override fun calculateCardDamage(mo: AbstractMonster?) {
        block = baseDamage + AbstractDungeon.player.exhaustPile.size() * magicNumber
        super.calculateCardDamage(mo)
    }

    override fun use(p: AbstractPlayer, m: AbstractMonster?) {
        m ?: return
        addToBot(
            VFXAction(
                WeightyImpactEffect(m.hb.cX, m.hb.cY)
            )
        )
        addToBot(WaitAction(0.8f))
        addToBot(
            DamageAction(
                m,
                DamageInfo(
                    p,
                    block,
                    damageTypeForTurn
                ),
                AttackEffect.FIRE
            )
        )
    }

    override fun makeCopy(): AbstractCard = DragonMeteor()

    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            //upgradeDamage(UPG_DMG);
            //this.block = this.baseBlock = this.baseDamage;
            //this.isBlockModified = true;
            upgradeMagicNumber(UPG_GAIN)
        }
    }

    companion object {
        const val ID = "DragonMeteor"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        const val IMG_PATH = "img/cards/DragonMeteor.png"
        private const val COST = 2
        private const val ATK_DMG = 14

        //private static final int UPG_DMG = 6;
        private const val DMG_GAIN = 1
        private const val UPG_GAIN = 1
    }
}
