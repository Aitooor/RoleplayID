plugins{
    id("java")
    id("com.github.johnrengelman.shadow") version ("7.1.2")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(8))
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://repo.unnamed.team/repository/unnamed-public/")
    maven("https://repo.triumphteam.dev/snapshots/")
    maven("https://jitpack.io")
    maven("https://nexus.sirblobman.xyz/public/")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.19.4-R0.1-SNAPSHOT") {
        exclude( group = "org.yaml", module = "snakeyaml")
    }
    compileOnly("me.clip:placeholderapi:2.11.2")

    implementation("team.unnamed:inject:1.0.1")
    implementation("dev.triumphteam:triumph-cmd-bukkit:2.0.0-SNAPSHOT")
    implementation("net.kyori:adventure-api:4.14.0")
    implementation("net.kyori:adventure-text-minimessage:4.14.0")
}

tasks {
    shadowJar {
        archiveVersion.set("${project.properties.getValue("shaded_version")}")
        archiveClassifier.set("")

        relocate("dev.triumphteam.gui", "net.notchsmp.basictp.libs.gui")

        minimize()
    }
}
tasks {
    build {
        dependsOn("shadowJar")
    }
}