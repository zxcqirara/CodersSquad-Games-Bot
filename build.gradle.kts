import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("jvm") version "1.7.21"
	application
	id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "dev.cha0s_f4me"
version = "1.0-SNAPSHOT"

repositories {
	mavenCentral()
	maven("https://maven.kotlindiscord.com/repository/maven-public/")
}

dependencies {
	implementation("dev.kord:kord-core:0.8.0-M17")
	implementation("com.kotlindiscord.kord.extensions:kord-extensions:1.5.6-SNAPSHOT")

	implementation("io.github.config4k:config4k:0.5.0")
	implementation("com.squareup.okio:okio:3.3.0")
}

tasks.withType<KotlinCompile> {
	kotlinOptions.jvmTarget = "11"
}

application {
	mainClass.set("MainKt")
}

tasks.withType<ShadowJar> {
	minimize()
}