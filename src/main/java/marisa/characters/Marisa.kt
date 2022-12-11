package marisa.characters

import marisa.MarisaMod
import marisa.cards.Marisa.MasterSpark
import marisa.patches.AbstractCardEnum
import marisa.patches.ThModClassEnum
import basemod.abstracts.CustomPlayer
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion
import com.badlogic.gdx.math.MathUtils
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor
import com.megacrit.cardcrawl.cards.DamageInfo
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.core.EnergyManager
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.core.Settings.GameLanguage
import com.megacrit.cardcrawl.events.beyond.SpireHeart
import com.megacrit.cardcrawl.events.city.Vampires
import com.megacrit.cardcrawl.helpers.FontHelper
import com.megacrit.cardcrawl.helpers.ImageMaster
import com.megacrit.cardcrawl.helpers.ScreenShake
import com.megacrit.cardcrawl.screens.CharSelectInfo
import com.megacrit.cardcrawl.unlock.UnlockTracker
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class Marisa(name: String) :
    CustomPlayer(name, ThModClassEnum.MARISA, ORB_TEXTURES, ORB_VFX, LAYER_SPEED, null, null) {
    init {
        dialogX = drawX // set location for text bubbles
        dialogY = drawY + 220.0f * Settings.scale
        logger.info("init Marisa")
        initializeClass(
            null,
            MARISA_SHOULDER_2,  // required call to load textures and setup energy/loadout
            MARISA_SHOULDER_1,
            MARISA_CORPSE,
            loadout,
            20.0f, -10.0f, 220.0f, 290.0f,
            EnergyManager(ENERGY_PER_TURN)
        )
        loadAnimation(MARISA_SKELETON_ATLAS, MARISA_SKELETON_JSON, 2.0f)
        // if you're using modified versions of base game animations or made animations in spine make sure to include this bit and the following lines
        state.setAnimation(0, MARISA_ANIMATION, true).apply {
            time = endTime * MathUtils.random()
            timeScale = 1.0f
        }

        stateData.setMix("Hit", "Idle", 0.1f)
        logger.info("init finish")
    }

    override fun getStartingDeck(): ArrayList<String> { // create starting deck
        val strikes = Array(4) { "Strike_MRS" }
        val defends = Array(4) { "Defend_MRS" }
        val others = arrayOf("MasterSpark", "UpSweep")
        return (strikes + defends + others).toCollection(ArrayList())
    }

    override fun getStartingRelics(): ArrayList<String> { // starting relics
        UnlockTracker.markRelicAsSeen("MiniHakkero")
        return arrayListOf("MiniHakkero")
    }

    private data class LoadoutText(val title: String, val flavor: String)

    private fun loadoutText(): LoadoutText = when (Settings.language) {
        GameLanguage.ZHS -> LoadoutText(
            title = """普通的魔法使""",
            flavor = """住在魔法森林的魔法使。 NL 善长于光和热的魔法。"""
        )

        GameLanguage.JPN -> LoadoutText(
            title = """普通の魔法使い""",
            flavor = """魔法の森に住んでいる普通の魔法使い。 NL 光と熱の魔法が得意。"""
        )

        GameLanguage.ZHT -> LoadoutText(
            title = """普通的魔法使""",
            flavor = """住在魔法森林的魔法使。 NL 善長於光和熱的魔法。"""
        )

        GameLanguage.KOR -> LoadoutText(
            title = """평범한 마법사""",
            flavor = """마법의 숲에 사는 "평범한" 마법사 입니다. NL 빛과 열 마법이 특기입니다."""
        )

        GameLanguage.FRA -> LoadoutText(
            title = "La magicienne ordinaire",
            flavor = """La magicienne "ordinaire" vie dans la forêt magique.  NL Spécialisées dans la magie de la lumière et de la chaleur."""
        )

        else -> LoadoutText(
            title = "The Ordinary Magician",
            flavor = """The "ordinary" magician lives in the Forest of Magic. NL Specializes in light and heat magic."""
        )
    }

    /**
     * the rest of the character loadout so includes your character select screen info plus hp and starting gold
     */
    override fun getLoadout(): CharSelectInfo {
        val (title, flavor) = loadoutText()
        return CharSelectInfo(
            title,
            flavor,
            STARTING_HP,
            MAX_HP,
            0,
            STARTING_GOLD,
            HAND_SIZE,
            this,
            startingRelics,
            startingDeck,
            false
        )
    }

    override fun getCardColor(): CardColor = AbstractCardEnum.MARISA_COLOR

    override fun getStartCardForEvent(): AbstractCard = MasterSpark()

    override fun getTitle(playerClass: PlayerClass) = when (Settings.language) {
        GameLanguage.ZHS -> """普通的魔法使"""
        GameLanguage.JPN -> """普通の魔法使い"""
        GameLanguage.ZHT -> """普通的魔法使"""
        GameLanguage.KOR -> """평범한 마법사"""
        else -> "The Ordinary Magician"
    }

    override fun getCardTrailColor(): Color = MarisaMod.STARLIGHT

    override fun getAscensionMaxHPLoss(): Int = ASCENSION_MAX_HP_LOSS

    override fun getEnergyNumFont(): BitmapFont = FontHelper.energyNumFontBlue

    override fun doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("SELECT_MRS", MathUtils.random(-0.1f, 0.1f))
        CardCrawlGame.screenShake.shake(
            ScreenShake.ShakeIntensity.MED,
            ScreenShake.ShakeDur.SHORT,
            false
        )
    }

    override fun getCustomModeCharacterButtonSoundKey() = "SELECT_MRS"

    override fun getLocalizedCharacterName() = when (Settings.language) {
        GameLanguage.JPN, GameLanguage.ZHS, GameLanguage.ZHT -> """魔理沙"""
        GameLanguage.KOR -> """마리사"""
        else -> "Marisa"
    }

    override fun newInstance(): AbstractPlayer = Marisa(name)

    override fun getVampireText(): String = Vampires.DESCRIPTIONS[1]

    override fun getCardRenderColor(): Color = MarisaMod.STARLIGHT

    override fun updateOrb(orbCount: Int) {
        energyOrb.updateOrb(orbCount)
    }

    override fun getOrb(): AtlasRegion = AtlasRegion(ImageMaster.loadImage(MarisaMod.CARD_ENERGY_ORB), 0, 0, 24, 24)

    override fun getSlashAttackColor(): Color = MarisaMod.STARLIGHT

    override fun getSpireHeartSlashEffect(): Array<AttackEffect> {
        return arrayOf(
            AttackEffect.SLASH_HEAVY,
            AttackEffect.FIRE,
            AttackEffect.SLASH_DIAGONAL,
            AttackEffect.SLASH_HEAVY,
            AttackEffect.FIRE,
            AttackEffect.SLASH_DIAGONAL
        )
    }

    override fun getSpireHeartText(): String = SpireHeart.DESCRIPTIONS[10]

    override fun damage(info: DamageInfo) {
        if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.output - currentBlock > 0) {
            val e = state.setAnimation(0, "Hit", false)
            e.timeScale = 1.0f
            state.addAnimation(0, "Idle", true, 0.0f)
        }
        super.damage(info)
    }

    override fun applyPreCombatLogic() {
        super.applyPreCombatLogic()
        MarisaMod.typhoonCounter = 0
        MarisaMod.logger.info(
            """Marisa : applyPreCombatLogic : I just reset the god damn typhoon counter ! current counter : ${MarisaMod.typhoonCounter}"""
        )
    }

    companion object {
        val logger: Logger = LogManager.getLogger(MarisaMod::class.java.name)
        private const val ENERGY_PER_TURN = 3 // how much energy you get every turn
        private const val MARISA_SHOULDER_2 = "img/char/Marisa/shoulder2.png" // shoulder2 / shoulder_1
        private const val MARISA_SHOULDER_1 = "img/char/Marisa/shoulder1.png" // shoulder1 / shoulder_2
        private const val MARISA_CORPSE = "img/char/Marisa/fallen.png" // dead corpse

        private const val MARISA_SKELETON_ATLAS =
            "img/char/Marisa/MarisaModelv3.atlas" // Marisa_v0 / MarisaModel_v02 /MarisaModelv3
        private const val MARISA_SKELETON_JSON = "img/char/Marisa/MarisaModelv3.json"
        private const val MARISA_ANIMATION = "Idle" // Sprite / Idle
        private val ORB_TEXTURES = arrayOf(
            "img/UI/EPanel/layer5.png",
            "img/UI/EPanel/layer4.png",
            "img/UI/EPanel/layer3.png",
            "img/UI/EPanel/layer2.png",
            "img/UI/EPanel/layer1.png",
            "img/UI/EPanel/layer0.png",
            "img/UI/EPanel/layer5d.png",
            "img/UI/EPanel/layer4d.png",
            "img/UI/EPanel/layer3d.png",
            "img/UI/EPanel/layer2d.png",
            "img/UI/EPanel/layer1d.png"
        )
        private const val ORB_VFX = "img/UI/energyBlueVFX.png"
        private val LAYER_SPEED = floatArrayOf(-40.0f, -32.0f, 20.0f, -20.0f, 0.0f, -10.0f, -8.0f, 5.0f, -5.0f, 0.0f)

        private const val STARTING_HP = 75
        private const val MAX_HP = 75
        private const val STARTING_GOLD = 99
        private const val HAND_SIZE = 5
        private const val ASCENSION_MAX_HP_LOSS = 5
    }
}
