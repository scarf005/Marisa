package marisa.cards

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction
import com.megacrit.cardcrawl.actions.common.GainEnergyAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.MarisaContinued
import marisa.patches.AbstractCardEnum

class SuperPerseids : CustomCard(
    ID,
    NAME,
    IMG_PATH,
    COST,
    DESCRIPTION,
    CardType.SKILL,
    AbstractCardEnum.MARISA_COLOR,
    CardRarity.UNCOMMON,
    CardTarget.SELF
) {
    init {
        baseDamage = DMG
        damage = baseDamage
        baseMagicNumber = STACK
        magicNumber = baseMagicNumber
        damageType = DamageType.THORNS
        damageTypeForTurn = DamageType.THORNS
    }

    override fun triggerWhenDrawn() {
        applyPowers()
        /*
    ThMod.logger.info("SuperPerseids : triggerWhenDrawn : Granting Charge-up "
        + "; : upgraded : " + this.upgraded
    );
    AbstractPlayer p = AbstractDungeon.player;
    addToBot(
        new ApplyPowerAction(
            p,
            p,
            new ChargeUpPower(p,this.magicNumber),
            this.magicNumber
        )
    );
    */addToBot(
            GainEnergyAction(1)
        )
    }

    override fun canUse(p: AbstractPlayer, unused: AbstractMonster?): Boolean = false

    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {}
    override fun triggerOnExhaust() {
        applyPowers()
        MarisaContinued.logger.info(
            "SuperPerseids : triggerOnExhaust : Damaging Random Enemy :"
                    + "; upgraded : " + upgraded
                    + "; damage : " + damage
        )
        addToBot(
            DamageRandomEnemyAction(
                DamageInfo(
                    AbstractDungeon.player,
                    damage,
                    DamageType.THORNS
                ),
                AttackEffect.FIRE
            )
        )
    }

    override fun makeCopy(): AbstractCard = SuperPerseids()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        upgradeDamage(UPG_DMG)
        upgradeMagicNumber(UPG_STACK)
    }

    companion object {
        const val ID = "marisa:SuperPerseids"
        const val IMG_PATH = "marisa/img/cards/SuperPerseids.png"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        private const val COST = -2
        private const val DMG = 16
        private const val UPG_DMG = 8
        private const val STACK = 2
        private const val UPG_STACK = 1
    }
}
