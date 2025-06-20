import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

val pluginName = "StaffChat"
val pluginVersion = "1.0.0"
val pluginMain = "org.insurgencedev.staffchat.StaffChat"
val author = "girlydragon"
val website = "https://insurgencedev.org"
val discordInviteUrl = "https://discord.com/invite/khaXNTbXW7"
val pluginDependencies = listOf(
    "me.clip:placeholderapi:2.11.6",
    "com.github.MilkBowl:VaultAPI:1.7"
)
val exclusions = listOf(
    "META-INF/**",
    "com/bgsoftware/**",
    "org/spigotmc/**",
    "org/bukkit/**",
    "org/jetbrains/annotations/**",
    "org/intellij/**",
    "net/milkbowl/vault/**"
)

val relocations = mapOf(
    "net.kyori.adventure" to "org.insurgencedev.staffchat.libs.kyori.adventure",
    "net.kyori.examination" to "org.insurgencedev.staffchat.libs.kyori.examination",
    "lua" to "org.insurgencedev.staffchat.libs.lua",
    "org.luaj" to "org.insurgencedev.staffchat.libs.luaj"
)
plugins {
    id("com.gradleup.shadow") version "9.0.0-beta4"
    id("java")
    id("io.freefair.lombok") version "8.0.1" // Lombok plugin
}

tasks.test {
    useJUnitPlatform()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

tasks {
    shadowJar {
        dependsOn(processResources)
        dependsOn(classes)
        archiveBaseName.set("$pluginName-$pluginVersion")
        archiveClassifier.set("")
        archiveVersion.set("")
        exclusions.forEach { exclude(it) }
        relocations.forEach { (from, to) ->
            relocate(from, to)
        }
    }
}

tasks.named("clean", Delete::class) {
    delete("$rootDir/compiled-jars")
}

allprojects {
    repositories {
        mavenCentral()
        mavenLocal()
        maven {
            url = uri("https://repo.extendedclip.com/releases/")
        }
        maven {
            url = uri("https://repo.bg-software.com/repository/api/")
        }
        maven {
            url = uri("https://jitpack.io")
        }
        maven {
            url = uri("https://oss.sonatype.org/content/repositories/snapshots")
        }
    }
}

subprojects {
    apply(plugin = "com.gradleup.shadow")
    apply(plugin = "java")

    dependencies {
        pluginDependencies.forEach { dependency ->
            compileOnly(dependency)
        }
        compileOnly("org.jetbrains:annotations:24.0.1")
        compileOnly(fileTree("${rootProject.projectDir}/libs") { include("*.jar") })
        compileOnly("org.projectlombok:lombok:1.18.36")
        annotationProcessor("org.projectlombok:lombok:1.18.36")
        implementation("net.kyori:adventure-api:4.10.0")
        implementation("net.kyori:adventure-text-serializer-legacy:4.10.0")
        implementation("net.kyori:adventure-text-minimessage:4.10.0")
        implementation("org.luaj:luaj-jse:3.0.1")

    }

    tasks {
        if (name == "Bukkit-Plugin") {
            shadowJar {
                configureShadowJar(this, "")
                doLast {
                    copy {
                        println("Copying ${archiveFileName.get()} to compiled-jars")
                        from("${layout.buildDirectory.get().asFile.path}/libs/${archiveFileName.get()}")
                        into("$rootDir/compiled-jars")
                    }
                }
            }
            dependencies {
                compileOnly("io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT")
                implementation(project(":ILib"))
            }
        }
        if (name == "Velocity-Plugin"){
            shadowJar {
                configureShadowJar(this, "-Velocity")
                doLast {
                    copy {
                        println("Copying ${archiveFileName.get()} to compiled-jars")
                        from("${layout.buildDirectory.get().asFile.path}/libs/${archiveFileName.get()}")
                        into("$rootDir/compiled-jars")
                    }
                }
            }
            dependencies {
                compileOnly("com.velocitypowered:velocity-api:3.3.0")
                implementation(project(":Proxy-Core"))
            }
        }
        if (name == "Bungee-Plugin") {
            shadowJar {
                configureShadowJar(this, "-Bungee")
                doLast {
                    copy {
                        println("Copying ${archiveFileName.get()} to compiled-jars")
                        from("${layout.buildDirectory.get().asFile.path}/libs/${archiveFileName.get()}")
                        into("$rootDir/compiled-jars")
                    }
                }
            }
            dependencies {
                compileOnly("net.md-5:bungeecord-api:1.19-R0.1-SNAPSHOT")
                implementation(project(":Proxy-Core"))
            }
        }
        if (name == "ILib") {
            dependencies {
                compileOnly("io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT")
            }
        }
        processResources {
            duplicatesStrategy = DuplicatesStrategy.INCLUDE
            from("${project(":Bukkit-Plugin").projectDir}/src/main/resources") {
                processResourceFile("plugin.yml")
                processResourceFile("config.yml")
            }
        }
    }
}

infix fun CopySpec.processResourceFile(fileName: String) {
    println("Running processResourceFile for $fileName")
    filesMatching(fileName) {
        println("|-----------------------------------|")
        println("File: $fileName was found")
        println("Replacing variables...")
        filter {
            it.replaceVariables()
        }
    }
    println("Variables has been replaced")
}

fun String.replaceVariables(): String {
    val replacements = mapOf(
        "@pluginName" to pluginName,
        "@pluginVersion" to pluginVersion,
        "@pluginMain" to pluginMain,
        "@author" to author,
        "@website" to website,
        "@discordInviteUrl" to discordInviteUrl,
    )

    var result = this
    replacements.forEach { (key, value) ->
        if (result.contains(key)) {
            println("Replacing $key with $value")
            result = result.replace(key, value)
        }
    }
    return result
}

fun configureShadowJar(task: ShadowJar, name: String, version: String = pluginVersion) {
    task.apply {
        archiveBaseName.set("$pluginName-$version$name")
        archiveClassifier.set("")
        archiveVersion.set("")
        exclusions.forEach { exclude(it) }
        relocations.forEach { (from, to) ->
            relocate(from, to)
        }
    }
}
