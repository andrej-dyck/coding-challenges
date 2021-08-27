import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.30"
    id("io.gitlab.arturbosch.detekt").version("1.17.1")
}

group = "ad.kata"
version = "1.0"

repositories {
    mavenLocal()
    mavenCentral()
    // detekt needs kotlinx-html for its report
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven") }
}

dependencies {
    implementation(kotlin("stdlib")) // for Kotlin sources

    // junit 5
    testImplementation(platform("org.junit:junit-bom:5.7.2"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.7.2")
    testImplementation("org.assertj:assertj-core:3.20.2")
    testImplementation("net.jqwik:jqwik:1.5.4")
}

/* Compile to JVM 8 */
tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

/* Source sets by Kotlin conventions /src and /test */
sourceSets.getByName("main") {
    withConvention(KotlinSourceSet::class) {
        kotlin.srcDirs("src/")
    }
}

sourceSets.getByName("test") {
    withConvention(KotlinSourceSet::class) {
        kotlin.srcDirs("test/")
    }
}

/* Detekt */
detekt {
    input = files("src/", "test/")
    config = files("detekt.yml")
}

tasks.withType<Detekt>().configureEach {
    jvmTarget = "1.8"
}

/* Check with Junit 5 only */
tasks.test {
    useJUnitPlatform {
        includeEngines("junit-jupiter", "jqwik")
        excludeEngines("junit-vintage")
    }
}

/* Gradle Wrapper */
tasks.withType<Wrapper> {
    gradleVersion = "7.0"
    distributionType = Wrapper.DistributionType.BIN
}