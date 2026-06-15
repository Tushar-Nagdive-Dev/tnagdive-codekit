package io.github.tusharnagdive.codekit.kitcollection.baseview;

import io.github.tusharnagdive.codekit.annotate.KitComponent;
import io.github.tusharnagdive.codekit.kitcollection.node.UniNode;
import io.github.tusharnagdive.codekit.kitcollection.struct.UniChain;
import io.github.tusharnagdive.codekit.kitcollection.utils.UniChainUtils;

@KitComponent(singleton = false)
public class UniChainImpl<T extends Comparable<T>> implements UniChain<T> {

    private UniNode<T> head;

    public UniChainImpl() {
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
        UniNode current = head;
        while (current != null) {
            System.out.print("("+current.data+")" + " --> ");
            current = current.next;
        }
        System.out.println("[X]");
    }

    @Override
    public void addAtFirst(T data) {
        /*Step 1: Create the new Node*/
        UniNode<T> newNode = new UniNode<T>(data);
        // Step 2: Point the new node's 'next' to the current head
        newNode.next = head;
        // Step 3: Shift the official 'head' reference to the new node
        head = newNode;
    }

    @Override
    public void addAtLast(T data) {
        // Step 1: Create the new node
        UniNode<T> newNode = new UniNode<T>(data);

        // Step 2: Handle the edge case (Empty List)
        if(this.head == null){
            head = newNode;
            return; // Exit the method early
        }

        UniNode<T> current = head;
        while (current.next != null){
            current = current.next;
        }
        current.next = newNode;
    }

    @Override
    public void insertAfter(T value, T data) {
        // 1. Handle empty list
        if(head == null) {
            System.out.println("SL List is empty");
            return;
        }

        UniNode<T> current = head;
        boolean isPresent = false;

        // 2. Loop until the end of the list (current != null)
        while (current != null) {
            if (current.data.equals(value)) {
                isPresent = true;

                // Only create the node when we know we need it
                UniNode<T> newNode = new UniNode<T>(data);

                // Swap pointers
                newNode.next = current.next;
                current.next = newNode;

                // Exit the loop immediately so we don't keep searching
                break;
            }
            current = current.next;
        }

        // 3. Fallback if value wasn't found
        if(!isPresent) {
            System.out.println("Provided value not present, data added at the end of the list");
            addAtLast(data);
        }
    }

    @Override
    public void insertBefore(T value, T data) {
        // 1. Handle empty list
        if (head == null) {
            System.out.println("SL List is empty");
            return;
        }

        // 2. THE MISSING EDGE CASE: What if the target is the very first node?
        if (head.data.equals(value)) {
            addAtFirst(data);
            return; // We are done!
        }

        // 3. Traverse the list, looking one step ahead
        UniNode<T> current = head;
        boolean isPresent = false;

        // We loop as long as there is a "next" node to check
        while (current.next != null) {

            // Peek ahead to see if the NEXT node is our target
            if (current.next.data.equals(value)) {
                isPresent = true;

                UniNode<T> newNode = new UniNode<T>(data);

                // Step A: Point new node to the target
                newNode.next = current.next;

                // Step B: Point current node to the new node
                current.next = newNode;

                break; // Stop searching once we insert
            }
            current = current.next;
        }

        // 4. Fallback if value wasn't found
        if (!isPresent) {
            System.out.println("Provided value not present, data added at the end of the list");
            addAtLast(data);
        }
    }

    @Override
    public void insertAtIndex(T value, Integer targetIndex) {
        // 1. Check Index 0 FIRST.
        // This safely handles inserting the very first node into an empty list!
        if (targetIndex == 0) {
            addAtFirst(value);
            return;
        }

        // 2. NOW check if the list is empty.
        // If they want index 5 but the list is empty, we reject it.
        if (head == null) {
            System.out.println("SL List is empty. Cannot insert at index " + targetIndex);
            return;
        }

        UniNode<T> current = head;
        int index = 0;

        // 3. Traverse and Count
        while (current != null) {
            // Look one step ahead
            if ((index + 1) == targetIndex) {
                UniNode<T> newNode = new UniNode<T>(value);

                // Pointer surgery
                newNode.next = current.next;
                current.next = newNode;

                return; // 'return' completely exits the method, doing the job of 'break'
            }
            current = current.next;
            index++;
        }

        // 4. Out of Bounds Fallback
        // If the loop finishes and we get here, the targetIndex was too large.
        System.out.println("Error: Target index " + targetIndex + " is out of bounds.");
    }

    @Override
    public void removeFirst() {
        if (head == null) {
            System.out.println("SL List is already empty");
        }else {
            head = head.next;
        }
    }

