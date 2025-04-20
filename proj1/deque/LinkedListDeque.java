package deque;

import java.util.NoSuchElementException;

public class LinkedListDeque<T> implements Deque<T> {
    static class Node<E> {
        private E item;
        private Node<E> prev;
        private Node<E> next;

        private Node(E item, Node<E> prev, Node<E> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }

        private Node() {
            this.item = null;
            this.prev = this;
            this.next = this;
        }
    }

    private final Node<T> sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new Node<>();
        size = 0;
    }

    @Override
    public void addFirst(T item) {
        if (isEmpty() || getHead() == null) {
            addFirstNodeOfList(item);
            return;
        }

        Node<T> headNode = getHead();
        Node<T> newNode = new Node<>(item, sentinel, headNode);

        sentinel.prev = newNode;
        headNode.prev = newNode;
        size++;
    }

    @Override
    public void addLast(T item) {
        if (isEmpty() || getTail() == null) {
            addFirstNodeOfList(item);
            return;
        }

        Node<T> tailNode = getTail();
        Node<T> newNode = new Node<>(item, tailNode, sentinel);

        sentinel.next = newNode;
        tailNode.next = newNode;
        size++;
    }

    private void addFirstNodeOfList(T item) {
        Node<T> newNode = new Node<>(item, sentinel, sentinel);
        sentinel.prev = newNode;
        sentinel.next = newNode;

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
        if (isEmpty() || getHead() == null) {
            System.out.println();
            return;
        }

        Node<T> current = getHead();

        while (current != sentinel) {
            System.out.print(current.item);
            current = current.next;

            if (current != sentinel) {
                System.out.print(" ");
            }
        }

        System.out.println();
    }

    @Override
    public T removeFirst() {
        if (isEmpty() || getHead() == null) {
//            throw new NoSuchElementException("LinkedListDeque is empty");
            return null;
        }

        if (size == 1) {
            return removeLastNodeOfList();
        }

        Node<T> headNode = getHead();
        T item = headNode.item;

        sentinel.prev = headNode.next;
        headNode.next.prev = sentinel;

        headNode.prev = null;
        headNode.next = null;
        headNode.item = null;

        size--;
        return item;
    }

    @Override
    public T removeLast() {
        if (isEmpty() || getTail() == null) {
//            throw new NoSuchElementException("LinkedListDeque is empty");
            return null;
        }

        if (size == 1) {
            return removeLastNodeOfList();
        }

        Node<T> tailNode = getTail();
        T item = tailNode.item;

        sentinel.next = tailNode.prev;
        tailNode.prev.next = sentinel;

        tailNode.prev = null;
        tailNode.next = null;
        tailNode.item = null;

        size--;
        return item;
    }

    private T removeLastNodeOfList() {
        Node<T> theLastNode = getHead();

        if (theLastNode == null) {
            throw new NoSuchElementException("LinkedListDeque is empty");
        }

        T item = theLastNode.item;

        sentinel.prev = sentinel;
        sentinel.next = sentinel;

        theLastNode.prev = null;
        theLastNode.next = null;
        theLastNode.item = null;

        size--;
        return item;
    }

    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", size: " + size);
        }

        Node<T> current = getHead();

        for (int i = 0; i < index; i++) {
            if (current == null) {
                throw new IndexOutOfBoundsException("Index: " + index + ", size: " + size);
            }
            current = current.next;
        }

        if (current == null) {
            throw new RuntimeException();
        }
        return current.item;
    }

    public T getRecursive(int index) {
        if (index >= size || index < 0) {
//            throw new IndexOutOfBoundsException("Index: " + index + ", size: " + size);
            return null;
        }

        return getRecursiveHelper(index, sentinel.prev);
    }

    private T getRecursiveHelper(int index, Node<T> current) {
        if (index == 0) {
            return current.item;
        }
        return getRecursiveHelper(index - 1, current.next);
    }

    private Node<T> getHead() {
        return isEmpty() ? null : sentinel.prev;
    }

    private Node<T> getTail() {
        return isEmpty() ? null : sentinel.next;
    }
}
