package marisa.cards.Marisa

import marisa.MarisaMod
import marisa.patches.AbstractCardEnum
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
    AbstractDungeon.actionManager.addToBottom(
        new ApplyPowerAction(
            p,
            p,
            new ChargeUpPower(p,this.magicNumber),
            this.magicNumber
        )
    );
    */AbstractDungeon.actionManager.addToBottom(
            GainEnergyAction(1)
        )
    }

    override fun canUse(p: AbstractPlayer, m: AbstractMonster): Boolean {
        return false
    }

    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {}
    override fun triggerOnExhaust() {
        applyPowers()
        MarisaMod.logger.info(
            "SuperPerseids : triggerOnExhaust : Damaging Random Enemy :"
                    + "; upgraded : " + upgraded
                    + "; damage : " + damage
        )
        AbstractDungeon.actionManager.addToBottom(
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

    override fun makeCopy(): AbstractCard {
        return SuperPerseids()
    }

    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            upgradeDamage(UPG_DMG)
            upgradeMagicNumber(UPG_STACK)
        }
    }

    companion object {
        const val ID = "SuperPerseids"
        const val IMG_PATH = "img/cards/SuperPerseids.png"
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
