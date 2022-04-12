package dev.brumagin.utility;

import java.util.Iterator;

public interface List <T>{

    void add(T elementToAdd);
    T remove(int i);
    Node getNode(int i);
    T getElement(int i);
    int size();

    Iterator iterator(Iterable iterate);
}
