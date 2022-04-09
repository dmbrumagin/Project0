package dev.brumagin.utility;

public interface List <T>{

    void add(T elementToAdd);
    T remove(int i);
    Node getNode(int i);
    T getElement(int i);
    int size();

}
