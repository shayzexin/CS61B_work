package deque;

import org.junit.Test;
import static org.junit.Assert.*;

public class ArrayDequeTest {

    @Test
    public void testBasicOps() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.addFirst("c");
        deque.addFirst("b");
        deque.addLast("d");

        assertEquals("b", deque.get(0));
        assertEquals("d", deque.get(2));
        assertEquals(3, deque.size());

        assertEquals("b", deque.removeFirst());
        assertEquals("d", deque.removeLast());
    }

    @Test
    public void testWrapAround() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();

        // add elements to force wrap around
        for (int i = 0; i < 10; i++) {
            deque.addLast(i);
        }

        for (int i = 0; i < 8; i++) {
            deque.removeFirst();
        }

        deque.addFirst(99);
        deque.addLast(100);

        assertEquals((Integer) 99, deque.get(0));
        assertEquals((Integer) 8, deque.get(1));
        assertEquals((Integer) 9, deque.get(2));
        assertEquals((Integer)100, deque.get(3));
        assertNull(deque.get(4));
    }

    @Test
    public void testResize() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();

        for (int i = 0; i < 100; i++) {
            deque.addLast(i);
        }

        assertEquals(100, deque.size());

        for (int i = 0; i < 95; i++) {
            deque.removeFirst();
        }

        assertEquals(5, deque.size());
    }

    @Test
    public void testNullsAndBounds() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        assertNull(deque.removeFirst());
        assertNull(deque.removeLast());
        assertNull(deque.get(0));

        deque.addFirst("x");
        assertEquals("x", deque.get(0));
        assertNull(deque.get(1));
    }

    @Test
    public void testAddRemoveAlternating() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.addFirst(1);
        deque.removeFirst();
        deque.addLast(2);
        deque.removeLast();
        assertTrue(deque.isEmpty());
    }

    @Test
    public void testLargeAddRemoveBackToZero() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        for (int i = 0; i < 1000; i++) {
            deque.addLast(i);
        }
        for (int i = 0; i < 1000; i++) {
            assertEquals((Integer) i, deque.removeFirst());
        }
        assertTrue(deque.isEmpty());
    }

    @Test
    public void testAddFirstWrapAround() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        for (int i = 0; i < 8; i++) {
            deque.addFirst(i); // fills array backwards
        }
        assertEquals((Integer) 7, deque.get(0));
        assertEquals((Integer) 0, deque.get(7));
    }

    @Test
    public void testAddLastWrapAround() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        for (int i = 0; i < 8; i++) {
            deque.addLast(i); // fills array forward
        }
        for (int i = 0; i < 4; i++) {
            deque.removeFirst(); // create space in front
        }
        for (int i = 8; i < 12; i++) {
            deque.addLast(i); // wrap-around
        }

        assertEquals((Integer) 4, deque.get(0));
        assertEquals((Integer) 11, deque.get(deque.size() - 1));
    }

    @Test
    public void testShrinkBelowQuarter() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        for (int i = 0; i < 64; i++) {
            deque.addLast(i);
        }
        for (int i = 0; i < 50; i++) {
            deque.removeLast(); // shrink usage to < 25%
        }
        assertEquals(14, deque.size());
        assertEquals((Integer) 13, deque.get(13));
    }

    @Test
    public void testAddRemoveSingleElement() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        deque.addFirst("one");
        assertEquals("one", deque.get(0));
        assertEquals("one", deque.removeLast());
        assertTrue(deque.isEmpty());
    }

    @Test
    public void testNullBehavior() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        assertNull(deque.get(0));
        assertNull(deque.removeFirst());
        assertNull(deque.removeLast());

        deque.addLast(10);
        assertNull(deque.get(5));
    }

    @Test
    public void testWrapAroundAfterResize() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();

        // fill to trigger resize
        for (int i = 0; i < 16; i++) {
            deque.addLast(i);
        }

        // remove first 10 to move front pointer forward
        for (int i = 0; i < 10; i++) {
            deque.removeFirst();
        }

        // add more to cause wrap-around and force resize
        for (int i = 100; i < 120; i++) {
            deque.addLast(i);
        }

        // test element order
        int expected = 10;
        for (int i = 0; i < 6; i++) {
            assertEquals((Integer) expected++, deque.get(i));
        }
        for (int i = 100; i < 120; i++) {
            assertEquals((Integer) i, deque.get(expected++ - 10));
        }
    }

    @Test
    public void testAddFirstThenRemoveLastStress() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        for (int i = 0; i < 1000; i++) {
            deque.addFirst(i);
        }
        for (int i = 0; i < 1000; i++) {
            int val = deque.removeLast();
            assertEquals(i, val);
        }

        assertTrue(deque.isEmpty());
    }

    @Test
    public void testAddLastThenRemoveFirstStress() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        for (int i = 0; i < 1000; i++) {
            deque.addLast(i);
        }
        for (int i = 0; i < 1000; i++) {
            int val = deque.removeFirst();
            assertEquals(i, val);
        }
        assertTrue(deque.isEmpty());
    }

    @Test
    public void testInterleavedOpsAndResizing() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        for (int i = 0; i < 32; i++) {
            deque.addLast(i);
        }
        for (int i = 0; i < 16; i++) {
            deque.removeFirst();
        }
        for (int i = 32; i < 64; i++) {
            deque.addFirst(i);
        }
        for (int i = 0; i < 10; i++) {
            deque.removeLast();
        }
        assertEquals(38, deque.size());
    }

    @Test
    public void testGetAfterWrapAround() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        for (int i = 0; i < 8; i++) {
            deque.addLast(i);
        }
        for (int i = 0; i < 5; i++) {
            deque.removeFirst(); // move front pointer
        }
        for (int i = 100; i < 105; i++) {
            deque.addLast(i); // wrap-around
        }
        assertEquals((Integer) 5, deque.get(0));
        assertEquals((Integer) 6, deque.get(1));
        assertEquals((Integer) 7, deque.get(2));
        assertEquals((Integer) 100, deque.get(3));
        assertEquals((Integer) 104, deque.get(7));
    }

}
