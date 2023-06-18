package marisa.fx.lib

import com.megacrit.cardcrawl.vfx.AbstractGameEffect
import kotlin.properties.Delegates

const val SOUND_EFFECT_KEY = "ORB_FROST_CHANNEL"

abstract class VfxGameEffect : AbstractGameEffect() {
    protected var timer: Float = 0.1f
    protected var hasStarted = false

    fun isFirstUpdate() = !hasStarted

    abstract fun start()
}
