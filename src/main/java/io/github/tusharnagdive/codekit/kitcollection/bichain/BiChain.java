package io.github.tusharnagdive.codekit.kitcollection.bichain;

import java.util.List;
import java.util.function.Function;

public sealed interface BiChain<T> permits BiChainImpl {

    /**
     * Creates a new instance of UniChain.
     * <pre>{@code UniChain<User> chain = UniChain.create(); }</pre>
     */
    static <T> BiChain<T> create() {
        return new BiChainImpl<>();
    }

    /**
     * Creates a new BiChain initialized with the elements of the provided collection.
     * @param elements Any Iterable (List, Set, etc.)
     */
    static <T> BiChain<T> from(Iterable<T> elements) {
        BiChain<T> chain = create();
        for (T element : elements) {
            chain.addAtLast(element);
        }
        return chain;
    }

    List<T> toList();

    void addAtLast(T data);

    void biChainPrintForward();

    void biChainPrintBackward();

    void addAtFirst(T data);

    T findFromLast(T value);

    <R> T findFromLast(Function<T, R> selector, R matchValue);

    T findFromFirst(T value);

    <R> T findFromFirst(Function<T, R> selector, R matchValue);

    void insertAfter(T data, T value);

    void insertBefore(T data, T value);

    void insertAtIndex(T data, Integer targetedIndex);

    void removeFirst();

    void removeLast();

    void removeNode(BiNode<T> node);

    void removeByValue(T data);

    <R> void removeByValue(Function<T, R> selector, R matchValue);
}
