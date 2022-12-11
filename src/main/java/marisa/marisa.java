package marisa;

import static marisa.patches.AbstractCardEnum.MARISA_COLOR;
import static marisa.patches.CardTagEnum.SPARK;
import static marisa.patches.ThModClassEnum.MARISA;

import marisa.action.SparkCostAction;
import marisa.cards.Marisa.AbsoluteMagnitude;
import marisa.cards.Marisa.Acceleration;
import marisa.cards.Marisa.AlicesGift;
import marisa.cards.Marisa.AsteroidBelt;
import marisa.cards.Marisa.BigCrunch;
import marisa.cards.Marisa.BinaryStars;
import marisa.cards.Marisa.BlazeAway;
import marisa.cards.Marisa.BlazingStar;
import marisa.cards.Marisa.CasketOfStar;
import marisa.cards.Marisa.ChargeUpSpray;
import marisa.cards.Marisa.ChargingUp;
import marisa.cards.Marisa.CollectingQuirk;
import marisa.cards.Marisa.DC;
import marisa.cards.Marisa.DarkMatter;
import marisa.cards.Marisa.DarkSpark;
import marisa.cards.Marisa.DeepEcologicalBomb;
import marisa.cards.Marisa.Defend_MRS;
import marisa.cards.Marisa.DoubleSpark;
import marisa.cards.Marisa.DragonMeteor;
import marisa.cards.Marisa.EarthLightRay;
import marisa.cards.Marisa.EnergyFlow;
import marisa.cards.Marisa.EnergyRecoil;
import marisa.cards.Marisa.EscapeVelocity;
import marisa.cards.Marisa.EventHorizon;
import marisa.cards.Marisa.FairyDestructionRay;
import marisa.cards.Marisa.FinalSpark;
import marisa.cards.Marisa.GalacticHalo;
import marisa.cards.Marisa.GasGiant;
import marisa.cards.Marisa.GrandCross;
import marisa.cards.Marisa.GravityBeat;
import marisa.cards.Marisa.IllusionStar;
import marisa.cards.Marisa.JA;
import marisa.cards.Marisa.LuminesStrike;
import marisa.cards.Marisa.MachineGunSpark;
import marisa.cards.Marisa.MagicAbsorber;
import marisa.cards.Marisa.MagicChant;
import marisa.cards.Marisa.ManaConvection;
import marisa.cards.Marisa.ManaRampage;
import marisa.cards.Marisa.MasterSpark;
import marisa.cards.Marisa.MaximisePower;
import marisa.cards.Marisa.MeteoricShower;
import marisa.cards.Marisa.MilkyWay;
import marisa.cards.Marisa.MillisecondPulsars;
import marisa.cards.Marisa.MysteriousBeam;
import marisa.cards.Marisa.NonDirectionalLaser;
import marisa.cards.Marisa.Occultation;
import marisa.cards.Marisa.OneTimeOff;
import marisa.cards.Marisa.OortCloud;
import marisa.cards.Marisa.OpenUniverse;
import marisa.cards.Marisa.Orbital;
import marisa.cards.Marisa.OrrerysSun;
import marisa.cards.Marisa.PowerUp;
import marisa.cards.Marisa.PropBag;
import marisa.cards.Marisa.PulseMagic;
import marisa.cards.Marisa.RefractionSpark;
import marisa.cards.Marisa.Robbery;
import marisa.cards.Marisa.SatelliteIllusion;
import marisa.cards.Marisa.ShootTheMoon;
import marisa.cards.Marisa.ShootingEcho;
import marisa.cards.Marisa.Singularity;
import marisa.cards.Marisa.SporeBomb;
import marisa.cards.Marisa.SprinkleStarSeal;
import marisa.cards.Marisa.StarBarrage;
import marisa.cards.Marisa.StarDustReverie;
import marisa.cards.Marisa.StarlightTyphoon;
import marisa.cards.Marisa.Strike_MRS;
import marisa.cards.Marisa.SuperNova;
import marisa.cards.Marisa.SuperPerseids;
import marisa.cards.Marisa.TreasureHunter;
import marisa.cards.Marisa.UltraShortWave;
import marisa.cards.Marisa.UnstableBomb;
import marisa.cards.Marisa.UpSweep;
import marisa.cards.Marisa.WitchLeyline;
import marisa.cards.Marisa.WitchOfGreed;
import marisa.cards.Marisa._6A;
import marisa.cards.derivations.BlackFlareStar;
import marisa.cards.derivations.Exhaustion_MRS;
import marisa.cards.derivations.GuidingStar;
import marisa.cards.derivations.Spark;
import marisa.cards.derivations.WhiteDwarf;
import marisa.cards.derivations.Wraith;
import marisa.characters.Marisa;
import marisa.event.Mushrooms_MRS;
import marisa.event.OrinTheCat;
import marisa.monsters.Orin;
import marisa.monsters.ZombieFairy;
import marisa.patches.AbstractCardEnum;
import marisa.potions.BottledSpark;
import marisa.potions.ShroomBrew;
import marisa.potions.StarNLove;
import marisa.powers.Marisa.GrandCrossPower;
import marisa.relics.AmplifyWand;
import marisa.relics.BewitchedHakkero;
import marisa.relics.BigShroomBag;
import marisa.relics.BreadOfAWashokuLover;
import marisa.relics.CatCart;
import marisa.relics.ExperimentalFamiliar;
import marisa.relics.HandmadeGrimoire;
import marisa.relics.MagicBroom;
import marisa.relics.MiniHakkero;
import marisa.relics.RampagingMagicTools;
import marisa.relics.ShroomBag;
import marisa.relics.SimpleLauncher;
import marisa.relics.SproutingBranch;
import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.helpers.RelicType;
import basemod.interfaces.EditCardsSubscriber;
import basemod.interfaces.EditCharactersSubscriber;
import basemod.interfaces.EditKeywordsSubscriber;
import basemod.interfaces.EditRelicsSubscriber;
import basemod.interfaces.EditStringsSubscriber;
import basemod.interfaces.OnCardUseSubscriber;
import basemod.interfaces.OnPowersModifiedSubscriber;
import basemod.interfaces.PostBattleSubscriber;
import basemod.interfaces.PostDrawSubscriber;
import basemod.interfaces.PostDungeonInitializeSubscriber;
import basemod.interfaces.PostEnergyRechargeSubscriber;
import basemod.interfaces.PostExhaustSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.core.Settings.GameLanguage;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.Exordium;
import com.megacrit.cardcrawl.dungeons.TheBeyond;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.Keyword;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("Duplicates")
@SpireInitializer
public class marisa implements PostExhaustSubscriber,
    PostBattleSubscriber,
    PostDungeonInitializeSubscriber,
    EditCharactersSubscriber,
    PostInitializeSubscriber,
    EditRelicsSubscriber,
    EditCardsSubscriber,
    EditStringsSubscriber,
    OnCardUseSubscriber,
    EditKeywordsSubscriber,
    OnPowersModifiedSubscriber,
    PostDrawSubscriber,
    PostEnergyRechargeSubscriber {

  public static final Logger logger = LogManager.getLogger(marisa.class.getName());

  private static final String ORIN_ENCOUNTER = "Orin";
  //private static final String ORIN_ENCOUNTER_ZHS = "\u963f\u71d0";
  private static final String ZOMBIE_FAIRY_ENC = "ZombieFairy";
  //private static final String ZOMBIE_FAIRY_ENC_ZHS = "\u50f5\u5c38\u5996\u7cbe";
  private static final String MOD_BADGE = "img/UI/badge.png";

  //card backgrounds
  private static final String ATTACK_CC = "img/512/bg_attack_MRS_s.png";
  private static final String SKILL_CC = "img/512/bg_skill_MRS_s.png";
  private static final String POWER_CC = "img/512/bg_power_MRS_s.png";
  private static final String ENERGY_ORB_CC = "img/512/cardOrb.png";

  private static final String ATTACK_CC_PORTRAIT = "img/1024/bg_attack_MRS.png";
  private static final String SKILL_CC_PORTRAIT = "img/1024/bg_skill_MRS.png";
  private static final String POWER_CC_PORTRAIT = "img/1024/bg_power_MRS.png";
  private static final String ENERGY_ORB_CC_PORTRAIT = "img/1024/cardOrb.png";

  public static final Color STARLIGHT = CardHelper.getColor(0, 10, 125);
  public static final String CARD_ENERGY_ORB = "img/UI/energyOrb.png";

  private static final String MY_CHARACTER_BUTTON = "img/charSelect/MarisaButton.png";
  private static final String MARISA_PORTRAIT = "img/charSelect/marisaPortrait.jpg";

  private static final String CARD_STRING = "localization/ThMod_Fnh_cards.json";
  private static final String CARD_STRING_FR = "localization/ThMod_Fnh_cards-fr.json";
  private static final String CARD_STRING_JP = "localization/ThMod_Fnh_cards-jp.json";
  private static final String CARD_STRING_ZH = "localization/ThMod_Fnh_cards-zh.json";
  private static final String CARD_STRING_ZHT = "localization/ThMod_Fnh_cards-zht.json";
  private static final String CARD_STRING_KR = "localization/ThMod_Fnh_cards-kr.json";
  private static final String RELIC_STRING = "localization/ThMod_Fnh_relics.json";
  private static final String RELIC_STRING_FR = "localization/ThMod_Fnh_relics-fr.json";
  private static final String RELIC_STRING_JP = "localization/ThMod_Fnh_relics-jp.json";
  private static final String RELIC_STRING_ZH = "localization/ThMod_Fnh_relics-zh.json";
  private static final String RELIC_STRING_ZHT = "localization/ThMod_Fnh_relics-zht.json";
  private static final String RELIC_STRING_KR = "localization/ThMod_Fnh_relics-kr.json";
  private static final String POWER_STRING = "localization/ThMod_Fnh_powers.json";
  private static final String POWER_STRING_FR = "localization/ThMod_Fnh_powers-fr.json";
  private static final String POWER_STRING_JP = "localization/ThMod_Fnh_powers-jp.json";
  private static final String POWER_STRING_ZH = "localization/ThMod_Fnh_powers-zh.json";
  private static final String POWER_STRING_ZHT = "localization/ThMod_Fnh_powers-zht.json";
  private static final String POWER_STRING_KR = "localization/ThMod_Fnh_powers-kr.json";
  private static final String POTION_STRING = "localization/ThMod_MRS_potions.json";
  private static final String POTION_STRING_FR = "localization/ThMod_MRS_potions-fr.json";
  private static final String POTION_STRING_JP = "localization/ThMod_MRS_potions-jp.json";
  private static final String POTION_STRING_ZH = "localization/ThMod_MRS_potions-zh.json";
  private static final String POTION_STRING_ZHT = "localization/ThMod_MRS_potions-zht.json";
  private static final String POTION_STRING_KR = "localization/ThMod_MRS_potions-kr.json";
  private static final String KEYWORD_STRING = "localization/ThMod_MRS_keywords.json";
  private static final String KEYWORD_STRING_FR = "localization/ThMod_MRS_keywords-fr.json";
  private static final String KEYWORD_STRING_JP = "localization/ThMod_MRS_keywords-jp.json";
  private static final String KEYWORD_STRING_KR = "localization/ThMod_MRS_keywords-kr.json";
  private static final String KEYWORD_STRING_ZHS = "localization/ThMod_MRS_keywords-zh.json";
  private static final String KEYWORD_STRING_ZHT = "localization/ThMod_MRS_keywords-zht.json";
  private static final String EVENT_PATH = "localization/ThMod_MRS_events.json";
  private static final String EVENT_PATH_KR = "localization/ThMod_MRS_events-kr.json";
  private static final String EVENT_PATH_ZHS = "localization/ThMod_MRS_events-zh.json";
  private static final String EVENT_PATH_ZHT = "localization/ThMod_MRS_events-zht.json";

  public static int typhoonCounter = 0;

  public static boolean isCatEventEnabled;
  public static boolean isDeadBranchEnabled;
  private Properties marisaModDefaultProp = new Properties();

  //public static boolean OrinEvent = false;

  private ArrayList<AbstractCard> cardsToAdd = new ArrayList<>();
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
  public static boolean Amplified(AbstractCard card, int AMP) {
    logger.info(
        "ThMod.Amplified : card to check : "
            + card.cardID
            + " ; costForTurn : "
            + card.costForTurn
    );
    AbstractPlayer p = AbstractDungeon.player;
    if ((p.hasPower("OneTimeOffPlusPower")) || (p.hasPower("OneTimeOffPower"))) {
      logger.info("ThMod.Amplified :OneTimeOff detected,returning false.");
      return false;
    }

    boolean res = false;
    if ((p.hasPower("MilliPulsaPower")) || (p.hasPower("PulseMagicPower"))
        || (card.freeToPlayOnce) || (card.purgeOnUse)) {
      logger.info(
          "ThMod.Amplified :Free Amplify tag detected,returning true : Milli :"
              + (p.hasPower("MilliPulsaPower"))
              + " ; Pulse :"
              + (p.hasPower("PulseMagicPower"))
              + " ; Free2Play :"
              + card.freeToPlayOnce
              + " ; purge on use :"
              + card.purgeOnUse
      );
      res = true;
    } else {
      if (EnergyPanel.totalCount >= (card.costForTurn + AMP)) {
        logger.info("ThMod.Amplified : Sufficient energy ,adding and returning true;");
        card.costForTurn += AMP;
        res = true;
        if (card.costForTurn > 0) {
          logger
              .info("ThMod.Amplified : False instance of 0 cost card,decreasing typhoon counter.");
          typhoonCounter--;
          logger.info("current Typhoon Counter : " + typhoonCounter);
        }
      }
    }

    if (res) {
      AbstractDungeon.actionManager.addToTop(
          new ApplyPowerAction(
              p,
              p,
              new GrandCrossPower(p)
          )
      );
      if (p.hasPower("EventHorizonPower")) {
        p.getPower("EventHorizonPower").onSpecificTrigger();
      }
      if (p.hasRelic("AmplifyWand")) {
        AbstractRelic r = p.getRelic("AmplifyWand");
        r.onTrigger();
      }
    }
    logger.info(
        "ThMod.Amplified : card : " + card.cardID + " ; Amplify : " + res + " ; costForTurn : "
            + card.costForTurn);
    return res;
  }

  public marisa() {
    BaseMod.subscribe(this);
    logger.info("creating the color : MARISA_COLOR");
    BaseMod.addColor(
        MARISA_COLOR,
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
    );
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
    );
    marisaModDefaultProp.setProperty("isCatEventEnabled", "TRUE");
    try {
      final SpireConfig config = new SpireConfig("vexMod", "vexModConfig", marisaModDefaultProp);
      config.load();
      isCatEventEnabled = config.getBool("isCatEventEnabled");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void receiveEditCharacters() {
    logger.info("begin editing characters");

    logger.info("add " + MARISA.toString());
    BaseMod.addCharacter(
        new Marisa("Marisa"),
        MY_CHARACTER_BUTTON,
        MARISA_PORTRAIT,
        MARISA
    );
    logger.info("done editing characters");
  }

  public void receiveEditRelics() {
    logger.info("Begin editing relics.");

    BaseMod.addRelicToCustomPool(
        new MiniHakkero(),
        MARISA_COLOR
    );
    BaseMod.addRelicToCustomPool(
        new BewitchedHakkero(),
        MARISA_COLOR
    );
    BaseMod.addRelicToCustomPool(
        new MagicBroom(),
        MARISA_COLOR
    );
    BaseMod.addRelicToCustomPool(
        new AmplifyWand(),
        MARISA_COLOR
    );
    BaseMod.addRelicToCustomPool(
        new ExperimentalFamiliar(),
        MARISA_COLOR
    );
    BaseMod.addRelicToCustomPool(
        new RampagingMagicTools(),
        MARISA_COLOR
    );
    BaseMod.addRelicToCustomPool(
        new BreadOfAWashokuLover(),
        MARISA_COLOR
    );
    BaseMod.addRelicToCustomPool(
        new SimpleLauncher(),
        MARISA_COLOR
    );
    BaseMod.addRelicToCustomPool(
        new HandmadeGrimoire(),
        MARISA_COLOR
    );
    BaseMod.addRelicToCustomPool(
        new ShroomBag(),
        MARISA_COLOR
    );
    BaseMod.addRelicToCustomPool(
        new SproutingBranch(),
        MARISA_COLOR
    );
    BaseMod.addRelicToCustomPool(
        new BigShroomBag(),
        MARISA_COLOR
    );
/*
    BaseMod.addRelicToCustomPool(
        new Cape(),
        MARISA_COLOR
    );
*/
    BaseMod.addRelic(
        new CatCart(),
        RelicType.SHARED
    );
    //BaseMod.addRelicToCustomPool(new Cape_1(), AbstractCardEnum.MARISA_COLOR);

    logger.info("Relics editing finished.");
  }

  public void receiveEditCards() {
    logger.info("starting editing cards");

    loadCardsToAdd();

    logger.info("adding cards for MARISA");

    for (AbstractCard card : cardsToAdd) {
      logger.info("Adding card : " + card.name);
      BaseMod.addCard(card);
    }

    logger.info("done editing cards");
  }

  public static void initialize() {
    new marisa();
  }

  @Override
  public void receivePostExhaust(AbstractCard c) {
    // TODO Auto-generated method stub
  }

  @Override
  public void receivePostBattle(AbstractRoom r) {
    typhoonCounter = 0;
    logger.info("ThMod : PostBattle ; typhoon-counter reset");
  }

  @Override
  public void receiveCardUsed(AbstractCard card) {
    marisa.logger.info("ThMod : Card used : " + card.cardID + " ; cost : " + card.costForTurn);
    if (
        (card.costForTurn == 0) ||
            (card.costForTurn <= -2) ||
            ((card.costForTurn == -1) && (AbstractDungeon.player.energy.energy <= 0))
    ) {
      typhoonCounter++;
      marisa.logger.info("typhoon-counter increased , now :" + typhoonCounter);
    }
    if (card.retain) {
      card.retain = false;
    }
    if (card.hasTag(SPARK)) {
      AbstractDungeon.actionManager.addToTop(
          new SparkCostAction()
      );
    }
  }

  @Override
  public void receivePostEnergyRecharge() {
    if (!AbstractDungeon.player.hand.isEmpty()) {
      for (AbstractCard c : AbstractDungeon.player.hand.group) {
        if (c instanceof GuidingStar) {
          AbstractDungeon.actionManager.addToBottom(
              new GainEnergyAction(1)
          );
          c.flash();
        }
      }
    }
  }

  @Override
  public void receivePowersModified() {
    // TODO Auto-generated method stub

  }

  @Override
  public void receivePostDungeonInitialize() {
    // TODO Auto-generated method stub
  }

  @Override
  public void receivePostDraw(AbstractCard arg0) {
    // TODO Auto-generated method stub
  }

  private static String loadJson(String jsonPath) {
    return Gdx.files.internal(jsonPath).readString(String.valueOf(StandardCharsets.UTF_8));
  }

  @Override
  public void receiveEditKeywords() {
    logger.info("Setting up custom keywords");

    String keywordsPath;
    switch (Settings.language) {
      case ZHT:
        keywordsPath = KEYWORD_STRING_ZHT;
        break;
      case ZHS:
        keywordsPath = KEYWORD_STRING_ZHS;
        break;
      case KOR:
        keywordsPath = KEYWORD_STRING_KR;
        break;
      case JPN:
        keywordsPath = KEYWORD_STRING_JP;
        break;
      case FRA:
        keywordsPath = KEYWORD_STRING_FR;
        break;
      default:
        keywordsPath = KEYWORD_STRING;
        break;
    }

    Gson gson = new Gson();
    Keywords keywords;
    keywords = gson.fromJson(loadJson(keywordsPath), Keywords.class);
    for (Keyword key : keywords.keywords) {
      logger.info("Loading keyword : " + key.NAMES[0]);
      BaseMod.addKeyword(key.NAMES, key.DESCRIPTION);
    }
    logger.info("Keywords setting finished.");
  }

  @Override
  public void receiveEditStrings() {
    logger.info("start editing strings");

    String relicStrings,
        cardStrings,
        powerStrings,
        potionStrings,
        eventStrings,
        relic,
        card,
        power,
        potion,
        event;

    if (Settings.language == Settings.GameLanguage.ZHS) {
      logger.info("lang == zhs");
      card = CARD_STRING_ZH;
      relic = RELIC_STRING_ZH;
      power = POWER_STRING_ZH;
      potion = POTION_STRING_ZH;
      event = EVENT_PATH_ZHS;
    } else if (Settings.language == Settings.GameLanguage.JPN) {
      logger.info("lang == jpn");
      card = CARD_STRING_JP;
      relic = RELIC_STRING_JP;
      power = POWER_STRING_JP;
      potion = POTION_STRING_JP;
      event = EVENT_PATH;
    } else if (Settings.language == Settings.GameLanguage.ZHT) {
      logger.info("lang == zht");
      card = CARD_STRING_ZHT;
      relic = RELIC_STRING_ZHT;
      power = POWER_STRING_ZHT;
      potion = POTION_STRING_ZHT;
      event = EVENT_PATH_ZHT;
    } else if (Settings.language == Settings.GameLanguage.KOR) {
      logger.info("lang == kor");
      card = CARD_STRING_KR;
      relic = RELIC_STRING_KR;
      power = POWER_STRING_KR;
      potion = POTION_STRING_KR;
      event = EVENT_PATH_KR;
    } else if (Settings.language == Settings.GameLanguage.FRA) {
      logger.info("lang == fra");
      card = CARD_STRING_FR;
      relic = RELIC_STRING_FR;
      power = POWER_STRING_FR;
      potion = POTION_STRING_FR;
      event = EVENT_PATH;
    } else {
      logger.info("lang == eng");
      card = CARD_STRING;
      relic = RELIC_STRING;
      power = POWER_STRING;
      potion = POTION_STRING;
      event = EVENT_PATH;
    }

    relicStrings = Gdx.files.internal(relic).readString(
        String.valueOf(StandardCharsets.UTF_8)
    );
    BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);
    cardStrings = Gdx.files.internal(card).readString(
        String.valueOf(StandardCharsets.UTF_8)
    );
    BaseMod.loadCustomStrings(CardStrings.class, cardStrings);
    powerStrings = Gdx.files.internal(power).readString(
        String.valueOf(StandardCharsets.UTF_8)
    );
    BaseMod.loadCustomStrings(PowerStrings.class, powerStrings);
    potionStrings = Gdx.files.internal(potion).readString(
        String.valueOf(StandardCharsets.UTF_8)
    );
    BaseMod.loadCustomStrings(PotionStrings.class, potionStrings);
    eventStrings = Gdx.files.internal(event).readString(
        String.valueOf(StandardCharsets.UTF_8)
    );
    BaseMod.loadCustomStrings(EventStrings.class, eventStrings);
    logger.info("done editing strings");
  }

  @Override
  public void receivePostInitialize() {

    logger.info("Adding badge, configs,event and potion");

    final ModPanel settingsPanel = new ModPanel();
    String labelText;
    if (Settings.language == Settings.GameLanguage.ZHS) {
      labelText = "\u4f7f\u7528\u5176\u4ed6\u89d2\u8272\u65f6\u662f\u5426\u5f00\u542f\u9ed1\u732b\u4e8b\u4ef6\uff1f";
    } else {
      labelText = "Enable Black Cat event when playing other characters?";
    }
    String labelText_branch;
    if (Settings.language == GameLanguage.ZHS) {
      labelText_branch = "\u4f7f\u7528\u539f\u7248\u7684\u6811\u679d";
    } else {
      labelText_branch = "Don't replace Dead Branch?";
    }
    final ModLabeledToggleButton enableBlackCatButton =
        new ModLabeledToggleButton(
            labelText,
            350.0f,
            700.0f,
            Settings.CREAM_COLOR,
            FontHelper.charDescFont,
            isCatEventEnabled,
            settingsPanel,
            label -> {
            },
            button -> {
              isCatEventEnabled = button.enabled;
              try {
                final SpireConfig config = new SpireConfig("MarisaMod", "MarisaModCongfig",
                    marisaModDefaultProp);
                config.setBool("enablePlaceholder", isCatEventEnabled);
                config.save();
              } catch (Exception e) {
                e.printStackTrace();
              }
            });

    final ModLabeledToggleButton enableDeadBranchButton =
        new ModLabeledToggleButton(
            labelText_branch,
            350.0f,
            600.0f,
            Settings.CREAM_COLOR,
            FontHelper.charDescFont,
            isDeadBranchEnabled,
            settingsPanel,
            label -> {
            },
            button -> {
              isCatEventEnabled = button.enabled;
              try {
                final SpireConfig config = new SpireConfig("MarisaMod", "MarisaModCongfig",
                    marisaModDefaultProp);
                config.setBool("enablePlaceholder", isDeadBranchEnabled);
                config.save();
              } catch (Exception e) {
                e.printStackTrace();
              }
            });

    settingsPanel.addUIElement(enableBlackCatButton);
    settingsPanel.addUIElement(enableDeadBranchButton);

    BaseMod.addEvent(Mushrooms_MRS.ID, Mushrooms_MRS.class, Exordium.ID);
    BaseMod.addEvent(OrinTheCat.ID, OrinTheCat.class, TheBeyond.ID);
/*
    //BaseMod.addEvent(TestEvent.ID, TestEvent.class);
    BaseMod.addEvent(TestEvent.ID, TestEvent.class, Exordium.ID);
    BaseMod.addEvent(TestEvent.ID, TestEvent.class, TheBeyond.ID);
    BaseMod.addEvent(TestEvent.ID, TestEvent.class, TheCity.ID);
*/
    BaseMod.addPotion(
        ShroomBrew.class,
        Color.NAVY.cpy(),
        Color.LIME.cpy(),
        Color.OLIVE,
        "ShroomBrew",
        MARISA
    );

    BaseMod.addPotion(
        StarNLove.class,
        Color.BLUE.cpy(),
        Color.YELLOW.cpy(),
        Color.NAVY,
        "StarNLove",
        MARISA
    );

    BaseMod.addPotion(
        BottledSpark.class,
        Color.BLUE.cpy(),
        Color.YELLOW.cpy(),
        Color.NAVY,
        "BottledSpark",
        MARISA
    );
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
    */
    BaseMod.addMonster(ORIN_ENCOUNTER, () -> new Orin());
    BaseMod.addMonster(ZOMBIE_FAIRY_ENC, () -> new ZombieFairy());
    final Texture badge = ImageMaster.loadImage(MOD_BADGE);
    BaseMod.registerModBadge(
        badge,
        "MarisaMod",
        "Flynn , Hell , Hohner_257 , Samsara",
        "A Mod of the poor blonde girl from Touhou Project(",
        settingsPanel
    );
  }

  private void loadCardsToAdd() {
    cardsToAdd.clear();

    cardsToAdd.add(new Strike_MRS());
    cardsToAdd.add(new Defend_MRS());
    cardsToAdd.add(new MasterSpark());
    cardsToAdd.add(new UpSweep());

    cardsToAdd.add(new DoubleSpark());
    cardsToAdd.add(new NonDirectionalLaser());
    cardsToAdd.add(new LuminesStrike());
    cardsToAdd.add(new MysteriousBeam());
    cardsToAdd.add(new WitchLeyline());
    cardsToAdd.add(new DC());
    cardsToAdd.add(new _6A());
    cardsToAdd.add(new UnstableBomb());
    cardsToAdd.add(new StarBarrage());
    cardsToAdd.add(new ShootingEcho());
    cardsToAdd.add(new MachineGunSpark());
    cardsToAdd.add(new DarkSpark());
    cardsToAdd.add(new DeepEcologicalBomb());
    cardsToAdd.add(new MeteoricShower());
    cardsToAdd.add(new GravityBeat());
    cardsToAdd.add(new GrandCross());
    cardsToAdd.add(new DragonMeteor());
    cardsToAdd.add(new RefractionSpark());
    cardsToAdd.add(new Robbery());
    cardsToAdd.add(new ChargeUpSpray());
    cardsToAdd.add(new AlicesGift());
    cardsToAdd.add(new FairyDestructionRay());
    cardsToAdd.add(new BlazingStar());
    cardsToAdd.add(new ShootTheMoon());
    cardsToAdd.add(new FinalSpark());
    cardsToAdd.add(new JA());
    cardsToAdd.add(new AbsoluteMagnitude());
    cardsToAdd.add(new TreasureHunter());
    cardsToAdd.add(new CollectingQuirk());

    cardsToAdd.add(new MilkyWay());
    cardsToAdd.add(new AsteroidBelt());
    cardsToAdd.add(new PowerUp());
    cardsToAdd.add(new SporeBomb());
    cardsToAdd.add(new IllusionStar());
    cardsToAdd.add(new EnergyRecoil());
    cardsToAdd.add(new GasGiant());
    cardsToAdd.add(new StarDustReverie());
    cardsToAdd.add(new MagicAbsorber());
    cardsToAdd.add(new Occultation());
    cardsToAdd.add(new EarthLightRay());
    cardsToAdd.add(new BlazeAway());
    cardsToAdd.add(new ChargingUp());
    cardsToAdd.add(new DarkMatter());
    cardsToAdd.add(new MagicChant());
    cardsToAdd.add(new OneTimeOff());
    cardsToAdd.add(new ManaConvection());
    cardsToAdd.add(new PropBag());
    cardsToAdd.add(new SprinkleStarSeal());
    cardsToAdd.add(new GalacticHalo());
    cardsToAdd.add(new SuperPerseids());
    cardsToAdd.add(new PulseMagic());
    cardsToAdd.add(new Orbital());
    cardsToAdd.add(new BigCrunch());
    cardsToAdd.add(new OpenUniverse());
    cardsToAdd.add(new StarlightTyphoon());
    cardsToAdd.add(new MaximisePower());
    cardsToAdd.add(new UltraShortWave());
    cardsToAdd.add(new ManaRampage());
    cardsToAdd.add(new BinaryStars());
    cardsToAdd.add(new Acceleration());

    cardsToAdd.add(new WitchOfGreed());
    cardsToAdd.add(new SatelliteIllusion());
    cardsToAdd.add(new OortCloud());
    cardsToAdd.add(new OrrerysSun());
    cardsToAdd.add(new EnergyFlow());
    cardsToAdd.add(new EventHorizon());
    cardsToAdd.add(new Singularity());
    cardsToAdd.add(new CasketOfStar());
    //cardsToAdd.add(new PolarisUnique());
    cardsToAdd.add(new EscapeVelocity());
    cardsToAdd.add(new MillisecondPulsars());
    cardsToAdd.add(new SuperNova());

    cardsToAdd.add(new Spark());
    cardsToAdd.add(new GuidingStar());
    cardsToAdd.add(new BlackFlareStar());
    cardsToAdd.add(new WhiteDwarf());
    cardsToAdd.add(new Exhaustion_MRS());
    cardsToAdd.add(new Strike_MRS());
    cardsToAdd.add(new Wraith());
  }

  class Keywords {

    Keyword[] keywords;
  }

  public static AbstractCard getRandomMarisaCard() {
    AbstractCard card;
    int rng = AbstractDungeon.miscRng.random(0, 100);
    if (rng == 15) {
      card = new GuidingStar();
    } else {
      card = AbstractDungeon.returnTrulyRandomCardInCombat().makeCopy();
    }
    return card;
  }
	/*



............................................................................................................................................
............................................................................................................................................
....................................................:7......................................................................................
....................................................7II7?,::::::::::::......................................................................
.................................,,,...............,,????7+~::::::::::::,...................................................................
..............................~:,,,:::......,..::::=I777 7?I77,:::::::::::..................................................................
...................................,,:::::::::::::,7777I777777777=,:::::::::................................................................
....................................,,,,:::,,,,,.::=7 777777?777777I::,,,,,,::..............................................................
...............................................,:,,777777777777?777777+.,,,,,,,.............................................................
..............................................:,I7III777777?77777I?77I77.,,,,,,,,...........................................................
............................................,,,:7I++II7777777?=7I77I=77I=,,,,,,,,,.....~?IIIIII?+=:,........................................
...........................................,,,,,,=7777777II77777?I?77+I7I,,,,,,,,.=+7777777777777777777777I?=====...........................
..........................................,,,,,,7II7777777777=?7II77???=~:..,,:,II77I?==+?I77777777777777IIIIII7:...........................
........................................,,,,,,,..===7777?=I=IIIII++++?I7III7I77~7II777777777777777777777777+................................
.......................................,,,,~7III7=I?...,~?I7?III=II..,,,,,,..~I~=?77I?+++?I7777777777I7777=.................................
..................................,,,,,,,,,~++III?+IIIIIIIIIIII7=====III7+~~?II77:,,,?7I?+?I7777777I++IIIII7................................
..........................,,,,,,+?III?7~~??IIII+7+==+:+?++?IIIIIIIIIIII?7=I?IIIIIIII=,,.,:,+77777?I7777=.,..................................
.......................,,,,,,,7IIIIII+?7III7I~~:III~?II=?III+:~IIIIIIIII?II,==:I7=II~,,,,,,,,.~7I77I=+7.....................................
...................::::::,,,,IIIIIIIIIIII:II=IIIIIII?I=I???~~+~I=I?II??I?I7II:III=?IIII+..I??I7,,,.+777.....................................
................,:::::::,,:~7IIIII7I7I7??I?=???I?IIII?+I?I=~I?,I?=??????IIII?I+??I=?=+III??IIII7.,,,,.......................................
.............:::::::,~+=+7IIIIIIII7?I7:???~?????~I???+~I??=~I?=~?I=+II~???????7????I=I??III7?II,,,,,:,,,:...................................
............:::::,+IIIIII?+II7II77777II??=????+?:I???+=????+?I+7+II=?:I?+??????=+????=???+IIIIIII:.7I~7?:,,,................................
...........,:::,7IIIIIIIIIIII7I777?+~77?I7???++=7?~+?++I~7?IIII~7+??~77??=I????~=+????=??:7IIII77I+?IIIIII=.,,:.............................
..............,7IIIIIIIII7777II++??++I??=????+=7II+??,I+=?+~~II?I777~~I???.I+??~=+?????+??+=?I7777IIII+?I7,:::::,,..........................
..................77IIIIII7I77777+++~I???~I??I~I.:=,~~=,,77I7+I+77I7I7~:...:?,I.+++:??????:?I+7777IIIIIIII7~,,::,,:.........................
...................IIII77777777?~+?==+I?+?+?++?+7:~=::~=~I777=+I7777I7I7=?=:::~.+::+?????+I==?~?7777777II???II7I7~::........................
....................,=+?II?=?7I???===?=+???+++=I7=~++~=?+7777?I777777777:==~~=:?=?+~===????++?+I77I?I77I77I7777777=.........................
...............................??+:=?=~???:~?+=I7II~=~~I7777777777777777~+I??+==+?+??:+=??+?=+~I77+7777:..:I777I=:..........................
...............................I??=??~??????=??I=IIIIII77777777I77777777IIII7=7+?????+:~+??=+=+?~?+~,.......................................
...............................~I????~???+???:IIII=7II777777777777777777II7+=I+I????I=+++?=+~+??............................................
..............................??+,??+~=?+:+???????I+II777I?+~~~~=I7I7777I+=I???????+=++~+??=???~............................................
.............................~?~+??+=+?:I~?:+????===7777777I=+??==777777+??7I??~=?+==?????+??==:............................................
.............................:I.+????:??:+:=~=???I,:I7I7777777777777777I~=+I++II?+?+??+??+???..,:...........................................
..............................~..?+??=?==I?~?+II?=+?=~==?=I77777777?=++=~==I===?I?++???+?++:?...............................................
...............................,..I++II:I??=+,I?~?~7III+~III?===?II?~IIII?~:=~=??I~I???+??+,.:,.............................................
....................................+I?III+I=I=?::~7777I=:~=???++==~I777I?:+~++~I~I??++?I:+,................................................
.....................................,.I++=I=~?:.I+II7I77777777777?I+7=+II=+=:.+,II?+~+=~:..................................................
....................................  7I:7?,.,,,=~=7~?7777777777777777..==?.,,,,.=II77777,..................................................
..................................I777777I~::::::7777=+77I?+====+?++I==.7~.~~::::.77777777,.................................................
.................................+ 77777II::::::,7777777  I+==+IIIIII7.~III7+++++++++++++++++++++++++++++++++++++++++=~.....................
................................=77777777I:::::::+777777777777?+?++.~7IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII?................
...............................I7777777777,,:::::,+777777777777~?:~IIIII====II:7?~7IIII+=III==~+III7~~III?IIIIII~:77?,=77III7+..............
...............................III77777777.::::::::::::,:::::,.7.:IIIIII....:....,.=7......?I+7II?,:....+?..7??IIII????I+I7III+.............
..............................++II77777777.::::::::::::~?+++???I.?IIIII7,,,,:.=:.==?III.?7:::::+III7..++++??II,:==.?~=7.?IIIII7.............
............................+7777777IIIII7,:::::::::::::.?+????I.+IIIII7====?......+II.=7?.IIIIIII.=.,I:?I:.7I,=?I.?:=I.?IIIIII.............
............................777777I=7I==?I,::::::::::::=I,:,...,,.?IIII7....?.......?.~II7~,..,~7I?..=I~..=7II,+==.?I:~.?IIII7..............
.............................:777?+~7777I7,:::::::::::,??+,:::::::.,IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII,...............
.................................:777+~+II~::::::::::,,,,::,,::::::,:,..=7I77777777777777777777777777777777777777777777?,...................
..................................+=IIIIIII.:::::::,,,,,,,,,,,,,,,,,,,,:::,,,,,,,.????????I,................................................
....................................7777777.,::::,,,,,,,,=.:,,,,,,,,,.??:,,,,::,,.7IIIIII77:................................................
....................................7IIIIII~.,,,,,,,,,,,==:.,,,,,,,,,,:~.,,,,,,,,.IIIIIIIII~................................................
............................................................................................................................................
............................................................................................................................................
............................................................................................................................................

	 */
}
