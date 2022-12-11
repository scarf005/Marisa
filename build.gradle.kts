import org.jetbrains.kotlin.com.google.gson.Gson
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.text.SimpleDateFormat

val slayTheSpireInstallDir = "${System.getenv("HOME")}/.local/share/Steam/steamapps/common/SlayTheSpire"
val modName = "MarisaMod"

buildscript {
    repositories {
        mavenCentral()
    }

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
    implementation("com.google.code.gson:gson:2.10")

    compileOnly(fileTree("lib"))
    compileOnly(files("${slayTheSpireInstallDir}/desktop-1.0.jar"))
}

sourceSets {
    main {
        kotlin.srcDir("src/main")
    }
}

@Suppress("PropertyName")
data class ModTheSpire(
    val modid: String,
    val name: String,
    val author_list: List<String>,
    val description: String,
    val dependencies: List<String>,
    val version: String,
    val sts_version: String,
    val update_json: String,
)

val template = file("src/main/resources/ModTheSpire.template.json")
val configPath = "src/main/resources/ModTheSpire.json"

val gson = Gson().newBuilder().setPrettyPrinting().create()
fun updateVersion() {
    val config = gson.fromJson(template.readText(), ModTheSpire::class.java)
    val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(System.currentTimeMillis())
    val newConfig = config.copy(version = date)
    println("Updating version to $date")
    file(configPath).writeText(gson.toJson(newConfig))
}
tasks.register("updateVersion") {
    updateVersion()
}

tasks.register<Jar>("createJar") {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    dependsOn("updateVersion")
    dependsOn(configurations.runtimeClasspath)

    from(sourceSets.main.get().output) { }
    from({
        configurations.runtimeClasspath.get()
            .filter { it.name.endsWith("createJar") }
            .map { zipTree(it) }
    })
    from(file("src/main/resources/ModTheSpire.json"))
}

tasks.register<Copy>("toMods") {
    dependsOn("clean")
    dependsOn("createJar")

    println("my jar: \"build/libs/${modName}.jar\"")
    from("build/libs/${modName}.jar")
    val dest = "${slayTheSpireInstallDir}/mods"
    println("moving to $dest")
    into(dest)
}

tasks.register<Copy>("toWorkshop") {
    dependsOn("clean")
    dependsOn("createJar")

    from("build/libs/${modName}.jar")
    into("${slayTheSpireInstallDir}/${modName}/content") // publish to Workshop folder
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}
