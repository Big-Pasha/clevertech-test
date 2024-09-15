plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    annotationProcessor("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.4.2.Final")

    compileOnly("org.projectlombok:lombok:1.18.30")
    implementation("org.mapstruct:mapstruct:1.4.2.Final")
    implementation("org.springframework.boot:spring-boot-starter-web:3.3.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test:3.3.0")
    testImplementation("org.jeasy:easy-random-core:5.0.0")
}

tasks.test {
    useJUnitPlatform()
}