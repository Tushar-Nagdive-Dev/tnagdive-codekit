package io.github.tusharnagdive.codekit.kitcollection.bichain;

import io.github.tusharnagdive.codekit.annotate.KitComponent;

@KitComponent(singleton = false)
final class BiChainImpl<T> implements BiChain<T> {
    public BiNode<T> head;
    public BiNode<T> tail;
}
