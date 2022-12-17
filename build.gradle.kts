import org.jetbrains.kotlin.com.google.gson.Gson
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.text.SimpleDateFormat

val modID = "MarisaContinued"

val (gameDir, modTheSpireDir, basemodDir) = run {
    val homeDir = System.getProperty("user.home")!!
    val steamDir = "$homeDir/.local/share/Steam/steamapps"
    val workShopDir = "$steamDir/workshop/content/Slay the Spire"
    val (mod, base) = 1605060445 to 1605833019
    Triple(
        "$steamDir/common/SlayTheSpire",
        "$workShopDir/$mod",
        "$workShopDir/$base",
    )
}

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

plugins {
    application
    java
    kotlin("jvm") version "1.8.0-Beta"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("reflect"))
    implementation(kotlin("stdlib-jdk8"))

    compileOnly("com.google.code.gson:gson:2.10")
    compileOnly(files("$gameDir/desktop-1.0.jar"))
    compileOnly(fileTree(modTheSpireDir))
    compileOnly(fileTree(basemodDir))
}

sourceSets {
    main { kotlin.srcDir("src/main") }
}

@Suppress("PropertyName")
data class ModTheSpire(
    val modid: String,
    val name: String = "Marisa: Continued (霧雨 魔理沙)",
    val description: String = "Adds Marisa (霧雨 魔理沙) from Touhou Project as a new playable character.",
    val version: String = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(System.currentTimeMillis()),
    val sts_version: String = "12-01-2022",
    val mts_version: String = "3.30.0",
    val author_list: List<String> = listOf("Flynn", "Hell", "Hohner257", "Samsara", "scarf005"),
    val credits: String = "Original mod: https://steamcommunity.com/sharedfiles/filedetails/?id=1614104912",
    val dependencies: List<String> = listOf("basemod"),
)

val gson = Gson().newBuilder().setPrettyPrinting().create()

tasks.register("updateVersion") {
    file("src/main/resources/ModTheSpire.json")
        .writeText(gson.toJson(ModTheSpire(modID)))
}

fun Jar.configure(version: String) {
    dependsOn(tasks.clean, tasks.jar, version)
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(sourceSets.main.get().output)
    exclude("ModTheSpire.template.json")
}

tasks.register<Jar>("devJar") {
    configure("updateVersion")
}

fun Copy.configure(dest: String) {
    dependsOn(tasks.clean, "devJar")
    from("build/libs/${modID}.jar")
    into(dest)
}

tasks.register<Copy>("toMods") {
    configure(dest = "${gameDir}/mods")
}

tasks.register<Copy>("toWorkshop") {
    configure(dest = "${gameDir}/${modID}/content")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}
