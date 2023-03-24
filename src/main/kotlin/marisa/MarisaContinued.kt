package marisa

import basemod.*
import basemod.BaseMod.GetMonster
import basemod.abstracts.CustomRelic
import basemod.helpers.RelicType
import basemod.interfaces.*
import com.badlogic.gdx.graphics.Color
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer
import com.google.gson.Gson
import com.megacrit.cardcrawl.actions.common.GainEnergyAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.dungeons.Exordium
import com.megacrit.cardcrawl.dungeons.TheBeyond
import com.megacrit.cardcrawl.helpers.CardHelper
import com.megacrit.cardcrawl.helpers.FontHelper
import com.megacrit.cardcrawl.helpers.ImageMaster
import com.megacrit.cardcrawl.localization.*
import com.megacrit.cardcrawl.potions.AbstractPotion
import marisa.action.SparkCostAction
import marisa.cards.*
import marisa.cards.derivations.*
import marisa.characters.Marisa
import marisa.event.Mushrooms_MRS
import marisa.event.OrinTheCat
import marisa.monsters.Orin
import marisa.monsters.ZombieFairy
import marisa.patches.AbstractCardEnum
import marisa.patches.CardTagEnum
import marisa.patches.ThModClassEnum
import marisa.potions.BottledSpark
import marisa.potions.ShroomBrew
import marisa.potions.StarNLove
import marisa.powers.Marisa.*
import marisa.relics.*
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.util.*

