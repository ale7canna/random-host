import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.21"
}

version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    compile("com.github.salomonbrys.kotson:kotson:2.5.0")
}

dependencies {
    testRuntime("org.slf4j:slf4j-nop:1.7.25")
    testCompile("io.kotlintest:kotlintest-runner-junit5:3.3.2")
    testCompile("io.mockk:mockk:1.9.3.kotlin12")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

val test by tasks.getting(Test::class) {
    useJUnitPlatform { }
}