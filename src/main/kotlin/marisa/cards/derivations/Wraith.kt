package marisa.cards.derivations

import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.SetDontTriggerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.CardQueueItem
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import marisa.powers.monsters.WraithPower

//public class Wraith
class Wraith : CustomCard(
    ID,
    NAME,
    imgUrl,
    COST,
    DESCRIPTION,
    CardType.CURSE,
    CardColor.CURSE,
    CardRarity.SPECIAL,
    CardTarget.SELF
) {
    override fun use(p: AbstractPlayer, unused: AbstractMonster?) {
        if (dontTriggerOnUseCard) {
            AbstractDungeon.actionManager.addToTop(
                ApplyPowerAction(
                    AbstractDungeon.player,
                    AbstractDungeon.player,
                    WraithPower(AbstractDungeon.player, 1),
                    1
                )
            )
        }
    }

    override fun triggerWhenDrawn() {
        addToBot(
            SetDontTriggerAction(this, false)
        )
    }

    override fun triggerOnEndOfTurnForPlayingCard() {
        dontTriggerOnUseCard = true
        AbstractDungeon.actionManager.cardQueue.add(
            CardQueueItem(this, true)
        )
    }

    override fun makeCopy(): AbstractCard = Wraith()

    override fun upgrade() {}

    companion object {
        const val ID = "marisa:Wraith"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(ID)
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        private const val COST = -2
        private const val imgUrl = "marisa/img/cards/wraith.png"
    }
}
