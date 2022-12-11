package ThMod.cards.Marisa

import ThMod.patches.AbstractCardEnum
import ThMod.patches.CardTagEnum
import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster

class MachineGunSpark : CustomCard(
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
        baseMagicNumber = CNT
        magicNumber = baseMagicNumber
        exhaust = true
        tags.add(CardTagEnum.SPARK)
    }

    override fun use(p: AbstractPlayer, m: AbstractMonster) {
        for (i in 0 until magicNumber) {
            AbstractDungeon.actionManager.addToBottom(
                DamageAction(
                    m,
                    DamageInfo(p, damage, damageTypeForTurn),
                    AttackEffect.SLASH_DIAGONAL,
                    true
                )
            )
        }
    }

    override fun makeCopy(): AbstractCard {
        return MachineGunSpark()
    }

    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            upgradeMagicNumber(UPG_CNT)
        }
    }

    companion object {
        const val ID = "MachineGunSpark"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        const val IMG_PATH = "img/cards/MachineGunSpark.png"
        private const val COST = 1
        private const val ATTACK_DMG = 1
        private const val CNT = 6
        private const val UPG_CNT = 2
    }
}