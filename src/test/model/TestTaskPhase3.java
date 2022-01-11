package model;

import model.exceptions.EmptyStringException;
import model.exceptions.InvalidProgressException;
import model.exceptions.NegativeInputException;
import model.exceptions.NullArgumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestTaskPhase3 {
    private Task t;

    @BeforeEach
    void doBefore() {
        t = new Task("test");
    }

    @Test
    void testConstructor() {
        assertEquals("test", t.getDescription());
        assertEquals("DEFAULT", t.getPriority().toString());
        assertNull(t.getDueDate());
        assertTrue(t.getTags().isEmpty());
        assertEquals("TODO", t.getStatus().getDescription());
    }

    @Test
    void testConstructorNullString() {
        try {
            t = new Task(null);
            fail("EmptyStringException should have been thrown");
        } catch (EmptyStringException e) {
            try {
                throw new EmptyStringException();
            } catch (EmptyStringException ee) {
                assertEquals("test", t.getDescription());
                assertEquals("DEFAULT", t.getPriority().toString());
                assertNull(t.getDueDate());
                assertTrue(t.getTags().isEmpty());
                assertEquals("TODO", t.getStatus().getDescription());
            }
        }
    }

    @Test
    void testConstructorEmptyString() {
        try {
            t = new Task("");
            fail("EmptyStringException should have been thrown");
        } catch (EmptyStringException e) {
            try {
                throw new EmptyStringException();
            } catch (EmptyStringException ee) {
                assertEquals("test", t.getDescription());
                assertEquals("DEFAULT", t.getPriority().toString());
                assertNull(t.getDueDate());
                assertTrue(t.getTags().isEmpty());
                assertEquals("TODO", t.getStatus().getDescription());
            }
        }
    }

    @Test
    void testAddTagEmpty() {
        t.addTag("hi");
        assertTrue(t.containsTag("hi"));
    }

    @Test
    void testAddTagEmptyString() {
        try {
            t.addTag("");
            fail("EmptyStringException should have been thrown");
        } catch (EmptyStringException e) {
            try {
                throw new EmptyStringException();
            } catch (EmptyStringException ee) {
                assertEquals("test", t.getDescription());
                assertEquals("DEFAULT", t.getPriority().toString());
                assertNull(t.getDueDate());
                assertTrue(t.getTags().isEmpty());
                assertEquals("TODO", t.getStatus().getDescription());
            }
        }
    }

    @Test
    void testAddTagNullString() {
        try {
            String snull = null;
            t.addTag(snull);
            fail("EmptyStringException should have been thrown");
        } catch (EmptyStringException e) {
            try {
                throw new EmptyStringException();
            } catch (EmptyStringException ee) {
                assertEquals("test", t.getDescription());
                assertEquals("DEFAULT", t.getPriority().toString());
                assertNull(t.getDueDate());
                assertTrue(t.getTags().isEmpty());
                assertEquals("TODO", t.getStatus().getDescription());
            }
        }
    }

    @Test
    void testAddTagTwice() {
        t.addTag("hi");
        t.addTag("hi");
        t.removeTag("hi");
        assertFalse(t.containsTag("hi"));
        assertTrue(t.getTags().isEmpty());
    }

    @Test
    void testAddTagNonEmpty() {
        t.addTag("hello");
        t.addTag("hi");
        assertTrue(t.containsTag("hi"));
        assertTrue(t.containsTag("hello"));
    }

    @Test
    void testAddSameNameDifferentObject() {
        t.addTag("hi");
        assertTrue(t.containsTag(new Tag("hi")));
    }

    @Test
    void testRemoveTagThere() {
        t.addTag("hi");
        t.removeTag("hi");
        assertTrue(t.getTags().isEmpty());
    }

    @Test
    void testRemoveTagNotThere() {
        t.addTag("hi");
        t.removeTag("hello");
        assertTrue(t.containsTag("hi"));
    }

    @Test
    void testRemoveTagSameNameDifferentObject() {
        t.addTag("hi");
        t.removeTag(new Tag("hi"));
        assertTrue(t.getTags().isEmpty());
    }

    @Test
    void testRemoveTagEmptyString() {
        try {
            t.addTag("hi");
            t.removeTag("");
            fail("EmptyStringException should have been thrown");
        } catch (EmptyStringException e) {
            try {
                throw new EmptyStringException();
            } catch (EmptyStringException ee) {
                assertTrue(t.containsTag("hi"));
                assertEquals(1, t.getTags().size());
            }
        }
    }

    @Test
    void testRemoveTagNullString() {
        try {
            t.addTag("hi");
            String snull = null;
            t.removeTag(snull);
            fail("EmptyStringException should have been thrown");
        } catch (EmptyStringException e) {
            try {
                throw new EmptyStringException();
            } catch (EmptyStringException ee) {
                assertTrue(t.containsTag("hi"));
                assertEquals(1, t.getTags().size());
            }
        }
    }

    @Test
    void testSetDescriptionEmptyString() {
        try {
            t.setDescription("");
            fail("EmptyStringException should have been thrown");
        } catch (EmptyStringException e) {
            try {
                throw new EmptyStringException();
            } catch (EmptyStringException ee) {
                assertEquals("test", t.getDescription());
            }
        }
    }

    @Test
    void testSetDescriptionNullString() {
        try {
            t.setDescription(null);
            fail("EmptyStringException should have been thrown");
        } catch (EmptyStringException e) {
            try {
                throw new EmptyStringException();
            } catch (EmptyStringException ee) {
                assertEquals("test", t.getDescription());
            }
        }
    }

    @Test
    void testContainsTagTrue() {
        for (int i = 0; i < 100; i++) {
            t.addTag(Integer.toString(i));
        }
        for (int i = 0; i < 100; i++) {
            assertTrue(t.containsTag(Integer.toString(i)));
        }
    }

    @Test
    void testContainsTagFalse() {
        assertFalse(t.containsTag("hi"));
    }

    @Test
    void testToString() {
        String duestr = new DueDate().toString();
        String str = "\n{\n" +
                "\tDescription: test\n" +
                "\tDue date: "+ duestr + "\n" +
                "\tStatus: IN PROGRESS\n" +
                "\tPriority: IMPORTANT & URGENT\n" +
                "\tTags: #project, #cpsc210\n" +
                "}";
        t.addTag("cpsc210");
        t.addTag("project");
        t.setPriority(new Priority(1));
        t.setStatus(Status.IN_PROGRESS);
        t.setDueDate(new DueDate());
        assertEquals(str, t.toString());
    }

    @Test
    void testGetTagsUnmodifiable() {
        try {
            t.getTags().add(new Tag("hi"));
            fail("UnsupportedOperationException should have been thrown");
        } catch (UnsupportedOperationException e) {
            assertFalse(t.containsTag("hi"));
        }
    }

    @Test
    void testSetPriorityNull() {
        try {
            t.setPriority(null);
            fail("NullArgumentException should have been thrown");
        } catch (NullArgumentException e) {
            try {
                throw new NullArgumentException();
            } catch (NullArgumentException ee) {
                assertEquals("test", t.getDescription());
                assertEquals("DEFAULT", t.getPriority().toString());
                assertNull(t.getDueDate());
                assertTrue(t.getTags().isEmpty());
                assertEquals("TODO", t.getStatus().getDescription());
            }
        }
    }

    @Test
    void testSetStatusNull() {
        try {
            t.setStatus(null);
            fail("NullArgumentException should have been thrown");
        } catch (NullArgumentException e) {
            try {
                throw new NullArgumentException();
            } catch (NullArgumentException ee) {
                assertEquals("test", t.getDescription());
                assertEquals("DEFAULT", t.getPriority().toString());
                assertNull(t.getDueDate());
                assertTrue(t.getTags().isEmpty());
                assertEquals("TODO", t.getStatus().getDescription());
            }
        }
    }

    @Test
    void testSetProgressException() {
        try {
            t.setProgress(-10);
            fail("InvalidProgressException should have been thrown");
        } catch (InvalidProgressException e) {
            try {
                throw new InvalidProgressException();
            } catch (InvalidProgressException ee) {
                // expected
            }
        }
        try {
            t.setProgress(101);
            fail("InvalidProgressException should have been thrown");
        } catch (InvalidProgressException e) {
            try {
                throw new InvalidProgressException();
            } catch (InvalidProgressException ee) {
                // expected
            }
        }
    }

    @Test
    void testSetProgress() {
        assertEquals(0, t.getProgress());
        t.setProgress(50);
        assertEquals(50, t.getProgress());
    }

    @Test
    void testSetEtcHourException() {
        try {
            t.setEstimatedTimeToComplete(-1);
            fail("NegativeInputException should have been thrown");
        } catch (NegativeInputException e) {
            try {
                throw new NegativeInputException();
            } catch (NegativeInputException ee) {
                // expected
            }
        }
    }

    @Test
    void testSetEtcHour() {
        assertEquals(0, t.getEstimatedTimeToComplete());
        t.setEstimatedTimeToComplete(3);
        assertEquals(3, t.getEstimatedTimeToComplete());
    }

    @Test
    void testContainsTagStringNull() {
        try {
            t.containsTag((String) null);
            fail("EmptyStringException should have been thrown");
        } catch (EmptyStringException e) {
            // expected
        }
    }

    @Test
    void testContainsTagStringEmpty() {
        try {
            t.containsTag("");
            fail("EmptyStringException should have been thrown");
        } catch (EmptyStringException e) {
            // expected
        }
    }

    @Test
    void testContainsTagNull() {
        try {
            t.containsTag((Tag) null);
            fail("NullArgumentException should have been thrown");
        } catch (NullArgumentException e) {
            // expected
        }
    }

    @Test
    void testNullDueDateToString() {
        String str = "\n{\n" +
                "\tDescription: test\n" +
                "\tDue date: \n" +
                "\tStatus: TODO\n" +
                "\tPriority: DEFAULT\n" +
                "\tTags: \n" +
                "}";
        assertEquals(str, t.toString());
    }

    @Test
    void testParseDescription() {
        t = new Task("hi ## today; important");
        Task t1 = new Task("hi ## tomorrow; important");
        Task t2 = new Task("hi ## tomorrow; urgent");
        Task t3 = new Task("hi ## urgent");
        Task t4 = new Task("hi ## important");
        assertFalse(t.equals(t1));
        assertFalse(t.equals(t2));
        assertFalse(t1.equals(t2));
        assertFalse(t3.equals(t4));
    }

    @Test
    void testGetDescription() {
        Todo t1 = new Task("hi");
        assertEquals("hi", t1.getDescription());
    }
}