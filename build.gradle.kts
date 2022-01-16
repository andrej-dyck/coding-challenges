plugins {
    kotlin("jvm") version "1.6.10"
    id("io.gitlab.arturbosch.detekt") version "1.19.0"
}

group = "ad.kata"
version = "1.0"

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    // junit 5
    testImplementation(platform("org.junit:junit-bom:5.8.1"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
    // assertJ
    testImplementation("org.assertj:assertj-core:3.22.0")
    // jqwik
    testImplementation("net.jqwik:jqwik:1.6.4")
}

/* Source sets by Kotlin conventions /src and /test */
val sources = setOf(
    "main" to "src/",
    "test" to "test/"
)
kotlin {
    sources.forEach { (set, dir) ->
        sourceSets[set].apply { kotlin.srcDir(dir) }
    }
}

/* Detekt */
detekt {
    source = files(sources.map { it.second })
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
    gradleVersion = "7.3"
    distributionType = Wrapper.DistributionType.BIN
}