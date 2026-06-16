# 🧰 CodeKit Data Structures: Mastering `UniChain`

While CodeKit is known for its blazing-fast Dependency Injection engine, **v1.0.2** introduces the `KitCollections` toolkit—a suite of high-performance, enterprise-grade data structures. 

The crown jewel of this toolkit is **`UniChain<T>`**: an enhanced, universally flexible Singly Linked List. It is designed to be completely **Zero-Invasive**, meaning it adapts to your custom domain models without forcing you to modify them.

This guide covers how to instantiate a `UniChain`, use its standard operations, and master its revolutionary functional sorting API.

---

## 1. Instantiating a `UniChain`

You can inject `UniChain` via `@Onwired` if you are using CodeKit's DI engine, but the cleanest way to generate a temporary data structure on the fly is using the **Programmatic Factory**.

```java
import io.github.tusharnagdive.codekit.kitcollection.KitCollections;
import io.github.tusharnagdive.codekit.kitcollection.struct.UniChain;

public class DataService {
    public void processData() {
        // Generates a fresh, highly optimized instance instantly!
        UniChain<String> logs = KitCollections.get(UniChain.class);
        logs.addAtLast("System Boot");
    }
}

```

---

## 2. Standard Operations (The Essentials)

`UniChain` comes with a massive suite of built-in methods that go far beyond a standard Java `LinkedList`.

### Insertion & Deletion

* `addAtFirst(T data)` / `addAtLast(T data)`
* `insertAtIndex(T value, Integer index)`
* `insertBefore(T target, T data)` / `insertAfter(T target, T data)`
* `removeFirst()` / `removeLast()`
* `removeByValue(T value)` / `removeAtIndex(Integer index)`

### Retrieval & Updating

* `retrieveByValue(T value)` / `retrieveAtIndex(Integer index)`
* `getNthEnd(Integer nthValue)`: Safely fetches the Nth node from the *end* of the list.
* `updateAtNode(Integer nodeNum, T value)`

### Advanced List Manipulations

* `reverse()`: Reverses the entire chain in-place.
* `concatenate(UniChain<T> otherChain)`: Merges another `UniChain` to the end of this one.
* `removeDuplicates()` / `hasDuplicate()`: Fast duplicate detection and removal.
* `isUniChainCircular()`: Detects infinite loops (cycles) in the chain.
* `clearSequentially()` / `clearInstantly()`: Memory-safe list destruction.

---

## 3. The Zero-Invasive Sorting Engine (The Star Feature)

In standard Java, sorting a custom object (like a `User`) requires you to modify your `User` class to implement `Comparable` or write clunky `Comparator` classes.

`UniChain` fixes this. It allows you to store **pure, untouched domain models** and sort them dynamically on the fly using a clean Functional API. You just pass a method reference (Getter) telling the framework which field to look at.

### The Two Sorting Algorithms

1. **`cheapSort(Function keyExtractor)`**: Best for small datasets. Memory efficient, but slower for massive lists.
2. **`enhancedSort(Function keyExtractor)`**: Powered by a highly optimized internal `mergeSort`. Best for massive enterprise datasets.

### Example: Sorting Custom Objects

```java
// 1. A completely standard, untouched Java class
public class User {
    private int id;
    private String name;
    
    public User(int id, String name) { this.id = id; this.name = name; }
    public int getId() { return id; }
    public String getName() { return name; }
}

// 2. The Implementation
public class UserService {
    public void rankUsers() {
        UniChain<User> userChain = KitCollections.get(UniChain.class);
        userChain.addAtLast(new User(3, "Charlie"));
        userChain.addAtLast(new User(1, "Alice"));
        userChain.addAtLast(new User(2, "Bob"));

        // Sort mathematically by ID using the high-speed enhanced sort!
        userChain.enhancedSort(User::getId);
        
        // Or sort alphabetically by Name!
        userChain.cheapSort(User::getName);
    }
}

```

---

## 4. Legacy Sorters & Guard Clauses

If you are storing native Java types that already know how to sort themselves (like `Integer` or `String`), you can use the legacy no-args sorters:

```java
UniChain<Integer> numbers = KitCollections.get(UniChain.class);
numbers.addAtLast(50);
numbers.addAtLast(10);
numbers.enhancedSort(); // Automatically sorts numerically!

```

### 🛡️ The Crash-Proof Guard Clause

What happens if a developer accidentally calls `cheapSort()` (with no arguments) on a list of `User` objects? Standard Java would instantly crash with a fatal `ClassCastException`.

CodeKit uses **Runtime Guard Clauses**. It intercepts the mistake, safely aborts the sorting process, prevents your application from crashing, and prints a helpful diagnostic warning guiding you to use the functional API:

> `CodeKit Warning: Cannot sort natively because User does not implement Comparable. Use the functional method.`

---

## 5. Why Choose UniChain?

* **Type Safety:** Target-type inference guarantees your data without unchecked cast warnings.
* **Architecture Integrity:** The underlying implementation (`UniChainImpl`) is completely hidden from the user, enforcing clean abstraction.
* **Developer Experience (DX):** Auto-completing functional method references (`User::getId`) make sorting complex data a one-line task.
