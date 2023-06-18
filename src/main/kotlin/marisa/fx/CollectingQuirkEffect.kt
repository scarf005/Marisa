package marisa.fx

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.megacrit.cardcrawl.actions.animations.VFXAction
import com.megacrit.cardcrawl.core.CardCrawlGame.screenShake
import com.megacrit.cardcrawl.core.CardCrawlGame.sound
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon.player
import com.megacrit.cardcrawl.dungeons.AbstractDungeon.effectsQueue
import com.megacrit.cardcrawl.dungeons.AbstractDungeon.getMonsters
import com.megacrit.cardcrawl.helpers.ScreenShake
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.relics.AbstractRelic
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect
import marisa.fx.lib.FireProjectileEffect.Companion.CollectingQuirkProjectile
import marisa.fx.lib.SOUND_EFFECT_KEY
import marisa.fx.lib.VfxGameEffect

private const val MAX_VFX_PROJECTILES = 50

class CollectingQuirkEffect(private val flipped: Boolean, private val monsterX: Float) : VfxGameEffect() {
    private val relics: List<AbstractRelic> = player.relics
    private var relicCounter = 0

    init {
        duration = if (Settings.FAST_MODE) 2.0f else 0.5f
    }

    override fun update() {
        if (isFirstUpdate()) { start() }

        duration -= Gdx.graphics.deltaTime
        timer -= Gdx.graphics.deltaTime
        if (relicCounter < relics.size.coerceAtMost(MAX_VFX_PROJECTILES) && timer < 0.0f) {
            timer += 0.03f
            effectsQueue.add(CollectingQuirkProjectile(relics[relicCounter].img, relics.size, flipped, monsterX))
            relicCounter += 1
        }
        if (duration < 0.0f) {
            isDone = true
        }
    }

    override fun start() {
        sound.playA(SOUND_EFFECT_KEY, -0.25f - relics.size.toFloat() / 200.0f)
        screenShake.shake(ScreenShake.ShakeIntensity.HIGH, ScreenShake.ShakeDur.MED, true)
        effectsQueue.add(BorderLongFlashEffect(Color.SKY))
        hasStarted = true
    }

    override fun render(spriteBatch: SpriteBatch) {}
    override fun dispose() {}

    companion object {
        fun toVfx(): VFXAction {
            val duration = if (Settings.FAST_MODE) 0.5f else 1.0f
            val monsters = getMonsters()
            val avgX = monsters.monsters.stream()
                .mapToDouble { m: AbstractMonster -> m.drawX.toDouble() }.average()
                .orElse(Settings.WIDTH.toDouble())
                .toFloat()
            return VFXAction(CollectingQuirkEffect(monsters.shouldFlipVfx(), avgX), duration)
        }
    }
}
