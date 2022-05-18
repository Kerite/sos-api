pluginManagement {
    repositories {
        maven("https://repo.spring.io/release")
        mavenCentral()
        gradlePluginPortal()
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "org.hibernate.orm") {
                useModule("org.hibernate:hibernate-gradle-plugin:5.6.7.Final")
            }
            if (requested.id.id == "org.springframework.boot.experimental.thin-launcher") {
                useModule("org.springframework.boot.experimental:spring-boot-thin-gradle-plugin:${requested.version}")
            }
        }
    }
}
rootProject.name = "sos-api"
