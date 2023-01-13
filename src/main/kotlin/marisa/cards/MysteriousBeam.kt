package marisa.cards

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.actions.common.DamageAction
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.patches.AbstractCardEnum

class MysteriousBeam : CustomCard(
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
        baseDamage = 0
    }

    override fun calculateCardDamage(mo: AbstractMonster) {
        var tmp = baseDamage.toFloat()
        for (p in mo.powers) {
            tmp = p.atDamageReceive(tmp, damageTypeForTurn)
        }
        for (p in mo.powers) {
            tmp = p.atDamageFinalReceive(tmp, damageTypeForTurn)
            if (baseDamage != tmp.toInt()) {
                isDamageModified = true
            }
        }
        damage = tmp.toInt()
    }

    override fun use(p: AbstractPlayer, m: AbstractMonster?) {
        val c = generateSequence { AbstractDungeon.returnTrulyRandomCardInCombat(CardType.ATTACK).makeCopy() }
            .first { it !is MysteriousBeam }
            .apply { if (upgraded) upgrade() }
            .apply { applyPowers() }

        addToBot(MakeTempCardInHandAction(c, true))
        baseDamage += c.damage

        m ?: return
        calculateCardDamage(m)
        addToBot(DamageAction(m, DamageInfo(p, damage, damageTypeForTurn), AttackEffect.SLASH_DIAGONAL))
    }

    override fun makeCopy(): AbstractCard = MysteriousBeam()

    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            rawDescription = DESCRIPTION_UPG
            initializeDescription()
        }
    }

    companion object {
        const val ID = "MysteriousBeam"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        val DESCRIPTION_UPG = cardStrings.UPGRADE_DESCRIPTION
        const val IMG_PATH = "img/cards/MysteriousBeam.png"
        private const val COST = 0
    }
}
