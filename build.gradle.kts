plugins {
    application
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

application.mainClass = "com.unknown.bot.Main"
group = "org.example"
version = "1.0"

val jdaVersion = "5.0.0-beta.24"

repositories {
    mavenCentral()
}

dependencies {
    implementation("net.dv8tion:JDA:$jdaVersion")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.isIncremental = true

    // Set this to the version of java you want to use,
    // the minimum required for JDA is 1.8
    sourceCompatibility = "8"
}