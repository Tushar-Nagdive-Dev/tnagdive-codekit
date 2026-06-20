# BiChain: Enterprise-Grade Data Orchestrator

`BiChain` is a high-performance, zero-invasive, doubly-linked list implementation designed to replace standard Java collections in performance-sensitive environments. Unlike generic collections, `BiChain` is built to support functional programming patterns and modern Java concurrency (Project Loom).

---

## 1. Why BiChain over `java.util.LinkedList`?

Standard Java `LinkedList` is a legacy implementation. `BiChain` provides a modern alternative:

* **Pincer Search ($O(n/2)$):** Standard lists scan linearly. `BiChain` uses **Virtual Threads** to scan from `head` and `tail` simultaneously, effectively cutting search latency by half.
* **Functional API:** No need for `Comparable` or `equals()` overrides in your domain models. Use method references (e.g., `User::getId`) to interact with your data.
* **Memory Efficiency:** `BiChain` is a lean, purpose-built collection. It lacks the overhead of `Deque`, `Cloneable`, and `Serializable` interfaces that bloat standard Java collections.
* **In-Place Mutation:** Functional `update` and `replace` methods eliminate boilerplate iterator loops.
* **Constant-Time Concatenation:** By maintaining an explicit `tail` pointer, `BiChain` performs list merging in $O(1)$ time, whereas standard lists require $O(n)$ traversal.

---

## 2. API Reference & Usage

### Creation

```java
// Create an empty chain
BiChain<User> chain = BiChain.create();

// Create from existing List/Iterable
BiChain<User> chain = BiChain.from(myList);

```

### Retrieval & Search

```java
// Concurrent Pincer Search (Spawns 2 Virtual Threads)
User result = chain.get(User::getId, 101);

// Standard searches
User first = chain.findFromFirst(User::getName, "Alice");
User last = chain.findFromLast(User::getName, "Alice");

```

### Functional Data Manipulation

```java
// Update: Mutate an object field without replacing the object
chain.update(User::getId, 101, u -> u.setName("New Name"));

// Replace: Swap the object at the found location
chain.replace(User::getId, 102, new User(102, "Bobby"));

```

### Data Orchestration

```java
// In-place Merge Sort (O(n log n))
chain.sortBy(User::getId);

// O(n) Deduplication (Hash-based)
chain.unique(User::getId);

// O(1) Concatenation
chain.concatenate(otherChain);

```

---

## 3. Complexity & Efficiency Analysis

`BiChain` is optimized for high-frequency data operations where performance is critical.

| Operation | Complexity | Note |
| --- | --- | --- |
| `addAtFirst` / `addAtLast` | $O(1)$ | Direct pointer modification |
| `removeNode` | $O(1)$ | No traversal; pointer bypass |
| `get` (Concurrent) | $O(n/2)$ | Bi-directional virtual thread scanning |
| `concatenate` | $O(1)$ | Instant linking of `tail` to `head` |
| `sortBy` | $O(n \log n)$ | In-place Merge Sort algorithm |
| `unique` | $O(n)$ | Linear scan using `HashSet` |

---

## 4. Enterprise Contract & Guidelines

1. **Thread Safety:** `get()` is concurrent-safe via virtual threads. However, mutations (`add`, `remove`, `sort`) are **not** thread-safe. Use `synchronized` blocks or `ReadWriteLock` if multiple threads modify the list simultaneously.
2. **Memory Management:** `concatenate` invalidates the source chain (`size = 0`, `head = null`) to ensure data ownership is transferred cleanly and to prevent "Split-Brain" pointer corruption.
3. **Error Handling:** The library throws standard `NoSuchElementException` and `IndexOutOfBoundsException` to maintain compatibility with standard Java collection behaviors.
4. **Generics:** All functional methods enforce `Comparable` bounds (e.g., `<R extends Comparable<R>>`), ensuring your sorting and deduplication logic never fails at runtime due to type mismatches.