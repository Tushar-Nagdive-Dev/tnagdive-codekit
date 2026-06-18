package io.github.tusharnagdive.codekit.kitcollection.bichain;

public final class BiNode<T> {
    public T data;
    public BiNode<T> next;
    public BiNode<T> prev;

    public BiNode(T data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }
}
