package marisa.action

import com.megacrit.cardcrawl.actions.AbstractGameAction
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect
import marisa.MarisaMod

class TreasureHunterDamageAction(target: AbstractCreature?, private val info: DamageInfo) : AbstractGameAction() {
    private var tier: RelicTier
    private var reward = false

    init {
        setValues(target, info)
        tier = RelicTier.RARE
        actionType = ActionType.DAMAGE
        duration = DURATION
    }

    override fun update() {
        if (duration == 0.1f && target != null) {
            AbstractDungeon.effectList.add(
                FlashAtkImgEffect(
                    target.hb.cX, target.hb.cY,
                    AttackEffect.BLUNT_HEAVY
                )
            )
            MarisaMod.logger.info(
                "TreasureHunterDamageAction : target : " +
                        target.id +
                        " ; damage : " +
                        info.base
            )
            target.damage(info)
            val mon = target as AbstractMonster
            val curRoom = AbstractDungeon.getCurrRoom()
            MarisaMod.logger.info(
                "TreasureHunterDamageAction : Checking : MonsterRoomElite :" +
                        curRoom.eliteTrigger +
                        " ; MonsterRoomBoss :" +
                        (curRoom is MonsterRoomBoss) +
                        " ; MindBloom boss fight :" + (AbstractDungeon.lastCombatMetricKey == "Mind Bloom Boss Battle")
            )
            reward = curRoom.eliteTrigger ||
                    curRoom is MonsterRoomBoss || AbstractDungeon.lastCombatMetricKey == "Mind Bloom Boss Battle" || AbstractDungeon.lastCombatMetricKey == "3 Sentries" || AbstractDungeon.lastCombatMetricKey == "Gremlin Nob" || AbstractDungeon.lastCombatMetricKey == "Lagavulin Event"
            if (curRoom !is MonsterRoomBoss && AbstractDungeon.lastCombatMetricKey != "Mind Bloom Boss Battle") {
                while (tier == RelicTier.RARE) {
                    tier = AbstractDungeon.returnRandomRelicTier()
                }
            }
            if (reward) {
                MarisaMod.logger.info(
                    "TreasureHunterDamageAction : Checking : isDying :" + mon.isDying +
                            " ; Current hp : " + mon.currentHealth
                )
                if (((target as AbstractMonster).isDying || target.currentHealth <= 0) &&
                    !target.halfDead &&
                    !target.hasPower("Minion")
                ) {
                    MarisaMod.logger.info("TreasureHunterDamageAction : Granting relic tier :" + tier)
                    AbstractDungeon.getCurrRoom().addRelicToRewards(tier)
                }
            }
            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions()
            }
        }
        tickDuration()
    }

    companion object {
        private const val DURATION = 0.1f
    }
}
