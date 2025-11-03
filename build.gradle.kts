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
    testImplementation("org.junit.jupiter:junit-jupiter-api:6.0.0")
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
