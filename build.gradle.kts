plugins {
    java
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "com.rankup.empire"
version = "0.0.1"

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven ("https://oss.sonatype.org/content/groups/public/")
    maven ("https://libraries.minecraft.net/")
    maven ("https://repo.aikar.co/content/groups/aikar/")

    maven( "https://jitpack.io")
}

dependencies {


    compileOnly ("org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT")
    compileOnly ("com.github.azbh111:craftbukkit-1.8.8:R")

    compileOnly("net.luckperms:api:5.3")

    implementation("org.jetbrains:annotations:24.0.0")
    implementation("com.zaxxer:HikariCP:3.4.5")
    implementation("com.github.SaiintBrisson.command-framework:bukkit:1.3.1")
    implementation(fileTree("inventory"))

    compileOnly (fileTree("libs"))

    compileOnly ("org.projectlombok:lombok:1.18.20")
    annotationProcessor ("org.projectlombok:lombok:1.18.20")

}

tasks {
    java {
        targetCompatibility = JavaVersion.VERSION_1_8
        sourceCompatibility = JavaVersion.VERSION_1_8
    }
    compileJava { options.encoding = "UTF-8"}

    java {
        shadowJar {
            archiveFileName.set("rank.jar")

            relocate(
                "me.devnatan.inventoryframework",
                "com.rankup.empire.rank.shared.inventory"
            )
            relocate(
                "me.saiintbrisson",
                "com.rankup.empire.rank.shared.spigot.command"
            )
        }
    }
}

