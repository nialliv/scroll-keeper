plugins {
    application
    id("io.freefair.lombok") version "9.0.0"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.commons:commons-lang3:3.19.0")
    implementation("com.google.code.gson:gson:2.13.2")
    implementation("org.apache.httpcomponents.client5:httpclient5:5.5.1")
    implementation("org.jsoup:jsoup:1.21.2")
    implementation("org.apache.poi:poi-ooxml:5.4.1")

    testImplementation("org.junit.jupiter:junit-jupiter-api:6.0.1")
    testImplementation("org.assertj:assertj-core:4.0.0-M1")
    testImplementation("org.mockito:mockito-core:5.20.0")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:6.0.1")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "ru.artemev.Main"
    }
}

tasks.test {
    useJUnitPlatform()
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

application {
    mainClass = "ru.artemev.Main"
}
