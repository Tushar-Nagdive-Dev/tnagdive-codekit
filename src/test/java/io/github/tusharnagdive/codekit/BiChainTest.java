package io.github.tusharnagdive.codekit;

import io.github.tusharnagdive.codekit.kitcollection.bichain.BiChain;
import io.github.tusharnagdive.codekit.model.Users;
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
    void testFindMethods() {
        intChain.addAtLast(10);
        intChain.addAtLast(20);
        intChain.addAtLast(10);

        // findFromFirst should find the first 10
        assertEquals(10, intChain.findFromFirst(10));
        // findFromLast should start from tail and find the 10 at the end
        assertEquals(10, intChain.findFromLast(10));
    }

    @Test
    void testInsertAfterAndBefore() {
        intChain.addAtLast(10);
        intChain.addAtLast(30);

        // 10 <-> 20 <-> 30
        intChain.insertAfter(10, 20);
        assertEquals(20, intChain.findFromFirst(20));

        // 5 <-> 10 <-> 20 <-> 30
        intChain.insertBefore(10, 5);
        assertEquals(5, intChain.findFromFirst(5));
    }

    @Test
    void testRemoveByValue() {
        intChain.addAtLast(10);
        intChain.addAtLast(20);
        intChain.addAtLast(30);

        intChain.removeByValue(20);

        assertNull(intChain.findFromFirst(20), "20 should be removed");
        assertNotNull(intChain.findFromFirst(10), "10 should still exist");
        assertNotNull(intChain.findFromFirst(30), "30 should still exist");
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

    @Test
    void testGetMethod() {
        BiChain<Users> userChain = BiChain.from(List.of(
            new Users(1, "user1", "user1@mail.com", "username1"),
            new Users(2, "user2", "user2@mail.com", "username2"),
            new Users(4, "user4", "user4@mail.com", "username4"),
            new Users(3, "user3", "user3@mail.com", "username3"),
            new Users(4, "user5", "user5@mail.com", "username5"),
            new Users(8, "user8", "user8@mail.com", "username8"),
            new Users(7, "user7", "user7@mail.com", "username7"),
            new Users(10, "user10", "user10@mail.com", "username10"),
            new Users(9, "user9", "user9@mail.com", "username9"),
            new Users(6, "user6", "user6@mail.com", "username6")
        ));
        Users result = userChain.get(Users::getUsername, "username8");
        assertEquals("username8", result.getUsername());
    }
}