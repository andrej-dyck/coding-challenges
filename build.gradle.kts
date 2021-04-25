import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.30"
    id("io.gitlab.arturbosch.detekt").version("1.16.0")
}

group = "ad.kata"
version = "1.0"

repositories {
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8")) // for Kotlin sources

    testImplementation(platform("org.junit:junit-bom:5.7.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core:3.18.1")
    testImplementation("net.jqwik:jqwik:1.5.0")
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
    reports {
        html {
            enabled = true
            destination = file("detekt/report.html")
        }
    }
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
    gradleVersion = "6.8"
    distributionType = Wrapper.DistributionType.BIN
}