package io.github.tusharnagdive.codekit.kitcollection.bichain;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * A Zero-Invasive, high-performance Doubly Linked List implementation.
 * <p>
 * This structure supports bidirectional traversal and concurrent searching,
 * making it ideal for performance-sensitive applications where data
 * manipulation must be both flexible and memory-efficient.
 * </p>
 * * @param <T> The type of elements held in this collection.
 */
public sealed interface BiChain<T> extends Iterable<T> permits BiChainImpl {

    /**
     * Stream Support
     * */
    default Stream<T> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    /**
     * Creates a new, empty BiChain instance.
     * @param <T> The element type.
     * @return A new {@link BiChain}.
     */
    static <T> BiChain<T> create() { return new BiChainImpl<>(); }

    /**
     * Creates a BiChain initialized with elements from an existing Iterable.
     * @param elements The source collection.
     * @param <T> The element type.
     * @return A {@link BiChain} populated with the provided data.
     */
    static <T> BiChain<T> from(Iterable<T> elements) {
        BiChain<T> chain = create();
        for (T element : elements) {
            chain.addAtLast(element);
        }
        return chain;
    }

    /** * @return The current number of nodes in O(1) time.
     */
    int size();

    /** * Exports the chain to a standard {@link List} in forward order.
     * @return A {@link List} representation of the chain.
     */
    List<T> toList();

    /** * Appends data to the end of the chain.
     * @param data The element to add.
     */
    void addAtLast(T data);

    /** * Prepends data to the beginning of the chain.
     * @param data The element to add.
     */
    void addAtFirst(T data);

    /** * Reverses the structure of the list in-place.
     * <p>Complexity: O(n)</p>
     */
    void reverse();

    /**
     * Updates an element by locating it via a selector and applying a mutation.
     * <p>Usage: {@code chain.update(User::getId, 101, u -> u.setName("New"));}</p>
     * @param selector Function to extract a field (e.g., {@code User::getId}).
     * @param matchValue The value to search for.
     * @param mutator {@link Consumer} to modify the found element.
     * @param <R> The type of the field.
     * @return {@code true} if updated, {@code false} if not found.
     */
    <R> boolean update(Function<T, R> selector, R matchValue, Consumer<T> mutator);

    /**
     * Replaces the data payload of the first found element matching the selector.
     * @param selector Function to extract a field.
     * @param matchValue The value to search for.
     * @param newData The new object to swap in.
     * @param <R> The type of the field.
     * @return {@code true} if replaced, {@code false} if not found.
     */
    <R> boolean replace(Function<T, R> selector, R matchValue, T newData);

    /**
     * Locates an element using a concurrent bidirectional search (O(n/2)).
     * <p>Spawns virtual threads to scan from both ends simultaneously.</p>
     * @param selector The field selector.
     * @param matchValue The value to match.
     * @param <R> The type of the field.
     * @return The element if found, {@code null} otherwise.
     */
    <R> T get(Function<T, R> selector, R matchValue);

    T get(T value);

    /** * Inserts data after a specific value is found.
     * @param data The target value to find.
     * @param value The new data to insert after target.
     */
    void insertAfter(T data, T value);

    /** * Inserts data before a specific value is found.
     * @param data The target value to find.
     * @param value The new data to insert before target.
     */
    void insertBefore(T data, T value);

    /** * Inserts data at a specific index.
     * @param data The element to insert.
     * @param targetedIndex The zero-based index.
     */
    void insertAtIndex(T data, Integer targetedIndex);

    /** Removes the head node. */
    void removeFirst();

    /** Removes the tail node. */
    void removeLast();

    /** * Deletes a specific node reference in O(1) time.
     * @param node The node instance to remove.
     */
    void removeNode(BiNode<T> node);

    /** * Removes the first occurrence of the data value.
     * @param data The value to remove.
     */
    void removeByValue(T data);

    /** * Removes an element located via a selector.
     * @param selector Field selector.
     * @param matchValue Value to match.
     */
    <R> void removeByValue(Function<T, R> selector, R matchValue);

    /** Prints list forward from head to tail. */
    void biChainPrintForward();

    /** Prints list backward from tail to head. */
    void biChainPrintBackward();

    /** sorting the BiChain</>. */
    <R extends Comparable<R>> void sortBy(Function<T, R> selector);

    /** sort for wrapper objects like String, Integer etc the BiChain</>. */
    void sort();

    /** unique() Remove Duplicates. */
    <R> void unique(Function<T, R> selector);

    T findFromLast(T value);
    <R> T findFromLast(Function<T, R> selector, R matchValue);
    T findFromFirst(T value);
    <R> T findFromFirst(Function<T, R> selector, R matchValue);
}