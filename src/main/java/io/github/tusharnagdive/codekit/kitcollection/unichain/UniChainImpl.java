package io.github.tusharnagdive.codekit.kitcollection.unichain;

import io.github.tusharnagdive.codekit.annotate.KitComponent;
import io.github.tusharnagdive.codekit.kitcollection.utils.UniChainUtils;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

@KitComponent(singleton = false)
final class UniChainImpl<T> implements UniChain<T> {

    public UniNode<T> head;

    public UniChainImpl() {}

    @Override
    public UniNode<T> getHead() { return this.head; }

    @Override
    public Integer indexOf(T value) {
        return indexOf(x -> x, value);
    }

    @Override
    public <R> Integer indexOf(Function<T, R> selector, R value) {
        Integer index = 0;
        UniNode<T> current = this.head;

        while (current != null) {
            R returnValue = selector.apply(current.data);
            if(Objects.equals(returnValue, value)) {
                return index;
            }
            current = current.next;
            index++;
        }

        return -1;
    }

    @Override
    public Integer length() {
        Integer length = 0;
        UniNode<T> current = head;
        while (current != null) {
            length++;
            current = current.next;
        }
        return length;
    }

    @Override
    public void uniChainPrint() {
        UniNode<T> current = head;
        while (current != null) {
            System.out.print("("+current.data+")" + " --> ");
            current = current.next;
        }
        System.out.println("[X]");
    }

    @Override
    public void addAtFirst(T data) {
        UniNode<T> newNode = new UniNode<>(data);
        newNode.next = head;
        head = newNode;
    }

    @Override
    public void addAtLast(T data) {
        UniNode<T> newNode = new UniNode<>(data);
        if(this.head == null){
            head = newNode;
            return;
        }
        UniNode<T> current = head;
        while (current.next != null){
            current = current.next;
        }
        current.next = newNode;
    }

    @Override
    public void insertAfter(T value, T data) {
        if(head == null) {
            System.out.println("SL List is empty");
            return;
        }
        UniNode<T> current = head;
        boolean isPresent = false;
        while (current != null) {
            if (current.data.equals(value)) {
                isPresent = true;
                UniNode<T> newNode = new UniNode<>(data);
                newNode.next = current.next;
                current.next = newNode;
                break;
            }
            current = current.next;
        }
        if(!isPresent) {
            System.out.println("Provided value not present, data added at the end of the list");
            addAtLast(data);
        }
    }

    @Override
    public void insertBefore(T value, T data) {
        if (head == null) {
            System.out.println("SL List is empty");
            return;
        }
        if (head.data.equals(value)) {
            addAtFirst(data);
            return;
        }
        UniNode<T> current = head;
        boolean isPresent = false;
        while (current.next != null) {
            if (current.next.data.equals(value)) {
                isPresent = true;
                UniNode<T> newNode = new UniNode<>(data);
                newNode.next = current.next;
                current.next = newNode;
                break;
            }
            current = current.next;
        }
        if (!isPresent) {
            System.out.println("Provided value not present, data added at the end of the list");
            addAtLast(data);
        }
    }

    @Override
    public void insertAtIndex(T value, Integer targetIndex) {
        if (targetIndex == 0) {
            addAtFirst(value);
            return;
        }
        if (head == null) {
            System.out.println("SL List is empty. Cannot insert at index " + targetIndex);
            return;
        }
        UniNode<T> current = head;
        int index = 0;
        while (current != null) {
            if ((index + 1) == targetIndex) {
                UniNode<T> newNode = new UniNode<>(value);
                newNode.next = current.next;
                current.next = newNode;
                return;
            }
            current = current.next;
            index++;
        }
        System.out.println("Error: Target index " + targetIndex + " is out of bounds.");
    }

    @Override
    public void removeFirst() {
        if (head == null) {
            System.out.println("SL List is already empty");
        } else {
            head = head.next;
        }
    }

    @Override
    public void removeLast() {
        if (head == null) {
            System.out.println("SL List is already empty");
            return;
        }
        if (head.next == null) {
            head = null;
            return;
        }
        UniNode<T> current = head;
        while (current.next.next != null) {
            current = current.next;
        }
        current.next = null;
    }

    @Override
    public void removeByValue(T value) {
        if (head == null) {
            System.out.println("SL List is already empty");
            return;
        }
        if (head.data.equals(value)) {
            head = head.next;
            return;
        }
        UniNode<T> current = head;
        while (current != null && current.next != null) {
            if (current.next.data.equals(value)) {
                current.next = current.next.next;
                break;
            }
            current = current.next;
        }
    }

    @Override
    public void removeAtIndex(Integer targetIndex) {
        if (head == null) {
            System.out.println("SL List is already empty");
            return;
        }
        if(head != null && targetIndex.equals(0)) {
            head = head.next;
            return;
        }
        UniNode<T> current = head;
        int index = 0;
        while (current != null) {
            if ((index + 1) == targetIndex) {
                current.next = current.next.next;
                return;
            }
            current = current.next;
            index++;
        }
        System.out.println("Error: Target index " + targetIndex + " is out of bounds.");
    }

    @Override
    public void clearSequentially() {
        UniNode<T> current = head;
        while (current != null) {
            UniNode<T> nextNode = current.next;
            current.data = null;
            current.next = null;
            current = nextNode;
        }
        head = null;
        System.out.println("List cleared sequentially.");
    }

    @Override
    public void clearInstantly() {
        head = null;
        System.out.println("List cleared instantly by the Garbage Collector.");
    }

    @Override
    public T retrieveByValue(T value) {
        return retrieveByValue(x -> x, value);
    }

