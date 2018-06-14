/*@Riku Fukumori
  Class which extends DoublyLinkedList
  and makes use of dummy nodes in list's
  head and tail to make code shorter and more succinct.*/

import structure5.Assert;
import structure5.DoublyLinkedList;
import structure5.DoublyLinkedNode;
import structure5.DoublyLinkedListIterator;
import java.util.Iterator;

public class LinkedList<E> extends DoublyLinkedList<E>{
    /**
     * Number of elements within the list.
     */
    protected int count;
    /**
     * Reference to head of the list.
     */
    protected DoublyLinkedNode<E> head;
    /**
     * Reference tail of the list.
     */
    protected DoublyLinkedNode<E> tail;

    /**
     * Constructs an empty list of two dummy nodes head and tail whose values are null.
     *
     * @post constructs an empty list
     *
     */
    public LinkedList(){
        head  = new DoublyLinkedNode<E>(null);
        tail = new DoublyLinkedNode<E>(null);
	head.setNext(tail);
	tail.setPrevious(head);
	count = 0;

    }
    /**
     * Determine the number of elements in the list.
     *
     *@post returns the number of elements in list.
     *
     * @return the number of elements found in the list.
     */

    public int size()
    {
	return count ;
    }

    /**
     * Determine if the list is empty.
     *
     * @post returns true iff the list has no elements.
     *
     * @return True iff list has no values.
     */
    public boolean isEmpty()
    {
	return size() == 0;
    }

    /**
     * Remove all values from list by having the dummy nodes point to each other instead of .
     *to any other nodes inbetween.
     * @post removes all the elements from the list
     */
    public void clear()
    {
	head.setNext(tail);
	tail.setPrevious(head);
	count = 0;
    }

    /**
     * A private routine to add an element after a node.
     * @param value the value to be added
     * @param previous the node the come before the one holding value
     * @pre previous is non null
     * @post list contains a node following previous that contains value
     */
    protected void insertAfter(E value, DoublyLinkedNode<E> previous)
    {
	DoublyLinkedNode newNext = previous.next();
	//creates a new node which has pointers to the previous nodes on either side
	DoublyLinkedNode insertedNode = new DoublyLinkedNode(value,newNext,previous);
	//set original nodes to point to new node
	previous.setNext(insertedNode);
	newNext.setPrevious(insertedNode);
	count++;
    }

    /**
     * A private routine to remove a node.
     * @param node the node to be removed
     * @pre node is non null
     * @post node node is removed from the list
     * @return the value of the node removed
     */
    protected E remove(DoublyLinkedNode<E> node)
    {
	//get nodes on either side of node that will be removed
	DoublyLinkedNode previous = node.previous();
	DoublyLinkedNode next = node.next();
	//set pointers of these nodes to point to each other
	previous.setNext(next);
	next.setPrevious(previous);
	count--;
	return node.value();
    }


    /**
     * Add a value to the head of the list.
     *
     * @pre value is not null
     * @post adds element to head of list
     *
     * @param value The value to be added.
     */
    public void addFirst(E value)
    {
	//to add first element, element should be inserted after dummy node head
	this.insertAfter(value,head);
    }

    /**
     * Add a value to the tail of the list.
     *
     * @pre value is not null
     * @post adds new value to tail of list
     *
     * @param value The value to be added.
     */
    public void addLast(E value)
    {
	//the last element should be added right before the tail dummy node
	this.insertAfter(value,tail.previous());
    }

    /**
     * Remove a value from the head of the list.
     * Value is returned.
     *
     * @pre list is not empty
     * @post removes first value from list
     *
     * @return The value removed from the list.
     */
    public E removeFirst()
    {
	//remove the node after the head to remove first node
	this.remove(head.next());
	return head.next().value();
    }

    /**
     * Remove a value from the tail of the list.
     *
     * @pre list is not empty
     * @post removes value from tail of list
     *
     * @return The value removed from the list.
     */
    public E removeLast()
    {
	//remove node right before tail dummy node to remove last element
	this.remove(tail.previous());
	return tail.previous().value();
    }

    /**
     * Get a copy of the first value found in the list.
     *
     * @pre list is not empty
     * @post returns first value in list.
     *
     * @return A reference to first value in list.
     */
    public E getFirst()
    {
	//call DoublyLinkedNode method value() on element after head dummy node
	return head.next().value();
    }

    /**
     * Get a copy of the last value found in the list.
     *
     * @pre list is not empty
     * @post returns last value in list.
     *
     * @return A reference to the last value in the list.
     */
    public E getLast()
    {
	//call DoublyLinkedNode method value() on element before tail dummy node
	return tail.previous().value();
    }

    /**
     * Insert the value at location.
     *
     * @pre 0 <= i <= size()
     * @post adds the ith entry of the list to value o
     * @param i the index of this new value
     * @param o the the value to be stored
     */
    public void add(int i, E o)
    {
	//call addFirst() method if location is at the head
	if (i == 0) addFirst(o);
	//call addLast() method if location is at the tail
	else if (i == size()) addLast(o);
	else {
	    //go through list to find the node associated with location
	    DoublyLinkedNode<E> finger = head.next();
	    while (i > 1){
		finger = finger.next();
		i--;
	    }
	    //once node is found, call insertAfter() method on that node
	    this.insertAfter(o,finger);
	}
    }

