# 🧰 Tnagdive CodeKit

A modern, lightning-fast, and lightweight Dependency Injection (DI) framework for Java.

**Tnagdive CodeKit** gives you the magic of automated component scanning and zero-friction dependency injection without the massive overhead of enterprise frameworks like Spring or Guice. It is designed to be perfectly modular—powerful enough to wire up multi-layered applications, yet lightweight enough to drop into a single-file playground script.

## 🚀 Why Use CodeKit?

* **Zero Boilerplate:** Stop writing `new MyDependency()`. Let the toolkit build your object graph automatically.
* **Interface-Ready:** Safely code to interfaces. The engine automatically finds and injects the correct implementations.
* **Plug-and-Play:** Wakes up instantly with a single method call: `CodeKit.onWire(this)`.
* **Lightweight:** No heavy server setups, no complex XML, and no slow reflection bottlenecks.

---

## 📦 Installation

CodeKit is distributed via JitPack. You can easily include it in any Gradle or Maven project.

### Gradle (Kotlin DSL)

Add the JitPack repository and the CodeKit dependency to your `build.gradle.kts`:

```kotlin
repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation("com.github.Tushar-Nagdive-Dev:tnagdive-codekit:1.0.0")
}

```

### Maven

Add the JitPack repository and the CodeKit dependency to your `pom.xml`:

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
        <version>1.0.0</version>
    </dependency>
</dependencies>

```

---

## 📖 Quick Start Guide

Using CodeKit takes less than 60 seconds. Here is the end-to-end workflow.

### 1. Mark Your Components

Tell CodeKit which classes it should manage by adding the `@KitComponent` annotation to your implementations.

```java
import io.github.tusharnagdive.codekit.annotate.KitComponent;

public interface PaymentService {
    void process();
}

@KitComponent
public class StripePaymentService implements PaymentService {
    @Override
    public void process() {
        System.out.println("Payment processed successfully.");
    }
}

```

### 2. Inject Your Dependencies

Use the `@Onwired` annotation wherever you need a tool injected. CodeKit handles the rest.

```java
import io.github.tusharnagdive.codekit.annotate.Onwired;

public class StoreController {
    
    // CodeKit automatically finds StripePaymentService and injects it here!
    @Onwired
    private PaymentService paymentService;
    
    public void checkout() {
        paymentService.process();
    }
}

```

### 3. Wake Up the Toolkit

In your application's entry point (or even inside a quick test script), call `CodeKit.onWire()`. This instantly scans your project's folders, instantiates your components, and wires everything together.

```java
import io.github.tusharnagdive.codekit.di.CodeKit;

public class MainApplication {

    private final StoreController controller;

    public MainApplication() {
        // 1. Instantly scan, instantiate, and inject dependencies into this class
        CodeKit.onWire(this);
        
        // 2. You can also manually pull fully-wired objects from the global context
        this.controller = CodeKit.getContext().getComponent(StoreController.class);
    }

    public static void main(String[] args) {
        MainApplication app = new MainApplication();
        app.controller.checkout(); // Output: "Payment processed successfully."
    }
}

```

---

## ⚙️ Advanced Features

### Optional Dependencies

If a dependency is not strictly required for your class to function, you can prevent CodeKit from throwing an error by marking it as optional:

```java
@Onwired(required = false)
private OptionalLogger optionalLogger; 

```

### Custom Package Scanning

By default, `CodeKit.onWire(this)` only scans the package where the calling class lives. If your application has a complex structure and you need to scan specific external packages, use the overloaded method:

```java
// Scans the current package AND the specified database toolkit package
CodeKit.onWire(this, "com.myapp.core", "com.myapp.database");

```

---

## 🤝 Contributing

Contributions, issues, and feature requests are welcome! Feel free to check the [issues page](https://www.google.com/search?q=https://github.com/Tushar-Nagdive-Dev/tnagdive-codekit/issues) if you want to contribute to the toolkit.

**Author:** Tushar Nagdive

**License:** MIT