@SpireInitializer
class MarisaContinued :
    PostInitializeSubscriber, PostEnergyRechargeSubscriber,
    EditCharactersSubscriber, EditStringsSubscriber, EditKeywordsSubscriber,
    EditCardsSubscriber, EditRelicsSubscriber, OnCardUseSubscriber {
    private enum class Config { CATEVENT, REPLACEDEADBRANCH }

    private val defaultConfig = Properties().apply {
        setProperty(Config.CATEVENT.name, "FALSE")
        setProperty(Config.REPLACEDEADBRANCH.name, "FALSE")
    }

    private fun getConfig() = SpireConfig(MOD_ID, "${MOD_ID}Config", defaultConfig)

    init {
        BaseMod.subscribe(this)

        isCatEventEnabled = getConfig().getBool(Config.CATEVENT.name)
        isDeadBranchEnabled = getConfig().getBool(Config.REPLACEDEADBRANCH.name)

        logger.info("creating the color : MARISA_COLOR")
        listOf(AbstractCardEnum.MARISA_COLOR, AbstractCardEnum.MARISA_DERIVATIONS)
            .forEach { color ->
                BaseMod.addColor(
                    color,
                    STARLIGHT, STARLIGHT, STARLIGHT, STARLIGHT, STARLIGHT, STARLIGHT, STARLIGHT,
                    ATTACK_CC, SKILL_CC, POWER_CC, ENERGY_ORB_CC,
                    ATTACK_CC_PORTRAIT, SKILL_CC_PORTRAIT, POWER_CC_PORTRAIT,
                    ENERGY_ORB_CC_PORTRAIT, CARD_ENERGY_ORB
                )
            }
    }

    override fun receiveEditCharacters() = logger.runInfo("adding Marisa to characters") {
        BaseMod.addCharacter(
            Marisa("Marisa"),
            MY_CHARACTER_BUTTON,
            MARISA_PORTRAIT,
            ThModClassEnum.MARISA
        )
    }

    override fun receiveEditRelics() = logger.runInfo("adding relics") {
        AutoAdd(MOD_ID).any(CustomRelic::class.java) { _, relic ->
            logger.info("adding ${relic.name}")
            BaseMod.addRelicToCustomPool(relic, AbstractCardEnum.MARISA_COLOR)
        }

        BaseMod.addRelic(CatCart(), RelicType.SHARED)
    }

    override fun receiveEditCards() = logger.runInfo("adding cards") {
        AutoAdd(MOD_ID).any(AbstractCard::class.java) { _, card ->
            logger.info("adding ${card.name}")
            BaseMod.addCard(card)
        }
    }

    override fun receiveCardUsed(card: AbstractCard) {
        logger.info("""ThMod : Card used : ${card.cardID} ; cost : ${card.costForTurn}""")
        card.retain = false
        if (card.hasTag(CardTagEnum.SPARK)) {
            AbstractDungeon.actionManager.addToTop(SparkCostAction())
        }
    }

    override fun receivePostEnergyRecharge() {
        if (AbstractDungeon.player.hand.isEmpty) return

        AbstractDungeon.player.hand.group
            .filterIsInstance<GuidingStar>()
            .forEach {
                AbstractDungeon.actionManager.addToBottom(GainEnergyAction(1))
                it.flash()
            }
    }

    override fun receiveEditKeywords() = logger.runInfo("custom keywords") {
        val gson = Gson()
        val keywords = gson.fromJson(localize("keywords"), Keywords::class.java)
        keywords.keywords.forEach { key ->
            logger.info("""Loading keyword : ${key.NAMES[0]}""")
            BaseMod.addKeyword(key.NAMES, key.DESCRIPTION)
        }
    }

    override fun receiveEditStrings() = logger.runInfo("localization") {
        listOf(
            RelicStrings::class.java, CardStrings::class.java, PowerStrings::class.java,
            PotionStrings::class.java, EventStrings::class.java
        )
            .map { it to it.simpleName.removeSuffix("Strings").lowercase() + "s" }
            .forEach { (cls, kind) ->
                BaseMod.loadCustomStrings(cls, localize(kind))
            }
    }

    override fun receivePostInitialize() = logger.runInfo("badge, configs,event and potion") {
        val settingsPanel = ModPanel()
        BaseMod.registerModBadge(
            ImageMaster.loadImage(MOD_BADGE),
            "MarisaContinued", // TODO: use constant for modid
            "Flynn, Hell, Hohner_257, Samsara, scarf005", // TODO: load from modthespire.json
            "A Mod of the poor blonde girl from Touhou Project",
            settingsPanel
        )
        fun toggleButton(text: String, y: Float, configName: String, enabled: Boolean) =
            ModLabeledToggleButton(text, 350.0f, y,
                Settings.CREAM_COLOR, FontHelper.charDescFont, enabled, settingsPanel, {}) {
                getConfig().run {
                    setBool(configName, it.enabled)
                    save()
                }
            }

        val enableBlackCatButton = toggleButton(
            "Enable Black Cat event when playing other characters?", 700.0f,
            Config.CATEVENT.name, isCatEventEnabled
        )
        val enableDeadBranchButton = toggleButton(
            "Don't replace Dead Branch?", 600.0f,
            Config.REPLACEDEADBRANCH.name, isDeadBranchEnabled
        )
        settingsPanel.addUIElement(enableBlackCatButton)
        settingsPanel.addUIElement(enableDeadBranchButton)
        BaseMod.addEvent(Mushrooms_MRS.ID, Mushrooms_MRS::class.java, Exordium.ID)
        BaseMod.addEvent(OrinTheCat.ID, OrinTheCat::class.java, TheBeyond.ID)

        data class PotionInfo(
            val cls: Class<out AbstractPotion>,
            val liquid: Color,
            val hybrid: Color,
            val spot: Color
        )
        listOf(
            PotionInfo(ShroomBrew::class.java, Color.NAVY.cpy(), Color.LIME.cpy(), Color.OLIVE),
            PotionInfo(StarNLove::class.java, Color.BLUE.cpy(), Color.YELLOW.cpy(), Color.NAVY),
            PotionInfo(BottledSpark::class.java, Color.BLUE.cpy(), Color.YELLOW.cpy(), Color.NAVY)
        ).forEach { (cls, liquid, hybrid, spot) ->
            BaseMod.addPotion(cls, liquid, hybrid, spot, cls.simpleName, ThModClassEnum.MARISA)
        }

        BaseMod.addMonster(ORIN_ENCOUNTER, GetMonster { Orin() })
        BaseMod.addMonster(ZOMBIE_FAIRY_ENC, GetMonster { ZombieFairy() })
    }

    internal inner class Keywords {
        lateinit var keywords: Array<Keyword>
    }

    companion object {
        val logger: Logger = LogManager.getLogger(Marisa::class.simpleName)

        @Suppress("MemberVisibilityCanBePrivate")
        lateinit var instance: MarisaContinued

        @Suppress("MemberVisibilityCanBePrivate", "unused")
        @JvmStatic
        fun initialize() {
            instance = MarisaContinued()
        }

        private const val ORIN_ENCOUNTER = "Orin"
        private const val ZOMBIE_FAIRY_ENC = "ZombieFairy"
        //        private const val ORIN_ENCOUNTER_ZHS = """阿燐"""
        //        private const val ZOMBIE_FAIRY_ENC_ZHS = """僵尸妖精"""

        private const val MOD_BADGE = "img/UI/badge.png"

        //card backgrounds
        private const val ATTACK_CC = "img/512/bg_attack_MRS_s.png"
        private const val SKILL_CC = "img/512/bg_skill_MRS_s.png"
        private const val POWER_CC = "img/512/bg_power_MRS_s.png"
        private const val ENERGY_ORB_CC = "img/512/cardOrb.png"
        private const val ATTACK_CC_PORTRAIT = "img/1024/bg_attack_MRS.png"
        private const val SKILL_CC_PORTRAIT = "img/1024/bg_skill_MRS.png"
        private const val POWER_CC_PORTRAIT = "img/1024/bg_power_MRS.png"
        private const val ENERGY_ORB_CC_PORTRAIT = "img/1024/cardOrb.png"
        val STARLIGHT: Color = CardHelper.getColor(0, 10, 125)
        const val CARD_ENERGY_ORB = "img/UI/energyOrb.png"
        private const val MY_CHARACTER_BUTTON = "img/charSelect/MarisaButton.png"
        private const val MARISA_PORTRAIT = "img/charSelect/marisaPortrait.jpg"

        @JvmField
        var isCatEventEnabled: Boolean = false

        @JvmField
        var isDeadBranchEnabled: Boolean = false

        @JvmStatic
        val randomMarisaCard: AbstractCard
            get() = AbstractDungeon.returnTrulyRandomCardInCombat().makeCopy()
    }
}
