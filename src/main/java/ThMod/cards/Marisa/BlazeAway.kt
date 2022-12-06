package ThMod.cards.Marisa

import ThMod.ThMod
import ThMod.action.BlazeAwayAction
import ThMod.patches.AbstractCardEnum
import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster

class BlazeAway : CustomCard(
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
        magicNumber = NUM
        baseMagicNumber = magicNumber
    }

    private fun lastAttack() = AbstractDungeon.actionManager.cardsPlayedThisTurn
        .reversed()
        .find { it.type == CardType.ATTACK }

    override fun applyPowers() {
        val last = lastAttack()
        rawDescription = if (last == null) {
            """$DESCRIPTION${EXTENDED_DESCRIPTION[2]}"""
        } else {
            """$DESCRIPTION${EXTENDED_DESCRIPTION[0]}${last.name}${EXTENDED_DESCRIPTION[1]}"""
        }
        initializeDescription()
    }

    override fun onMoveToDiscard() {
        rawDescription = DESCRIPTION
        initializeDescription()
    }

    override fun use(p: AbstractPlayer, m: AbstractMonster) {
        val last = lastAttack()
        if (last == null) {
            ThMod.logger.info("BlazeAway : error : last attack is null ")
        } else {
            ThMod.logger.info("""BlazeAway : last attack :${last.cardID}""")
            val card = last.makeStatEquivalentCopy()
            if (card.costForTurn >= 0) {
                card.setCostForTurn(0)
            }
            ThMod.logger.info(
                """BlazeAway : card :${card.cardID} ; baseD :${card.baseDamage} ; D : ${card.damage} ; baseB :${card.baseBlock} ; B : ${card.block} ; baseM :${card.baseMagicNumber} ; M : ${card.magicNumber} ; C : ${card.cost} ; CFT : ${card.costForTurn}"""
            )
            repeat((0 until magicNumber).count()) {
                AbstractDungeon.actionManager.addToBottom(
                    BlazeAwayAction(card)
                )
            }
        }
    }

    override fun makeCopy(): AbstractCard = BlazeAway()

    override fun upgrade() {
        if (!upgraded) {
            upgradeMagicNumber(UPG_NUM)
            upgradeName()
            //upgradeBaseCost(0);
            //this.rawDescription = DESCRIPTION_UPG;
            //initializeDescription();
            //this.exhaust = false;
        }
    }

    companion object {
        const val ID = "BlazeAway"
        const val IMG_PATH = "img/cards/blazeAway.png"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        val DESCRIPTION_UPG = cardStrings.UPGRADE_DESCRIPTION
        val EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION
        private const val COST = 1
        private const val NUM = 1
        private const val UPG_NUM = 1
    }
}