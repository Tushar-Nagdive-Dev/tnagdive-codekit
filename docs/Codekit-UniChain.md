# 🧰 CodeKit Collections: UniChain<T>

## Version 1.0.2 Documentation

### Overview

`UniChain<T>` is CodeKit's enterprise-grade, generic singly linked list implementation designed for modern Java applications.

Unlike traditional collection frameworks, UniChain follows a **Zero-Invasive Design Philosophy**, allowing developers to work with plain domain models without requiring framework inheritance, custom wrappers, or interface implementations.

```java
public class User {

    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
```

The `User` class above can be stored, manipulated, and sorted directly within UniChain without implementing `Comparable<User>`.

---

# Creating a UniChain

## Recommended Approach

```java
UniChain<User> users = UniChain.create();
```

### Why `create()`?

CodeKit v1.1.0 adopts the Static Factory Pattern used throughout modern Java APIs.

Examples from the Java Platform:

```java
List<String> list = List.of();

Optional<User> user = Optional.of(value);
```

Following the same principle:

```java
UniChain<User> users = UniChain.create();
```

Benefits:

* No unchecked casting
* No registry lookups
* No reflection
* Better IDE auto-completion
* Cleaner developer experience
* Hidden implementation details
* Improved maintainability

---

# Deprecated API

## Deprecated Since v1.0.2

```java
UniChain<User> users =
        KitCollections.get(UniChain.class);
```

### Status

⚠ Deprecated

### Removal Target

CodeKit v2.0.0

### Why Was It Deprecated?

The original factory relied on runtime type resolution.

```java
KitCollections.get(UniChain.class);
```

Although functional, this approach introduced several architectural drawbacks:

### 1. Runtime Type Lookup

Every creation request required registry resolution.

```java
registry.get(UniChain.class);
```

This adds unnecessary indirection for a structure whose implementation is already known.

### 2. Unchecked Generic Casting

The internal implementation relied on type casting.

```java
return (T) supplier.get();
```

Compile-time safety becomes weaker and IDE assistance is reduced.

### 3. Reduced API Discoverability

Developers must know that a factory class exists before creating collections.

```java
KitCollections.get(...)
```

Whereas:

```java
UniChain.create()
```

is immediately discoverable from the collection interface itself.

### 4. Increased Maintenance Cost

Every new collection required manual registry registration.

```java
registry.put(UniChain.class, UniChainImpl::new);
```

Future structures such as:

* BiChain
* StackChain
* QueueChain
* PriorityChain

would all require additional registry maintenance.

---

# Migration Guide

## Before

```java
UniChain<Integer> numbers =
        KitCollections.get(UniChain.class);
```

## After

```java
UniChain<Integer> numbers =
        UniChain.create();
```

No additional code changes are required.

---

# Core Operations

## Insert Operations

```java
addAtFirst(T value);
addAtLast(T value);

insertBefore(T target, T value);
insertAfter(T target, T value);

insertAtIndex(T value, Integer index);
```

---

## Delete Operations

```java
removeFirst();
removeLast();

removeByValue(T value);
removeAtIndex(Integer index);
```

---

## Retrieval Operations

```java
retrieveByValue(T value);

retrieveAtIndex(Integer index);

getNthEnd(Integer n);
```

---

## Update Operations

```java
updateAtNode(Integer nodeNumber, T value);
```

---

## Structural Operations

```java
reverse();

concatenate(UniChain<T> otherChain);

clearInstantly();

clearSequentially();
```

---

## Validation Utilities

```java
hasDuplicate();

removeDuplicates();

isUniChainCircular();
```

---

# Functional Sorting Engine

One of UniChain's most powerful capabilities is its Functional Sorting Engine.

Traditional Java collections often require:

```java
implements Comparable<User>
```

or

```java
new Comparator<User>() { ... }
```

UniChain eliminates both requirements.

---

## Sorting by a Property

```java
UniChain<User> users = UniChain.create();

users.enhancedSort(User::getId);
```

---

## Sorting by Name

```java
users.enhancedSort(User::getName);
```

---

## Sorting by Salary

```java
users.enhancedSort(User::getSalary);
```

---

# Available Sorting Algorithms

## cheapSort(Function)

Algorithm:

```text
Bubble Sort
```

Complexity:

```text
O(n²)
```

Recommended for:

* Small datasets
* Educational demonstrations
* Memory-sensitive workloads

---

## enhancedSort(Function)

Algorithm:

```text
Merge Sort
```

Complexity:

```text
O(n log n)
```

Recommended for:

* Enterprise applications
* Large datasets
* Production workloads

---

# Natural Sorting

For Java types that already implement Comparable:

```java
UniChain<Integer> numbers = UniChain.create();

numbers.addAtLast(50);
numbers.addAtLast(10);

numbers.enhancedSort();
```

Output:

```text
10
50
```

Supported examples:

* Integer
* Long
* Double
* Float
* String
* LocalDate
* LocalDateTime

---

# Runtime Safety Guards

CodeKit protects applications from accidental misuse.

Example:

```java
UniChain<User> users = UniChain.create();

users.enhancedSort();
```

Since `User` does not implement Comparable, CodeKit safely aborts the operation and displays a diagnostic warning.

```text
CodeKit Warning:
Cannot sort natively because User does not implement Comparable.
Use the functional sorting API instead.
```

This prevents runtime failures such as:

```java
ClassCastException
```

and helps maintain application stability.

---

# Design Principles

Every CodeKit collection follows the same architectural principles.

✓ Generic First

✓ Zero-Invasive Domain Models

✓ Functional APIs

✓ Hidden Implementations

✓ Runtime Safety Guards

✓ Enterprise-Ready Performance

✓ Modern Java Design Patterns

---

# Roadmap

Upcoming collections planned for the CodeKit Collections ecosystem:

```java
BiChain<T>
StackChain<T>
QueueChain<T>
PriorityChain<T>
TreeChain<T>
```

All future collections will follow the same creation model:

```java
BiChain.create();

StackChain.create();

QueueChain.create();
```

ensuring a consistent and intuitive developer experience across the entire framework.
