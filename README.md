# 🧰 Tnagdive CodeKit

A modern, lightning-fast, and lightweight Dependency Injection (DI) framework and utility toolkit for Java.

**Tnagdive CodeKit** gives you the magic of automated component scanning and zero-friction dependency injection without the massive overhead of enterprise frameworks like Spring or Guice. It is designed to be perfectly modular—powerful enough to wire up multi-layered applications, yet lightweight enough to drop into a single-file playground script. 

Beyond DI, CodeKit now ships with its own suite of built-in data structures and utilities that are automatically injected into your project with zero configuration.

---

## 📦 Installation

CodeKit is distributed via JitPack. You can easily include it in any Java 21+ Gradle or Maven project.

### Gradle (Kotlin DSL)
```kotlin
repositories {
    mavenCentral()
    maven { url = uri("[https://jitpack.io](https://jitpack.io)") }
}

dependencies {
    implementation("com.github.Tushar-Nagdive-Dev:tnagdive-codekit:1.0.2")
}

```

### Maven

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>[https://jitpack.io](https://jitpack.io)</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.Tushar-Nagdive-Dev</groupId>
        <artifactId>tnagdive-codekit</artifactId>
        <version>1.0.2</version>
    </dependency>
</dependencies>

```

---

## 🚀 Features & Usage

* **Zero Boilerplate:** Stop writing `new MyDependency()`. Let the toolkit build your object graph automatically.
* **Interface-Ready:** Safely code to interfaces. The engine automatically finds and injects the correct implementations.
* **Enterprise Scoping:** Native support for both Singletons (shared services) and Prototypes (fresh instances per injection).
* **The "Batteries Included" Toolkit:** Access built-in, optimized data structures directly from the framework.
* **Plug-and-Play:** Wakes up instantly with a single method call.

### Quick Start

CodeKit wires up your application in three simple steps:

```java
// 1. Mark your implementations
@KitComponent
public class StripePaymentService implements PaymentService { ... }

// 2. Inject your dependencies
public class StoreController {
    @Onwired
    private PaymentService paymentService;
}

// 3. Wake up the toolkit in your entry point
public class MainApplication {
    public MainApplication() {
        CodeKit.onWire(this);
    }
}

```

---

## 📚 Documentation & Advanced Details

**For detailed views, advanced features, and complete release notes, please check the `docs/` folder.**

Inside the `docs/` directory of this repository, you will find:

* **The Official Usage Guide:** Detailed instructions on Enterprise Scoping, Optional Dependencies, and Custom Package Scanning.
* **KitCollections Manual:** How to use our programmatic collection factory and the Zero-Invasive Functional Sorting API (`userChain.enhancedSort(User::getId)`).
* **Release Notes:** Comprehensive changelogs for the current and past framework versions.

---

## 🤝 Contributing

Contributions, issues, and feature requests are welcome! Feel free to check the [issues page](https://www.google.com/search?q=https://github.com/Tushar-Nagdive-Dev/tnagdive-codekit/issues) if you want to contribute to the toolkit.

**Author:** Tushar Nagdive

**License:** MIT
