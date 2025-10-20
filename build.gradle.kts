plugins {
    java
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "net.milkbowl.vaultlite"
version = "1.0.0"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://repo.codemc.org/repository/maven-public/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    mavenCentral()
}

dependencies {
    // Usar versão compatível com Java 17 (1.20.1 é baseada em 17)
    compileOnly("io.papermc.paper:paper-api:1.20.1-R0.1-SNAPSHOT")
    implementation("net.milkbowl.vault:VaultAPI:1.7")
    compileOnly("net.luckperms:api:5.4")
    compileOnly("me.clip:placeholderapi:2.11.6")
    compileOnly("net.kyori:adventure-api:4.17.0")
    compileOnly("net.kyori:adventure-key:4.17.0")

    // Lombok
    compileOnly("org.projectlombok:lombok:1.18.32")
    annotationProcessor("org.projectlombok:lombok:1.18.32")
}

sourceSets {
    named("main") {
        java.setSrcDirs(listOf("src"))
        resources.setSrcDirs(listOf("src/main/resources"))
    }
}

tasks.processResources {
    // Use only src/main/resources/plugin.yml; ignore root one if exists
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    filesMatching("**/plugin.yml") { expand(mapOf("version" to version)) }
}

tasks.shadowJar {
    archiveClassifier.set("")
    // Incluir apenas VaultAPI como dependência embutida
    dependencies {
        include(dependency("net.milkbowl.vault:VaultAPI"))
    }
}

tasks.build {
    dependsOn(tasks.shadowJar)
}


