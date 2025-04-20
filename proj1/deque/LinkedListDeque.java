package deque;

import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedListDeque<T> implements Deque<T> {
    class Node {
        private T item;
        private Node prev;
        private Node next;

        private Node(T item, Node prev, Node next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }

        private Node() {
            this.item = null;
            this.prev = null;
            this.next = null;
        }
    }

    private final Node sentinel;
    private int size;

    public LinkedListDeque() {
        size = 0;
        sentinel = new Node();
        sentinel.prev = sentinel.next = sentinel;
    }

    @Override
    public void addFirst(T item) {
        Node newNode = new Node(item, sentinel, sentinel.next);
        sentinel.next.prev = newNode;
        sentinel.next = newNode;
        size++;
    }

    @Override
    public void addLast(T item) {
        Node newNode = new Node(item, sentinel.prev, sentinel);
        sentinel.prev.next = newNode;
        sentinel.prev = newNode;

        size++;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        Node current = sentinel.next;
        while (current != sentinel) {
            System.out.print(current.item + " ");
            current = current.next;
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        Node tmp = sentinel.next;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;

        size = (size == 0) ? size : size - 1;
        return (T) tmp.item;
    }

    @Override
    public T removeLast() {
        Node tmp = sentinel.prev;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;

        size = (size == 0) ? size : size - 1;
        return (T) tmp.item;
    }

    @Override
    public T get(int index) {
        Node current = sentinel.next;
        while (current != sentinel && index > 0) {
            current = current.next;
            index--;
        }
        return (index == 0) ? (T) current.item : null;
    }

    public T getRecursive(int index) {
        return getRecursiveHelper(index, sentinel.next);
    }

    private T getRecursiveHelper(int index, Node current) {
        if (current == sentinel) {
            return null;
        }
        if (index == 0) {
            return (T) current.item;
        }
        return getRecursiveHelper(index - 1, current.next);
    }

    public Iterator<T> iterator() {
        return new LinkedListDequeIterator();
    }

    private class LinkedListDequeIterator implements Iterator<T> {
        private Node ptr;
        LinkedListDequeIterator() {
            ptr = sentinel.next;
        }

        @Override
        public boolean hasNext() {
            return (ptr != sentinel);
        }

        @Override
        public T next() {
            T item = (T) ptr.item;
            ptr = ptr.next;
            return item;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Deque)) {
            return false;
        }

        Deque other = (Deque) o;
        if (size != other.size()) {
            return false;
        }

        Node current = sentinel.next;
        for (int i = 0; i < size; i++) {
            if (!current.item.equals(other.get(i))) {
                return false;
            }
            current = current.next;
        }
        return true;
    }
}
