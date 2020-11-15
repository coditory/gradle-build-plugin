plugins {
    id("com.gradle.enterprise").version("3.5")
}

rootProject.name = "build-plugin"

gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
    }
}
