package deque;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Comparator;

public class MaxArrayDequeTest {

    // 比较整数大小
    private static class IntComparator implements Comparator<Integer> {
        public int compare(Integer a, Integer b) {
            return a - b;
        }
    }

    // 比较字符串长度
    private static class LengthComparator implements Comparator<String> {
        public int compare(String a, String b) {
            return a.length() - b.length();
        }
    }

    // 字典序比较
    private static class LexComparator implements Comparator<String> {
        public int compare(String a, String b) {
            return a.compareTo(b);
        }
    }

    @Test
    public void testMaxWithIntegers() {
        MaxArrayDeque<Integer> mad = new MaxArrayDeque<>(new IntComparator());

        mad.addLast(10);
        mad.addLast(20);
        mad.addLast(15);

        assertEquals((Integer) 20, mad.max());
    }

    @Test
    public void testMaxEmptyDeque() {
        MaxArrayDeque<Integer> mad = new MaxArrayDeque<>(new IntComparator());
        assertNull(mad.max());
    }

    @Test
    public void testMaxWithStrings_LengthVsLex() {
        MaxArrayDeque<String> mad = new MaxArrayDeque<>(new LengthComparator());

        mad.addLast("hi");
        mad.addLast("hello");
        mad.addLast("world");

        // 默认用长度比较
        assertEquals("hello", mad.max());

        // 用字典序比较
        assertEquals("world", mad.max(new LexComparator()));
    }

    @Test
    public void testMaxAfterRemovals() {
        MaxArrayDeque<Integer> mad = new MaxArrayDeque<>(new IntComparator());

        mad.addLast(5);
        mad.addLast(10);
        mad.addLast(7);
        mad.removeLast(); // remove 7

        assertEquals((Integer) 10, mad.max());

        mad.removeLast(); // remove 10
        assertEquals((Integer) 5, mad.max());

        mad.removeFirst(); // remove 5
        assertNull(mad.max());
    }
}
