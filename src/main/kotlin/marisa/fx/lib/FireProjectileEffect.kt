package marisa.fx.lib

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.megacrit.cardcrawl.core.CardCrawlGame.sound
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.dungeons.AbstractDungeon.effectsQueue
import com.megacrit.cardcrawl.vfx.AbstractGameEffect
import com.megacrit.cardcrawl.vfx.combat.IceShatterEffect
import marisa.fx.MeteoricShowerEffect

const val PROJECTILE_IMPACT_SOUND_KEY = "ORB_FROST_EVOKE"

class FireProjectileEffect : AbstractGameEffect() {
    private lateinit var projectile: ProjectileData
    private var waitTimer = 0f
    private var floorY = AbstractDungeon.floorY + MathUtils.random(-200.0f, 50.0f) * Settings.scale
    private var monsterX = 0f
    override fun update() {
        waitTimer -= Gdx.graphics.deltaTime
        if (!(waitTimer > 0.0f)) {
            projectile.x += projectile.vX * Gdx.graphics.deltaTime
            projectile.y -= projectile.vY * Gdx.graphics.deltaTime

            if (projectile.shouldImpactFloor) { checkFloorCollision() }
            if (projectile.shouldImpactWall) { checkWallCollision() }
            if (projectile.shouldAccelerate) { checkAccelerate() }
        }
    }

    private fun checkWallCollision() {
        val hittingWall = projectile.x > monsterX
        if (hittingWall) {
            impact()
            isDone = true
        }
    }

    private fun checkAccelerate() {
        when {
            projectile.accelerationXFactor > 1.0f && projectile.accelerationYFactor > 1.0f -> {
                if(projectile.x > projectile.accelerationX * Settings.scale && projectile.y < projectile.accelerationY * Settings.scale) {
                    projectile.vX *= projectile.accelerationXFactor
                    projectile.vY *= projectile.accelerationYFactor
                    projectile.shouldAccelerate = false
                }
            }
            projectile.accelerationXFactor > 1.0f -> {
                if(projectile.x > projectile.accelerationX * Settings.scale) {
                    projectile.vX *= projectile.accelerationXFactor
                    projectile.shouldAccelerate = false
                }
            }
            projectile.accelerationYFactor > 1.0f -> {
                if(projectile.y < projectile.accelerationY * Settings.scale) {
                    projectile.vY *= projectile.accelerationYFactor
                    projectile.shouldAccelerate = false
                }
            }
        }
    }

    private fun checkFloorCollision() {
        val hittingTheFloor = projectile.y < floorY
        if (hittingTheFloor) {
            impact()
            isDone = true
        }
    }

    private fun impact() {
        var pitch = 0.8f
        pitch += MathUtils.random(-0.2f, 0.2f) // 80
        sound.playA(PROJECTILE_IMPACT_SOUND_KEY, pitch)
        for (i in 0..3) {
            effectsQueue.add(IceShatterEffect(projectile.x, projectile.y))
        }
    }

    override fun render(spriteBatch: SpriteBatch) {
        if (waitTimer < 0.0f) {
            spriteBatch.setBlendFunction(770, 1)
            spriteBatch.color = projectile.color
            spriteBatch.draw(
                projectile.texture,
                projectile.x,
                projectile.y,
                48.0f,
                48.0f,
                projectile.width,
                projectile.height,
                projectile.scale,
                projectile.scale,
                projectile.rotation,
                0,
                0,
                projectile.texture!!.width,
                projectile.texture!!.height,
                false,
                false
            )
            spriteBatch.setBlendFunction(770, 771)
        }
    }

    override fun dispose() {}
    class ProjectileData {
        @JvmField
        var texture: Texture? = null
        @JvmField
        var x = 0f
        @JvmField
        var y = 0f
        @JvmField
        var vX = 0f
        @JvmField
        var vY = 0f
        @JvmField
        var width = 0f
        @JvmField
        var height = 0f
        @JvmField
        var shouldImpactFloor = false
        @JvmField
        var shouldImpactWall = false
        // acceleration is a mode where a projectile proceeds at its initial speed until it crosses a threshold
        // 'accelerationX/Y'. Its velocity is then multiplied by the 'accelerationX/YFactor'.
        @JvmField
        var shouldAccelerate = false
        @JvmField
        var accelerationX = -1.0f
        @JvmField
        var accelerationY = (Settings.HEIGHT * 2).toFloat()
        @JvmField
        var accelerationXFactor = 1f
        @JvmField
        var accelerationYFactor = 1f
        @JvmField
        var scale = 0f
        @JvmField
        var duration = 0f
        @JvmField
        var color: Color? = null
        @JvmField
        var rotation = 0f

