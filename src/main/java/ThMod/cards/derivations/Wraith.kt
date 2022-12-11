package ThMod.cards.derivations

import ThMod.powers.monsters.WraithPower
import basemod.abstracts.CustomCard
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.SetDontTriggerAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.CardQueueItem
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster

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
    override fun use(p: AbstractPlayer, m: AbstractMonster) {
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
        AbstractDungeon.actionManager.addToBottom(
            SetDontTriggerAction(this, false)
        )
    }

    override fun triggerOnEndOfTurnForPlayingCard() {
        dontTriggerOnUseCard = true
        AbstractDungeon.actionManager.cardQueue.add(
            CardQueueItem(this, true)
        )
    }

    override fun makeCopy(): AbstractCard {
        return Wraith()
    }

    override fun upgrade() {}

    companion object {
        const val ID = "Wraith"
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings("Wraith")
        val NAME = cardStrings.NAME
        val DESCRIPTION = cardStrings.DESCRIPTION
        private const val COST = -2
        private const val imgUrl = "img/cards/wraith.png"
    }
}