package io.github.tusharnagdive.codekit.kitcollection.struct;

public interface UniChain<T> {
    Integer length();

    void uniChainPrint();

    void addAtFirst(T data);

    void addAtLast(T data);

    void insertAfter(T value, T data);

    void insertBefore(T value, T data);

    void insertAtIndex(T value, Integer index);

    void removeFirst();

    void removeLast();

    void removeByValue(T value);

    void removeAtIndex(Integer index);

    void clearSequentially();

    void clearInstantly();

    void retrieveByValue(T value);

    void retrieveAtIndex(Integer index);

    T getNthEnd(Integer nthValue);

    void updateAtNode(Integer nodeNum, T value);

    void reverse();

    void cheapSort();

    void enhancedSort();
}
