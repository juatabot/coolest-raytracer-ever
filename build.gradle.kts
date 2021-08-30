import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.21"
    application
}

group = "me.stan"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jscience:jscience:4.3.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.1")
}

tasks.test {
    useJUnitPlatform()

    testLogging {
        events("passed", "skipped", "failed", "standardOut", "standardError")
    }
}

tasks.jar { // could also be a new task rather than the default one
    manifest {
        attributes["Main-Class"] = "MainKt"
    }

    from(sourceSets.main.get().output)
    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClassName = "MainKt"
}