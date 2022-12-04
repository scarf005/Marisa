import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val slayTheSpireInstallDir = "${System.getenv("HOME")}/.local/share/Steam/steamapps/common/SlayTheSpire"
val modName = "Marisa"

buildscript {
    repositories {
        mavenCentral()
    }

    dependencies {
        classpath(kotlin("gradle-plugin", version = "1.3.31"))
    }
}

plugins {
    application
    kotlin("jvm") version "1.7.20"
}

repositories {
    jcenter()
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    compileClasspath(fileTree("lib"))
    compileClasspath(files("${slayTheSpireInstallDir}/desktop-1.0.jar"))
}

sourceSets {
    main {
        java {
            srcDirs.add(file("src/main/kotlin/"))
        }
    }
}

tasks.register<Jar>("jar1") {
    archiveName = "$modName.jar"
    from(sourceSets.main.get().output) { }
    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get()
            .filter { it.name.endsWith("jar") }
            .map { zipTree(it) }
    })
    from(file("src/ModTheSpire.json"))
}

tasks.register<Copy>("copyJarToStsMods") {
    dependsOn("clean")
    dependsOn("jar1")

    if (slayTheSpireInstallDir == null || slayTheSpireInstallDir == "null") {
        throw Exception("STS_HOME is not set.")
    }

    from("build/libs/${modName}.jar")
    into("$slayTheSpireInstallDir\\mods")
}

tasks.register<Copy>("copyJarToWorkshopFolder") {
    dependsOn("clean")
    dependsOn("jar1")

    if (slayTheSpireInstallDir == null || slayTheSpireInstallDir == "null") {
        throw Exception("STS_HOME is not set.")
    }

    from("build/libs/${modName}.jar")
    into("${slayTheSpireInstallDir}/${modName}/content") // publish to Workshop folder
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}