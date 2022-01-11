package model;

import model.exceptions.EmptyStringException;
import model.exceptions.NullArgumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class TestProject {

    Project p;
    Task t;

    @BeforeEach
    void doBefore() {
        p = new Project("test");
    }

    @Test
    void testConstructor() {
        assertEquals(0, p.getNumberOfTasks());
        assertEquals("test", p.getDescription());
    }

    @Test
    void testConstructorEmptyString() {
        try {
            p = new Project("");
            fail("EmptyStringException should have been thrown");
        } catch (EmptyStringException e) {
            try {
                throw new EmptyStringException();
            } catch (EmptyStringException ee) {
                assertEquals(0, p.getNumberOfTasks());
                assertEquals("test", p.getDescription());
            }
        }
    }

    @Test
    void testConstructorNullString() {
        try {
            p = new Project(null);
            fail("EmptyStringException should have been thrown");
        } catch (EmptyStringException e) {
            try {
                throw new EmptyStringException();
            } catch (EmptyStringException ee) {
                assertEquals(0, p.getNumberOfTasks());
                assertEquals("test", p.getDescription());
            }
        }
    }

    @Test
    void testAddTaskEmpty() {
        t = new Task("hi");
        p.add(t);
        assertTrue(p.contains(t));
        assertEquals(1, p.getNumberOfTasks());
        assertTrue(p.contains(t));
    }

    @Test
    void testAddTaskTwice() {
        t = new Task("hi");
        p.add(t);
        p.add(t);
        assertTrue(p.contains(t));
        assertEquals(1, p.getNumberOfTasks());
        assertTrue(p.contains(t));
    }

    @Test
    void testAddTaskNonEmpty() {
        t = new Task("hi");
        Task t1 = new Task("hello");
        p.add(t);
        p.add(t1);
        assertTrue(p.contains(t));
        assertTrue(p.contains(t1));
        assertEquals(2, p.getNumberOfTasks());
        assertTrue(p.contains(t));
        assertTrue(p.contains(t1));
    }

    @Test
    void testAddTaskNull() {
        try {
            p.add(null);
            fail("NullArgumentException should have been thrown");
        } catch (NullArgumentException e) {
            try {
                throw new NullArgumentException();
            } catch (NullArgumentException ee) {
                assertEquals(0, p.getNumberOfTasks());
                assertEquals("test", p.getDescription());
            }
        }
    }

    @Test
    void testAddProjects() {
        Task t1 = new Task("hi");
        Project p1 = new Project("a");
        Project p2 = new Project("a");
        Project p3 = new Project("b");
        p.add(p1);
        p.add(t1);
        assertTrue(p.contains(p1));
        assertTrue(p.contains(t1));
        assertTrue(p.contains(p2));
        assertFalse(p.contains(p3));
    }

    @Test
    void testRemoveTaskThere() {
        t = new Task("hi");
        p.add(t);
        p.remove(t);
        assertEquals(0, p.getNumberOfTasks());
    }

    @Test
    void testRemoveTaskNotThere() {
        t = new Task("hi");
        Task t1 = new Task("hi");
        Task t2 = new Task("hello");
        p.add(t);
        p.remove(t1);
        assertEquals(0, p.getNumberOfTasks());
        assertFalse(p.contains(t));
        p.remove(t2);
        assertEquals(0, p.getNumberOfTasks());
        assertFalse(p.contains(t));
    }

    @Test
    void testRemoveTaskNull() {
        t = new Task("hi");
        p.add(t);
        try {
            p.remove(null);
        } catch (NullArgumentException e) {
            try {
                throw new NullArgumentException();
            } catch (NullArgumentException ee) {
                assertEquals(1, p.getNumberOfTasks());
                assertTrue(p.contains(t));
            }
        }
    }

    @Test
    void testGetProgressNonEmpty() {
        t = new Task("hi");
        t.setProgress(100);
        p.add(t);
        assertEquals(100, p.getProgress());
        Task t1 = new Task("hello");
        p.add(t1);
        assertEquals(50, p.getProgress());
        Task t2 = new Task("bonjour");
        t2.setProgress(100);
        p.add(t2);
        assertEquals(66, p.getProgress());
    }

    @Test
    void testIsCompletedEmpty() {
        assertFalse(p.isCompleted());
    }

    @Test
    void testIsCompletedNonEmptyTrue() {
        t = new Task("hi");
        t.setProgress(100);
        Task t1 = new Task("hello");
        t1.setProgress(100);
        p.add(t);
        p.add(t1);
        assertTrue(p.isCompleted());
    }

    @Test
    void testIsCompletedNonEmptyFalse() {
        t = new Task("hi");
        t.setStatus(Status.DONE);
        Task t1 = new Task("hello");
        t1.setStatus(Status.IN_PROGRESS);
        Task t2 = new Task("bonjour");
        t2.setStatus(Status.DONE);
        p.add(t);
        p.add(t1);
        p.add(t2);
        assertFalse(p.isCompleted());
    }

    @Test
    void testGetTasksUnmodifiable() {
        try {
            p.getTasks().add(new Task("hi"));
            fail("Need to throw UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            try {
                throw new UnsupportedOperationException();
            } catch (UnsupportedOperationException ee) {
                assertEquals(0, p.getNumberOfTasks());
            }
        }
    }

    @Test
    void testContainsNull() {
        t = new Task("hi");
        p.add(t);
        try {
            p.contains(null);
            fail("NullArgumentException should have been thrown");
        } catch (NullArgumentException e) {
            try {
                throw new NullArgumentException();
            } catch (NullArgumentException ee) {
                assertEquals(1, p.getNumberOfTasks());
                assertTrue(p.contains(t));
            }
        }
    }

    @Test
    void testGetProgressEmpty() {
        assertEquals(0, p.getProgress());
    }

    @Test
    void testGetProgressZero() {
        Task t1 = new Task("hi");
        Task t2 = new Task("hello");
        Task t3 = new Task("h");
        p.add(t1);
        p.add(t2);
        p.add(t3);
        assertEquals(0, p.getProgress());
    }

    @Test
    void testGetProgress() {
        Task t1 = new Task("hi");
        Task t2 = new Task("hello");
        Task t3 = new Task("h");
        t1.setProgress(100);
        p.add(t1);
        p.add(t2);
        p.add(t3);
        assertEquals(33, p.getProgress());
    }

    @Test
    void testGetProgressMultipleNonZero() {
        Task t1 = new Task("hi");
        Task t2 = new Task("hello");
        Task t3 = new Task("h");
        t1.setProgress(100);
        t2.setProgress(50);
        t3.setProgress(25);
        p.add(t1);
        p.add(t2);
        p.add(t3);
        assertEquals(58, p.getProgress());
    }

    @Test
    void testGetProgressSubProject() {
        Task t1 = new Task("hi");
        Task t2 = new Task("hello");
        Task t3 = new Task("h");
        t1.setProgress(100);
        t2.setProgress(50);
        t3.setProgress(25);
        p.add(t1);
        p.add(t2);
        p.add(t3);
        Project p1 = new Project("hi");
        p1.add(p);
        Task t4 = new Task("life");
        p1.add(t4);
        assertEquals(29, p1.getProgress());
    }

    @Test
    void testCannotAddSelf() {
        p.add(p);
        assertFalse(p.contains(p));
    }

    @Test
    void testGetEtcHourEmpty() {
        assertEquals(0, p.getEstimatedTimeToComplete());
    }

    @Test
    void testGetEtcHourZero() {
        Task t1 = new Task("hi");
        Task t2 = new Task("hello");
        Task t3 = new Task("h");
        p.add(t1);
        p.add(t2);
        p.add(t3);
        assertEquals(0, p.getEstimatedTimeToComplete());
    }

    @Test
    void testGetEtcHour() {
        Task t1 = new Task("hi");
        Task t2 = new Task("hello");
        Task t3 = new Task("h");
        t1.setEstimatedTimeToComplete(8);
        p.add(t1);
        p.add(t2);
        p.add(t3);
        assertEquals(8, p.getEstimatedTimeToComplete());
    }

    @Test
    void testGetEtcHourMultipleNonZero() {
        Task t1 = new Task("hi");
        Task t2 = new Task("hello");
        Task t3 = new Task("h");
        t1.setEstimatedTimeToComplete(2);
        t2.setEstimatedTimeToComplete(8);
        t3.setEstimatedTimeToComplete(10);
        p.add(t1);
        p.add(t2);
        p.add(t3);
        assertEquals(20, p.getEstimatedTimeToComplete());
    }

    @Test
    void testGetEtcHourSubProject() {
        Task t1 = new Task("hi");
        Task t2 = new Task("hello");
        Task t3 = new Task("h");
        t1.setEstimatedTimeToComplete(2);
        t2.setEstimatedTimeToComplete(8);
        t3.setEstimatedTimeToComplete(10);
        p.add(t1);
        p.add(t2);
        p.add(t3);
        Project p1 = new Project("hi");
        p1.add(p);
        Task t4 = new Task("life");
        t4.setEstimatedTimeToComplete(4);
        p1.add(t4);
        assertEquals(24, p1.getEstimatedTimeToComplete());
    }

    @Test
    void testProjectIteratorOneOfAll() {
        Todo t1 = new Task("hi");
        Todo t2 = new Task("hello");
        Todo t3 = new Task("h");
        Todo t4 = new Task("t");
        t1.setPriority(new Priority(2));
        t2.setPriority(new Priority(4));
        t3.setPriority(new Priority(1));
        t4.setPriority(new Priority(3));
        p.add(t1);
        p.add(t2);
        p.add(t3);
        p.add(t4);
        Iterator<Todo> itr = p.iterator();
        assertTrue(itr.hasNext());
        assertEquals(t3, itr.next());
        assertTrue(itr.hasNext());
        assertEquals(t1, itr.next());
        assertTrue(itr.hasNext());
        assertEquals(t4, itr.next());
        assertTrue(itr.hasNext());
        assertEquals(t2, itr.next());
        assertFalse(itr.hasNext());
    }

    @Test
    void testProjectIteratorInOrder() {
        Todo t1 = new Task("hi");
        Todo t2 = new Task("hello");
        Todo t3 = new Task("h");
        Todo t4 = new Task("t");
        t1.setPriority(new Priority(2));
        t2.setPriority(new Priority(2));
        t3.setPriority(new Priority(2));
        t4.setPriority(new Priority(2));
        p.add(t1);
        p.add(t2);
        p.add(t3);
        p.add(t4);
        Iterator<Todo> itr = p.iterator();
        assertTrue(itr.hasNext());
        assertEquals(t1, itr.next());
        assertTrue(itr.hasNext());
        assertEquals(t2, itr.next());
        assertTrue(itr.hasNext());
        assertEquals(t3, itr.next());
        assertTrue(itr.hasNext());
        assertEquals(t4, itr.next());
        assertFalse(itr.hasNext());
    }

    @Test
    void testProjectIteratorTwoOfAll() {
        Todo t1 = new Task("hi");
        Todo t2 = new Task("hello");
        Todo t3 = new Task("h");
        Todo t4 = new Task("t");
        Todo t5 = new Task("a");
        Todo t6 = new Task("b");
        Todo t7 = new Task("c");
        Todo t8 = new Task("d");
        t1.setPriority(new Priority(2));
        t2.setPriority(new Priority(4));
        t3.setPriority(new Priority(1));
        t4.setPriority(new Priority(3));
        t5.setPriority(new Priority(3));
        t6.setPriority(new Priority(2));
        t7.setPriority(new Priority(1));
        t8.setPriority(new Priority(4));
        p.add(t1);
        p.add(t2);
        p.add(t3);
        p.add(t4);
        p.add(t5);
        p.add(t6);
        p.add(t7);
        p.add(t8);
        Iterator<Todo> itr = p.iterator();
        assertTrue(itr.hasNext());
        assertEquals(t3, itr.next());
        assertTrue(itr.hasNext());
        assertEquals(t7, itr.next());
        assertTrue(itr.hasNext());
        assertEquals(t1, itr.next());
        assertTrue(itr.hasNext());
        assertEquals(t6, itr.next());
        assertTrue(itr.hasNext());
        assertEquals(t4, itr.next());
        assertTrue(itr.hasNext());
        assertEquals(t5, itr.next());
        assertTrue(itr.hasNext());
        assertEquals(t2, itr.next());
        assertTrue(itr.hasNext());
        assertEquals(t8, itr.next());
        assertFalse(itr.hasNext());
    }

    @Test
    void testProjectIteratorException() {
        Todo t1 = new Task("hi");
        p.add(t1);
        Iterator<Todo> itr = p.iterator();
        assertTrue(itr.hasNext());
        assertEquals(t1, itr.next());
        assertFalse(itr.hasNext());
        try {
            itr.next();
            fail("NoSuchElementException should have been thrown");
        } catch (NoSuchElementException e) {
            // expected
        }
    }

    @Test
    void testProjectIteratorEmpty() {
        Iterator<Todo> itr = p.iterator();
        assertFalse(itr.hasNext());
        try {
            itr.next();
            fail("NoSuchElementException should have been thrown");
        } catch (NoSuchElementException e) {
            // expected
        }
    }

    @Test
    void testProjectHashCode() {
        Project p1 = new Project("test");
        assertEquals(p, p1);
        Project p2 = new Project("a");
        assertFalse(p.equals(p2));
        assertFalse(p1.equals(p2));
    }

    @Test
    void testHashCode() {
        Project p1 = new Project("test");
        assertEquals(p.hashCode(), p1.hashCode());
    }
}