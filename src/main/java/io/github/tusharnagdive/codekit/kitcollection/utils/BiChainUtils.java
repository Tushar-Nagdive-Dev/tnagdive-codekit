package io.github.tusharnagdive.codekit.kitcollection.utils;

import io.github.tusharnagdive.codekit.kitcollection.bichain.BiNode;
import java.util.function.Function;

public class BiChainUtils {

    // Added <T, R ...> here
    public static <T, R extends Comparable<R>> BiNode<T> mergeSort(BiNode<T> head, Function<T, R> selector) {
        if (head == null || head.next == null) return head;

        BiNode<T> middle = getMiddle(head);
        BiNode<T> nextToMiddle = middle.next;
        middle.next = null;
        if (nextToMiddle != null) nextToMiddle.prev = null;

        BiNode<T> left = mergeSort(head, selector);
        BiNode<T> right = mergeSort(nextToMiddle, selector);

        return sortedMerge(left, right, selector);
    }

    private static <T, R extends Comparable<R>> BiNode<T> sortedMerge(BiNode<T> a, BiNode<T> b, Function<T, R> selector) {
        if (a == null) return b;
        if (b == null) return a;

        if (selector.apply(a.data).compareTo(selector.apply(b.data)) <= 0) {
            a.next = sortedMerge(a.next, b, selector);
            if (a.next != null) a.next.prev = a;
            return a;
        } else {
            b.next = sortedMerge(a, b.next, selector);
            if (b.next != null) b.next.prev = b;
            return b;
        }
    }

    private static <T> BiNode<T> getMiddle(BiNode<T> head) {
        BiNode<T> slow = head, fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }
}