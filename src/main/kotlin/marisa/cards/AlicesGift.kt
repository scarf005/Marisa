package marisa.cards

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.MarisaContinued
import marisa.patches.AbstractCardEnum

class AlicesGift : CustomCard(
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
        baseDamage = ATK
        damage = baseDamage
        exhaust = true
    }

    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            upgradeDamage(ATK_UPG)
        }
    }

    override fun applyPowers() {
        super.applyPowers()
        retain = true
    }

    override fun use(p: AbstractPlayer, m: AbstractMonster) {
        if (isAmplified(AMP)) {
            damage *= 3
        }
        AbstractDungeon.actionManager.addToBottom(
            DamageAction(
                m,
                DamageInfo(p, damage, damageTypeForTurn),
                AttackEffect.FIRE
            )
        )
    }

    companion object {
        const val ID = "AlicesGift"
        const val IMG_PATH = "img/cards/GiftDoll_v2.png"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        private const val COST = 0
        private const val ATK = 5
        private const val ATK_UPG = 2
        private const val AMP = 2
    }
}
