# 🧰 Tnagdive CodeKit: The Open-Source DI & Utility Framework

A modern, lightning-fast, and deeply extensible Dependency Injection (DI) framework and utility toolkit for Java.

**Tnagdive CodeKit** provides the magic of automated component scanning, zero-friction dependency injection, and high-performance data structures without the massive overhead, sluggish startup times, or steep learning curves associated with legacy enterprise frameworks like Spring or Guice. It is designed from the ground up to be perfectly modular—powerful enough to wire up complex, multi-layered enterprise applications, yet lightweight enough to drop into a single-file playground script.

---

## 🌍 Our Open-Source Philosophy

CodeKit is proudly **100% Free and Open-Source Software (FOSS)**, released under the permissive **MIT License**.

We believe that foundational developer tools should be accessible to everyone, transparent in their execution, and free from restrictive vendor lock-in. By open-sourcing CodeKit, we are inviting the global Java community to inspect, modify, fork, and enhance the framework.

**What this means for you as a developer:**

* **Absolute Freedom:** You can use CodeKit in your personal hobby projects, internal company tools, or massive closed-source commercial applications without any legal friction.
* **Radical Transparency:** There is no "black magic" under the hood. If you want to know exactly how the `@Onwired` dependency graph resolves, or how the `UniChain` internal `mergeSort` algorithm achieves its performance, the source code is entirely open for you to study and audit.
* **Community-Driven Evolution:** The framework grows based on real-world developer needs, not corporate mandates. We actively welcome pull requests, feature proposals, and architectural debates from developers of all skill levels.

---

## ⚙️ Core Architectural Features

CodeKit is divided into two deeply integrated modules: the **DI Engine** and the **KitCollections Toolkit**.

### 1. The Lightweight Dependency Injection Engine

Stop manually instantiating your object graphs with `new MyDependency()`. CodeKit handles the lifecycle and wiring of your entire application natively.

* **Interface-Driven Design:** Safely code to contracts (interfaces). The CodeKit engine automatically maps interfaces to their active implementations at runtime, enforcing clean architecture.
* **Enterprise Scoping (Singletons vs. Prototypes):** Control your memory profile with precision. Use `@KitComponent(singleton = true)` for shared, stateless services (like a `DatabaseRepository`), and `@KitComponent(singleton = false)` to generate fresh, stateful prototypes on every injection (like an `OrderValidator` or a temporary data structure).
* **Graceful Degradation:** Use `@Onwired(required = false)` for optional dependencies (like an external analytics logger). If the module isn't found, CodeKit safely bypasses the injection without crashing your server.

### 2. KitCollections & The Zero-Invasive Data Structures

Beyond DI, CodeKit ships with a built-in suite of highly optimized utilities, completely eliminating the need to import heavy third-party libraries like Google Guava or Apache Commons for basic tasks.

* **The `UniChain` Omni-Structure:** A revolutionary Singly Linked List that acts as a universal storage container.
* **Zero-Invasive Functional Sorting:** You no longer need to modify your custom business objects (like a `User` or `Product` class) to implement the `Comparable` interface. Store pure, untouched domain models and sort them dynamically using Java Method References (e.g., `userChain.enhancedSort(User::getId)`).
* **Runtime Guard Clauses:** The framework acts as a safety net. If a developer attempts a mathematically impossible operation (like legacy-sorting an un-sortable `HashMap`), CodeKit intercepts the crash, aborts the operation, and prints a diagnostic warning, keeping your production environment stable.
* **The Programmatic Factory:** Bypass the DI engine entirely if needed. Generate temporary, high-speed collections on the fly inside any method using `KitCollections.get(UniChain.class)`.

---

## 📦 Installation

CodeKit is distributed globally via JitPack. You can integrate it into any **Java 21+** Gradle or Maven project in seconds.

### Gradle (Kotlin DSL)

```kotlin
repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
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
        <url>https://jitpack.io</url>
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

## 🚀 Quick Start Bootstrapping

CodeKit wakes up and wires your entire application with a single line of code.

```java
// 1. Mark your core services
@KitComponent
public class StripePaymentService implements PaymentService { ... }

// 2. Inject them securely into your controllers
public class StoreController {
    @Onwired
    private PaymentService paymentService;
}

// 3. Ignite the framework at your application's entry point
public class MainApplication {
    public MainApplication() {
        // Instantly scans packages, maps interfaces, and resolves the DI graph
        CodeKit.onWire(this);
    }
}

```

---

## 📚 Official Documentation

To keep this repository clean, all deep-dive tutorials, architecture explanations, and version changelogs are hosted in the `docs/` directory.

**We highly recommend reviewing the official guides:**

* 📘 **[CodeKit DI Guide: Singletons, Prototypes, and Enterprise Architecture](https://www.google.com/search?q=docs/Codekit%2520%40KitComponent%2520and%2520%40Onwired%2520functions.md)**
* 📙 **[KitCollections Manual: Mastering UniChain and Functional Sorting](https://www.google.com/search?q=docs/Codekit%2520UniChain%2520Documentation.md)**
* 📝 **[v1.0.2 Release Notes](https://www.google.com/search?q=docs/1.0.2-Release-Notes.md)**

---

## 🤝 Join the Open-Source Community

CodeKit thrives on collaboration. Whether you are a senior enterprise architect or a junior developer making your first open-source contribution, there is a place for you here.

* **Found a bug?** Open an issue on our tracker with a stack trace and a minimal reproducible example.
* **Have a feature idea?** Start a discussion thread! We are actively looking for feedback on our upcoming `BiChain` data structure and step-builder patterns.
* **Want to write code?** Check out our `good first issue` tags, fork the repository, and submit a Pull Request. All PRs are reviewed meticulously to ensure high architectural standards.

**[Explore the GitHub Issues Page](https://www.google.com/search?q=https://github.com/Tushar-Nagdive-Dev/tnagdive-codekit/issues)**

**Author & Lead Architect:** Tushar Nagdive

**License:** MIT License