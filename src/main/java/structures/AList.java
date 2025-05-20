package structures;

public class AList<E> implements IList1<E> {

    private Object[] array;
    private int size;
    private int capacity;
    
    public AList() {
        this.capacity = 100;
        this.array = new Object[capacity];
        this.size = 0;
    }
    
    public boolean add(E element) {
        if (this.size == this.capacity) {
            this.capacity *= 2;
            Object[] newArray = new Object[this.capacity];
            for (int i=0;i<this.size;i++) {
                newArray[i] = this.array[i];
            }
            this.array = newArray;
        }
        this.array[this.size] = element;
        this.size++;
        return true;
    }

    public boolean contains(E element) {
        for (int i = 0; i < size; i++) {
            if (element.equals(this.array[i])) {return true;}
        }
        return false;
    }

    public int size() {
        return size;
    }
    
    // This line allows us to cast our object to type (E) without any warnings.
    // For further detais, please see: http://docs.oracle.com/javase/1.5.0/docs/api/java/lang/SuppressWarnings.html
    @SuppressWarnings("unchecked") 
    public E get(int index) {
        return (E) this.array[index];
    }
    
    public int indexOf(E element) {
        for (int i = 0; i < this.size; i++) {
            if (element.equals(this.array[i])) {return i;}
        }
        return -1;
    }

    public boolean remove(E element) {
        int index = this.indexOf(element);
        if (index >= 0) {
            for (int i=index+1;i<this.size();i++) {
                this.set(i-1, this.get(i));
            }
            this.array[size-1] = null;
            size--;
            return true;
        }
        return false;
    }

    public E set(int index, E element) {
        if (index >= 0 && index < this.size) {
            E tmp = this.get(index);
            this.array[index] = element;
            return tmp;
        }
        return null;
    }
    
    public int[] toIntArray() {
            int[] result =  new int[this.size];
        for (int i=0;i<this.size;i++) {
            result[i] = (int) this.get(i);
        }
        return result;
    }

}
