package structures;

/**
 * An interface for a generic list.
 */
public interface IList1<E> {

    public boolean add(E element);
    public boolean contains(E element);
    
    // Removes an element form the list. 
    // Returns true on success, false if the element was not found.
    public boolean remove(E element);
    
    // Returns the number of elements stored sin the list.
    public int size();
    
    // Returns the element stored at position index.
    public E get(int index);
    
    // Returns the index of element in the list, returns -1 if element was not found.
    public int indexOf(E element);
    
    // Sets position index of the list to element.
    public E set(int index, E element);

    // Returns an array containing all the elements in the list. Only for int!!!!
    public int[] toIntArray();
        
}
