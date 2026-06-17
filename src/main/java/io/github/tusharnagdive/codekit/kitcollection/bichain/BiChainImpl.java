package io.github.tusharnagdive.codekit.kitcollection.bichain;

import io.github.tusharnagdive.codekit.annotate.KitComponent;

@KitComponent(singleton = false)
final class BiChainImpl<T> implements BiChain<T> {
    public BiNode<T> head;
    public BiNode<T> tail;

    @Override
    public void addAtLast(T data) {
        BiNode<T> newNode = new BiNode<>(data);
        if (head == null) {
            head = tail = newNode;
        } else {
            newNode.prev = tail; // Point back to current tail
            tail.next = newNode; // Current tail points to new node
            tail = newNode;      // Update tail reference
        }
    }

    @Override
    public void biChainPrintForward() {
        BiNode current = this.head;
        if(current == null) {
            IO.println("(--EMPTY--)");
            return;
        }
        IO.print("\n[X] <-- ");
        while (current != null) {
            IO.print("(" + current.data + ")");

            if(current.next != null) {
                IO.print(" <---> ");
            }
            current = current.next;
        }
        IO.print(" --> [X]\n");
    }

    @Override
    public void biChainPrintBackward() {
        BiNode current = this.tail;
        if(current == null) {
            IO.println("(--EMPTY--)");
            return;
        }
        IO.print("\n[X] <-- ");
        while (current != null) {
            IO.print("(" + current.data + ")");

            if(current.prev != null) {
                IO.print(" <---> ");
            }
            current = current.prev;
        }
        IO.print(" --> [X]\n");
    }
}
