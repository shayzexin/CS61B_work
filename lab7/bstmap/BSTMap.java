package bstmap;

import java.util.*;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V>{
    private class BSTNode {
        K key;
        V value;
        BSTNode left;
        BSTNode right;

        public BSTNode(K key, V value) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }

    private BSTNode root;
    private int size;

    public BSTMap() {
        this.root = null;
        size = 0;
    }

    public void printInOrder() {
        Iterator<K> iterator = iterator();

        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }
    }

    @Override
    public void clear() {
        size = 0;
        root = null;
    }

    private BSTNode findKeyNode(K key) {
        BSTNode current = root;
        while (current != null) {
            int cmp = current.key.compareTo(key);

            if (cmp == 0) {
                return current;
            }
            current = (cmp < 0) ? current.right : current.left;
        }
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        return findKeyNode(key) != null;
    }

    @Override
    public V get(K key) {
        BSTNode node = findKeyNode(key);
        return (node == null) ? null : node.value;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        if (root == null) {
            size = 1;
            root = new BSTNode(key, value);
            return;
        }

        BSTNode current = root;
        BSTNode parent = null;

        while (current != null) {
            parent = current;
            int cmp = current.key.compareTo(key);

            if (cmp == 0) {
                current.value = value;
                return;
            }

            current = (cmp < 0) ? current.right : current.left;
        }

        size++;
        BSTNode newNode = new BSTNode(key, value);
        int cmp = parent.key.compareTo(key);
        if (cmp < 0) {
            parent.right = newNode;
            return;
        }

        parent.left = newNode;
    }

    @Override
    public Set<K> keySet() {
        Set<K> keySet = new HashSet<>();
        if (root == null) {
            return keySet;
        }

        Deque<BSTNode> stack = new ArrayDeque<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            BSTNode current = stack.pop();
            keySet.add(current.key);

            if (current.right != null) {
                stack.push(current.right);
            }

            if (current.left != null) {
                stack.push(current.left);
            }
        }
        return keySet;
    }

    @Override
    public V remove(K key) {
        return remove(key, null);
    }

    @Override
    public V remove(K key, V value) {
        BSTNode current = root;
        BSTNode parent = null;

        while (current != null) {
            int cmp = current.key.compareTo(key);

            if (cmp == 0) {
                boolean canDelete = value == null || current.value.equals(value);
                return canDelete ? delete(current, parent) : null;
            }

            parent = current;
            current = (cmp < 0) ? current.right : current.left;
        }
        return null;
    }

    private V delete(BSTNode des, BSTNode parent) {
        V value = des.value;
        size--;

        if (des.left == null || des.right == null) {
            BSTNode child = (des.left == null) ? des.right : des.left;

            if (parent == null) {
                root = child;
                return value;
            } else if (parent.left == des) {
                parent.left = child;
            } else {
                parent.right = child;
            }
        } else {
            BSTNode successor = findMinAndDelete(des.right, des);
            des.key = successor.key;
            des.value = successor.value;
        }

        return value;
    }

    private BSTNode findMinAndDelete(BSTNode node, BSTNode parent) {
        while (node.left != null) {
            parent = node;
            node = node.left;
        }

        if (parent.right == node) {
            parent.right = node.right;
        } else {
            parent.left = node.right;
        }

        return node;
    }

    @Override
    public Iterator<K> iterator() {
        return new BSTIterator();
    }

    private class BSTIterator implements Iterator<K> {
        private final Deque<BSTNode> stack;

        public BSTIterator() {
            stack = new ArrayDeque<>();
            BSTNode current = root;
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public K next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            BSTNode node = stack.pop();
            K key = node.key;

            BSTNode current = node.right;
            while (current != null) {
                stack.push(current);
                current = current.left;
            }

            return key;
        }
    }
}
