package dev.brumagin.utility;

public class Node<T> {
    Node thisNode;
    Node next;

    Node(){
        this.thisNode= this;
        this.next = null;
    }

}
