package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> implements Deque<T> {
    private ArrayDeque<T> internalDeque;
    private Comparator<T> defaultComparator;

    public MaxArrayDeque(Comparator<T> c) {
        internalDeque = new ArrayDeque<>();
        this.defaultComparator = c;
    }

    public T max() {
        return max(defaultComparator);
    }

    public T max(Comparator<T> c) {
        if (isEmpty()) {
            return null;
        }

        T maxItem = get(0);
        for (int i = 1; i < size(); i++) {
            T item = get(i);
            if (c.compare(item, maxItem) > 0) {
                maxItem = item;
            }
        }

        return maxItem;
    }

    @Override
    public void addFirst(T item) {
        internalDeque.addFirst(item);
    }

    @Override
    public void addLast(T item) {
        internalDeque.addLast(item);
    }

    @Override
    public boolean isEmpty() {
        return internalDeque.isEmpty();
    }

    @Override
    public int size() {
        return internalDeque.size();
    }

    @Override
    public void printDeque() {
        internalDeque.printDeque();
    }

    @Override
    public T removeFirst() {
        return internalDeque.removeFirst();
    }

    @Override
    public T removeLast() {
        return internalDeque.removeLast();
    }

    @Override
    public T get(int index) {
        return internalDeque.get(index);
    }
}
