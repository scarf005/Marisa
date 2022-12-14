package marisa.cards

import marisa.patches.AbstractCardEnum
import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.GainBlockAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.powers.Marisa.ChargeUpPower

class EnergyRecoil : CustomCard(
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
        block = 0
        baseBlock = block
    }

    override fun applyPowers() {
        val p = AbstractDungeon.player
        baseBlock = (if (upgraded) 3 else 0)
        if (p.hasPower(ChargeUpPower.POWER_ID)) {
            baseBlock += p.getPower(ChargeUpPower.POWER_ID).amount
            super.applyPowers()
        }
        if (block > 0) {
            val extendString = EXTENDED_DESCRIPTION[0] + block + EXTENDED_DESCRIPTION[1]
            rawDescription = if (upgraded) {
                DESCRIPTION_UPG + extendString
            } else {
                DESCRIPTION + extendString
            }
            initializeDescription()
        }
    }

    override fun onMoveToDiscard() {
        rawDescription = if (upgraded) {
            DESCRIPTION_UPG
        } else {
            DESCRIPTION
        }
        initializeDescription()
    }

    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        if (block > 0) {
            AbstractDungeon.actionManager.addToBottom(
                GainBlockAction(p, p, block)
            )
        }
    }

    override fun makeCopy(): AbstractCard = EnergyRecoil()

    override fun upgrade() {
        if (!upgraded) {
            upgradeName()
            rawDescription = DESCRIPTION_UPG
            initializeDescription()
        }
    }

    companion object {
        const val ID = "EnergyRecoil"
        const val IMG_PATH = "img/cards/recoil.png"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        val DESCRIPTION_UPG = cardStrings.UPGRADE_DESCRIPTION
        private val EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION
        private const val COST = 1
    }
}
