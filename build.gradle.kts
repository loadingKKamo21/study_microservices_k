import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.7.18" apply false
	id("io.spring.dependency-management") version "1.0.15.RELEASE"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21" apply false
}

allprojects {
	group = "com.programmingtechie"
	version = "0.0.1-SNAPSHOT"
	
	repositories {
		mavenCentral()
	}
	
	tasks.withType<JavaCompile> {
		sourceCompatibility = "1.8"
		targetCompatibility = "1.8"
	}
	
	tasks.withType<KotlinCompile> {
		kotlinOptions {
			freeCompilerArgs += "-Xjsr305=strict"
			jvmTarget = "1.8"
		}
	}
}

subprojects {
	apply {
		plugin("org.springframework.boot")
		plugin("io.spring.dependency-management")
		plugin("kotlin")
		plugin("kotlin-spring")
	}
	
	dependencies {
		implementation("org.springframework.boot:spring-boot-starter-aop")
		implementation("org.springframework.boot:spring-boot-starter-validation")
		implementation("org.jetbrains.kotlin:kotlin-reflect")
		//jackson-module-kotlin
		implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
		//kotlin-logging
		implementation("io.github.oshai:kotlin-logging-jvm:5.1.0")
		//Gson
		implementation("com.google.code.gson:gson:2.10.1")
		runtimeOnly("org.springframework.boot:spring-boot-devtools")
		testImplementation("org.springframework.boot:spring-boot-starter-test")
		//Mockito-Kotlin
		testImplementation("org.mockito.kotlin:mockito-kotlin:4.1.0")
	}
	
	dependencyManagement {
		imports {
			mavenBom("org.springframework.cloud:spring-cloud-dependencies:2021.0.8")
		}
	}
	
	tasks.withType<Test> {
		useJUnitPlatform()
	}
}
