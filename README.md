This is an incredible milestone. Upgrading your `README.md` is the final step in officially releasing these enterprise-level features to the public.

To make sure developers understand exactly how powerful **`tnagdive-codekit`** has become, I have updated your documentation to highlight the two massive upgrades we just built: **Prototype Scoping** and the **Built-in Utility Kit**. I also bumped the version number to `1.0.2` in the installation instructions to reflect your latest stable release.

Here is your fully updated, professional `README.md` ready to be pushed to GitHub!

```markdown
# 🧰 Tnagdive CodeKit

A modern, lightning-fast, and lightweight Dependency Injection (DI) framework and utility toolkit for Java.

**Tnagdive CodeKit** gives you the magic of automated component scanning and zero-friction dependency injection without the massive overhead of enterprise frameworks like Spring or Guice. It is designed to be perfectly modular—powerful enough to wire up multi-layered applications, yet lightweight enough to drop into a single-file playground script. 

Beyond DI, CodeKit now ships with its own suite of built-in data structures and utilities that are automatically injected into your project with zero configuration.

## 🚀 Why Use CodeKit?

* **Zero Boilerplate:** Stop writing `new MyDependency()`. Let the toolkit build your object graph automatically.
* **Interface-Ready:** Safely code to interfaces. The engine automatically finds and injects the correct implementations.
* **Enterprise Scoping:** Native support for both Singletons (shared services) and Prototypes (fresh instances per injection).
* **The "Batteries Included" Toolkit:** Access built-in, optimized data structures (like `UniChain`) directly from the framework.
* **Plug-and-Play:** Wakes up instantly with a single method call: `CodeKit.onWire(this)`.

---

## 📦 Installation

CodeKit is distributed via JitPack. You can easily include it in any Java 21+ Gradle or Maven project.

### Gradle (Kotlin DSL)

Add the JitPack repository and the CodeKit dependency to your `build.gradle.kts`:

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

Add the JitPack repository and the CodeKit dependency to your `pom.xml`:

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

## 📖 Quick Start Guide

Using CodeKit takes less than 60 seconds.

### 1. Mark Your Components

Tell CodeKit which classes it should manage by adding the `@KitComponent` annotation to your implementations. By default, these are created as **Singletons** (one shared instance for your whole app).

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

In your application's entry point, call `CodeKit.onWire(this)`. This instantly scans your project's folders, instantiates your components, and wires everything together.

```java
import io.github.tusharnagdive.codekit.di.CodeKit;

public class MainApplication {

    @Onwired
    private StoreController controller;

    public MainApplication() {
        // Instantly scan, instantiate, and inject dependencies into this class
        CodeKit.onWire(this);
    }

    public static void main(String[] args) {
        MainApplication app = new MainApplication();
        app.controller.checkout(); // Output: "Payment processed successfully."
    }
}

```

---

## ⚙️ Advanced Features (New in v1.0.2)

### 1. Enterprise Scoping: Singletons vs. Prototypes

By default, DI frameworks share the exact same object across your entire application (The Singleton Trap). If you are building stateful objects or data structures, you need a brand-new instance every time you inject it.

You can now explicitly tell CodeKit to generate a fresh instance on every `@Onwired` call by setting `singleton = false`:

```java
// Framework will generate a fresh instance everywhere it is injected!
@KitComponent(singleton = false)
public class TemporaryCache implements CacheManager { ... }

```

### 2. Built-in Utility Kit (Auto-Scanning)

CodeKit is more than just an injector; it is a developer toolkit. CodeKit now silently auto-scans its own internal packages, giving you instant access to optimized utilities without any manual configuration.

For example, you can instantly inject our built-in `UniChain` data structure:

```java
import io.github.tusharnagdive.codekit.kitcollection.struct.UniChain;

public class DataProcessor {
    
    // No setup required! CodeKit automatically provides this built-in Prototype tool.
    @Onwired
    private UniChain<Integer> numberChain;
    
    public DataProcessor() {
        CodeKit.onWire(this);
    }
    
    public void process() {
        numberChain.addAtFirst(10);
        numberChain.cheapSort();
        numberChain.uniChainPrint();
    }
}

```

### 3. Optional Dependencies

If a dependency is not strictly required for your class to function, you can prevent CodeKit from throwing a crash error by marking it as optional:

```java
@Onwired(required = false)
private OptionalLogger optionalLogger; 

```

### 4. Custom Package Scanning

By default, `CodeKit.onWire(this)` scans your toolkit and the package where the calling class lives. If your application has a complex structure and you need to scan specific external packages, use the overloaded method:

```java
// Scans the current package AND the specified database toolkit package
CodeKit.onWire(this, "com.myapp.core", "com.myapp.database");

```

---

## 🤝 Contributing

Contributions, issues, and feature requests are welcome! Feel free to check the [issues page](https://www.google.com/search?q=https://github.com/Tushar-Nagdive-Dev/tnagdive-codekit/issues) if you want to contribute to the toolkit.

**Author:** Tushar Nagdive

**License:** MIT

```

```