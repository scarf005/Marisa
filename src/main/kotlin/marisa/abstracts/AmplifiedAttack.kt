package marisa.abstracts

import basemod.abstracts.CustomCard
import com.badlogic.gdx.math.MathUtils
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster

abstract class AmplifiedAttack(
    id: String?,
    name: String?,
    img: String?,
    cost: Int,
    rawDescription: String?,
    type: CardType?,
    color: CardColor?,
    rarity: CardRarity?,
    target: CardTarget?
) : CustomCard(
    id,
    name,
    img,
    cost,
    rawDescription,
    type,
    color,
    rarity,
    target
) {
    private var ampDamage = -1
    protected var ampNumber = 0
    protected lateinit var multiAmpDamage: IntArray
    protected var isException = false
    override fun applyPowers() {
        if (!isException) {
            damage = baseDamage
            ampDamage = baseDamage + ampNumber
            block = ampDamage.also { baseBlock = it }
        }
        isDamageModified = false
        isBlockModified = false
        var tmp = damage.toFloat()
        var amp = block.toFloat()
        tmp = calculate(tmp, null)
        amp = calculate(amp, null)
        if (baseDamage != tmp.toInt()) {
            isDamageModified = true
        }
        if (ampDamage != amp.toInt()) {
            isBlockModified = true
        }
        damage = MathUtils.floor(tmp)
        block = MathUtils.floor(amp)
        if (isMultiDamage) {
            val m = AbstractDungeon.getCurrRoom().monsters.monsters
            multiDamage = IntArray(m.size)
            for (i in m.indices) {
                multiDamage[i] = MathUtils.floor(tmp)
            }
            multiAmpDamage = IntArray(m.size)
            for (i in m.indices) {
                multiAmpDamage[i] = MathUtils.floor(amp)
            }
        }
    }

    private fun calculate(base: Float, target: AbstractMonster?): Float {
        var temp = base
        val player = AbstractDungeon.player
        if (AbstractDungeon.player.hasRelic("WristBlade") && costForTurn == 0) {
            temp += 3.0f
        }
        for (p in player.powers) {
            temp = p.atDamageGive(temp, damageTypeForTurn)
        }
        if (target != null) {
            for (p in target.powers) {
                temp = p.atDamageReceive(temp, damageTypeForTurn)
            }
        }
        for (p in player.powers) {
            temp = p.atDamageFinalGive(temp, damageTypeForTurn)
        }
        if (target != null) {
            for (p in target.powers) {
                temp = p.atDamageFinalReceive(temp, damageTypeForTurn)
            }
        }
        if (temp < 0) {
            temp = 0f
        }
        return temp
    }

    override fun calculateDamageDisplay(mo: AbstractMonster?) {
        calculateCardDamage(mo)
    }

    override fun calculateCardDamage(mo: AbstractMonster?) {
        if (!isException) {
            damage = baseDamage
            ampDamage = baseDamage + ampNumber
            block = ampDamage.also { baseBlock = it }
        }
        isDamageModified = false
        isBlockModified = false
        if (!isMultiDamage && mo != null) {
            var tmp = damage.toFloat()
            var amp = block.toFloat()
            tmp = calculate(tmp, mo)
            amp = calculate(amp, mo)
            if (baseDamage != tmp.toInt()) {
                isDamageModified = true
            }
            if (ampDamage != amp.toInt()) {
                isBlockModified = true
            }
            damage = MathUtils.floor(tmp)
            block = MathUtils.floor(amp)
        } else {
            val m = AbstractDungeon.getCurrRoom().monsters.monsters
            val tmp = FloatArray(m.size)
            val amp = FloatArray(m.size)
            for (i in m.indices) {
                tmp[i] = calculate(damage.toFloat(), m[i])
                amp[i] = calculate(block.toFloat(), m[i])
                if (baseDamage != tmp[i].toInt()) {
                    isDamageModified = true
                }
                if (ampDamage != amp[i].toInt()) {
                    isBlockModified = true
                }
            }
            multiDamage = IntArray(m.size)
            for (i in tmp.indices) {
                multiDamage[i] = MathUtils.floor(tmp[i])
            }
            multiAmpDamage = IntArray(m.size)
            for (i in amp.indices) {
                multiAmpDamage[i] = MathUtils.floor(amp[i])
            }
            damage = multiDamage[0]
            block = multiAmpDamage[0]
        }
    }
}
