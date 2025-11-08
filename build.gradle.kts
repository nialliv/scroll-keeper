plugins {
    application
    id("io.freefair.lombok") version "9.0.0"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.commons:commons-lang3:3.19.0")
    implementation("org.json:json:20250517")
    testImplementation("org.junit.jupiter:junit-jupiter-api:6.0.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:6.0.1")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
//    testImplementation("org.junit.jupiter:junit-jupiter-params:6.0.1")
    testImplementation("org.mockito:mockito-core:5.20.0")
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
