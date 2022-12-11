package marisa.event

import marisa.relics.BigShroomBag
import marisa.relics.ShroomBag
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.curses.Parasite
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.events.AbstractEvent
import com.megacrit.cardcrawl.events.RoomEventDialog
import com.megacrit.cardcrawl.events.exordium.Mushrooms
import com.megacrit.cardcrawl.helpers.CardLibrary
import com.megacrit.cardcrawl.helpers.ImageMaster
import com.megacrit.cardcrawl.helpers.MonsterHelper
import com.megacrit.cardcrawl.localization.EventStrings
import com.megacrit.cardcrawl.relics.Circlet
import com.megacrit.cardcrawl.relics.OddMushroom
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect
import org.apache.logging.log4j.LogManager

class Mushrooms_MRS : AbstractEvent() {
    private val fgImg = ImageMaster.loadImage("images/events/fgShrooms.png")
    private val bgImg = ImageMaster.loadImage("images/events/bgShrooms.png")
    override fun update() {
        super.update()
        if (!RoomEventDialog.waitForInput) {
            buttonEffect(roomEventText.selectedOption)
        }
    }

    override fun buttonEffect(buttonPressed: Int) {
        when (buttonPressed) {
            0 -> {
                when (screenNum) {
                    0 -> {
                        AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter(ENC_NAME)
                        roomEventText.updateBodyText(FIGHT_MSG)
                        roomEventText.updateDialogOption(0, OPTIONS[3])
                        roomEventText.removeDialogOption(1)
                        logMetric("Mushrooms_MRS", "Fought Mushrooms")
                        screenNum += 2
                    }

                    1 -> {
                        openMap()
                    }

                    2 -> {
                        if (Settings.isDailyRun) {
                            AbstractDungeon.getCurrRoom().addGoldToRewards(AbstractDungeon.miscRng.random(25))
                        } else {
                            AbstractDungeon.getCurrRoom().addGoldToRewards(AbstractDungeon.miscRng.random(20, 30))
                        }
                        if (AbstractDungeon.player.hasRelic("ShroomBag")) {
                            AbstractDungeon.getCurrRoom().addRelicToRewards(BigShroomBag())
                        } else {
                            if (AbstractDungeon.player.hasRelic("BigShroomBag")) {
                                if (AbstractDungeon.player.hasRelic("OddMushroom")) {
                                    AbstractDungeon.getCurrRoom().addRelicToRewards(Circlet())
                                } else {
                                    AbstractDungeon.getCurrRoom().addRelicToRewards(OddMushroom())
                                }
                            } else {
                                AbstractDungeon.getCurrRoom().addRelicToRewards(ShroomBag())
                            }
                        }
                        enterCombat()
                        AbstractDungeon.lastCombatMetricKey = ENC_NAME
                    }
                }
                return
            }

            1 -> {
                val curse: AbstractCard = Parasite()
                val healAmt = (AbstractDungeon.player.maxHealth.toFloat() * HEAL_AMT).toInt()
                logMetricObtainCardAndHeal("Mushrooms_MRS", "Healed and dodged fight", curse, healAmt)
                AbstractDungeon.player.heal(healAmt)
                AbstractDungeon.effectList.add(
                    ShowCardAndObtainEffect(
                        curse, Settings.WIDTH.toFloat() / 2.0f,
                        Settings.HEIGHT.toFloat() / 2.0f
                    )
                )
                roomEventText.updateBodyText(HEAL_MSG)
                roomEventText.updateDialogOption(0, OPTIONS[4])
                roomEventText.removeDialogOption(1)
                screenNum = 1
                return
            }

            else -> logger.info("ERROR: case $buttonPressed should never be called")
        }
    }

    override fun render(sb: SpriteBatch) {
        sb.color = Color.WHITE
        sb.draw(bgImg, 0.0f, -10.0f, Settings.WIDTH.toFloat(), 1080.0f * Settings.scale)
        sb.draw(
            fgImg, 0.0f, -20.0f * Settings.scale, Settings.WIDTH.toFloat(),
            1080.0f * Settings.scale
        )
    }

    init {
        roomEventText.clear()
        body = DESCRIPTIONS[2]
        roomEventText.addDialogOption(OPTIONS[0])
        val temp = (AbstractDungeon.player.maxHealth.toFloat() * 0.25f).toInt()
        roomEventText
            .addDialogOption(OPTIONS[1] + temp + OPTIONS[2], CardLibrary.getCopy("Parasite"))
        AbstractDungeon.getCurrRoom().phase = RoomPhase.EVENT
        hasDialog = true
        hasFocus = true
    }

    companion object {
        private val logger = LogManager.getLogger(
            Mushrooms::class.java.name
        )
        const val ID = "Mushrooms_MRS"
        private val eventStrings: EventStrings = CardCrawlGame.languagePack.getEventString("Mushrooms_MRS")
        val NAME: String? = eventStrings.NAME
        val DESCRIPTIONS: Array<String> = eventStrings.DESCRIPTIONS
        private val OPTIONS = eventStrings.OPTIONS
        private const val ENC_NAME = "The Mushroom Lair"
        private const val HEAL_AMT = 0.25f
        private val HEAL_MSG: String = DESCRIPTIONS[0]
        private val FIGHT_MSG: String = DESCRIPTIONS[1]
    }
}
