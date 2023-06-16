package marisa.fx

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.megacrit.cardcrawl.actions.animations.VFXAction
import com.megacrit.cardcrawl.core.CardCrawlGame.screenShake
import com.megacrit.cardcrawl.core.CardCrawlGame.sound
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon.effectsQueue
import com.megacrit.cardcrawl.dungeons.AbstractDungeon.getMonsters
import com.megacrit.cardcrawl.helpers.ImageMaster
import com.megacrit.cardcrawl.helpers.ScreenShake
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect
import marisa.fx.lib.FireProjectileEffect.Companion.MeteoricShowerProjectile
import marisa.fx.lib.SOUND_EFFECT_KEY
import marisa.fx.lib.VfxGameEffect

class MeteoricShowerEffect(
    private val numHits: Int,
    private val flipped: Boolean,
    private val monsterX: Float
) : VfxGameEffect() {
    private var timer = 0.1f
    private val origDuration = if (Settings.FAST_MODE) 2.0f else 0.5f
    private var starCounter = 0

    init {
        duration = origDuration
    }

    override fun update() {
        if (duration == origDuration) {
            sound.playA(SOUND_EFFECT_KEY, -0.25f - numHits.toFloat() / 200.0f)
            screenShake.shake(ScreenShake.ShakeIntensity.HIGH, ScreenShake.ShakeDur.MED, true)
            effectsQueue.add(BorderLongFlashEffect(Color.SKY))
        }
        duration -= Gdx.graphics.deltaTime
        timer -= Gdx.graphics.deltaTime
        if (starCounter < numHits.coerceAtMost(50) && timer < 0.0f) {
            timer += 0.03f
            effectsQueue.add(MeteoricShowerProjectile(numHits, flipped, monsterX))
            starCounter += 1
        }
        if (duration < 0.0f) {
            isDone = true
        }
    }

    override fun render(spriteBatch: SpriteBatch) {}
    override fun dispose() {}

    companion object {
        private const val METEORIC_SHOWER_PROJECTILE_TEXPATH = "img/vfx/star_128.png"
        @JvmField
        val METEORIC_SHOWER_PROJECTILE: Texture = ImageMaster.loadImage(METEORIC_SHOWER_PROJECTILE_TEXPATH)

        fun toVfx(numHits: Int): VFXAction {
            val duration = if (Settings.FAST_MODE) 0.5f else 1.0f
            val monsters = getMonsters()
            val avgX = monsters.monsters.stream()
                .mapToDouble { m: AbstractMonster -> m.drawX.toDouble() }.average()
                .orElse(Settings.WIDTH.toDouble())
                .toFloat()
            return VFXAction(MeteoricShowerEffect(numHits, monsters.shouldFlipVfx(), avgX), duration)
        }
    }
}
