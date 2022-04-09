package dev.brumagin.utility;

public class Node<T> {
    Node thisNode;
    Node next;
    T element;

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public void setElement(T element) {
        this.element = element;
    }

    public T getElement() {
        return element;
    }

    Node(){
        this.thisNode= this;
        this.next = null;
        this.element = null;
    }
    Node(T element){
        this.thisNode= this;
        this.next = null;
        this.element = element;
    }

}
