package io.github.tusharnagdive.codekit.kitcollection.bichain;

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

    void addAtLast(T data);

    void biChainPrintForward();

    void biChainPrintBackward();
}