    @Override
    public void removeLast() {
        // 1. Empty List Check (Added the return statement)
        if (head == null) {
            System.out.println("SL List is already empty");
            return;
        }

        // 2. THE CRITICAL FIX: The Single Node Edge Case
        // If the first node has no next node, it's the ONLY node.
        if (head.next == null) {
            head = null; // Completely clear the list
            return;
        }

        // 3. Traversal
        UniNode<T> current = head;

        // We can put the look-ahead logic right in the loop condition!
        // It loops AS LONG AS the second-to-last node condition is NOT met.
        while (current.next.next != null) {
            current = current.next;
        }

        // 4. Sever the tie
        // Once the loop breaks, we know current is sitting on the second-to-last node.
        current.next = null;
    }

    @Override
    public void removeByValue(T value) {
        // 1. Added the missing return statement
        if (head == null) {
            System.out.println("SL List is already empty");
            return;
        }

        // 2. THE CORRECTED HEAD NODE CHECK
        // If the head is the target, just shift the head pointer!
        // We don't need to check current.next here. This safely handles single-node lists too.
        if (head.data.equals(value)) {
            head = head.next;
            return;
        }

        UniNode<T> current = head;

        // 3. Traversal and Bypass
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

        if(head!= null && targetIndex.equals(0)) {
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
            // Step 1: Hold the bridge (save the next node so we don't lose the list)
            UniNode<T> nextNode = current.next;
            // Step 2: Burn the bridge (destroy the current node's connections and data)
            current.data = null;
            current.next = null;
            // Step 3: Step forward
            current = nextNode;
        }
        // Step 4: Reset the master anchor now that all nodes are destroyed
        head = null;
        System.out.println("List cleared sequentially.");
    }

    @Override
    public void clearInstantly() {
        // Drop the master anchor.
        // The Garbage Collector instantly orphans the first node,
        // which orphans the second, which orphans the third, etc.
        head = null;
        System.out.println("List cleared instantly by the Garbage Collector.");
    }

    @Override
    public void retrieveByValue(T value) {
        if (head == null) {
            System.out.println("SL List is already empty");
            return;
        }
        UniNode<T> current = head;
        while (current != null) {
            if (current.data.equals(value)) {
                System.out.println("("+current.data+")");
                return;
            }
            current = current.next;
        }

        System.out.println("no data found");
    }

    @Override
    public void retrieveAtIndex(Integer targetIndex) {
        if (head == null) {
            System.out.println("SL List is empty");
            return;
        }

        UniNode<T> current = head;
        int index = 0;
        while (current != null) {
            if(index == targetIndex) {
                System.out.println("("+current.data+")");
                return;
            }
            current = current.next;
            index++;
        }

        System.out.println("Error: index out of bounds");
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
        return  reference.data;
    }

    @Override
    public void updateAtNode(Integer nodeNum, T value) {
        // 1. Guard clause: Don't convert an update into an insert
        if (head == null) {
            throw new IllegalStateException("Cannot update: The linked list is empty.");
        }
        // 2. Guard clause: Prevent zero or negative indexing bugs
        if (nodeNum == null || nodeNum <= 0) {
            throw new IllegalArgumentException("nodeNum must be 1 or greater.");
        }
        UniNode<T> current = head;
        // Traverse to the desired node
        for (int i = 1; i < nodeNum && current != null; i++) {
            current = current.next;
        }
        // 3. Update if found, otherwise throw out of bounds
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
        // Setup our placeholders before we start walking
        UniNode<T> current = head;      // We start holding the very first node
        UniNode<T> prev = null;         // There is no "previous" node yet
        UniNode<T> next = null;         // A placeholder for our safety save

        // Keep doing this shuffle until we run out of nodes
        while (current != null) {
            // STEP 1: Look ahead and save
            // Grab the address of the rest of the list so we don't lose it!
            next = current.next;
            // STEP 2: Turn around!
            // Change the current node's arrow to point backwards
            current.next = prev;
            // STEP 3: Step forward (Part 1)
            // We are done with this node, so it becomes the 'prev' for the next round
            prev = current;
            // STEP 4: Step forward (Part 2)
            // Move our 'current' hand to the saved 'nextNode' to continue down the line
            current = next;
        }
        // When the loop finishes, 'current' is null, and 'prev' is holding
        // what used to be the last node. This is our new starting point!
        head = prev;
    }

    @Override
    public void cheapSort() {
        if (head == null || head.next == null) {
            return;
        }
        boolean isSwapped;
        do {
            isSwapped = false;
            UniNode<T> current = head;
            while (current != null && current.next != null) {
                if (current.data.compareTo(current.next.data) > 0) {
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
    public void enhancedSort() {
        UniChainUtils.mergeSort(head);
    }
}
