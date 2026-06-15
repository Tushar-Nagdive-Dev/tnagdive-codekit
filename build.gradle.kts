plugins {
    id("java-library")
    id("maven-publish")
}

group = "io.github.tusharnagdive"
version = "1.0.0"

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
    withSourcesJar()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            artifactId = "tnagdive-codekit"
            pom {
                name.set("Tnagdive CodeKit")
                description.set("A modern, developer-focused Java utility toolkit.")
                url.set("https://github.com/Tushar-Nagdive-Dev/tnagdive-codekit")

                licenses {
                    license {
                        name.set("The MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }

                developers {
                    developer {
                        id.set("tusharnagdive")
                        name.set("Tushar Nagdive")
                    }
                }
            }
        }
    }
}