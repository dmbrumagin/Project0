package dev.brumagin.utility;


import java.util.Iterator;

public class LinkedList<T> implements List, Iterable<T> {

    Node first;
    Node last;
    int size;

    public LinkedList(){
        first= null;
        last= null;
        size = 0;
    }

    @Override
    public void add(Object elementToAdd) {

        Node node = new Node(elementToAdd);

        if(first == null)
            first = node;

        if(last != null)
            last.next = node;

        last = node;
        size++;

    }

    @Override
    public Object remove(int i) {

        Node previousObject = getNode(i-1);
        Node objectToRemove = getNode(i);

        if(previousObject != previousObject)
        previousObject.next= objectToRemove.next;
        else{
            if(first.next != null)
            first = first.next;
            else{
                first=null;
            }
        }
        size--;
        return objectToRemove;
    }

    @Override
    public Node getNode(int node) throws NullPointerException{
        Node current= first;

        for(int i = 0 ; i < node ; i++){
            current = current.next;
        }

        return current;
    }

    @Override
    public Object getElement(int node) {
        Node returnNode = getNode(node);

        return returnNode.getElement();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator iterator(Iterable iterate) {
        return iterator();
    }


    @Override
    public Iterator<T> iterator() {

        Iterator<T> it = new Iterator<T>()
        {
            private Node currentIndex = null;

            @Override
            public boolean hasNext()
            {
                if(currentIndex!=null)
                    return currentIndex.next != null;

                else{
                    return first != null;
                }
            }
            @Override
            public T next()
            {
                if(currentIndex==null){
                    currentIndex=first;
                    return (T) first.getElement();}
                currentIndex = currentIndex.next;
                return (T)currentIndex.getElement();
            }
        };
        return it;
    }

}
