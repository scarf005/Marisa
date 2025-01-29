package marisa.powers.Marisa

import com.badlogic.gdx.graphics.Texture
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.powers.AbstractPower
import com.megacrit.cardcrawl.relics.AbstractRelic
import marisa.MarisaContinued

class PropBagPower(owner: AbstractCreature?, r: AbstractRelic) : AbstractPower() {
    private val r: AbstractRelic
    private val p: AbstractPlayer
    private val rName: String

    init {
        name = NAME
        ID = POWER_ID + IdOffset
        this.owner = owner
        IdOffset++
        amount = -1
        type = PowerType.BUFF
        img = Texture("marisa/img/powers/diminish.png")
        this.r = r
        p = AbstractDungeon.player
        rName = r.name
        MarisaContinued.logger.info("PropBagPower : Granting relic : $rName")
        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(
            Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f, r
        )
        r.atBattleStart()
        updateDescription()
    }

    override fun stackPower(stackAmount: Int) {}
    override fun onVictory() {
        p.loseRelic(r.relicId)
    }

    override fun updateDescription() {
        description = DESCRIPTIONS[0] + rName + DESCRIPTIONS[1]
    }

    companion object {
        const val POWER_ID = "marisa:PropBagPower"
        private val powerStrings = CardCrawlGame.languagePack
            .getPowerStrings(POWER_ID)
        val NAME = powerStrings.NAME
        val DESCRIPTIONS = powerStrings.DESCRIPTIONS
        private var IdOffset = 0
    }
}