        //rotate((float) relicCount, flipped, data);
        fun rotate(relicCount: Float, flipped: Boolean, data: ProjectileData) {
            val rotation = Vector2(data.vX, data.vY)
            if (flipped) {
                data.rotation = rotation.angle() + 225.0f - relicCount / 3.0f
            } else {
                data.rotation = rotation.angle() - 45.0f + relicCount / 3.0f
            }
        }

        fun fastMode() {
            if (Settings.FAST_MODE) {
                vX *= 2f
                vY *= 2f
            }
        }

        fun scale() {
            width *= Settings.scale
            height *= Settings.scale
            scale *= Settings.scale
            x *= Settings.scale
            vX *= scale
            vX *= Settings.scale
            vY *= Settings.scale
        }

        fun flip() {
            x = Settings.WIDTH - x
            vX *= -1f
            accelerationX = Settings.WIDTH - accelerationX
        }
    }

    fun createCollectingQuirkProjectileData(img: Texture, relicCount: Int, flipped: Boolean, scale: Float): ProjectileData {
        val data = ProjectileData()
        data.texture = img
        data.width = 128f
        data.height = 128f
        data.shouldAccelerate = true
        data.accelerationXFactor = 5.0f
        data.shouldImpactWall = false
        data.x = MathUtils.random(-300f, 0f)
        data.vX = MathUtils.random(600.0f, 900.0f) - relicCount.toFloat() * 5.0f
        data.accelerationX = 300.0f
        if (flipped) {
            data.flip()
        }
        val yRange = Settings.HEIGHT.toFloat() / 3
        data.y = Settings.HEIGHT.toFloat() / 2 + MathUtils.random(-yRange, yRange)
        data.vY = 0f
        data.duration = 2.0f
        data.scale = MathUtils.random(1.25f, 1.75f) + relicCount.toFloat() * 0.04f
        data.scale()
        data.color = Color(0.9f, 0.9f, 1.0f, MathUtils.random(0.9f, 1.0f))
        data.rotation = MathUtils.random(0, 360 - 1).toFloat()
        data.fastMode()
        return data
    }

    fun createMeteoricShowerProjectileData(numHits: Int, flipped: Boolean, scale: Float): ProjectileData {
        val data = ProjectileData()
        data.texture = MeteoricShowerEffect.METEORIC_SHOWER_PROJECTILE
        data.shouldImpactFloor = true
        data.width = 64f
        data.height = 64f
        data.x = MathUtils.random(500.0f, 1500.0f)
        data.vX = MathUtils.random(600.0f, 900.0f) - numHits.toFloat() * 5.0f
        if (flipped) {
            data.flip()
        }
        data.y = Settings.HEIGHT.toFloat() + MathUtils.random(100.0f, 300.0f) - 48.0f
        data.vY = MathUtils.random(2500.0f, 4000.0f) - numHits.toFloat() * 10.0f
        data.duration = 2.0f
        data.scale = MathUtils.random(1.25f, 2.0f) + numHits.toFloat() * 0.04f
        data.scale()
        data.color = Color(0.9f, 0.9f, 1.0f, MathUtils.random(0.9f, 1.0f))
        data.rotation = MathUtils.random(0, 360 - 1).toFloat()
        data.fastMode()
        return data
    }

    companion object {
        fun CollectingQuirkProjectile(img: Texture, relicCount: Int, flipped: Boolean, x: Float): FireProjectileEffect =
            FireProjectileEffect().apply {
                projectile = createCollectingQuirkProjectileData(img, relicCount, flipped, scale)
                monsterX = x + MathUtils.random(-100.0f, 100.0f) * Settings.scale
                waitTimer = MathUtils.random(0.0f, 0.5f)
                renderBehind = MathUtils.randomBoolean()
        }

        fun MeteoricShowerProjectile(numHits: Int, flipped: Boolean, x: Float): FireProjectileEffect =
            FireProjectileEffect().apply {
                projectile = createMeteoricShowerProjectileData(numHits, flipped, scale)
                monsterX = (x + MathUtils.random(-100.0f, 100.0f)) * Settings.scale
                waitTimer = MathUtils.random(0.0f, 0.5f)
                renderBehind = MathUtils.randomBoolean()
        }
    }
}
