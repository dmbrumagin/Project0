package dev.brumagin.utilitytests;

import dev.brumagin.utility.LinkedList;
import dev.brumagin.utility.Node;
import org.junit.jupiter.api.*;

public class LinkedListTest {


    @Test
    void linked_list_create_and_add_plus_size(){
        LinkedList linkedList = new LinkedList();
        linkedList.add("FirstEntry");
        linkedList.add("SecondEntry");
        linkedList.add("ThirdEntry");
        Assertions.assertEquals(linkedList.size(),3);
    }

    @Test
    void linked_list_get(){
        LinkedList linkedList = new LinkedList();
       String first = "FirstEntry";
       linkedList.add(first);
        linkedList.add("SecondEntry");
        linkedList.add("ThirdEntry");

        Assertions.assertEquals(linkedList.getNode(0).getElement(),first);
    }

    @Test
    void linked_list_remove_and_size(){
        LinkedList linkedList = new LinkedList();
        linkedList.add("FirstEntry");
        linkedList.add("SecondEntry");
        linkedList.add("ThirdEntry");
        linkedList.remove(0);
        Assertions.assertEquals(linkedList.size(),2);

    }
    @Test
    void linked_list_remove_all_and_size(){
        LinkedList linkedList = new LinkedList();
        linkedList.add("FirstEntry");
        linkedList.add("SecondEntry");
        linkedList.add("ThirdEntry");
        linkedList.remove(0);
        Node s = (Node) linkedList.remove(0);
        linkedList.remove(0);
        Assertions.assertEquals("SecondEntry",s.getElement());
    }
    @Test
    void linked_list_remove_and_get(){
        LinkedList linkedList = new LinkedList();
        String test = "stringToReturn";
        linkedList.add("FirstEntry");
        linkedList.add(test);
        linkedList.add("SecondEntry");
        linkedList.add("ThirdEntry");
        Assertions.assertEquals(linkedList.getNode(1).getElement(),test);
    }

    @Test
    void linked_list_get_element(){
        LinkedList linkedList = new LinkedList();
        String test = "stringToReturn";
        linkedList.add("FirstEntry");
        linkedList.add(test);
        linkedList.add("SecondEntry");
        linkedList.add("ThirdEntry");
        Assertions.assertEquals(linkedList.getElement(1),test);
    }

    @Test
    void linked_list_iterable_has_next(){
        LinkedList<String> linkedList = new LinkedList<>();
        String test = "stringToReturn";
        linkedList.add("FirstEntry");
        linkedList.add("SecondEntry");
        linkedList.add("ThirdEntry");
        linkedList.add("FourthEntry");
        System.out.println(linkedList);
        for (String s : linkedList){
            System.out.println(s);
            System.out.println(linkedList.size());
        }
    }

}
