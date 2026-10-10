package marisa.testing

import basemod.BaseMod
import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Graphics
import com.badlogic.gdx.backends.headless.HeadlessFiles
import com.badlogic.gdx.backends.headless.HeadlessNativesLoader
import com.badlogic.gdx.backends.headless.mock.audio.MockAudio
import com.badlogic.gdx.backends.headless.mock.graphics.MockGraphics
import com.badlogic.gdx.graphics.GL20
import com.evacipated.cardcrawl.modthespire.Loader
import com.evacipated.cardcrawl.modthespire.ModInfo
import com.evacipated.cardcrawl.modthespire.Patcher
import com.megacrit.cardcrawl.audio.MusicMaster
import com.megacrit.cardcrawl.audio.SoundMaster
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.CharacterManager
import com.megacrit.cardcrawl.core.AbstractCreature
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.core.Settings
import com.megacrit.cardcrawl.helpers.*
import com.megacrit.cardcrawl.integrations.DistributorFactory
import com.megacrit.cardcrawl.integrations.PublisherIntegration
import com.megacrit.cardcrawl.localization.LocalizedStrings
import com.megacrit.cardcrawl.metrics.MetricData
import com.megacrit.cardcrawl.powers.AbstractPower
import com.megacrit.cardcrawl.screens.DisplayOption
import com.megacrit.cardcrawl.unlock.UnlockTracker
import javassist.ClassPool
import marisa.MarisaContinued
import java.io.File
import java.lang.reflect.Proxy
import java.nio.IntBuffer

/**
 * Boots the ModTheSpire-patched game headlessly the way `CardCrawlGame.create` does,
 * with BaseMod and this mod initialized, so cards, powers and relics run their production code.
 */
object Game {
    init {
        HeadlessNativesLoader.load()
        Gdx.files = HeadlessFiles()
        Gdx.graphics = object : MockGraphics() {
            val mode = object : Graphics.DisplayMode(1920, 1080, 60, 32) {}
            override fun getDisplayMode() = mode
            override fun getDisplayModes() = arrayOf(mode)

            // One frame at 60 FPS.
            override fun getDeltaTime() = 1 / 60f
            override fun getRawDeltaTime() = 1 / 60f
        }
        Gdx.gl = stub<GL20>(
            // Report every query as supported, compiled and complete.
            "glGetIntegerv" to { args -> (args[1] as IntBuffer).put(0, 16) },
            "glGetShaderiv" to { args -> (args[2] as IntBuffer).put(0, 1) },
            "glGetProgramiv" to { args -> (args[2] as IntBuffer).put(0, 1) },
            "glCheckFramebufferStatus" to { GL20.GL_FRAMEBUFFER_COMPLETE },
        )
        Gdx.gl20 = Gdx.gl
        Gdx.audio = MockAudio()
        Gdx.app = stub<Application>("getType" to { Application.ApplicationType.Desktop })
        CardCrawlGame.publisherIntegration =
            stub<PublisherIntegration>("getType" to { DistributorFactory.Distributor.STEAM })

        val mods = System.getProperty("mods").split(File.pathSeparator).map { ModInfo.ReadModInfo(File(it)) }
        Loader.MODINFOS = mods.toTypedArray()
        Patcher.bustEnums(Game::class.java.classLoader, Loader.MODINFOS)
        Loader::class.java.getDeclaredField("POOL").apply { isAccessible = true }.set(null, ClassPool.getDefault())
        BaseMod.initialize()
        MarisaContinued.initialize()

        Settings.language = Settings.GameLanguage.ENG
        CardCrawlGame.languagePack = LocalizedStrings()
        Settings.initialize(false)
        Settings.displayOptions = arrayListOf(DisplayOption(1920, 1080))
        CardCrawlGame.sound = SoundMaster()
        CardCrawlGame.music = MusicMaster()
        CardCrawlGame.playerName = "Test"
        AbstractCreature.initialize()
        AbstractCard.initialize()
        GameDictionary.initialize()
        ImageMaster.initialize()
        AbstractPower.initialize()
        FontHelper.initialize()
        AbstractCard.initializeDynamicFrameWidths()
        UnlockTracker.initialize()
        CardLibrary.initialize()
        RelicLibrary.initialize()
        TipTracker.initialize()
        // As a player who has seen the tutorials, which would open screens mid-action.
        TipTracker.disableAllFtues()
        CardCrawlGame.metricData = MetricData()
        CardCrawlGame.characterManager = CharacterManager()
        BaseMod.publishEditCharacters()
        BaseMod.publishPostInitialize()
    }

    fun init() = Unit

    /** An interface implementation whose methods run [answers] by name, or do nothing and return a zero value. */
    private inline fun <reified T> stub(vararg answers: Pair<String, (Array<out Any?>) -> Any?>): T =
        Proxy.newProxyInstance(T::class.java.classLoader, arrayOf(T::class.java)) { _, method, args ->
            val answer = answers.toMap()[method.name]
            if (answer != null) answer(args ?: emptyArray()).takeUnless { method.returnType == Void.TYPE }
            else when (method.returnType) {
                Int::class.javaPrimitiveType -> 1
                Long::class.javaPrimitiveType -> 0L
                Boolean::class.javaPrimitiveType -> false
                Float::class.javaPrimitiveType -> 0f
                else -> null
            }
        } as T
}
