package deque;

import sun.tools.tree.NaryExpression;

import static java.lang.Math.max;

public class ArrayDeque<T> implements Deque<T> {
    private T[] arrayDeque;
    private int capacity;
    private int size;
    private int head;
    private int tail;

    private final int MIN_CAPACITY = 8;

    public ArrayDeque() {
        arrayDeque = (T[]) new Object[MIN_CAPACITY];
        capacity = 8;
        size = 0;
        head = 0;
        tail = 1;
    }

    @Override
    public void addFirst(T item) {
        checkCapacity();

        if (isEmpty()) {
            addFirstElement(item);
            return;
        }

        head = circularIndex(head - 1);
        arrayDeque[head] = item;
        size++;
    }

    @Override
    public void addLast(T item) {
        checkCapacity();

        if (isEmpty()) {
            addFirstElement(item);
            return;
        }

        arrayDeque[tail] = item;
        tail = circularIndex(tail + 1);
        size++;
    }

    private void addFirstElement(T item) {
        arrayDeque[head] = item;
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
        if (isEmpty()) {
            System.out.println();
            return;
        }

        int current = head;
        for (int i = 0; i < size; i++) {
            System.out.print(arrayDeque[current]);
            if (i < size - 1) {
                System.out.print(" ");
            }
            current = circularIndex(current + 1);
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
//             throw new NoSuchElementException("ArrayDeque is empty");
            return null;
        }

        size--;
        T item = arrayDeque[head];
        arrayDeque[head] = null;
        head = circularIndex(head + 1);

        checkCapacity();
        return item;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            // throw new NoSuchElementException("ArrayDeque is empty");
            return null;
        }

        size--;
        tail = circularIndex(tail - 1);
        T item = arrayDeque[tail];
        arrayDeque[tail] = null;

        checkCapacity();
        return item;
    }

    @Override
    public T get(int index) {
        int absoluteIndex = circularIndex(head + index);
        return arrayDeque[absoluteIndex];
    }

    private void resize(int capacity) {
        capacity = max(MIN_CAPACITY, capacity);
        T[] newArrayDeque = (T[]) new Object[capacity];

        for (int i = 0; i < size; i++) {
            newArrayDeque[i] = arrayDeque[circularIndex(head + i)];
        }

        this.capacity = capacity;
        this.head = 0;
        this.tail = size;

        arrayDeque = newArrayDeque;
    }

    private void checkCapacity() {
        if (size == capacity) {
            resize(2 * capacity);
            return;
        }

        double loadFactor = (double) size / capacity;
        double valve = 0.25;

        if (capacity >= 16 && loadFactor < valve) {
            resize(capacity / 2);
        }
    }

    private int circularIndex(int index) {
        return (index % capacity + capacity) % capacity;
    }
}
