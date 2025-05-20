package structures;

public class MyHashMap<E> implements IHashMap<E> {
    // 使用更合理的初始容量
    private static final int INITIAL_CAPACITY = 1024;

    // 负载因子，当元素数量超过容量*负载因子时进行扩容
    private static final float LOAD_FACTOR = 0.75f;

    // 存储元素的节点类，包含key和value
    private static class Node<E> {
        int key;
        E value;
        Node<E> next;

        Node(int key, E value, Node<E> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    private Node<E>[] table;
    private int size;
    private int threshold; // 扩容阈值

    @SuppressWarnings("unchecked")
    public MyHashMap() {
        table = (Node<E>[]) new Node[INITIAL_CAPACITY];
        size = 0;
        threshold = (int) (INITIAL_CAPACITY * LOAD_FACTOR);
    }

    // 根据key确定数组索引
    private int indexFor(int key, int length) {
        // 确保索引为正数且在数组范围内
        return Math.abs(key % length);
    }

    @Override
    public boolean add(int key, E value) {
        int i = indexFor(key, table.length);

        // 检查是否已存在相同的key
        for (Node<E> e = table[i]; e != null; e = e.next) {
            if (e.key == key) {
                // 键已存在，不添加
                return false;
            }
        }

        // 添加新节点到链表头部
        table[i] = new Node<>(key, value, table[i]);
        size++;

        // 检查是否需要扩容
        if (size > threshold) {
            resize(2 * table.length);
        }

        return true;
    }

    /**
     * 扩容哈希表
     * 
     * @param newCapacity 新的容量
     */
    @SuppressWarnings("unchecked")
    private void resize(int newCapacity) {
        Node<E>[] oldTable = table;
        int oldCapacity = oldTable.length;

        // 创建新的哈希表
        Node<E>[] newTable = (Node<E>[]) new Node[newCapacity];

        // 重新哈希所有元素
        for (int i = 0; i < oldCapacity; i++) {
            Node<E> e = oldTable[i];
            while (e != null) {
                Node<E> next = e.next;

                // 计算新的索引
                int index = indexFor(e.key, newCapacity);

                // 将节点插入到新哈希表中
                e.next = newTable[index];
                newTable[index] = e;

                e = next;
            }
        }

        table = newTable;
        threshold = (int) (newCapacity * LOAD_FACTOR);
    }

    @Override
    public E get(int key) {
        int i = indexFor(key, table.length);

        // 遍历链表查找元素
        for (Node<E> e = table[i]; e != null; e = e.next) {
            if (e.key == key) {
                return e.value;
            }
        }
        return null;
    }

    @Override
    public boolean isContains(int key) {
        int i = indexFor(key, table.length);

        for (Node<E> e = table[i]; e != null; e = e.next) {
            if (e.key == key) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean remove(int key) {
        int i = indexFor(key, table.length);

        // 如果是链表头
        Node<E> prev = null;
        for (Node<E> e = table[i]; e != null; e = e.next) {
            if (e.key == key) {
                if (prev == null) {
                    table[i] = e.next;
                } else {
                    prev.next = e.next;
                }
                size--;
                return true;
            }
            prev = e;
        }

        return false;
    }

    /**
     * 获取所有存储的键
     * 
     * @return 包含所有键的数组
     */
    public int[] getAllKeys() {
        int[] keys = new int[size];
        int index = 0;

        for (int i = 0; i < table.length; i++) {
            for (Node<E> e = table[i]; e != null; e = e.next) {
                keys[index++] = e.key;
            }
        }

        return keys;
    }

    /**
     * 获取所有存储的值
     * 
     * @return 包含所有值的数组
     */
    @SuppressWarnings("unchecked")
    public E[] getAllValues() {
        E[] values = (E[]) new Object[size];
        int index = 0;

        for (int i = 0; i < table.length; i++) {
            for (Node<E> e = table[i]; e != null; e = e.next) {
                values[index++] = e.value;
            }
        }

        return values;
    }
}