    @Override
    public <R> T retrieveByValue(Function<T, R> selector, R matchValue) {
        if (head == null) {
            throw new NullPointerException("UniChain is empty");
        }

        UniNode<T> current = head;
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
    public T retrieveAtIndex(Integer targetIndex) {
        if (head == null) {
            throw new NullPointerException("UniChain is null");
        }
        UniNode<T> current = head;
        int index = 0;
        while (current != null) {
            if(index == targetIndex) {
                return (T) current.data;
            }
            current = current.next;
            index++;
        }
        throw new IndexOutOfBoundsException("Index " + targetIndex + " is out of bounds.");
    }

    @Override
    public T getNthEnd(Integer nthValue) {
        if (head == null) {
            throw new IllegalArgumentException("SL List is empty");
        }
        if(nthValue == null || nthValue <= 0) {
            throw new IllegalArgumentException("nth value can't be null, 0, and  negative");
        }
        int diff = 1;
        UniNode<T> main = head;
        UniNode<T> reference = head;
        while (main != null) {
            if (diff > nthValue) {
                reference = reference.next;
            }
            main = main.next;
            diff++;
        }
        if(nthValue >= diff) {
            throw new IllegalArgumentException("nth value out of bounds");
        }
        return reference.data;
    }

    @Override
    public void updateAtNode(Integer nodeNum, T value) {
        if (head == null) {
            throw new IllegalStateException("Cannot update: The linked list is empty.");
        }
        if (nodeNum == null || nodeNum <= 0) {
            throw new IllegalArgumentException("nodeNum must be 1 or greater.");
        }
        UniNode<T> current = head;
        for (int i = 1; i < nodeNum && current != null; i++) {
            current = current.next;
        }
        if (current != null) {
            current.data = value;
        } else {
            throw new IllegalArgumentException("nodeNum is out of bounds.");
        }
    }

    @Override
    public void reverse() {
        if (head == null) {
            throw new IllegalArgumentException("SL List is empty");
        }
        UniNode<T> current = head;
        UniNode<T> prev = null;
        UniNode<T> next = null;

        while (current != null) {
            next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        head = prev;
    }

    @Override
    public void concatenate(UniChain<T> uniChain) {
        if(uniChain == null || uniChain.getHead() == null) { return; }
        if(this.head == null) {
            this.head = uniChain.getHead();
            return;
        }
        UniNode<T> current = head;
        while (current.next != null) {
            current = current.next;
        }
        current.next = uniChain.getHead();
    }

    @Override
    public boolean isUniChainCircular() {
        if(head == null || head.next == null) { return false; }
        UniNode<T> slow = head;
        UniNode<T> fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if(slow == fast) { return true; }
        }
        return false;
    }

    @Override
    public void removeDuplicates() {
        removeDuplicates(x -> x);
    }

    @Override
    public <R> void removeDuplicates(Function<T, R> selector) {
        if (head == null || head.next == null) return;

        Set<R> seen = new HashSet<>();
        UniNode<T> current = head;
        UniNode<T> prev = null;

        while (current != null) {
            R key = selector.apply(current.data);

            if (seen.contains(key)) {
                // Duplicate found: link previous to the one after current
                prev.next = current.next;
            } else {
                // New item: track it and move prev forward
                seen.add(key);
                prev = current;
            }
            current = current.next;
        }
    }

    @Override
    public boolean hasDuplicate() {
        if (head == null || head.next == null) { return false; }
        HashSet<T> seen = new HashSet<>();
        UniNode<T> current = head;
        while (current != null) {
            if(!seen.add(current.data)) { return true; }
            current = current.next;
        }
        return false;
    }

    // --- SORTING IMPLEMENTATIONS ---

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void cheapSort() {
        if (head == null || head.next == null) { return; }

        if (!(head.data instanceof Comparable)) {
            System.err.println("CodeKit Warning: Cannot sort natively because "
                    + head.data.getClass().getSimpleName() + " does not implement Comparable. Use the functional method.");
            return;
        }

        boolean isSwapped;
        do {
            isSwapped = false;
            UniNode<T> current = head;
            while (current != null && current.next != null) {
                Comparable currentData = (Comparable) current.data;
                if (currentData.compareTo(current.next.data) > 0) {
                    isSwapped = true;
                    T temp = current.data;
                    current.data = current.next.data;
                    current.next.data = temp;
                }
                current = current.next;
            }
        } while (isSwapped);
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public void enhancedSort() {
        if (head == null || head.next == null) { return; }
        if (!(head.data instanceof Comparable)) {
            System.err.println("CodeKit Warning: Cannot sort natively because "
                    + head.data.getClass().getSimpleName() + " does not implement Comparable. Use the functional method.");
            return;
        }
        this.head = UniChainUtils.mergeSort(this.head, (Comparator<T>) Comparator.naturalOrder());
    }

    @Override
    public <U extends Comparable<? super U>> void cheapSort(Function<? super T, ? extends U> keyExtractor) {
        if (head == null || head.next == null) { return; }
        Comparator<T> secretComparator = Comparator.comparing(keyExtractor);
        boolean isSwapped;
        do {
            isSwapped = false;
            UniNode<T> current = head;
            while (current != null && current.next != null) {
                if (secretComparator.compare(current.data, current.next.data) > 0) {
                    isSwapped = true;
                    T temp = current.data;
                    current.data = current.next.data;
                    current.next.data = temp;
                }
                current = current.next;
            }
        } while (isSwapped);
    }

    @Override
    public <U extends Comparable<? super U>> void enhancedSort(Function<? super T, ? extends U> keyExtractor) {
        Comparator<T> secretComparator = Comparator.comparing(keyExtractor);
        this.head = UniChainUtils.mergeSort(this.head, secretComparator);
    }
}