    /**
     * Remove and return the value at location i.
     *
     * @pre 0 <= i < size()
     * @post removes and returns the object found at that location.
     *
     * @param i the position of the value to be retrieved.
     * @return the value retrieved from location i (returns null if i invalid)
     */
    public E remove(int i)
    {
	//if element is head, call removeFirst() method which will remove first value after
	//dummy node
	if (i == 0) return removeFirst();
	//if element is tail, call removeLast() method
	else if (i == size()) return removeLast();
	//search through list to find node associated with location trying to find
	DoublyLinkedNode<E> finger = head.next();
	while (i > 0){
		finger = finger.next();
		i--;
	}
	//once node is found call remove() method to remove that node
	this.remove(finger);
	// finger's value is old value, return it
	return finger.value();
    }

    /**
     * Get the value at location i.
     *
     * @pre 0 <= i < size()
     * @post returns the object found at that location.
     *
     * @param i the position of the value to be retrieved.
     * @return the value retrieved from location i (returns null if i invalid)
     */
    public E get(int i)
    {
	DoublyLinkedNode<E> finger = head.next();
	// search for the ith element or end of list
	while (i > 0)
	    {
		finger = finger.next();
		i--;
	    }

	// not end of list, return the value found
	return finger.value();
    }

    /**
     * Set the value stored at location i to object o, returning the old value.
     *
     * @pre 0 <= i < size()
     * @post sets the ith entry of the list to value o, returns the old value.
     * @param i the location of the entry to be changed.
     * @param o the new value
     * @return the former value of the ith entry of the list.
     */
    public E set(int i, E o)
    {
	DoublyLinkedNode<E> finger = head.next();
	// search for the ith element or the end of the list
	while (i > 0)
	    {
		finger = finger.next();
		i--;
	    }
	// get old value, update new value
	E result = finger.value();
	finger.setValue(o);
	return result;
    }

    /**
     * Determine the first location of a value in the list.
     *
     * @pre value is not null
     * @post returns the (0-origin) index of the value,
     *   or -1 if the value is not found
     *
     * @param value The value sought.
     * @return the index (0 is the first element) of the value, or -1
     */
    public int indexOf(E value)
    {
	int i = 0;
	DoublyLinkedNode<E> finger = head.next();
	// search for value or end of list, counting along the way
	while (finger != tail && !finger.value().equals(value))
	    {
		finger = finger.next();
		i++;
	    }
	// finger points to value, i is index
	if (finger == tail)
	    {   // value not found, return indicator
		return -1;
	    } else {
	    // value found, return index
	    return i;
	}
    }

    /**
     * Determine the last location of a value in the list.
     *
     * @pre value is not null
     * @post returns the (0-origin) index of the value,
     *   or -1 if the value is not found
     *
     * @param value the value sought.
     * @return the index (0 is the first element) of the value, or -1
     */
    public int lastIndexOf(E value)
    {
	int i = size()-1;
	DoublyLinkedNode<E> finger = tail.previous();
	// search for the last matching value, result is desired index
	while (finger != head && !finger.value().equals(value))
	    {
		finger = finger.previous();
		i--;
	    }
	if (finger == head)
	    {   // value not found, return indicator
		return -1;
	    } else {
	    // value found, return index
	    return i;
	}
    }

    /**
     * Check to see if a value is within the list.
     *
     * @pre value not null
     * @post returns true iff value is in the list
     *
     * @param value A value to be found in the list.
     * @return True if value is in list.
     */
    public boolean contains(E value)
    {
	//call method indexOf() to go through list to find if value exists and if not returns
	//-1
	return this.indexOf(value) != -1;
    }

    /**
     * Remove a value from the list.  At most one value is removed.
     * Any duplicates remain.  Because comparison is done with "equals,"
     * the actual value removed is returned for inspection.
     *
     * @pre value is not null.  List can be empty.
     * @post first element matching value is removed from list
     *
     * @param value The value to be removed.
     * @return The value actually removed.
     */
    public E remove(E value)
    {
	//goes through list to look for node associated with value to remvove
	DoublyLinkedNode<E> finger = head.next();
	while (finger != tail &&
	       !finger.value().equals(value))
	    {
		finger = finger.next();
	    }
	//once found, call remove() method to remove node associated with value
	if (finger != tail)
	    {
		this.remove(finger);
	}
	return null;
    }

    /**
     * Construct an iterator to traverse the list.
     *
     * @post returns iterator that allows the traversal of list.
     *
     * @return An iterator that traverses the list from head to tail.
     */
    public Iterator<E> iterator()
    {
	return new DoublyLinkedListIterator<E>(head,tail);
    }

    /**
     * Construct a string representation of the list.
     *
     * @post returns a string representing list
     *
     * @return A string representing the elements of the list.
     */
    public String toString()
    {
	//uses iterator method to create string representation of list
	StringBuffer s = new StringBuffer();
	s.append("<LinkedList:");
	Iterator<E> li = iterator();
	while (li.hasNext())
	    {
		s.append(" "+li.next());
	    }
	s.append(">");
	return s.toString();
    }
}
