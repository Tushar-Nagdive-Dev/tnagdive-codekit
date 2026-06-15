package io.github.tusharnagdive.codekit.kitcollection;

import io.github.tusharnagdive.codekit.kitcollection.baseview.UniChainImpl;
import io.github.tusharnagdive.codekit.kitcollection.struct.UniChain;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class KitCollections {
    private static final Map<Class<?>, Supplier<?>> registry = new HashMap<>();

    static {
        registry.put(UniChain.class, UniChainImpl::new);
    }

    /**
     * The Magic Factory Method.
     * Uses Target-Type Inference to return fully generic data structures cleanly.
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(Class<T> collectionType) {
        Supplier<?> supplier = registry.get(collectionType);
        if (supplier == null) {
            throw new IllegalArgumentException(String.format("No KitCollection found for interface: %s", collectionType.getSimpleName()));
        }
        return (T) supplier.get();
    }
}
