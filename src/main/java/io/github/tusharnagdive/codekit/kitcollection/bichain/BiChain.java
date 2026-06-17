package io.github.tusharnagdive.codekit.kitcollection.bichain;

public sealed interface BiChain<T> permits BiChainImpl {

    /**
     * Creates a new instance of UniChain.
     * <pre>{@code UniChain<User> chain = UniChain.create(); }</pre>
     */
    static <T> BiChain<T> create() {
        return new BiChainImpl<>();
    }
}
