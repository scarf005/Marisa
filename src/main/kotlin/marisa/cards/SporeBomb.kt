package marisa.cards

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.powers.VulnerablePower
import marisa.MarisaContinued
import marisa.patches.AbstractCardEnum

class SporeBomb : CustomCard(
    ID,
    NAME,
    IMG_PATH,
    COST,
    DESCRIPTION,
    CardType.SKILL,
    AbstractCardEnum.MARISA_COLOR,
    CardRarity.COMMON,
    CardTarget.ENEMY
) {
    init {
        baseMagicNumber = STC
        magicNumber = baseMagicNumber
    }

    override fun use(p: AbstractPlayer, m: AbstractMonster?) {
        if (MarisaContinued.isAmplified(this, AMP)) {
            for (mo in AbstractDungeon.getCurrRoom().monsters.monsters) {
                AbstractDungeon.actionManager.addToBottom(
                    ApplyPowerAction(
                        mo,
                        p,
                        VulnerablePower(mo, magicNumber, false),
                        magicNumber,
                        true
                    )
                )
            }
        } else {
            AbstractDungeon.actionManager.addToBottom(
                ApplyPowerAction(
                    m,
                    p,
                    VulnerablePower(m, magicNumber, false),
                    magicNumber,
                    true
                )
            )
        }
    }

    override fun makeCopy(): AbstractCard = SporeBomb()

    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            upgradeMagicNumber(UPG_STC)
        }
    }

    companion object {
        const val ID = "SporeBomb"
        const val IMG_PATH = "img/cards/SporeCrump.png"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        private const val COST = 0
        private const val AMP = 1
        private const val STC = 2
        private const val UPG_STC = 1
    }
}
