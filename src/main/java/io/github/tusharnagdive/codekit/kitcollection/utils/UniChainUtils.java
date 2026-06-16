package io.github.tusharnagdive.codekit.kitcollection.utils;

import io.github.tusharnagdive.codekit.kitcollection.node.UniNode;
import java.util.Comparator;

public class UniChainUtils {

    public static <T> UniNode<T> mergeSort(UniNode<T> head, Comparator<T> comparator) {
        if (head == null || head.next == null) {
            return head;
        }

        UniNode<T> middle = split(head);
        UniNode<T> nextOfMiddle = middle.next;
        middle.next = null;

        UniNode<T> leftNode = mergeSort(head, comparator);
        UniNode<T> rightNode = mergeSort(nextOfMiddle, comparator);

        return merge(leftNode, rightNode, comparator);
    }

    private static <T> UniNode<T> split(UniNode<T> head) {
        if (head == null) {
            return head;
        }
        UniNode<T> slow = head;
        UniNode<T> fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    private static <T> UniNode<T> merge(UniNode<T> left, UniNode<T> right, Comparator<T> comparator) {
        UniNode<T> sortedSlNode = new UniNode<>(null);
        UniNode<T> tail = sortedSlNode;

        while (left != null && right != null) {
            if(comparator.compare(left.data, right.data) <= 0) {
                tail.next = left;
                left = left.next;
            } else {
                tail.next = right;
                right = right.next;
            }
            tail = tail.next;
        }

        if(left != null) { tail.next = left; }
        if(right != null) { tail.next = right; }

        return sortedSlNode.next;
    }
}