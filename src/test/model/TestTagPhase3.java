package model;

import model.exceptions.EmptyStringException;
import model.exceptions.NullArgumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestTagPhase3 {
    private Tag t;
    private Task t1 = new Task("hi");
    private Task t2;

    @BeforeEach
    void doBefore() {
        t = new Tag("Funk");
        t1 = new Task("hi");
        t2 = new Task("hello");
    }

    @Test
    void testConstructor() {
        assertEquals("Funk", t.getName());
        assertEquals("#Funk", t.toString());
    }

    @Test
    void testNullString() {
        try {
            t = new Tag(null);
            fail("Should have thrown EmptyStringException");
        } catch (EmptyStringException e) {
            try {
                throw new EmptyStringException();
            } catch (EmptyStringException ee) {
                assertEquals("Funk", t.getName());
                assertEquals("#Funk", t.toString());
            }
        }
    }

    @Test
    void testEmptyString() {
        try {
            t = new Tag("");
            fail("Should have thrown EmptyStringException");
        } catch (EmptyStringException e) {
            try {
                throw new EmptyStringException();
            } catch (EmptyStringException ee) {
                assertEquals("Funk", t.getName());
                assertEquals("#Funk", t.toString());
            }
        }
    }

    @Test
    void testAddTagEmpty() {
        t.addTask(t1);
        assertTrue(t.containsTask(t1));
    }

    @Test
    void testAddTagNullString() {
        try {
            t.addTask(null);
            fail("NullArgumentException should have been thrown");
        } catch (NullArgumentException e) {
            try {
                throw new NullArgumentException();
            } catch (NullArgumentException ee) {
                assertTrue(t.getTasks().isEmpty());
            }
        }
    }

    @Test
    void testAddTagTwice() {
        t.addTask(t1);
        t.addTask(t1);
        t.removeTask(t1);
        assertFalse(t.containsTask(t1));
        assertTrue(t.getTasks().isEmpty());
    }

    @Test
    void testAddTagNonEmpty() {
        t.addTask(t2);
        t.addTask(t1);
        assertTrue(t.containsTask(t2));
        assertTrue(t.containsTask(t1));
    }

    @Test
    void testAddSameNameDifferentObject() {
        t.addTask(t1);
        assertTrue(t.containsTask(new Task("hi")));
    }

    @Test
    void testRemoveTagThere() {
        t.addTask(t1);
        t.removeTask(t1);
        assertTrue(t.getTasks().isEmpty());
    }

    @Test
    void testRemoveTagNotThere() {
        t.addTask(t1);
        t.removeTask(t2);
        assertTrue(t.containsTask(t1));
    }

    @Test
    void testRemoveTagSameNameDifferentObject() {
        t.addTask(t1);
        t.removeTask(new Task("hi"));
        assertTrue(t.getTasks().isEmpty());
    }

    @Test
    void testRemoveTagNullString() {
        try {
            t.addTask(t1);
            t.removeTask(null);
            fail("NullArgumentException should have been thrown");
        } catch (NullArgumentException e) {
            try {
                throw new NullArgumentException();
            } catch (NullArgumentException ee) {
                assertTrue(t.containsTask(t1));
                assertEquals(1, t.getTasks().size());
            }
        }
    }
}
