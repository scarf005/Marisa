package marisa.powers.Marisa

import com.badlogic.gdx.graphics.Texture
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction
import com.megacrit.cardcrawl.actions.utility.UseCardAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.AbstractCard.CardType
import com.megacrit.cardcrawl.cards.CardQueueItem
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.powers.AbstractPower

class ManaRampagePower(owner: AbstractCreature?, amount: Int) : AbstractPower() {
    init {
        name = NAME
        ID = POWER_ID
        this.owner = owner
        this.amount = amount
        updateDescription()
        img = Texture("img/powers/doubleTap.png")
    }

    override fun updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1]
    }

    override fun onUseCard(card: AbstractCard, action: UseCardAction) {
        if (card.purgeOnUse || card.type != CardType.ATTACK || amount <= 0) return

        flash()
        for (i in 0 until amount) {
            val m = action.target?.let { it as AbstractMonster }
            val tmp = card.makeStatEquivalentCopy()
            AbstractDungeon.player.limbo.addToBottom(tmp)
            tmp.apply {
                current_x = card.current_x
                current_y = card.current_y
                target_x = Settings.WIDTH / 2.0f - 300.0f * Settings.scale
                target_y = Settings.HEIGHT / 2.0f
                freeToPlayOnce = true
                if (m != null) {
                    calculateCardDamage(m)
                }
                purgeOnUse = true
            }
            AbstractDungeon.player.limbo.addToBottom(tmp)
            AbstractDungeon.actionManager.cardQueue.add(CardQueueItem(tmp, m, card.energyOnUse))
        }
        AbstractDungeon.actionManager
            .addToBottom(RemoveSpecificPowerAction(owner, owner, this))
    }

    override fun atEndOfTurn(isPlayer: Boolean) {
        if (!isPlayer) return

        AbstractDungeon.actionManager
            .addToBottom(RemoveSpecificPowerAction(owner, owner, this))
    }

    companion object {
        const val POWER_ID = "ManaRampagePower"
        private val powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID)
        val NAME = powerStrings.NAME
        val DESCRIPTIONS = powerStrings.DESCRIPTIONS
    }
}
