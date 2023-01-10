plugins {
    kotlin("jvm") version "1.8.0"
    id("io.gitlab.arturbosch.detekt") version "1.21.0"
}

group = "ad.kata"
version = "1.0"

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    // JUnit 5
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.1")
    // AssertJ
    testImplementation("org.assertj:assertj-core:3.24.1")
    // jqwik
    testImplementation("net.jqwik:jqwik:1.7.1")
}

/* Source sets by Kotlin conventions /src and /test */
sourceSets.main { kotlin.srcDirs("src/") }
sourceSets.test { kotlin.srcDirs("test/") }

/* Resources */
sourceSets["main"].resources.srcDirs("resources")
sourceSets["test"].resources.srcDirs("test-resources")

/* Detekt */
detekt {
    source = files("src/", "test/")
    config = files("detekt.yml")
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
    gradleVersion = "7.6"
    distributionType = Wrapper.DistributionType.BIN
}