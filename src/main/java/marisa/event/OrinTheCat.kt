package marisa.event

import marisa.marisa
import marisa.cards.derivations.Wraith
import marisa.relics.CatCart
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.events.AbstractEvent
import com.megacrit.cardcrawl.events.RoomEventDialog
import com.megacrit.cardcrawl.helpers.CardLibrary
import com.megacrit.cardcrawl.helpers.MonsterHelper
import com.megacrit.cardcrawl.monsters.AbstractMonster
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect

class OrinTheCat : AbstractEvent() {
    private var screen: CurScreen = CurScreen.INTRO
    private val orin: AbstractMonster?
    private val satori: Boolean

    private enum class CurScreen {
        INTRO, PRE_COMBAT, END
    }

    init {
        /*
    initializeImage(
        "images/events/sphereClosed.png",
        1120.0F * Settings.scale,
        AbstractDungeon.floorY - 50.0F * Settings.scale
    );
*/
        roomEventText.clear()
        body = INTRO_MSG
        satori = AbstractDungeon.player.name == "Komeiji"
        if (satori) {
            roomEventText.addDialogOption(OPTIONS[4])
        } else {
            roomEventText.addDialogOption(OPTIONS[0], CardLibrary.getCopy("Wraith"))
        }
        roomEventText.addDialogOption(OPTIONS[1])
        hasDialog = true
        hasFocus = true
        AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter("Orin")
        orin = AbstractDungeon.getCurrRoom().monsters.monsters[0]
        if (orin != null) {
            marisa.logger.info("OrinTheCat : orin get : " + orin.name)
        } else {
            marisa.logger.info("OrinTheCat : error : null orin")
        }
    }

    override fun update() {
        super.update()
        if (!RoomEventDialog.waitForInput) {
            buttonEffect(roomEventText.selectedOption)
        }
    }

    override fun onEnterRoom() {
        marisa.logger.info("OrinTheCat : OnEnterRoom")
        AbstractDungeon.getCurrRoom().rewards.clear()
    }

    override fun buttonEffect(buttonPressed: Int) {
        when (screen) {
            CurScreen.INTRO -> when (buttonPressed) {
                0 -> if (satori) {
                    screen = CurScreen.END
                    AbstractDungeon.getCurrRoom().spawnRelicAndObtain(
                        (Settings.WIDTH / 2).toFloat(), (Settings.HEIGHT / 2).toFloat(), CatCart()
                    )
                    roomEventText.updateBodyText(DESCRIPTIONS[3])
                    roomEventText.updateDialogOption(0, OPTIONS[3])
                    roomEventText.clearRemainingOptions()
                    logMetric(ID, "Special")
                    return
                } else {
                    marisa.logger.info("OrinTheCat : INTRO : Skipping fight!")
                    screen = CurScreen.END
                    /*
              if (orin != null) {
                AbstractDungeon.actionManager.addToBottom(new EscapeAction(orin));
              }
              */roomEventText.updateBodyText(DESCRIPTIONS[2])
                    roomEventText.updateDialogOption(0, OPTIONS[3])
                    roomEventText.clearRemainingOptions()
                    AbstractDungeon.effectList.add(
                        ShowCardAndObtainEffect(
                            Wraith(),
                            Settings.WIDTH / 2.0f,
                            Settings.HEIGHT / 2.0f
                        )
                    )
                    logMetricIgnored(ID)
                    return
                }

                1 -> if (satori) {
                    screen = CurScreen.END
                    /*
              if (orin != null) {
                AbstractDungeon.actionManager.addToBottom(new EscapeAction(orin));
              }
              */roomEventText.updateBodyText(DESCRIPTIONS[4])
                    roomEventText.updateDialogOption(0, OPTIONS[3])
                    roomEventText.clearRemainingOptions()
                    logMetricIgnored(ID)
                    return
                } else {
                    marisa.logger.info("OrinTheCat : INTRO : Fight!")
                    screen = CurScreen.PRE_COMBAT
                    roomEventText.updateBodyText(DESCRIPTIONS[1])
                    roomEventText.updateDialogOption(0, OPTIONS[2])
                    roomEventText.clearRemainingOptions()
                    logMetric(ID, "Fight")
                    return
                }
            }

            CurScreen.PRE_COMBAT -> {
                marisa.logger.info("OrinTheCat : PreCombat : Adding Reward")
                val currRoom = AbstractDungeon.getCurrRoom()
                currRoom.rewards.clear()
                if (!AbstractDungeon.player.hasRelic("CatCart")) {
                    if (Settings.isDailyRun) {
                        currRoom.addGoldToRewards(AbstractDungeon.miscRng.random(50))
                    } else {
                        currRoom.addGoldToRewards(AbstractDungeon.miscRng.random(45, 55))
                    }
                    currRoom.addRelicToRewards(CatCart())
                }
                AbstractDungeon.getCurrRoom().eliteTrigger = true
                //this.img = ImageMaster.loadImage("images/events/sphereOpen.png");
                marisa.logger.info("OrinTheCat : PreCombat : Entering combat")
                enterCombat()
                AbstractDungeon.lastCombatMetricKey = "Orin"
            }

            CurScreen.END -> {
                marisa.logger.info("OrinTheCat : end : Opening Map")
                openMap()
            }
        }
    }

    companion object {
        const val ID = "OrinTheCat"
        private val eventStrings = CardCrawlGame.languagePack.getEventString(ID)
        val NAME = eventStrings.NAME!!
        private val DESCRIPTIONS = eventStrings.DESCRIPTIONS
        private val OPTIONS = eventStrings.OPTIONS
        private val INTRO_MSG = DESCRIPTIONS[0]
    }
}
