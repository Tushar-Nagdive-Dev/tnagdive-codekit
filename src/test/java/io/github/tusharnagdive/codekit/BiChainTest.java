package io.github.tusharnagdive.codekit;

import io.github.tusharnagdive.codekit.kitcollection.bichain.BiChain;
import org.junit.jupiter.api.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class BiChainTest {

    static class User {
        int id;
        String name;
        User(int id, String name) { this.id = id; this.name = name; }
        public int getId() { return id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }

    private BiChain<User> chain;

    @BeforeEach
    void setUp() {
        chain = BiChain.create();
        chain.addAtLast(new User(1, "Alice"));
        chain.addAtLast(new User(2, "Bob"));
        chain.addAtLast(new User(3, "Charlie"));
    }

    @Test
    @DisplayName("Test Basic Add and Size")
    void testAddAndSize() {
        assertEquals(3, chain.size());
        chain.addAtFirst(new User(0, "Start"));
        assertEquals(4, chain.size());
    }

    @Test
    @DisplayName("Test Concurrent Search (get)")
    void testGet() {
        User found = chain.get(User::getId, 2);
        assertNotNull(found);
        assertEquals("Bob", found.getName());
    }

    @Test
    @DisplayName("Test Functional Update")
    void testUpdate() {
        boolean updated = chain.update(User::getId, 1, u -> u.setName("Alice Updated"));
        assertTrue(updated);
        assertEquals("Alice Updated", chain.get(User::getId, 1).getName());
    }

    @Test
    @DisplayName("Test In-place Sort by Field")
    void testSortBy() {
        chain.addAtLast(new User(0, "Zebra"));
        chain.sortBy(User::getId);
        List<User> list = chain.toList();
        assertEquals(0, list.get(0).getId());
        assertEquals(3, list.get(3).getId());
    }

    @Test
    @DisplayName("Test Unique Deduplication")
    void testUnique() {
        chain.addAtLast(new User(2, "Bob Duplicate"));
        // Sort first so duplicates are adjacent!
//        chain.sortBy(User::getId);
        chain.unique(User::getId);

        assertEquals(3, chain.size());
    }

    @Test
    @DisplayName("Test Concatenation")
    void testConcatenate() {
        BiChain<User> other = BiChain.create();
        other.addAtLast(new User(4, "Dave"));

        chain.concatenate(other);
        assertEquals(4, chain.size());
        assertNotNull(chain.get(User::getId, 4));
    }

    @Test
    @DisplayName("Test Reverse")
    void testReverse() {
        chain.reverse();
        assertEquals("Charlie", chain.toList().get(0).getName());
    }

    @Test
    @DisplayName("Test Stream Integration")
    void testStream() {
        long count = chain.stream().filter(u -> u.getId() > 1).count();
        assertEquals(2, count);
    }
}