package structures;

public interface IHashMap<E> {
    public boolean add(int key, E value);

    public E get(int key);

    public boolean isContains(int key);

    public int size();

    public boolean remove(int key);

    public int[] getAllKeys();

    public E[] getAllValues();
}
