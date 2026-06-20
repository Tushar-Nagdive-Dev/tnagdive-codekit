package io.github.tusharnagdive.codekit;

import io.github.tusharnagdive.codekit.kitcollection.unichain.UniNode;
import io.github.tusharnagdive.codekit.kitcollection.unichain.UniChain;
import io.github.tusharnagdive.codekit.model.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UniChainImplTest {

    private UniChain<Integer> uniChain;
    private UniChain<String> uniChainString;


    @BeforeEach
    void setUp() {
        uniChain = UniChain.create();
        uniChainString = UniChain.create();
    }

    @Test
    void testAddAtFirstAndLength() {
        assertEquals(0, uniChain.size());
        uniChain.addAtFirst(10);
        uniChain.addAtFirst(20);

        assertEquals(2, uniChain.size());
        assertEquals(20, uniChain.getHead().data);
    }

    @Test
    void testAddAtLast() {
        uniChain.addAtLast(10);
        uniChain.addAtLast(20);

        assertEquals(2, uniChain.size());
        assertEquals(10, uniChain.getHead().data);
        assertEquals(20, uniChain.getHead().next.data);
    }

    @Test
    void testInsertAfter() {
        uniChain.addAtLast(10);
        uniChain.addAtLast(30);
        uniChain.insertAfter(10, 20); // Insert 20 after 10

        assertEquals(3, uniChain.size());
        assertEquals(20, uniChain.getHead().next.data);
    }

    @Test
    void testInsertBefore() {
        uniChain.addAtLast(10);
        uniChain.addAtLast(30);
        uniChain.insertBefore(30, 20); // Insert 20 before 30

        assertEquals(3, uniChain.size());
        assertEquals(20, uniChain.getHead().next.data);
    }

    @Test
    void testInsertAtIndex() {
        uniChain.addAtLast(10);
        uniChain.addAtLast(30);
        uniChain.insertAtIndex(20, 1); // Insert 20 at index 1

        assertEquals(3, uniChain.size());
        assertEquals(20, uniChain.getHead().next.data);
    }

    // --- 3. Removal Tests ---

    @Test
    void testRemoveFirst() {
        uniChain.addAtLast(10);
        uniChain.addAtLast(20);
        uniChain.removeFirst();

        assertEquals(1, uniChain.size());
        assertEquals(20, uniChain.getHead().data);
    }

    @Test
    void testRemoveLast() {
        uniChain.addAtLast(10);
        uniChain.addAtLast(20);
        uniChain.addAtLast(30);
        uniChain.removeLast();

        assertEquals(2, uniChain.size());
        assertNull(uniChain.getHead().next.next);
    }

    @Test
    void testRemoveByValue() {
        uniChain.addAtLast(10);
        uniChain.addAtLast(20);
        uniChain.addAtLast(30);
        uniChain.removeByValue(20);

        assertEquals(2, uniChain.size());
        assertEquals(30, uniChain.getHead().next.data);
    }

    @Test
    void testRemoveAtIndex() {
        uniChain.addAtLast(10);
        uniChain.addAtLast(20);
        uniChain.addAtLast(30);
        uniChain.removeAtIndex(1); // Removes 20

        assertEquals(2, uniChain.size());
        assertEquals(30, uniChain.getHead().next.data);
    }

    // --- 4. Clearing Tests ---

    @Test
    void testClearSequentially() {
        uniChain.addAtLast(10);
        uniChain.addAtLast(20);
        uniChain.clearSequentially();

        assertNull(uniChain.getHead());
        assertEquals(0, uniChain.size());
    }

    @Test
    void testClearInstantly() {
        uniChain.addAtLast(10);
        uniChain.addAtLast(20);
        uniChain.clearInstantly();

        assertNull(uniChain.getHead());
        assertEquals(0, uniChain.size());
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

        UniChain<Integer> uniChain2 = UniChain.create();
        uniChain2.addAtLast(3);
        uniChain2.addAtLast(4);

        uniChain.concatenate(uniChain2);

        assertEquals(4, uniChain.size());
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
        tail.next = uniChain.getHead().next;

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

        assertEquals(4, uniChainString.size());
    }

    @Test
    void testIndexOf() {
        UniChain<Users> usersUniChain = UniChain.create();
        Users user1 = new Users(1, "user1", "user1@mail.com", "username01");
        usersUniChain.addAtLast(user1);
        Users user2 = new Users(2, "user2", "user2@mail.com", "username02");
        usersUniChain.addAtLast(user2);
        Users user3 = new Users(3, "user3", "user3@mail.com", "username03");
        usersUniChain.addAtLast(user3);
        Users user4 = new Users(4, "user4", "user4@mail.com", "username04");
        usersUniChain.addAtLast(user4);
        Users user5 = new Users(5, "user5", "user5@mail.com", "username05");
        usersUniChain.addAtLast(user5);

        Integer returnIndex = usersUniChain.indexOf(Users::getUsername, "username03");
        assertEquals(2, returnIndex);

        UniChain<Integer> uniChainInteger = UniChain.create();
        uniChainInteger.addAtLast(1);
        uniChainInteger.addAtLast(2);
        uniChainInteger.addAtLast(3);
        uniChainInteger.addAtLast(4);
        uniChainInteger.addAtLast(5);
        uniChainInteger.addAtLast(6);
        uniChainInteger.addAtLast(7);

        Integer returnedIndex = uniChainInteger.indexOf(3);
        assertEquals(2, returnedIndex);
    }

    @Test
    void testRetrieveAtIndex() {
        UniChain<Users> usersUniChain = UniChain.create();
        Users user1 = new Users(1, "user1", "user1@mail.com", "username01");
        usersUniChain.addAtLast(user1);
        Users user2 = new Users(2, "user2", "user2@mail.com", "username02");
        usersUniChain.addAtLast(user2);
        Users user3 = new Users(3, "user3", "user3@mail.com", "username03");
        usersUniChain.addAtLast(user3);
        Users user4 = new Users(4, "user4", "user4@mail.com", "username04");
        usersUniChain.addAtLast(user4);
        Users user5 = new Users(5, "user5", "user5@mail.com", "username05");
        usersUniChain.addAtLast(user5);

        Integer returnIndex = usersUniChain.indexOf(Users::getEmail, "user4@mail.com");
        Users returnedUser = usersUniChain.retrieveAtIndex(returnIndex);
        IO.println(returnedUser);
        assertEquals(user4, returnedUser);
    }
}
