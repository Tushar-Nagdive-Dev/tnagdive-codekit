package io.github.tusharnagdive.codekit.kitcollection.bichain;

import io.github.tusharnagdive.codekit.kitcollection.unichain.UniNode;

public final class BiNode<T> {
    public T data;
    public UniNode<T> next;
    public UniNode<T> prev;

    public BiNode(T data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }
}
