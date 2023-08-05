package marisa.cards

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.actions.common.DrawCardAction
import com.megacrit.cardcrawl.actions.common.GainEnergyAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.patches.AbstractCardEnum
import marisa.powers.Marisa.ChargeUpPower
import marisa.powers.Marisa.OneTimeOffPlusPower
import marisa.relics.SimpleLauncher

class ChargeUpSpray : CustomCard(
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
    //private static final int UPG_DRAW = 0;
    init {
        baseDamage = ATTACK_DMG
        baseMagicNumber = DRAW
        magicNumber = baseMagicNumber
    }

    override fun use(p: AbstractPlayer, m: AbstractMonster?) {
        val cnt = if (p.hasRelic(SimpleLauncher.ID)) 6 else 8

        if (p.hasPower(ChargeUpPower.POWER_ID) && !p.hasPower(OneTimeOffPlusPower.POWER_ID)) {
            if (p.getPower(ChargeUpPower.POWER_ID).amount >= cnt) {
                AbstractDungeon.actionManager.addToTop(
                    DrawCardAction(AbstractDungeon.player, magicNumber)
                )
                AbstractDungeon.actionManager.addToTop(
                    GainEnergyAction(1)
                )
            }
        }
        addToBot(
            DamageAction(
                m,
                DamageInfo(p, damage, damageTypeForTurn),
                AttackEffect.SLASH_DIAGONAL
            )
        )
    }

    override fun makeCopy(): AbstractCard = ChargeUpSpray()

    override fun upgrade() {
        if (upgraded) return
        upgradeName()
        upgradeDamage(UPGRADE_PLUS_DMG)
    }

    companion object {
        const val ID = "ChargeUpSpray"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        const val IMG_PATH = "marisa/img/cards/ChargeUpSpray.png"
        private const val COST = 1
        private const val ATTACK_DMG = 8
        private const val UPGRADE_PLUS_DMG = 4
        private const val DRAW = 2
    }
}
