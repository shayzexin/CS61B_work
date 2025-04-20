package deque;

import jh61b.junit.In;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;


/** Performs some basic linked list tests. */
public class LinkedListDequeTest {

    @Test
    /** Adds a few things to the list, checking isEmpty() and size() are correct,
     * finally printing the results.
     *
     * && is the "and" operation. */
    public void addIsEmptySizeTest() {

        LinkedListDeque<String> lld1 = new LinkedListDeque<String>();

		assertTrue("A newly initialized LLDeque should be empty", lld1.isEmpty());
		lld1.addFirst("front");

		// The && operator is the same as "and" in Python.
		// It's a binary operator that returns true if both arguments true, and false otherwise.
        assertEquals(1, lld1.size());
        assertFalse("lld1 should now contain 1 item", lld1.isEmpty());

		lld1.addLast("middle");
		assertEquals(2, lld1.size());

		lld1.addLast("back");
		assertEquals(3, lld1.size());

		System.out.println("Printing out deque: ");
		lld1.printDeque();

    }

    @Test
    /** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
    public void addRemoveTest() {

//        System.out.println("Make sure to uncomment the lines below (and delete this print statement).");
        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
		// should be empty
		assertTrue("lld1 should be empty upon initialization", lld1.isEmpty());

		lld1.addFirst(10);
		// should not be empty
		assertFalse("lld1 should contain 1 item", lld1.isEmpty());

		lld1.removeFirst();
		// should be empty
		assertTrue("lld1 should be empty after removal", lld1.isEmpty());
    }

    @Test
    /* Tests removing from an empty deque */
    public void removeEmptyTest() {

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();
        lld1.addFirst(3);

        lld1.removeLast();
        lld1.removeFirst();
        lld1.removeLast();
        lld1.removeFirst();

        int size = lld1.size();
        String errorMsg = "  Bad size returned when removing from empty deque.\n";
        errorMsg += "  student size() returned " + size + "\n";
        errorMsg += "  actual size() returned 0\n";

        assertEquals(errorMsg, 0, size);
    }

    @Test
    /* Check if you can create LinkedListDeques with different parameterized types*/
    public void multipleParamTest() {

        LinkedListDeque<String>  lld1 = new LinkedListDeque<String>();
        LinkedListDeque<Double>  lld2 = new LinkedListDeque<Double>();
        LinkedListDeque<Boolean> lld3 = new LinkedListDeque<Boolean>();

        lld1.addFirst("string");
        lld2.addFirst(3.14159);
        lld3.addFirst(true);

        String s = lld1.removeFirst();
        double d = lld2.removeFirst();
        boolean b = lld3.removeFirst();
    }

    @Test
    /* check if null is return when removing from an empty LinkedListDeque. */
    public void emptyNullReturnTest() {

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();

        boolean passed1 = false;
        boolean passed2 = false;
        assertNull("Should return null when removeFirst is called on an empty Deque,", lld1.removeFirst());
        assertNull("Should return null when removeLast is called on an empty Deque,", lld1.removeLast());

    }

    @Test
    /* Add large number of elements to deque; check if order is correct. */
    public void bigLLDequeTest() {

        LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
        for (int i = 0; i < 1000000; i++) {
            lld1.addLast(i);
        }

        for (double i = 0; i < 500000; i++) {
            assertEquals("Should have the same value", i, (double) lld1.removeFirst(), 0.0);
        }

        for (double i = 999999; i > 500000; i--) {
            assertEquals("Should have the same value", i, (double) lld1.removeLast(), 0.0);
        }

    }

    @Test
    public void getTest() {
        LinkedListDeque<Integer> linkedListDeque = new LinkedListDeque<>();

        // 添加元素 0-8
        for (int i = 0; i <= 8; i++) {
            linkedListDeque.addLast(i);
        }

        // 测试 get() 方法
        assertEquals(5, (int)linkedListDeque.get(5));  // 第6个元素(index 5)应该是5

        // 测试 getRecursive() 方法
        assertEquals(6, (int)linkedListDeque.getRecursive(6));  // 第7个元素(index 6)应该是6
    }

    @Test
    public void testAddAndPrint() {
        LinkedListDeque<Integer> deque = new LinkedListDeque<>();
        deque.addFirst(2);
        deque.addFirst(1);
        deque.addLast(3);
        deque.addLast(4);

        assertEquals(4, deque.size());
        assertEquals((Integer) 1, deque.get(0));
        assertEquals((Integer) 4, deque.get(3));
    }

    @Test
    public void testRemoveAndIsEmpty() {
        LinkedListDeque<Integer> deque = new LinkedListDeque<>();
        deque.addLast(10);
        deque.addLast(20);

        assertFalse(deque.isEmpty());
        assertEquals((Integer) 10, deque.removeFirst());
        assertEquals((Integer) 20, deque.removeLast());
        assertTrue(deque.isEmpty());
        assertNull(deque.removeFirst());
    }

    @Test
    public void testGetRecursive() {
        LinkedListDeque<String> deque = new LinkedListDeque<>();
        deque.addLast("a");
        deque.addLast("b");
        deque.addLast("c");

        assertEquals("a", deque.getRecursive(0));
        assertEquals("c", deque.getRecursive(2));
        assertNull(deque.getRecursive(5));
    }

}
