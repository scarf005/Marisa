package marisa

import basemod.*
import basemod.BaseMod.GetMonster
import basemod.helpers.RelicType
import basemod.interfaces.*
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer
import com.google.gson.Gson
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction
import com.megacrit.cardcrawl.actions.common.GainEnergyAction
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.core.Settings.GameLanguage
import com.megacrit.cardcrawl.dungeons.AbstractDungeon
import com.megacrit.cardcrawl.dungeons.Exordium
import com.megacrit.cardcrawl.dungeons.TheBeyond
import com.megacrit.cardcrawl.helpers.CardHelper
import com.megacrit.cardcrawl.helpers.FontHelper
import com.megacrit.cardcrawl.helpers.ImageMaster
import com.megacrit.cardcrawl.localization.*
import com.megacrit.cardcrawl.potions.AbstractPotion
import com.megacrit.cardcrawl.rooms.AbstractRoom
import com.megacrit.cardcrawl.ui.panels.EnergyPanel
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
import java.nio.charset.StandardCharsets
import java.util.*

@SpireInitializer
class MarisaMod : PostExhaustSubscriber, PostBattleSubscriber, PostDungeonInitializeSubscriber,
    EditCharactersSubscriber,
    PostInitializeSubscriber, EditRelicsSubscriber, EditCardsSubscriber, EditStringsSubscriber, OnCardUseSubscriber,
    EditKeywordsSubscriber, OnPowersModifiedSubscriber, PostDrawSubscriber, PostEnergyRechargeSubscriber {
    private val marisaModDefaultProp = Properties()

    init {
        BaseMod.subscribe(this)
        logger.info("creating the color : MARISA_COLOR")
        listOf(AbstractCardEnum.MARISA_COLOR, AbstractCardEnum.MARISA_DERIVATIONS)
            .forEach { color ->
                BaseMod.addColor(
                    color,
                    STARLIGHT, STARLIGHT, STARLIGHT, STARLIGHT, STARLIGHT, STARLIGHT, STARLIGHT,
                    ATTACK_CC, SKILL_CC, POWER_CC, ENERGY_ORB_CC,
                    ATTACK_CC_PORTRAIT, SKILL_CC_PORTRAIT, POWER_CC_PORTRAIT, ENERGY_ORB_CC_PORTRAIT,
                    CARD_ENERGY_ORB
                )
            }

        marisaModDefaultProp.setProperty("isCatEventEnabled", "TRUE")
        try {
            val config = SpireConfig("vexMod", "vexModConfig", marisaModDefaultProp)
            config.load()
            isCatEventEnabled = config.getBool("isCatEventEnabled")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun receiveEditCharacters() {
        logger.info("begin editing characters")
        logger.info("""add ${ThModClassEnum.MARISA.toString()}""")
        BaseMod.addCharacter(
            Marisa("Marisa"),
            MY_CHARACTER_BUTTON,
            MARISA_PORTRAIT,
            ThModClassEnum.MARISA
        )
        logger.info("done editing characters")
    }

    override fun receiveEditRelics() {
        logger.info("Begin editing relics.")
        arrayOf(
            MiniHakkero(), BewitchedHakkero(), MagicBroom(), AmplifyWand(),
            ExperimentalFamiliar(), RampagingMagicTools(), BreadOfAWashokuLover(), SimpleLauncher(),
            HandmadeGrimoire(), ShroomBag(), SproutingBranch(), BigShroomBag()
        ).forEach { BaseMod.addRelicToCustomPool(it, AbstractCardEnum.MARISA_COLOR) }
        BaseMod.addRelic(CatCart(), RelicType.SHARED)
        logger.info("Relics editing finished.")
    }

    override fun receiveEditCards() {
        logger.info("adding cards for MARISA")
        cardsToAdd().forEach { card ->
            logger.info("""Adding card : ${card.name}""")
            BaseMod.addCard(card)
        }
        logger.info("done editing cards")
    }

    override fun receivePostExhaust(c: AbstractCard) {
        // TODO Auto-generated method stub
    }

    override fun receivePostBattle(r: AbstractRoom) {
        typhoonCounter = 0
        logger.info("ThMod : PostBattle ; typhoon-counter reset")
    }

    override fun receiveCardUsed(card: AbstractCard) {
        logger.info("""ThMod : Card used : ${card.cardID} ; cost : ${card.costForTurn}""")
        if (card.costForTurn == 0 || card.costForTurn <= -2 || card.costForTurn == -1 && AbstractDungeon.player.energy.energy <= 0) {
            typhoonCounter++
            logger.info("typhoon-counter increased , now :$typhoonCounter")
        }
        if (card.retain) {
            card.retain = false
        }
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

    override fun receivePowersModified() {
        // TODO Auto-generated method stub
    }

    override fun receivePostDungeonInitialize() {
        // TODO Auto-generated method stub
    }

    override fun receivePostDraw(arg0: AbstractCard) {
        // TODO Auto-generated method stub
    }

    override fun receiveEditKeywords() {
        logger.info("Setting up custom keywords")
        val gson = Gson()
        val keywordsPath = "localization/keywords-$langName.json"
        val keywords = gson.fromJson(loadJson(keywordsPath), Keywords::class.java)
        keywords.keywords.forEach { key ->
            logger.info("""Loading keyword : ${key.NAMES[0]}""")
            BaseMod.addKeyword(key.NAMES, key.DESCRIPTION)
        }
        logger.info("Keywords setting finished.")
    }

    override fun receiveEditStrings() {
        logger.info("start editing strings")
        logger.info("lang : $langName")

        listOf(
            RelicStrings::class.java, CardStrings::class.java, PowerStrings::class.java,
            PotionStrings::class.java, EventStrings::class.java
        )
            .map { it to it.simpleName.removeSuffix("Strings").lowercase() + "s" }
            .forEach { (cls, kind) ->
                Gdx.files.internal("localization/$kind-$langName.json")
                    .readString(StandardCharsets.UTF_8.toString())
                    .also { BaseMod.loadCustomStrings(cls, it) }
            }

        logger.info("done editing strings")
    }

    override fun receivePostInitialize() {
        logger.info("Adding badge, configs,event and potion")
        val settingsPanel = ModPanel()

        val (labelText, labelTextBranch) = when (Settings.language) {
            GameLanguage.ZHS -> """使用其他角色时是否开启黑猫事件？""" to """使用原版的树枝"""
            else -> "Enable Black Cat event when playing other characters?" to "Don't replace Dead Branch?"
        }

        val buttonLambda = { button: ModToggleButton ->
            isCatEventEnabled = button.enabled
            try {
                val config = SpireConfig(
                    "MarisaMod", "MarisaModConfig",
                    marisaModDefaultProp
                )
                config.setBool("enablePlaceholder", isCatEventEnabled)
                config.save()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        val enableBlackCatButton = ModLabeledToggleButton(
            labelText,
            350.0f, 700.0f,
            Settings.CREAM_COLOR,
            FontHelper.charDescFont,
            isCatEventEnabled,
            settingsPanel,
            { },
            buttonLambda
        )
        val enableDeadBranchButton = ModLabeledToggleButton(
            labelTextBranch,
            350.0f, 600.0f,
            Settings.CREAM_COLOR,
            FontHelper.charDescFont,
            isDeadBranchEnabled,
            settingsPanel,
            { },
            buttonLambda
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
        val badge = ImageMaster.loadImage(MOD_BADGE)
        BaseMod.registerModBadge(
            badge,
            "MarisaMod",
            "Flynn , Hell , Hohner_257 , Samsara, scarf005",
            "A Mod of the poor blonde girl from Touhou Project(",
            settingsPanel
        )
    }

    internal inner class Keywords {
        lateinit var keywords: Array<Keyword>
    }

    private fun cardsToAdd() = listOf(
        Strike_MRS(), Defend_MRS(), MasterSpark(), UpSweep(), DoubleSpark(), NonDirectionalLaser(),
        LuminesStrike(), MysteriousBeam(), WitchLeyline(), DC(), _6A(), UnstableBomb(), StarBarrage(),
        ShootingEcho(), MachineGunSpark(), DarkSpark(), DeepEcologicalBomb(), MeteoricShower(),
        GravityBeat(), GrandCross(), DragonMeteor(), RefractionSpark(), Robbery(), ChargeUpSpray(),
        AlicesGift(), FairyDestructionRay(), BlazingStar(), ShootTheMoon(), FinalSpark(), JA(),
        AbsoluteMagnitude(), TreasureHunter(), CollectingQuirk(), MilkyWay(), AsteroidBelt(),
        PowerUp(), SporeBomb(), IllusionStar(), EnergyRecoil(), GasGiant(), StarDustReverie(),
        MagicAbsorber(), Occultation(), EarthLightRay(), BlazeAway(), ChargingUp(), DarkMatter(),
        MagicChant(), OneTimeOff(), ManaConvection(), PropBag(), SprinkleStarSeal(), GalacticHalo(),
        SuperPerseids(), PulseMagic(), Orbital(), BigCrunch(), OpenUniverse(), StarlightTyphoon(),
        MaximisePower(), UltraShortWave(), ManaRampage(), BinaryStars(), Acceleration(), WitchOfGreed(),
        SatelliteIllusion(), OortCloud(), OrrerysSun(), EnergyFlow(), EventHorizon(), Singularity(),
        CasketOfStar(), EscapeVelocity(), MillisecondPulsars(), SuperNova(), Spark(), GuidingStar(),
        BlackFlareStar(), WhiteDwarf(), Exhaustion_MRS(), Wraith(),
    )

    companion object {
        @Suppress("MemberVisibilityCanBePrivate")
        lateinit var instance: MarisaMod

        @Suppress("MemberVisibilityCanBePrivate", "unused")
        @JvmStatic
        fun initialize() {
            instance = MarisaMod()
        }

        private val langName
            get() = when (Settings.language) {
                GameLanguage.ZHT -> "zht"
                GameLanguage.ZHS -> "zh"
                GameLanguage.KOR -> "kr"
                GameLanguage.JPN -> "jp"
                GameLanguage.FRA -> "fr"
                else -> "en"
            }


        val logger: Logger = LogManager.getLogger(Marisa::class.simpleName)

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

        var typhoonCounter = 0

        @JvmField
        var isCatEventEnabled = false

        @JvmField
        var isDeadBranchEnabled = false

        /** TODO: it does lots of stuff I cannot understand, split it into multiple parts */
        fun isAmplified(card: AbstractCard, multiplier: Int): Boolean {
            logger.info(
                """ThMod.Amplified : card to check : ${card.cardID} ; costForTurn : ${card.costForTurn}"""
            )
            val p = AbstractDungeon.player

            fun isFree() = p.hasPower(MillisecondPulsarsPower.POWER_ID) || p.hasPower(PulseMagicPower.POWER_ID)
                    || card.freeToPlayOnce || card.purgeOnUse

            fun canPay() = EnergyPanel.totalCount >= card.costForTurn + multiplier

            if (p.hasPower(OneTimeOffPlusPower.POWER_ID) || p.hasPower(OneTimeOffPower.POWER_ID)) {
                logger.info("ThMod.Amplified :OneTimeOff detected,returning false.")
                return false
            }

            val res = when {
                isFree() -> {
                    logger.info(
                        """ThMod.Amplified :Free Amplify tag detected,returning true : Milli :${
                            p.hasPower(MillisecondPulsarsPower.POWER_ID)
                        } ; 
                        |Pulse :${p.hasPower(PulseMagicPower.POWER_ID)} ; 
                        |Free2Play :${card.freeToPlayOnce} ; 
                        |purge on use :${card.purgeOnUse}""".trimMargin()
                    )
                    true
                }

                canPay() -> {
                    logger.info("ThMod.Amplified : Sufficient energy, adding and returning true;")
                    card.costForTurn += multiplier
                    true
                }

                else -> false
            }

//                res = true
//                if (card.costForTurn > 0) {
//                    logger
//                        .info("ThMod.Amplified : False instance of 0 cost card,decreasing typhoon counter.")
//                    typhoonCounter--
//                    logger.info("current Typhoon Counter : $typhoonCounter")
//                }
            if (res) {
                AbstractDungeon.actionManager.addToTop(ApplyPowerAction(p, p, GrandCrossPower(p)))
                p.getPower(EventHorizonPower.POWER_ID)?.onSpecificTrigger()
                p.getRelic(AmplifyWand.ID)?.onTrigger()
            }
            logger.info(
                """ThMod.Amplified : card : ${card.cardID} ; Amplify : $res ; costForTurn : ${card.costForTurn}"""
            )
            return res
        }

        private fun loadJson(jsonPath: String): String =
            Gdx.files.internal(jsonPath).readString(StandardCharsets.UTF_8.toString())

        @JvmStatic
        val randomMarisaCard: AbstractCard
            get() = when (AbstractDungeon.miscRng.random(0, 100)) {
                15 -> GuidingStar()
                else -> AbstractDungeon.returnTrulyRandomCardInCombat().makeCopy()
            }
    }
}
