import org.gradle.api.tasks.wrapper.Wrapper.DistributionType.ALL

plugins {
    `java-gradle-plugin`
    `maven-publish`
    idea
    jacoco
}

group = "de.sklaholz"
version = "0.0.1"
if (project.hasProperty("SNAPSHOT")) {
    version = "$version-SNAPSHOT"
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
    withSourcesJar()
}

repositories {
    mavenCentral()
}

val lombokVersion by extra("1.18.34")
val sahiProLibVersion by extra("11.0.1.4")

dependencies {
    /*******************************
     * Annotation Processors
     * *******************************/
    annotationProcessor(group = "org.projectlombok", name = "lombok", version = lombokVersion)

    /*******************************
     * Dependencies
     *******************************/
    implementation(group = "org.projectlombok", name = "lombok", version = lombokVersion)

    implementation(group = "in.co.sahi.distributed", name = "ant-sahi", version = sahiProLibVersion)
    implementation(group = "in.co.sahi.distributed", name = "sahi", version = sahiProLibVersion)

    /*******************************
     * Test Dependencies
     *******************************/
    testImplementation(platform("org.junit:junit-bom:5.10.3"))
    testImplementation(group = "org.junit.jupiter", name = "junit-jupiter")
    testImplementation(group = "org.junit.jupiter", name = "junit-jupiter-params")
    testRuntimeOnly(group = "org.junit.platform", name = "junit-platform-launcher")
    testImplementation(group = "org.assertj", name = "assertj-core", version = "3.26.3")
    testImplementation(group = "org.mockito", name = "mockito-junit-jupiter", version = "5.12.0")
}

gradlePlugin {
    val greeting by plugins.creating {
        id = "de.sklaholz.sahi-pro-runner"
        implementationClass = "de.sklaholz.sahi.gradle.plugins.SahiProRunnerPlugin"
    }
}

publishing {
    publications {
        create<MavenPublication>("pluginMaven") {
            artifactId = "sahi-runner-plugin"
        }
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

tasks.wrapper {
    distributionType = ALL
    gradleVersion = "8.9"
}
