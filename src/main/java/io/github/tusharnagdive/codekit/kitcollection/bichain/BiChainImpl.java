package io.github.tusharnagdive.codekit.kitcollection.bichain;

import io.github.tusharnagdive.codekit.annotate.KitComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;

@KitComponent(singleton = false)
final class BiChainImpl<T> implements BiChain<T> {
    public BiNode<T> head;
    public BiNode<T> tail;
    private int size = 0;

    @Override
    public int size() {
        return this.size;
    }

    // In BiChainImpl
    @Override
    public List<T> toList() {
        List<T> result = new ArrayList<>();
        BiNode<T> current = head;
        while(current != null) {
            size++;
            result.add(current.data);
            current = current.next;
        }
        return result;
    }

    @Override
    public void addAtLast(T data) {
        BiNode<T> newNode = new BiNode<T>(data);
        if (head == null) {
            head = tail = newNode;
        } else {
            newNode.prev = tail; // Point back to current tail
            tail.next = newNode; // Current tail points to new node
            tail = newNode;      // Update tail reference
        }
        size++;
    }

    @Override
    public void biChainPrintForward() {
        BiNode<T> current = this.head;
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
        BiNode<T> current = this.tail;
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

    @Override
    public void addAtFirst(T data) {
        BiNode<T> newNode = new BiNode<T>(data);

        if (head == null) {
            head = newNode;
            tail = newNode;
            return;
        }

        head.prev = newNode;
        newNode.next = head;
        head = newNode;
        size++;
    }

    @Override
    public T findFromLast(T value) {
        return findFromLast(x -> x, value);
    }

    @Override
    public <R> T findFromLast(Function<T, R> selector, R matchValue) {
        if (head == null) {
            throw new NullPointerException("UniChain is empty");
        }

        BiNode<T> current = head;
        while (current != null) {
            // Extract the field value (e.g., current.data.getId())
            R fieldValue = selector.apply(current.data);

            // Safely compare the extracted field with the desired matchValue
            if (Objects.equals(fieldValue, matchValue)) {
                return current.data;
            }
            current = current.next;
        }
        return null; // Not found
    }

    @Override
    public T findFromFirst(T value) {
        return findFromFirst(x -> x, value);
    }

    @Override
    public <R> T findFromFirst(Function<T, R> selector, R matchValue) {
        if (head == null) {
            throw new NullPointerException("UniChain is empty");
        }

        BiNode<T> current = tail;
        while (current != null) {
            // Extract the field value (e.g., current.data.getId())
            R fieldValue = selector.apply(current.data);

            // Safely compare the extracted field with the desired matchValue
            if (Objects.equals(fieldValue, matchValue)) {
                return current.data;
            }
            current = current.prev;
        }
        return null; // Not found
    }

    @Override
    public void insertAfter(T data, T value) {
        if(head == null) {
            throw new NullPointerException("BiChain is null");
        }

        BiNode<T> current = this.head;
        while (current != null) {
            if (current.data.equals(data)) {
                BiNode<T> newNode = new BiNode<T>(value);
                // Connect newNode to the node ahead of it
                newNode.next = current.next;
                //Connect newNode to the current node behind it
                newNode.prev = current;
                // 5. Update the node ahead (if it exists) to point back to newNode
                if (newNode.next != null) {
                    newNode.next.prev = newNode;
                }
                current.next = newNode;
                size++;
                return;
            }
            current = current.next;
        }
    }

    @Override
    public void insertBefore(T data, T value) {
        if (head == null) {
            throw new NullPointerException("BiChain is null");
        }

        BiNode<T> current = this.head;

        while (current != null) {
            // 1. Find the target node
            if (current.data.equals(data)) {
                // 2. Create the new node
                BiNode<T> newNode = new BiNode<T>(value);

                // 3. Connect newNode to the nodes around it
                newNode.prev = current.prev; // Look backward
                newNode.next = current;      // Look forward to current

                // 4. Safely connect the node BEFORE current to newNode
                if (current.prev != null) {
                    // We are in the middle of the list
                    current.prev.next = newNode;
                } else {
                    // Edge Case: We are inserting before the very first node!
                    // This means newNode is now the official head of the list.
                    this.head = newNode;
                }

                // 5. Connect current back to the newNode
                current.prev = newNode;

                // 6. Insertion complete, exit the method
                size++;
                return;
            }
            current = current.next;
        }
    }

    @Override
    public void insertAtIndex(T data, Integer targetedIndex) {
        // 1. Prevent negative indices right away
        if (targetedIndex < 0) {
            throw new IndexOutOfBoundsException("Index cannot be negative.");
        }

        // 2. Handle the empty list scenario safely
        if (head == null) {
            if (targetedIndex == 0) {
                head = new BiNode<T>(data);
                return;
            } else {
                throw new IndexOutOfBoundsException("List is empty. Cannot insert at index: " + targetedIndex);
            }
        }

        Integer index = 0;
        BiNode<T> current = this.head;

        while (current != null) {
            // 3. We found the exact spot to insert BEFORE the current node
            if (targetedIndex.equals(index)) {
                BiNode<T> newNode = new BiNode<T>(data); // FIXED: Passing 'data'

                // Your excellent pointer rewiring logic:
                newNode.prev = current.prev;
                newNode.next = current;

                if (current.prev != null) {
                    current.prev.next = newNode;
                } else {
                    this.head = newNode;
                }
                current.prev = newNode;
                size++;
                return;
            }

            // 4. EDGE CASE: We reached the last node, and they want to insert at the very end
            if (current.next == null && targetedIndex.equals(index + 1)) {
                BiNode<T> newNode = new BiNode<T>(data);
                current.next = newNode;
                newNode.prev = current;
                size++;
                return;
            }

            // Move to the next node
            current = current.next;

            // 5. FIXED: Increment the counter so we don't loop infinitely!
            index++;
        }

        // If we make it here, they asked for an index way bigger than our list
        throw new IndexOutOfBoundsException("Index out of bounds: " + targetedIndex);
    }

    @Override
    public void removeFirst() {
        if (head == null) {
            throw new NullPointerException("BiChain is null");
        }
        this.head = this.head.next;
        if (this.head != null) {
            this.head.prev = null;
        } else  {
            this.tail = null;
        }
        size--;
    }

    @Override
    public <R> boolean update(Function<T, R> selector, R matchValue, Consumer<T> mutator) {
        T foundData = get(selector, matchValue);

        if (foundData != null) {
            mutator.accept(foundData);
            return true;
        }
        return false;
    }

    // For total data replacement
    public <R> boolean replace(Function<T, R> selector, R matchValue, T newData) {
        // Traverse manually to keep the reference
        BiNode<T> current = head;
        while (current != null) {
            if (Objects.equals(selector.apply(current.data), matchValue)) {
                current.data = newData; // Direct replacement
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public void removeLast() {
        if(tail == null) {
            throw new NullPointerException("BiChain is null");
        }

        this.tail = this.tail.prev;
        if (this.tail != null) {
            this.tail.next = null;
        }  else  {
            this.head = null;
        }
        size--;
    }

    /**
     * Deletion by Reference - O(1) Time Complexity
     * Instantly bypasses the target node. No loops required.
     */
    @Override
    public void removeNode(BiNode<T> nodeToDelete) {
        if (nodeToDelete == null) return;

        if (this.head == nodeToDelete) this.head = nodeToDelete.next;
        if (this.tail == nodeToDelete) this.tail = nodeToDelete.prev;

        if (nodeToDelete.next != null) nodeToDelete.next.prev = nodeToDelete.prev;
        if (nodeToDelete.prev != null) nodeToDelete.prev.next = nodeToDelete.next;

        // Clean up ties to the list to help the Garbage Collector
        nodeToDelete.next = null;
        nodeToDelete.prev = null;
        size--;
    }

    @Override
    public void removeByValue(T data) {
        removeByValue(x -> x, data);
    }

    public <R> void removeByValue(Function<T, R> selector, R matchValue) {
        if (this.head == null) return;

        BiNode<T> current = this.head;
        while (current != null) {
            R fieldValue = selector.apply(current.data);

            if (Objects.equals(fieldValue, matchValue)) {
                removeNode(current); // Use your existing O(1) delete logic
                size--;
                return; // Exit after removing the first match
            }
            current = current.next;
        }
    }

    @Override
    public T get(T value) {
        return get(x -> x, value);
    }

    @Override
    public <R> T get(Function<T, R> selector, R matchValue) {
        AtomicReference<T> result = new AtomicReference<>();

        Thread forward = Thread.startVirtualThread(() -> {
            BiNode<T> current = this.head;
            while (current != null && result.get() == null) {
                if(Objects.equals(selector.apply(current.data), matchValue)) {
                    result.compareAndSet(null, current.data);
                }
                current = current.next;
            }
        });

        Thread backward = Thread.startVirtualThread(() -> {
           BiNode<T> current = this.tail;
           while (current != null && result.get() == null) {
               if(Objects.equals(selector.apply(current.data), matchValue)) {
                   result.compareAndSet(null, current.data);
               }
               current = current.prev;
           }
        });
        try{
            forward.join();
            backward.join();
        }catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }

        return result.get();
    }

    @Override
    public void reverse() {
        if (head == null || head.next == null) return; // Empty or single-node list

        BiNode<T> current = head;
        BiNode<T> temp = null;

        // Swap next and prev for every node
        while (current != null) {
            // Swap pointers
            temp = current.prev;
            current.prev = current.next;
            current.next = temp;

            // Move to the next node (which is actually in current.prev now)
            current = current.prev;
        }

        // After the loop, temp points to the node before the old head.
        // So temp.prev is the new head.
        if (temp != null) {
            tail = head; // Old head is now the tail
            head = temp.prev; // New head is the last node visited
        }
    }
}
