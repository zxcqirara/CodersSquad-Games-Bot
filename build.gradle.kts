import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("jvm") version "1.7.21"
	application
	id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "dev.cha0s_f4me"
version = "1.1"

repositories {
	mavenCentral()
	maven("https://maven.kotlindiscord.com/repository/maven-public/")
}

dependencies {
	implementation("dev.kord:kord-core:0.8.0-M17")
	implementation("com.kotlindiscord.kord.extensions:kord-extensions:1.5.6-SNAPSHOT")

	implementation("io.github.config4k:config4k:0.5.0")
	implementation("com.squareup.okio:okio:3.3.0")

	implementation("org.slf4j:slf4j-log4j12:2.0.5")
}

tasks.withType<KotlinCompile> {
	kotlinOptions.jvmTarget = "11"
}

application {
	mainClass.set("BotKt")
}