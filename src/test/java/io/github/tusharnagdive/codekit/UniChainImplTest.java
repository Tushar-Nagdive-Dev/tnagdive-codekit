package io.github.tusharnagdive.codekit;

import io.github.tusharnagdive.codekit.kitcollection.baseview.UniChainImpl;
import io.github.tusharnagdive.codekit.kitcollection.node.UniNode;
import io.github.tusharnagdive.codekit.kitcollection.struct.UniChain;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class UniChainImplTest {

    private UniChainImpl<Integer> uniChain;
    private UniChainImpl<String> uniChainString;

//    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
//    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        uniChain = new UniChainImpl<>();
        uniChainString = new UniChainImpl<>();
//        System.setOut(new PrintStream(outContent));
    }

//    @AfterEach
//    void tearDown() {
//        System.setOut(originalOut);
//    }

    @Test
    void testAddAtFirstAndLength() {
        assertEquals(0, uniChain.length());
        uniChain.addAtFirst(10);
        uniChain.addAtFirst(20);

        assertEquals(2, uniChain.length());
        assertEquals(20, uniChain.getHead().data);
    }

    @Test
    void testAddAtLast() {
        uniChain.addAtLast(10);
        uniChain.addAtLast(20);

        assertEquals(2, uniChain.length());
        assertEquals(10, uniChain.getHead().data);
        assertEquals(20, uniChain.getHead().next.data);
    }

    @Test
    void testInsertAfter() {
        uniChain.addAtLast(10);
        uniChain.addAtLast(30);
        uniChain.insertAfter(10, 20); // Insert 20 after 10

        assertEquals(3, uniChain.length());
        assertEquals(20, uniChain.getHead().next.data);
    }

    @Test
    void testInsertBefore() {
        uniChain.addAtLast(10);
        uniChain.addAtLast(30);
        uniChain.insertBefore(30, 20); // Insert 20 before 30

        assertEquals(3, uniChain.length());
        assertEquals(20, uniChain.getHead().next.data);
    }

    @Test
    void testInsertAtIndex() {
        uniChain.addAtLast(10);
        uniChain.addAtLast(30);
        uniChain.insertAtIndex(20, 1); // Insert 20 at index 1

        assertEquals(3, uniChain.length());
        assertEquals(20, uniChain.getHead().next.data);
    }

    // --- 3. Removal Tests ---

    @Test
    void testRemoveFirst() {
        uniChain.addAtLast(10);
        uniChain.addAtLast(20);
        uniChain.removeFirst();

        assertEquals(1, uniChain.length());
        assertEquals(20, uniChain.getHead().data);
    }

    @Test
    void testRemoveLast() {
        uniChain.addAtLast(10);
        uniChain.addAtLast(20);
        uniChain.addAtLast(30);
        uniChain.removeLast();

        assertEquals(2, uniChain.length());
        assertNull(uniChain.getHead().next.next);
    }

    @Test
    void testRemoveByValue() {
        uniChain.addAtLast(10);
        uniChain.addAtLast(20);
        uniChain.addAtLast(30);
        uniChain.removeByValue(20);

        assertEquals(2, uniChain.length());
        assertEquals(30, uniChain.getHead().next.data);
    }

    @Test
    void testRemoveAtIndex() {
        uniChain.addAtLast(10);
        uniChain.addAtLast(20);
        uniChain.addAtLast(30);
        uniChain.removeAtIndex(1); // Removes 20

        assertEquals(2, uniChain.length());
        assertEquals(30, uniChain.getHead().next.data);
    }

    // --- 4. Clearing Tests ---

    @Test
    void testClearSequentially() {
        uniChain.addAtLast(10);
        uniChain.addAtLast(20);
        uniChain.clearSequentially();

        assertNull(uniChain.getHead());
        assertEquals(0, uniChain.length());
    }

    @Test
    void testClearInstantly() {
        uniChain.addAtLast(10);
        uniChain.addAtLast(20);
        uniChain.clearInstantly();

        assertNull(uniChain.getHead());
        assertEquals(0, uniChain.length());
    }

    // --- 5. Retrieval & Update Tests ---

    @Test
    void testRetrieveByValue() {
        uniChain.addAtLast(10);
        uniChain.addAtLast(20);
        Integer value = uniChain.retrieveByValue(20);
        assertEquals(20, value);
    }

    @Test
    void testGetNthEnd() {
        uniChain.addAtLast(10);
        uniChain.addAtLast(20);
        uniChain.addAtLast(30);
        uniChain.addAtLast(40);

        // 2nd from the end should be 30
        assertEquals(30, uniChain.getNthEnd(2));
    }

    @Test
    void testGetNthEndThrowsExceptions() {
        uniChain.addAtLast(10);

        assertThrows(IllegalArgumentException.class, () -> uniChain.getNthEnd(0));
        assertThrows(IllegalArgumentException.class, () -> uniChain.getNthEnd(5)); // Out of bounds
    }

    @Test
    void testUpdateAtNode() {
        uniChain.addAtLast(10);
        uniChain.addAtLast(20);
        uniChain.updateAtNode(2, 99); // Update 2nd node (1-based index) to 99

        assertEquals(99, uniChain.getHead().next.data);
    }

    // --- 6. Complex Operations (Reverse, Sort, Concat, Loop) ---

    @Test
    void testReverse() {
        uniChain.addAtLast(1);
        uniChain.addAtLast(2);
        uniChain.addAtLast(3);
        uniChain.reverse();

        assertEquals(3, uniChain.getHead().data);
        assertEquals(2, uniChain.getHead().next.data);
        assertEquals(1, uniChain.getHead().next.next.data);
    }

    @Test
    void testCheapSort() {
        uniChain.addAtLast(30);
        uniChain.addAtLast(10);
        uniChain.addAtLast(20);
        uniChain.cheapSort();

        assertEquals(10, uniChain.getHead().data);
        assertEquals(20, uniChain.getHead().next.data);
        assertEquals(30, uniChain.getHead().next.next.data);
    }

    @Test
    void testConcatenate() {
        uniChain.addAtLast(1);
        uniChain.addAtLast(2);

        UniChain<Integer> uniChain2 = new UniChainImpl<>();
        uniChain2.addAtLast(3);
        uniChain2.addAtLast(4);

        uniChain.concatenate(uniChain2);

        assertEquals(4, uniChain.length());
        assertEquals(3, uniChain.getHead().next.next.data);
    }

    @Test
    void testIsUniChainCircularFalse() {
        uniChain.addAtLast(10);
        uniChain.addAtLast(20);
        uniChain.addAtLast(30);

        assertFalse(uniChain.isUniChainCircular());
    }

    @Test
    void testIsUniChainCircularTrue() {
        uniChain.addAtLast(10);
        uniChain.addAtLast(20);
        uniChain.addAtLast(30);
        uniChain.addAtLast(40);

        // Artificially create a loop for testing: Tail points back to Head
        UniNode<Integer> tail = uniChain.getHead().next.next.next;
        // Node with value 20
        tail.next = uniChain.head.next;

        assertTrue(uniChain.isUniChainCircular());
    }

    @Test
    void testRemoveDuplicates() {
        uniChainString.addAtFirst("str1");
        uniChainString.addAtFirst("str2");
        uniChainString.addAtFirst("str3");
        uniChainString.addAtFirst("str4");
        uniChainString.addAtFirst("str2");
        uniChainString.addAtFirst("str3");
        uniChainString.removeDuplicates();

        assertEquals(4, uniChainString.length());
    }
}
