plugins {
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Use TestNG framework
    testImplementation("org.testng:testng:7.4.0")
    // This dependency is used by the application.
    implementation("com.google.guava:guava:30.0-jre")
}

application{
    //Define the main class for the application
    mainClass.set("com.lyit.Main")

}

tasks.test{
    //Use TestNG for unit tests
    useTestNG()
}
