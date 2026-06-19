package io.github.tusharnagdive.codekit;

import io.github.tusharnagdive.codekit.kitcollection.bichain.BiChain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class BiChainTest {

    private BiChain<Integer> intChain;

    @BeforeEach
    void setUp() {
        intChain = BiChain.create();
    }

    @Test
    void testBasicAdditions() {
        intChain.addAtLast(10);
        intChain.addAtLast(20);
        intChain.addAtFirst(5);

        // Expected: 5 <-> 10 <-> 20
        assertEquals(5, intChain.findFromFirst(x -> x, 5));
        assertEquals(20, intChain.findFromFirst(x -> x, 20));
    }

    @Test
    void testFunctionalRemoval() {
        // Setup: List of Users
        record User(int id, String name) {}
        BiChain<User> userChain = BiChain.from(List.of(
                new User(1, "Alice"),
                new User(2, "Bob")
        ));

        // Remove by ID
        userChain.removeByValue(User::id, 1);

        // Assert: Should not find ID 1, should find ID 2
        assertNull(userChain.findFromFirst(User::id, 1));
        assertNotNull(userChain.findFromFirst(User::id, 2));
    }

    @Test
    void testInsertAtIndexEdges() {
        intChain.addAtLast(10);
        intChain.addAtLast(30);

        // Insert 20 at index 1: 10 <-> 20 <-> 30
        intChain.insertAtIndex(20, 1);

        assertEquals(20, intChain.findFromFirst(x -> x, 20));
    }

    @Test
    void testRemovalIntegrity() {
        intChain.addAtLast(1);
        intChain.addAtLast(2);
        intChain.addAtLast(3);

        intChain.removeFirst(); // Removes 1
        intChain.removeLast();  // Removes 3

        // Only 2 should remain
        assertNotNull(intChain.findFromFirst(x -> x, 2));
        assertNull(intChain.findFromFirst(x -> x, 1));
        assertNull(intChain.findFromFirst(x -> x, 3));
    }
}