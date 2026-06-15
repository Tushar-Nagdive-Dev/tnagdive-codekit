package io.github.tusharnagdive.codekit.kitcollection.node;

public class UniNode<T> {
    public T data;
    public UniNode<T> next;

    public UniNode(T data) {
        this.data = data;
        this.next = null;
    }
}
