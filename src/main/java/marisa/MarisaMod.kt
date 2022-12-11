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
import com.megacrit.cardcrawl.rooms.AbstractRoom
import com.megacrit.cardcrawl.ui.panels.EnergyPanel
import marisa.action.SparkCostAction
import marisa.cards.Marisa.*
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
import marisa.powers.Marisa.GrandCrossPower
import marisa.relics.*
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.nio.charset.StandardCharsets
import java.util.*

@SpireInitializer
class MarisaMod : PostExhaustSubscriber, PostBattleSubscriber, PostDungeonInitializeSubscriber, EditCharactersSubscriber,
    PostInitializeSubscriber, EditRelicsSubscriber, EditCardsSubscriber, EditStringsSubscriber, OnCardUseSubscriber,
    EditKeywordsSubscriber, OnPowersModifiedSubscriber, PostDrawSubscriber, PostEnergyRechargeSubscriber {
    private val marisaModDefaultProp = Properties()

    //public static boolean OrinEvent = false;
    private val cardsToAdd = ArrayList<AbstractCard>()

    private val langName = when (Settings.language) {
        GameLanguage.ZHT -> "zht"
        GameLanguage.ZHS -> "zh"
        GameLanguage.KOR -> "ko"
        GameLanguage.JPN -> "jp"
        GameLanguage.FRA -> "fr"
        else -> "en"
    }

    init {
        BaseMod.subscribe(this)
        logger.info("creating the color : MARISA_COLOR")
        BaseMod.addColor(
            AbstractCardEnum.MARISA_COLOR,
            STARLIGHT,
            STARLIGHT,
            STARLIGHT,
            STARLIGHT,
            STARLIGHT,
            STARLIGHT,
            STARLIGHT,
            ATTACK_CC,
            SKILL_CC,
            POWER_CC,
            ENERGY_ORB_CC,
            ATTACK_CC_PORTRAIT,
            SKILL_CC_PORTRAIT,
            POWER_CC_PORTRAIT,
            ENERGY_ORB_CC_PORTRAIT,
            CARD_ENERGY_ORB
        )
        BaseMod.addColor(
            AbstractCardEnum.MARISA_DERIVATIONS,
            STARLIGHT,
            STARLIGHT,
            STARLIGHT,
            STARLIGHT,
            STARLIGHT,
            STARLIGHT,
            STARLIGHT,
            ATTACK_CC,
            SKILL_CC,
            POWER_CC,
            ENERGY_ORB_CC,
            ATTACK_CC_PORTRAIT,
            SKILL_CC_PORTRAIT,
            POWER_CC_PORTRAIT,
            ENERGY_ORB_CC_PORTRAIT,
            CARD_ENERGY_ORB
        )
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
        logger.info("add " + ThModClassEnum.MARISA.toString())
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
        BaseMod.addRelicToCustomPool(
            MiniHakkero(),
            AbstractCardEnum.MARISA_COLOR
        )
        BaseMod.addRelicToCustomPool(
            BewitchedHakkero(),
            AbstractCardEnum.MARISA_COLOR
        )
        BaseMod.addRelicToCustomPool(
            MagicBroom(),
            AbstractCardEnum.MARISA_COLOR
        )
        BaseMod.addRelicToCustomPool(
            AmplifyWand(),
            AbstractCardEnum.MARISA_COLOR
        )
        BaseMod.addRelicToCustomPool(
            ExperimentalFamiliar(),
            AbstractCardEnum.MARISA_COLOR
        )
        BaseMod.addRelicToCustomPool(
            RampagingMagicTools(),
            AbstractCardEnum.MARISA_COLOR
        )
        BaseMod.addRelicToCustomPool(
            BreadOfAWashokuLover(),
            AbstractCardEnum.MARISA_COLOR
        )
        BaseMod.addRelicToCustomPool(
            SimpleLauncher(),
            AbstractCardEnum.MARISA_COLOR
        )
        BaseMod.addRelicToCustomPool(
            HandmadeGrimoire(),
            AbstractCardEnum.MARISA_COLOR
        )
        BaseMod.addRelicToCustomPool(
            ShroomBag(),
            AbstractCardEnum.MARISA_COLOR
        )
        BaseMod.addRelicToCustomPool(
            SproutingBranch(),
            AbstractCardEnum.MARISA_COLOR
        )
        BaseMod.addRelicToCustomPool(
            BigShroomBag(),
            AbstractCardEnum.MARISA_COLOR
        )
        /*
    BaseMod.addRelicToCustomPool(
        new Cape(),
        MARISA_COLOR
    );
*/BaseMod.addRelic(
            CatCart(),
            RelicType.SHARED
        )
        //BaseMod.addRelicToCustomPool(new Cape_1(), AbstractCardEnum.MARISA_COLOR);
        logger.info("Relics editing finished.")
    }

    override fun receiveEditCards() {
        logger.info("starting editing cards")
        loadCardsToAdd()
        logger.info("adding cards for MARISA")
        for (card in cardsToAdd) {
            logger.info("Adding card : " + card.name)
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
        logger.info("ThMod : Card used : " + card.cardID + " ; cost : " + card.costForTurn)
        if (card.costForTurn == 0 || card.costForTurn <= -2 || card.costForTurn == -1 && AbstractDungeon.player.energy.energy <= 0) {
            typhoonCounter++
            logger.info("typhoon-counter increased , now :$typhoonCounter")
        }
        if (card.retain) {
            card.retain = false
        }
        if (card.hasTag(CardTagEnum.SPARK)) {
            AbstractDungeon.actionManager.addToTop(
                SparkCostAction()
            )
        }
    }

    override fun receivePostEnergyRecharge() {
        if (!AbstractDungeon.player.hand.isEmpty) {
            for (c in AbstractDungeon.player.hand.group) {
                if (c is GuidingStar) {
                    AbstractDungeon.actionManager.addToBottom(
                        GainEnergyAction(1)
                    )
                    c.flash()
                }
            }
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
        val keywordsPath = "localization/ThMod_MRS_keywords-$langName.json"
        val keywords = gson.fromJson(loadJson(keywordsPath), Keywords::class.java)
        for (key in keywords.keywords) {
            logger.info("""Loading keyword : ${key.NAMES[0]}""")
            BaseMod.addKeyword(key.NAMES, key.DESCRIPTION)
        }
        logger.info("Keywords setting finished.")
    }

    override fun receiveEditStrings() {
        logger.info("start editing strings")
        logger.info("lang : $langName")

        val relicStrings: String
        val cardStrings: String
        val powerStrings: String
        val potionStrings: String
        val eventStrings: String
        val relic = "localization/relics-$langName.json"
        val card = "localization/cards-$langName.json"
        val power = "localization/powers-$langName.json"
        val potion = "localization/potions-$langName.json"
        val event = "localization/events-$langName.json"

        relicStrings = Gdx.files.internal(relic).readString(StandardCharsets.UTF_8.toString())
        BaseMod.loadCustomStrings(RelicStrings::class.java, relicStrings)
        cardStrings = Gdx.files.internal(card).readString(StandardCharsets.UTF_8.toString())
        BaseMod.loadCustomStrings(CardStrings::class.java, cardStrings)
        powerStrings = Gdx.files.internal(power).readString(StandardCharsets.UTF_8.toString())
        BaseMod.loadCustomStrings(PowerStrings::class.java, powerStrings)
        potionStrings = Gdx.files.internal(potion).readString(StandardCharsets.UTF_8.toString())
        BaseMod.loadCustomStrings(PotionStrings::class.java, potionStrings)
        eventStrings = Gdx.files.internal(event).readString(StandardCharsets.UTF_8.toString())
        BaseMod.loadCustomStrings(EventStrings::class.java, eventStrings)

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
            350.0f,
            700.0f,
            Settings.CREAM_COLOR,
            FontHelper.charDescFont,
            isCatEventEnabled,
            settingsPanel,
            {  },
            buttonLambda)
        val enableDeadBranchButton = ModLabeledToggleButton(
            labelTextBranch,
            350.0f,
            600.0f,
            Settings.CREAM_COLOR,
            FontHelper.charDescFont,
            isDeadBranchEnabled,
            settingsPanel,
            {  },
            buttonLambda)
        settingsPanel.addUIElement(enableBlackCatButton)
        settingsPanel.addUIElement(enableDeadBranchButton)
        BaseMod.addEvent(Mushrooms_MRS.ID, Mushrooms_MRS::class.java, Exordium.ID)
        BaseMod.addEvent(OrinTheCat.ID, OrinTheCat::class.java, TheBeyond.ID)
        /*
    //BaseMod.addEvent(TestEvent.ID, TestEvent.class);
    BaseMod.addEvent(TestEvent.ID, TestEvent.class, Exordium.ID);
    BaseMod.addEvent(TestEvent.ID, TestEvent.class, TheBeyond.ID);
    BaseMod.addEvent(TestEvent.ID, TestEvent.class, TheCity.ID);
*/BaseMod.addPotion(
            ShroomBrew::class.java,
            Color.NAVY.cpy(),
            Color.LIME.cpy(),
            Color.OLIVE,
            "ShroomBrew",
            ThModClassEnum.MARISA
        )
        BaseMod.addPotion(
            StarNLove::class.java,
            Color.BLUE.cpy(),
            Color.YELLOW.cpy(),
            Color.NAVY,
            "StarNLove",
            ThModClassEnum.MARISA
        )
        BaseMod.addPotion(
            BottledSpark::class.java,
            Color.BLUE.cpy(),
            Color.YELLOW.cpy(),
            Color.NAVY,
            "BottledSpark",
            ThModClassEnum.MARISA
        )
        /*
    String orin, zombieFairy;
    switch (Settings.language) {
      case ZHS:
        orin = ORIN_ENCOUNTER_ZHS;
        zombieFairy = ZOMBIE_FAIRY_ENC_ZHS;
        break;
      default:
        orin = ORIN_ENCOUNTER;
        zombieFairy = ZOMBIE_FAIRY_ENC;
        break;
    }
    */BaseMod.addMonster(ORIN_ENCOUNTER, GetMonster { Orin() })
        BaseMod.addMonster(ZOMBIE_FAIRY_ENC, GetMonster { ZombieFairy() })
        val badge = ImageMaster.loadImage(MOD_BADGE)
        BaseMod.registerModBadge(
            badge,
            "MarisaMod",
            "Flynn , Hell , Hohner_257 , Samsara",
            "A Mod of the poor blonde girl from Touhou Project(",
            settingsPanel
        )
    }

    private fun loadCardsToAdd() {
        cardsToAdd.clear()
        cardsToAdd.add(Strike_MRS())
        cardsToAdd.add(Defend_MRS())
        cardsToAdd.add(MasterSpark())
        cardsToAdd.add(UpSweep())
        cardsToAdd.add(DoubleSpark())
        cardsToAdd.add(NonDirectionalLaser())
        cardsToAdd.add(LuminesStrike())
        cardsToAdd.add(MysteriousBeam())
        cardsToAdd.add(WitchLeyline())
        cardsToAdd.add(DC())
        cardsToAdd.add(_6A())
        cardsToAdd.add(UnstableBomb())
        cardsToAdd.add(StarBarrage())
        cardsToAdd.add(ShootingEcho())
        cardsToAdd.add(MachineGunSpark())
        cardsToAdd.add(DarkSpark())
        cardsToAdd.add(DeepEcologicalBomb())
        cardsToAdd.add(MeteoricShower())
        cardsToAdd.add(GravityBeat())
        cardsToAdd.add(GrandCross())
        cardsToAdd.add(DragonMeteor())
        cardsToAdd.add(RefractionSpark())
        cardsToAdd.add(Robbery())
        cardsToAdd.add(ChargeUpSpray())
        cardsToAdd.add(AlicesGift())
        cardsToAdd.add(FairyDestructionRay())
        cardsToAdd.add(BlazingStar())
        cardsToAdd.add(ShootTheMoon())
        cardsToAdd.add(FinalSpark())
        cardsToAdd.add(JA())
        cardsToAdd.add(AbsoluteMagnitude())
        cardsToAdd.add(TreasureHunter())
        cardsToAdd.add(CollectingQuirk())
        cardsToAdd.add(MilkyWay())
        cardsToAdd.add(AsteroidBelt())
        cardsToAdd.add(PowerUp())
        cardsToAdd.add(SporeBomb())
        cardsToAdd.add(IllusionStar())
        cardsToAdd.add(EnergyRecoil())
        cardsToAdd.add(GasGiant())
        cardsToAdd.add(StarDustReverie())
        cardsToAdd.add(MagicAbsorber())
        cardsToAdd.add(Occultation())
        cardsToAdd.add(EarthLightRay())
        cardsToAdd.add(BlazeAway())
        cardsToAdd.add(ChargingUp())
        cardsToAdd.add(DarkMatter())
        cardsToAdd.add(MagicChant())
        cardsToAdd.add(OneTimeOff())
        cardsToAdd.add(ManaConvection())
        cardsToAdd.add(PropBag())
        cardsToAdd.add(SprinkleStarSeal())
        cardsToAdd.add(GalacticHalo())
        cardsToAdd.add(SuperPerseids())
        cardsToAdd.add(PulseMagic())
        cardsToAdd.add(Orbital())
        cardsToAdd.add(BigCrunch())
        cardsToAdd.add(OpenUniverse())
        cardsToAdd.add(StarlightTyphoon())
        cardsToAdd.add(MaximisePower())
        cardsToAdd.add(UltraShortWave())
        cardsToAdd.add(ManaRampage())
        cardsToAdd.add(BinaryStars())
        cardsToAdd.add(Acceleration())
        cardsToAdd.add(WitchOfGreed())
        cardsToAdd.add(SatelliteIllusion())
        cardsToAdd.add(OortCloud())
        cardsToAdd.add(OrrerysSun())
        cardsToAdd.add(EnergyFlow())
        cardsToAdd.add(EventHorizon())
        cardsToAdd.add(Singularity())
        cardsToAdd.add(CasketOfStar())
        cardsToAdd.add(EscapeVelocity())
        cardsToAdd.add(MillisecondPulsars())
        cardsToAdd.add(SuperNova())
        cardsToAdd.add(Spark())
        cardsToAdd.add(GuidingStar())
        cardsToAdd.add(BlackFlareStar())
        cardsToAdd.add(WhiteDwarf())
        cardsToAdd.add(Exhaustion_MRS())
        cardsToAdd.add(Strike_MRS())
        cardsToAdd.add(Wraith())
        //cardsToAdd.add(new PolarisUnique());
    }

    @Suppress("unused")
    fun initialize() {
        MarisaMod()
    }
    internal inner class Keywords {
        lateinit var keywords: Array<Keyword>
    }

    companion object {
        @JvmField
        val logger: Logger = LogManager.getLogger(Marisa::class.java.name)
        private const val ORIN_ENCOUNTER = "Orin"

        //private static final String ORIN_ENCOUNTER_ZHS = "\u963f\u71d0";
        private const val ZOMBIE_FAIRY_ENC = "ZombieFairy"

        //private static final String ZOMBIE_FAIRY_ENC_ZHS = "\u50f5\u5c38\u5996\u7cbe";
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

        //private ArrayList<AbstractRelic> relicsToAdd = new ArrayList<>();
        /*
  //For Spark Themed cards
  public static boolean isSpark(AbstractCard card) {
    return (
        (card.cardID.equals("Spark")) ||
            (card.cardID.equals("DarkSpark")) ||
            (card.cardID.equals("Strike_MRS")) ||
            (card.cardID.equals("FinalSpark")) ||
            (card.cardID.equals("DoubleSpark")) ||
            (card.cardID.equals("RefractionSpark")) ||
            (card.cardID.equals("MachineGunSpark")) ||
            (card.cardID.equals("MasterSpark"))
    );
  }*/
        //For the FXXKING Exhaustion curse
        /*
  public static boolean ExhaustionCheck() {
    boolean res = false;
    for (AbstractCard c : AbstractDungeon.player.hand.group) {
      if (c instanceof Exhaustion_MRS) {
        res = true;
      }
    }
    return res;
  }
*/
        //For Amplify cards
        fun isAmplified(card: AbstractCard, multiplier: Int): Boolean {
            logger.info(
                """ThMod.Amplified : card to check : ${card.cardID} ; costForTurn : ${card.costForTurn}"""
            )
            val p = AbstractDungeon.player
            if (p.hasPower("OneTimeOffPlusPower") || p.hasPower("OneTimeOffPower")) {
                logger.info("ThMod.Amplified :OneTimeOff detected,returning false.")
                return false
            }
            var res = false
            if (p.hasPower("MilliPulsePower") || p.hasPower("PulseMagicPower")
                || card.freeToPlayOnce || card.purgeOnUse
            ) {
                logger.info(
                    """ThMod.Amplified :Free Amplify tag detected,returning true : Milli :${p.hasPower("MilliPulsePower")} ; Pulse :${
                        p.hasPower(
                            "PulseMagicPower"
                        )
                    } ; Free2Play :${card.freeToPlayOnce} ; purge on use :${card.purgeOnUse}"""
                )
                res = true
            } else {
                if (EnergyPanel.totalCount >= card.costForTurn + multiplier) {
                    logger.info("ThMod.Amplified : Sufficient energy ,adding and returning true;")
                    card.costForTurn += multiplier
                    res = true
                    if (card.costForTurn > 0) {
                        logger
                            .info("ThMod.Amplified : False instance of 0 cost card,decreasing typhoon counter.")
                        typhoonCounter--
                        logger.info("current Typhoon Counter : $typhoonCounter")
                    }
                }
            }
            if (res) {
                AbstractDungeon.actionManager.addToTop(
                    ApplyPowerAction(
                        p,
                        p,
                        GrandCrossPower(p)
                    )
                )
                if (p.hasPower("EventHorizonPower")) {
                    p.getPower("EventHorizonPower").onSpecificTrigger()
                }
                if (p.hasRelic("AmplifyWand")) {
                    val r = p.getRelic("AmplifyWand")
                    r.onTrigger()
                }
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