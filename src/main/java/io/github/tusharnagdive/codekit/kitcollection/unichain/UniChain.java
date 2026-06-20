package io.github.tusharnagdive.codekit.kitcollection.unichain;

import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * The core interface for the UniChain framework.
 * UniChain provides a zero-invasive, functional-first approach to singly linked list management.
 *
 * @param <T> The type of objects contained in the chain.
 */
public sealed interface UniChain<T> extends Iterable<T> permits UniChainImpl{

    /**
     * Stream Support
     * */
    default Stream<T> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    /**
     * Parallel Stream Support
     * */
    default Stream<T> parallelStream() {
        return StreamSupport.stream(spliterator(), true);
    }

    /**
     * Creates a new instance of UniChain.
     * <pre>{@code UniChain<User> chain = UniChain.create(); }</pre>
     */
    static <T> UniChain<T> create() {
        return new UniChainImpl<>();
    }

    /** @return The current size of the chain. */
    int size();

    /** @return The head node of the chain. */
    UniNode<T> getHead();

    /**
     * Finds the index of a value using .equals().
     * <pre>{@code int idx = chain.indexOf("Data"); }</pre>
     */
    Integer indexOf(T value);

    /**
     * Functional index lookup by field.
     * <pre>{@code int idx = chain.indexOf(User::getId, 101); }</pre>
     * @param <R> The type of the field.
     */
    <R> Integer indexOf(Function<T, R> selector, R value);

    /** Prints the chain structure to console. */
    void uniChainPrint();

    // --- Mutation Methods ---

    /** Adds data to the start. */
    void addAtFirst(T data);

    /** Adds data to the end. */
    void addAtLast(T data);

    /** * Inserts after specific value.
     * <pre>{@code chain.insertAfter("target", "new"); }</pre>
     */
    void insertAfter(T value, T data);

    /** Inserts before specific value. */
    void insertBefore(T value, T data);

    /** Inserts at specific 0-based index. */
    void insertAtIndex(T value, Integer index);

    // --- Removal Methods ---

    /** Removes the head node. */
    void removeFirst();

    /** Removes the tail node. */
    void removeLast();

    /** Removes the first node matching the provided value. */
    void removeByValue(T value);

    /** Removes the node at index. */
    void removeAtIndex(Integer index);

    /** Iteratively clears the list (safer for deep chains). */
    void clearSequentially();

    /** Sets head to null for immediate GC. */
    void clearInstantly();

    // --- Retrieval ---

    /** Retrieves object matching value. */
    T retrieveByValue(T value);

    /** Retrieves object matching value by ok field. */
    <R> T retrieveByValue(Function<T, R> selector, R matchValue);

    /** Retrieves object at index. */
    T retrieveAtIndex(Integer index);

    /** * Gets element from end (1 = tail).
     * <pre>{@code User last = chain.getNthEnd(1); }</pre>
     */
    T getNthEnd(Integer nthValue);

    /** Updates data at node index. */
    void updateAtNode(Integer nodeNum, T value);

    /** Reverses the list in-place. */
    void reverse();

    /** Appends another chain to this one. */
    void concatenate(UniChain<T> uniChain);

    /** Checks for cycle references. */
    boolean isUniChainCircular();

    /** Removes duplicates using native equality. */
    void removeDuplicates();

    /** * Removes duplicates based on field.
     * <pre>{@code chain.removeDuplicates(User::getEmail); }</pre>
     */
    <R> void removeDuplicates(Function<T, R> selector);

    /** Checks if duplicates exist. */
    boolean hasDuplicate();

    // --- The Sorting Engine ---

    /** Legacy sort for Comparable objects. */
    void cheapSort();

    /** Optimized sort for Comparable objects. */
    void enhancedSort();

    /** * Functional sort by field.
     * <pre>{@code chain.enhancedSort(User::getAge); }</pre>
     * @param <U> The type of the field (must be Comparable).
     */
    <U extends Comparable<? super U>> void cheapSort(Function<? super T, ? extends U> keyExtractor);

    /** * Functional merge-sort by field (Recommended).
     * <pre>{@code chain.enhancedSort(User::getSalary); }</pre>
     * @param <U> The type of the field (must be Comparable).
     */
    <U extends Comparable<? super U>> void enhancedSort(Function<? super T, ? extends U> keyExtractor);
}