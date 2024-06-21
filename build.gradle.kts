import org.jetbrains.kotlin.com.google.gson.Gson
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

val modID = "MarisaContinued"
val jarFile = "$buildDir/libs/${modID}.jar"
val changelog = File("docs/changelog/changelog.md").readText()
val changeBBCode = File("docs/changelog/changelog.bbcode").readText()
val changeSts = File("docs/changelog/changelog.sts.txt").readText()

val userSteamDir = property("userSteamDir") ?: throw error("userSteamDir is not set")
val gameDir = "$userSteamDir/common/SlayTheSpire"

/** [store link](https://store.steampowered.com/app/646570/Slay_the_Spire/) */
val gameSteamId = 646570

/** [workshop link](https://steamcommunity.com/sharedfiles/filedetails?id=1605833019) */
val baseModId = 1605833019

/** [workshop link](https://steamcommunity.com/sharedfiles/filedetails?id=1605060445) */
val modTheSpireId = 1605060445

/** [workshop link](https://steamcommunity.com/sharedfiles/filedetails?id=2902980404) */
val marisaModId = 2902980404

val workShopDir = "$userSteamDir/workshop/content/$gameSteamId"
val modTheSpireDir = "$workShopDir/$modTheSpireId"
val basemodDir = "$workShopDir/$baseModId"

println(
    """
        userSteamDir: $userSteamDir
        gameDir: $gameDir
        basemodDir: $basemodDir
        modTheSpireDir: $modTheSpireDir
    """.trimIndent()
)

buildscript {
    repositories { mavenCentral() }

    dependencies {
        classpath(kotlin("gradle-plugin", version = "1.3.31"))
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

kotlin {
    sourceSets.all {
        languageSettings {
            languageVersion = "2.0"
        }
    }
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_1_8)
    }
}

plugins {
    application
    java
    kotlin("jvm") version "2.0.0"
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation(kotlin("reflect"))
    implementation(kotlin("stdlib-jdk8"))

    compileOnly("com.google.code.gson:gson:2.10.1")
    compileOnly(files("$gameDir/desktop-1.0.jar"))
    compileOnly(fileTree(modTheSpireDir))
    compileOnly(fileTree(basemodDir))
}

sourceSets {
    main {
        kotlin {
            srcDir("src/main/kotlin")
        }
        resources {
            srcDir("src/main/resources")
            exclude("**/*.ts", "**/deno.*", "schemas/**")
        }
    }
}

@Suppress("PropertyName", "LongLine")
data class ModTheSpire(
    val modid: String,
    val name: String = "Marisa: Continued (霧雨 魔理沙)",
    val description: String = "Adds Marisa (霧雨 魔理沙) from Touhou Project as a new playable character.",
    val version: String,
    val sts_version: String = "12-01-2022",
    val mts_version: String = "3.30.0",
    val author_list: List<String> = listOf("Flynn", "Hell", "Hohner257", "Samsara", "scarf005"),
    val credits: String = "Original mod: https://steamcommunity.com/sharedfiles/filedetails/?id=1614104912",
    val dependencies: List<String> = listOf("basemod"),
)

data class Config(
    val steamPublishedID: Long = 2902980404,
    val title: String = "Marisa: Continued",
    val description: String = File("docs/workshop.bbcode").readText(),
    val visibility: String = "public",
    val changeNote: String,
    val tags: List<String> = listOf(
        "Touhou", "Character", "Marisa", "Kirisame Marisa",
        "English", "Simplified Chinese", "Traditional Chinese", "French", "Korean", "Japanese"
    ),
)

val gson: Gson = Gson().newBuilder().disableHtmlEscaping().setPrettyPrinting().create()
val configFile = file("src/main/resources/ModTheSpire.json")

tasks.processResources {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.register("modthespire") {
    description = "Generates ModTheSpire.json"

    val config = ModTheSpire(
        modID,
        description = """
            |Adds Marisa (霧雨 魔理沙) from Touhou Project as a new playable character.
            |
            |${changeSts}
            """.trimMargin(),
        version = File("docs/changelog/version.txt").readText().trim()
    )

    configFile.writeText(gson.toJson(config) + "\n")
}

tasks.jar {
    description = "Builds the mod jar file."

    dependsOn("modthespire")
    from(sourceSets.main.get().output)
    println("built jar!")
}

tasks.register("hardlink") {
    description = "Creates hard link for release on steam directory."
    exec {
        commandLine("deno", "task", "--quiet", "link", "--quiet")
    }
}

tasks.register("changelog") {
    description = "Write changelog to steam workshop description"

    dependsOn("hardlink", tasks.jar)
    file("$gameDir/${modID}/config.json")
        .writeText(gson.toJson(Config(changeNote = changeBBCode)) + "\n")
}
