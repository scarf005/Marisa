import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val slayTheSpireInstallDir = "${System.getenv("HOME")}/.local/share/Steam/steamapps/common/SlayTheSpire"
val modName = "Marisa"

description = "test"

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
    java
    kotlin("jvm") version "1.7.20"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    compileOnly(fileTree("lib"))
    compileOnly(files("${slayTheSpireInstallDir}/desktop-1.0.jar"))
}

sourceSets {
    main {
        java.srcDir("src/main")
//        java {
//            srcDirs.add(file("src/main/kotlin/"))
//        }
    }
}

val jar1 = "jar1"

tasks.register<Jar>(jar1) {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

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
    dependsOn(jar1)

    from("build/libs/${modName}.jar")
    into("$slayTheSpireInstallDir\\mods")
}

tasks.register<Copy>("copyJarToWorkshopFolder") {
    dependsOn("clean")
    dependsOn(jar1)

    from("build/libs/${modName}.jar")
    into("${slayTheSpireInstallDir}/${modName}/content") // publish to Workshop folder
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}