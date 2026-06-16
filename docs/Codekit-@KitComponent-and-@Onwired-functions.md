# 🧰 CodeKit Dependency Injection: Mastering `@KitComponent` and `@Onwired`

At the heart of the **Tnagdive CodeKit** framework is a blazing-fast, lightweight Dependency Injection (DI) engine. CodeKit eliminates the need for complex XML configurations or massive boilerplate setup by relying on two simple, powerful annotations: `@KitComponent` and `@Onwired`.

This guide covers how to use these annotations effectively, the difference between Singletons and Prototypes, and a complete enterprise-level architecture example.

---

## 1. The Annotations Explained

### `@KitComponent`
This annotation is placed on the **implementation class** (never the interface). It tells the CodeKit engine: *"I want you to manage the lifecycle of this class and make it available for injection."*

**Key Parameters:**
* `singleton` (boolean, default: `true`): 
  * When `true`, CodeKit creates exactly **one** instance of this class and shares it across the entire application. This is perfect for stateless services, repositories, and configurations.
  * When `false`, CodeKit creates a **brand new** instance every time it is injected. This is mandatory for stateful objects, data structures (like `UniChain`), and temporary caches.

### `@Onwired`
This annotation is placed on a **field** where you want CodeKit to inject a dependency. CodeKit will automatically scan its registry, find the correct implementation for that interface, and inject it securely.

**Key Parameters:**
* `required` (boolean, default: `true`):
  * When `true`, the application will throw a fatal error if CodeKit cannot find a matching component to inject.
  * When `false`, CodeKit will silently skip the injection if the component is missing, leaving the field as `null`.

---

## 2. Enterprise-Level Example: E-Commerce Architecture

In a real-world enterprise application, you should **always code to interfaces**. This allows you to easily swap out implementations (e.g., swapping a Stripe payment gateway for PayPal) without breaking the rest of your application.

Here is a complete, multi-layered architecture using CodeKit.

### Step 1: Define the Interfaces (The Contract)
```java
public interface PaymentGateway {
    boolean chargeCard(double amount);
}

public interface InventoryService {
    boolean reserveItem(String sku);
}

public interface OrderRepository {
    void saveOrder(String orderId);
}

```

### Step 2: Create the Implementations (The `@KitComponent`)

We mark our concrete classes with `@KitComponent`. Notice how we make the core services Singletons, but we make the `OrderValidator` a **Prototype** (`singleton = false`) because it holds temporary, order-specific state.

```java
import io.github.tusharnagdive.codekit.annotate.KitComponent;

// --- SINGLETONS (Stateless Services) ---

@KitComponent
public class StripePaymentGateway implements PaymentGateway {
    @Override
    public boolean chargeCard(double amount) {
        System.out.println("Charging $" + amount + " via Stripe...");
        return true; 
    }
}

@KitComponent
public class RedisInventoryService implements InventoryService {
    @Override
    public boolean reserveItem(String sku) {
        System.out.println("Reserving item " + sku + " in Redis...");
        return true;
    }
}

@KitComponent
public class PostgresOrderRepository implements OrderRepository {
    @Override
    public void saveOrder(String orderId) {
        System.out.println("Saving order " + orderId + " to PostgreSQL DB.");
    }
}

// --- PROTOTYPE (Stateful Service) ---

@KitComponent(singleton = false)
public class OrderValidator {
    private int validationAttempts = 0; // Stateful data!
    
    public boolean validate(String orderId) {
        validationAttempts++;
        System.out.println("Validating order " + orderId + " (Attempt: " + validationAttempts + ")");
        return true;
    }
}

```

### Step 3: Wire the Application Together (The `@Onwired`)

Now, we create our `OrderController`. This class doesn't care *how* payments are processed or *where* data is saved; it just relies on CodeKit to provide the right tools via `@Onwired`.

```java
import io.github.tusharnagdive.codekit.annotate.Onwired;

public class OrderController {

    // CodeKit automatically finds and injects the Singletons
    @Onwired
    private PaymentGateway paymentGateway;

    @Onwired
    private InventoryService inventoryService;

    @Onwired
    private OrderRepository orderRepository;

    // CodeKit injects a brand NEW instance of the Validator here!
    @Onwired
    private OrderValidator validator;

    public void processCheckout(String orderId, String sku, double amount) {
        System.out.println("--- Starting Checkout for " + orderId + " ---");
        
        if (validator.validate(orderId) && inventoryService.reserveItem(sku)) {
            if (paymentGateway.chargeCard(amount)) {
                orderRepository.saveOrder(orderId);
                System.out.println("Checkout Complete!");
            }
        }
    }
}

```

### Step 4: Bootstrapping the Application

To bring the application to life, you simply invoke `CodeKit.onWire(this)` at the entry point of your program.

```java
import io.github.tusharnagdive.codekit.di.CodeKit;
import io.github.tusharnagdive.codekit.annotate.Onwired;

public class MainApplication {

    @Onwired
    private OrderController orderController;

    public MainApplication() {
        // This single line scans the project, creates the components, 
        // and injects everything securely.
        CodeKit.onWire(this);
    }

    public static void main(String[] args) {
        MainApplication app = new MainApplication();
        
        // Process a sample order
        app.orderController.processCheckout("ORD-7781", "MACBOOK-PRO", 1999.99);
    }
}

```

---

## 3. Best Practices & Architecture Rules

1. **Always Code to Interfaces:** Never inject a concrete class if you can avoid it. Inject `PaymentGateway`, not `StripePaymentGateway`. CodeKit is designed to cleanly map interfaces to their active `@KitComponent` implementation.
2. **Beware the Singleton Trap:** If a class holds data that changes per user or per request (like a temporary List, an active Database Connection, or a Validation counter), you **must** use `@KitComponent(singleton = false)`. If you leave it as a Singleton, multiple users will overwrite each other's data, causing severe Thread-Safety issues.
3. **Use Optional Dependencies for Graceful Degradation:** If you have a feature like an `AnalyticsLogger` that isn't critical to the app functioning, use `@Onwired(required = false)`. If the analytics component fails to load, CodeKit will leave the field null instead of crashing the server, allowing you to wrap it in a simple `if (logger != null)` check.
